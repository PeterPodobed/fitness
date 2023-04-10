package by.it_academy.fitness.dao.entity.audit;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(schema = "public", name = "audit" )
public class AuditEntity {
    @Id
    @Column(name = "uuid")
    private UUID uuid;
    @Column(name = "dt_create")
    private LocalDateTime dt_create;
    @Column(name = "examUser")
    private UUID examUser;
    @Column(name = "text")
    private String text;

    @Column(name = "type")
    private String type;
    @Column(name = "id")
    private UUID id;

    public AuditEntity(UUID uuid, LocalDateTime dt_create, UUID examUser,
                       String text, String type, UUID id) {
        this.uuid = uuid;
        this.dt_create = dt_create;
        this.examUser = examUser;
        this.text = text;
        this.type = type;
        this.id = id;
    }

    public AuditEntity() {
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

    public UUID getExamUser() {
        return examUser;
    }

    public void setExamUser(UUID user) {
        this.examUser = user;
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
        return "AuditEntity{" +
                "uuid=" + uuid +
                ", dt_create=" + dt_create +
                ", user='" + examUser + '\'' +
                ", text='" + text + '\'' +
                ", type=" + type +
                ", id='" + id + '\'' +
                '}';
    }
}
