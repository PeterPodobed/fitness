package by.it_academy.fitness.dao.entity.users;


import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(schema = "public", name = "user" )
@SecondaryTable(schema = "public", name = "user_code",
        pkJoinColumns = @PrimaryKeyJoinColumn(name = "uuid")
)
public class UserEntity {

    @Id
    @Column(name = "uuid")
    private UUID uuid;

    @Column(name = "dtCreate")
    private LocalDateTime dtCreate;

    @Column(name = "dtUpdate")
    @Version
    private LocalDateTime dtUpdate;

    @Column(name = "mail")
    private String mail;

    @Column(name = "fio")
    private String fio;
    @Enumerated(EnumType.STRING)
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinTable(schema = "public", name = "user_role",
            joinColumns=
            @JoinColumn(name="user_uuid"),
            inverseJoinColumns=
            @JoinColumn(name="role_id")
    )
    private UserRoleEntity role;

    @Enumerated(EnumType.STRING)
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinTable(schema = "public", name = "user_status",
            joinColumns=
            @JoinColumn(name="user_uuid"),
            inverseJoinColumns=
            @JoinColumn(name="status_id")
    )
    private UserStatusEntity status;

    @Column(name = "code", table= "user_code")
    private String verificationCode;

    @Column(name = "password")
    private String password;

    public UserEntity() {
    }

    public UserEntity(UUID uuid, LocalDateTime dtCreate, LocalDateTime dtUpdate,
                      String mail, String fio, UserRoleEntity role, UserStatusEntity status,
                      String verificationCode, String password) {
        this.uuid = uuid;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.mail = mail;
        this.fio = fio;
        this.role = role;
        this.status = status;
        this.verificationCode = verificationCode;
        this.password = password;
    }

    public UserEntity(UUID uuid, LocalDateTime dtCreate, LocalDateTime dtUpdate,
                      String mail, String fio, UserRoleEntity role, UserStatusEntity status) {
        this.uuid = uuid;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.mail = mail;
        this.fio = fio;
        this.role = role;
        this.status = status;
    }

    public UserEntity(UUID uuid, LocalDateTime dtCreate, LocalDateTime dtUpdate,
                      String mail, String fio, UserRoleEntity role, UserStatusEntity status,
                      String password) {
        this.uuid = uuid;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.mail = mail;
        this.fio = fio;
        this.role = role;
        this.status = status;
        this.password = password;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public LocalDateTime getDtCreate() {
        return dtCreate;
    }

    public void setDtCreate(LocalDateTime dtCreate) {
        this.dtCreate = dtCreate;
    }

    public LocalDateTime getDtUpdate() {
        return dtUpdate;
    }

    public void setDtUpdate(LocalDateTime dt_update) {
        this.dtUpdate = dt_update;
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

    public UserRoleEntity getRole() {
        return role;
    }

    public void setRole(UserRoleEntity role) {
        this.role = role;
    }

    public UserStatusEntity getStatus() {
        return status;
    }

    public void setStatus(UserStatusEntity status) {
        this.status = status;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equals(uuid, that.uuid) && Objects.equals(dtCreate, that.dtCreate) && Objects.equals(dtUpdate, that.dtUpdate) && Objects.equals(mail, that.mail) && Objects.equals(fio, that.fio) && Objects.equals(role, that.role) && Objects.equals(status, that.status) && Objects.equals(verificationCode, that.verificationCode) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, dtCreate, dtUpdate, mail, fio, role, status, verificationCode, password);
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "uuid=" + uuid +
                ", dtCreate=" + dtCreate +
                ", dtUpdate=" + dtUpdate +
                ", mail='" + mail + '\'' +
                ", fio='" + fio + '\'' +
                ", role=" + role +
                ", status=" + status +
                ", verificationCode=" + verificationCode +
                ", password='" + password + '\'' +
                '}';
    }
}
