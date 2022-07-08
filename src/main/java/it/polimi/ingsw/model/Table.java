package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exception.EmptyTableException;
import it.polimi.ingsw.model.exception.FullTableException;

import java.io.Serializable;

public class Table implements Serializable {
    private final int NUMBEROFSEATS = 10;
    private final PawnColor color;
    private final Student[] students;
    private boolean professor;

    /**
     * Creates a new table of the color passed as parameter
     * @param color color of the new created table
     */
    public Table(PawnColor color) {
        this.color = color;
        students = new Student[NUMBEROFSEATS];
        professor = false;
    }

    /**
     * Returns the color of the students and the professor which can be put on the table
     * @return color of the table
     */
    public PawnColor getColor() {
        return color;
    }

    /**
     * Returns the value of the professor.
     * The value is true if the professor is on the table and is false if the table doesn't contain the professor
     * @return value of the professor
     */
    public boolean hasProfessor() {
        return professor;
    }

    /**
     * Returns the number of students the table contains
     * @return number of students the table contains
     */
    public int getNumberOfStudents() {
        int i;
        for (i = 0; i < NUMBEROFSEATS ; i++) {
            if (students[i] == null) break;
        }
        return i;
    }

    /**
     * Sets the value of the professor to the value passed as parameter.
     * The true value means the professor is put on the table and the false value means the professor is removed
     * @param value value to set to professor
     */
    public void setProfessor(boolean value) {
        this.professor = value;
    }

    /**
     * Adds a student on the table
     * @param newStudent student to add on the table
     * @throws FullTableException if there are no more empty seats on the table, the student cannot be added
     */
    public void addStudent(Student newStudent) throws FullTableException {
        if (students.length <= getNumberOfStudents()) throw new FullTableException(newStudent);
        for (int i = 0; i < NUMBEROFSEATS; i++) {
            if (students[i] == null) {
                students[i] = newStudent;
                break;
            }
        }
    }

    /**
     * Removes a student from the table
     * @return the student removed from the table
     * @throws EmptyTableException if there are no more students on the table
     */
    public Student removeStudentFromTable() throws EmptyTableException {
        Student removedStudent = null;
        if (getNumberOfStudents() <= 0) throw new EmptyTableException();
        for (int i = 0; i < NUMBEROFSEATS; i++) {
            if (students[i] == null) {
                removedStudent = students[--i];
                students[i] = null;
                if (i == 0) setProfessor(false);
                break;
            }
        }
        return removedStudent;
    }

}
