package by.it_academy.fitness.core.dto.users;

import by.it_academy.fitness.core.exception.MultipleErrorResponse;
import by.it_academy.fitness.core.exception.validationUsers.EmailValid;
import by.it_academy.fitness.core.exception.validationUsers.FioValid;
import by.it_academy.fitness.core.exception.validationUsers.PasswordValid;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Objects;

@JsonPropertyOrder({
        "mail",
        "fio",
        "password"})
public class UserRegistrationDto {
    String mail;
    String fio;
    String password;

    public UserRegistrationDto(String mail, String fio, String password) throws MultipleErrorResponse {
        this.mail = mail;
        this.fio = fio;
        this.password = password;
        validate();
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void validate() throws MultipleErrorResponse {
        MultipleErrorResponse errorResponse = new MultipleErrorResponse("invalid fields");
        EmailValid.validate(errorResponse, this.mail);
        FioValid.validate(errorResponse, this.fio);
        PasswordValid.validate(errorResponse, this.password);
        if (!errorResponse.getErrors().isEmpty()) {
            throw errorResponse;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRegistrationDto that = (UserRegistrationDto) o;
        return Objects.equals(mail, that.mail) && Objects.equals(fio, that.fio) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mail, fio, password);
    }

    @Override
    public String toString() {
        return "UserRegistration{" +
                "mail='" + mail + '\'' +
                ", fio='" + fio + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
