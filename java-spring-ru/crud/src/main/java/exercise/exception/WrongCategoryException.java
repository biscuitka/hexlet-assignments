package exercise.exception;

public class WrongCategoryException extends RuntimeException {
    public WrongCategoryException(String message) {
        super(message);
    }
}