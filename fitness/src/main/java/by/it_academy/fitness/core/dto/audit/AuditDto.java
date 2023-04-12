package by.it_academy.fitness.core.dto.audit;

import by.it_academy.fitness.core.dto.audit.enums.EssenceType;
import by.it_academy.fitness.service.convertion.json.LocalDateTimeToLongSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;
import java.util.UUID;

public class AuditDto {
    private UUID uuid;
    @JsonSerialize(using = LocalDateTimeToLongSerializer.class)
    private LocalDateTime dt_create;
    private UUID user;
    private String text;
    private String type;
    private UUID id;

    public AuditDto(UUID uuid, LocalDateTime dt_create, UUID user,
                    String text, String type, UUID id) {
        this.uuid = uuid;
        this.dt_create = dt_create;
        this.user = user;
        this.text = text;
        this.type = type;
        this.id = id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public LocalDateTime getDt_create() {
        return dt_create;
    }

    public void setDt_create(LocalDateTime dt_create) {
        this.dt_create = dt_create;
    }

    public UUID getUser() {
        return user;
    }

    public void setUser(UUID user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "AuditDto{" +
                "uuid=" + uuid +
                ", dt_create=" + dt_create +
                ", user=" + user +
                ", text='" + text + '\'' +
                ", type=" + type +
                ", id='" + id + '\'' +
                '}';
    }
}
