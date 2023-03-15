package by.it_academy.fitness.core.exception.validationProducts;

import by.it_academy.fitness.core.exception.ErrorField;
import by.it_academy.fitness.core.exception.MultipleErrorResponse;

public class ProductCreateValid {
    public static final String DATA_PATTERN = "[0-9.]";
    public static void validate(MultipleErrorResponse errorResponse, String data) {
        if (data == null || data.isBlank()) {
            errorResponse.add(new ErrorField("Данные " + data + " отсутствуют", "data"));
        }
        if (data != null && data.matches(DATA_PATTERN)) {
            errorResponse.add(new ErrorField("Данные введены некорректно", "data"));
        }

    }
}
