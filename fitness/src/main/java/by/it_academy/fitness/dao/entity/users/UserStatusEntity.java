package by.it_academy.fitness.dao.entity.users;

import by.it_academy.fitness.core.dto.users.enums.UserStatus;
import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(schema = "public", name = "status")
public class UserStatusEntity {

    @Id
    @Enumerated(EnumType.STRING)
    private UserStatus status;


    public UserStatusEntity(UserStatus status) {
        this.status = status;
    }

    public UserStatusEntity() {
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "UserStatusEntity{" +
                "status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserStatusEntity that = (UserStatusEntity) o;
        return status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(status);
    }
}
