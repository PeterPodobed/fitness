package by.it_academy.fitness.core.exception;

public class UserMessage extends IllegalArgumentException{
    public UserMessage() {
    }

    public UserMessage(String message) {
        super(message);
    }
}
