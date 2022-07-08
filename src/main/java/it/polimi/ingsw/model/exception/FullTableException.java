package it.polimi.ingsw.model.exception;

import it.polimi.ingsw.model.Student;

/**
 * Notifies the table is full and that cannot be added any students.
 * Contains the student which triggered the exception
 */
public class FullTableException extends Exception {
    private Student tooManyStudent;

    /**
     * Constructs the exception containing the student which triggered the exception
     * @param tooManyStudent student which triggered the exception
     */
    public FullTableException(Student tooManyStudent) {
        super();
        this.tooManyStudent = tooManyStudent;
    }

    /**
     * Returns the student which triggered the exception
     * @return student which triggered the exception
     */
    public Student getTooManyStudent() {
        return tooManyStudent;
    }
}
