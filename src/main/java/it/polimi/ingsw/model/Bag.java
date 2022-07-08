package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exception.EmptyBagException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Bag implements Serializable {
    private final List<Student> students;

    /**
     * Creates a new bag instance. It is filled with 26 students for each color
     */
    public Bag() {
        students = new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            students.add(new Student(PawnColor.YELLOW));
            students.add(new Student(PawnColor.BLUE));
            students.add(new Student(PawnColor.GREEN));
            students.add(new Student(PawnColor.RED));
            students.add(new Student(PawnColor.PINK));
        }
    }

    /**
     * Draws a certain amount of students from the bag so the bag removes the students from itself and returns them to the caller.
     * The method doesn't throw an EmptyBagException because it is assumed that it is called just one time at the beginning, with a low numberOfStudents value
     * @param numberOfStudents number of students to draw
     * @param color color of the students that has to be drawn
     * @return list of students required
     */
    public List<Student> drawStudentsByColor(int numberOfStudents, PawnColor color) {
        List<Student> returnStudents = new ArrayList<>();
        for (int i = 0; i < numberOfStudents; i++) {
            for (int j = 0; j < students.size(); j++) {
                if (students.get(j).getColor() == color) {
                    returnStudents.add(students.remove(j));
                    break;
                }
            }
        }
        return returnStudents;
    }

    /**
     * Draws a certain amount of students from the bag so the bag remove the students from itself and return them to the caller
     * @param numberOfStudents number of students to draw
     * @return list of students required
     * @throws EmptyBagException if the bag doesn't contain the required amount of students
     */
    public List<Student> drawStudents(int numberOfStudents) throws EmptyBagException {
        List<Student> drawnStudents = new ArrayList<>();
        if (numberOfStudents > students.size()) {
            List<Student> returnedStudents = new ArrayList<>();
            returnedStudents.addAll(students);
            students.removeAll(students);
            throw new EmptyBagException(returnedStudents);
        }
        for (int i = 0; i < numberOfStudents; i++) {
            int index = new Random().nextInt(students.size());
            drawnStudents.add(students.remove(index));
        }
        return drawnStudents;
    }

    /**
     * Inserts every student of the list in the bag
     * @param newStudents students to insert in the bag
     */
    public void addStudents(List<Student> newStudents){
        students.addAll(newStudents);
    }

}