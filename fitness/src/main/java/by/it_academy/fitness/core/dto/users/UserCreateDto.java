package by.it_academy.fitness.core.dto.users;

import by.it_academy.fitness.core.dto.users.enums.UserRole;
import by.it_academy.fitness.core.dto.users.enums.UserStatus;
import by.it_academy.fitness.core.exception.MultipleErrorResponse;
import by.it_academy.fitness.core.exception.validationUsers.PasswordValid;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class UserCreateDto extends UserDto{
    UserDto userCreate;
    String password;

    public UserCreateDto(UUID uuid, LocalDateTime dt_create, LocalDateTime dt_update,
                         String mail, String fio, UserRole role, UserStatus status,
                         UserDto userCreate, String password) throws MultipleErrorResponse {
        super(uuid, dt_create, dt_update, mail, fio, role, status);
        this.userCreate = userCreate;
        this.password = password;
        validate();
    }

    public UserDto getUserCreate() {
        return userCreate;
    }

    public void setUserCreate(UserDto userCreate) {
        this.userCreate = userCreate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void validate() throws MultipleErrorResponse {
        MultipleErrorResponse errorResponse = new MultipleErrorResponse("invalid fields");
        PasswordValid.validate(errorResponse, this.password);
        if (!errorResponse.getErrors().isEmpty()) {
            throw errorResponse;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserCreateDto that = (UserCreateDto) o;
        return Objects.equals(userCreate, that.userCreate) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userCreate, password);
    }

    @Override
    public String toString() {
        return "UserCreate{" +
                "userCreate=" + userCreate +
                ", password='" + password + '\'' +
                '}';
    }
}
