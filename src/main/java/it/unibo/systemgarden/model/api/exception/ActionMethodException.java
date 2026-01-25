package it.unibo.systemgarden.model.api.exception;


/**
 * Custom Exception thrown when an error occurs during the execution of an action method.
 */
public class ActionMethodException extends Exception {
    
    public ActionMethodException(String message) {
        super(message);
    }
}
