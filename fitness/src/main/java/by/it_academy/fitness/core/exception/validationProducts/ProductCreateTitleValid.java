package by.it_academy.fitness.core.exception.validationProducts;

import by.it_academy.fitness.core.exception.ErrorField;
import by.it_academy.fitness.core.exception.MultipleErrorResponse;

public class ProductCreateTitleValid {

    public static final String TITLE_PATTERN = "[a-zA-Z0-9#№]";

    public static void validate(MultipleErrorResponse errorResponse, String data) {
        if (data == null || data.isBlank()) {
            errorResponse.add(new ErrorField("Данные " + data + " отсутствуют", "data"));
        }
        if (data != null && data.matches(TITLE_PATTERN)) {
            errorResponse.add(new ErrorField("Данные введены некорректно", "data"));
        }

    }
}
