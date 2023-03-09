package by.it_academy.fitness.core.exception.validationUsers;

import by.it_academy.fitness.core.exception.ErrorField;
import by.it_academy.fitness.core.exception.MultipleErrorResponse;
import by.it_academy.fitness.core.exception.SingleErrorResponse;

public class EmailValid {
    public final static String EMAIL_PATTERN = "^((?:[\\w-]\\.?)+)@((?:[\\w-]+\\.)+)([A-Za-z]{2,4})";

    public static void validate(MultipleErrorResponse errorResponse, String mail) {
        if (mail == null || mail.isBlank()) {
            errorResponse.add(new ErrorField("empty", "mail"));
        }
        if (mail != null && !mail.matches(EMAIL_PATTERN)) {
            errorResponse.add(new ErrorField("invalid email format", "mail"));
        }
    }

    public static void validate(String mail) throws SingleErrorResponse {
        if (mail.isBlank()) {
            throw new SingleErrorResponse("empty", "mail");
        }
        if (!mail.matches(EMAIL_PATTERN)) {
            throw new SingleErrorResponse("invalid email format", "mail");
        }
    }
}
