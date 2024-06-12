package exercise.exception;

public class WrongAuthorException extends RuntimeException {
    public WrongAuthorException(String message) {
        super(message);
    }
}