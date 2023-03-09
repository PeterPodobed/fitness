package by.it_academy.fitness.dao.entity.users;


import by.it_academy.fitness.core.dto.users.enums.UserRole;
import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(schema = "public", name = "role")
public class UserRoleEntity {
    @Id
    @Enumerated(EnumType.STRING)
    private UserRole role;

    public UserRoleEntity(UserRole role) {
        this.role = role;
            }

    public UserRoleEntity() {
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserRoleEntity{" +
                "role=" + role +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRoleEntity that = (UserRoleEntity) o;
        return role == that.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(role);
    }
}
