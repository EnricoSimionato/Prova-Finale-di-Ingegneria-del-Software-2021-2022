package it.polimi.ingsw.model.exception;

import it.polimi.ingsw.model.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * Notifies that the bag is empty.
 * If the bag contains some students but not enough the Exception contains the students drawn
 */
public class EmptyBagException extends Exception {
    private List<Student> drawnStudents;

    /**
     * Constructs an exception containing the drawn students
     * @param drawnStudents students drawn from the bag when the exception has been thrown
     */
    public EmptyBagException(List<Student> drawnStudents) {
        super();
        this.drawnStudents = new ArrayList<>();
        this.drawnStudents.addAll(drawnStudents);
    }

    /**
     * Returns the students drawn from the bag when the exception has been thrown
     * @return students drawn from the bag when the exception has been thrown
     */
    public List<Student> getDrawnStudents() {
        return drawnStudents;
    }
}
