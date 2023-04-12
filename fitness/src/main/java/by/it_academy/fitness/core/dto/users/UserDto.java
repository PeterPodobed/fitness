package by.it_academy.fitness.core.dto.users;

import by.it_academy.fitness.core.dto.users.enums.UserRole;
import by.it_academy.fitness.core.dto.users.enums.UserStatus;
import by.it_academy.fitness.core.exception.MultipleErrorResponse;
import by.it_academy.fitness.core.exception.validationUsers.EmailValid;
import by.it_academy.fitness.core.exception.validationUsers.FioValid;
import by.it_academy.fitness.service.convertion.json.LocalDateTimeToLongSerializer;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class UserDto {
    private UUID uuid;
    @JsonSerialize(using = LocalDateTimeToLongSerializer.class)
    private LocalDateTime dt_create;
    @JsonSerialize(using = LocalDateTimeToLongSerializer.class)
    private LocalDateTime dt_update;
    private String mail;
    private String fio;
    private UserRole role;
    private UserStatus status;


    public UserDto(UUID uuid, LocalDateTime dt_create, LocalDateTime dt_update,
                   String mail, String fio, UserRole role, UserStatus status) throws MultipleErrorResponse{
        this.uuid = uuid;
        this.dt_create = dt_create;
        this.dt_update = dt_update;
        this.mail = mail;
        this.fio = fio;
        this.role = role;
        this.status = status;
        validate();
    }

    public UserDto(UUID uuid, LocalDateTime dt_create, LocalDateTime dt_update,
                   String fio, UserRole role, UserStatus status) {
        this.uuid = uuid;
        this.dt_create = dt_create;
        this.dt_update = dt_update;
        this.fio = fio;
        this.role = role;
        this.status = status;
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

    public LocalDateTime getDt_update() {
        return dt_update;
    }

    public void setDt_update(LocalDateTime dt_update) {
        this.dt_update = dt_update;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public void validate() throws MultipleErrorResponse {
        MultipleErrorResponse errorResponse = new MultipleErrorResponse("invalid fields");
        EmailValid.validate(errorResponse, this.mail);
        FioValid.validate(errorResponse, this.fio);
        if (!errorResponse.getErrors().isEmpty()) {
            throw errorResponse;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto user = (UserDto) o;
        return Objects.equals(uuid, user.uuid) && Objects.equals(dt_create, user.dt_create) &&
                Objects.equals(dt_update, user.dt_update) && Objects.equals(mail, user.mail) &&
                Objects.equals(fio, user.fio) && role == user.role && status == user.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, dt_create, dt_update, mail, fio, role, status);
    }

    @Override
    public String toString() {
        return "User{" +
                "uuid='" + uuid + '\'' +
                ", dt_create=" + dt_create +
                ", dt_update=" + dt_update +
                ", mail='" + mail + '\'' +
                ", fio='" + fio + '\'' +
                ", role=" + role +
                ", status=" + status +
                '}';
    }
}
