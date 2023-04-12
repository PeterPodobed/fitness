package by.it_academy.fitness.service.convertion.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class LocalDateTimeToLongSerializer extends JsonSerializer<LocalDateTime> {


    @Override
    public void serialize(LocalDateTime value,
                          JsonGenerator gen,
                          SerializerProvider serializers) throws IOException {
        gen.writeNumber(value.toInstant(ZoneOffset.UTC).toEpochMilli());
    }
}
