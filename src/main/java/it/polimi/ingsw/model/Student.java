package it.polimi.ingsw.model;

import java.io.Serializable;

public class Student implements Serializable {
    private final PawnColor color;

    /**
     * Creates a student whose color is the color passed as parameter
     * @param color color of the new student
     */
    public Student(PawnColor color) {
        this.color = color;
    }

    /**
     * Returns the color of the student
     * @return color of the student
     */
    public PawnColor getColor() {
        return color;
    }

    /**
     * Returns true if the object is equal to o. it means the two students has the same color
     * @param o object to be compared to the instance
     * @return true, if the objects are equal, false, if they are not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return color == student.color;
    }
}
