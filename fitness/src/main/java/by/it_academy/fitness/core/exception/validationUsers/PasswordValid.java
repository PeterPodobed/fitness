package by.it_academy.fitness.core.exception.validationUsers;

import by.it_academy.fitness.core.exception.ErrorField;
import by.it_academy.fitness.core.exception.MultipleErrorResponse;
import by.it_academy.fitness.core.exception.SingleErrorResponse;

public class PasswordValid {
    public static final String PASSWORD_PATTERN = "^(?=.*\\d)(?=.*[a-zA-Z])(?=.*\\W).{8,20}$";

    public static void validate(MultipleErrorResponse errorResponse, String password) {
        if (password == null || password.isBlank()) {
            errorResponse.add(new ErrorField("empty", "password"));
        }
        if (password != null && password.matches(PASSWORD_PATTERN)) {
            errorResponse.add(new ErrorField("invalid password format", "password"));
        }
    }

    public static void validate(String password) throws SingleErrorResponse {
        if (password == null || password.isBlank()) {
            throw new SingleErrorResponse("IllegalArgument", "empty");
        }
        if (password.matches(PASSWORD_PATTERN)) {
            throw new SingleErrorResponse("InvalidFormat", "invalid password format");
        }
    }
}
