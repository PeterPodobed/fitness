package by.it_academy.fitness.core.exception.validationUsers;

import by.it_academy.fitness.core.exception.ErrorField;
import by.it_academy.fitness.core.exception.MultipleErrorResponse;
import by.it_academy.fitness.core.exception.SingleErrorResponse;

public class FioValid {
    public static void validate(MultipleErrorResponse errorResponse, String fio) {
        if (fio == null || fio.isBlank()) {
            errorResponse.add(new ErrorField("empty", "fio"));
        }
    }

    public static void validate(String fio) throws SingleErrorResponse {
        if (fio == null || fio.isBlank()) {
            throw new SingleErrorResponse("empty", "fio");
        }
    }
}
