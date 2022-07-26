package it.polimi.ingsw.model.exception;

public class InconsistentStateException extends Exception {
    private String className;
    private String methodName;
    public InconsistentStateException(String message, String className, String methodName) {
        super(message);
        this.className = className;
        this.methodName = methodName;
    }

    public String getClassName() {
        return className;
    }

    public String getMethodName() {
        return methodName;
    }
}
