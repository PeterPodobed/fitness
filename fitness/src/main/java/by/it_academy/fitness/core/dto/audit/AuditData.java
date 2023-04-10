package by.it_academy.fitness.core.dto.audit;

import java.util.UUID;

public class AuditData {
    private String mail;
    private String text;
    private String type;
    private UUID id;

    public AuditData(String mail, String text, String type, UUID id) {
        this.mail = mail;
        this.text = text;
        this.type = type;
        this.id = id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
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
        return "AuditData{" +
                "mail='" + mail + '\'' +
                ", text='" + text + '\'' +
                ", type='" + type + '\'' +
                ", id='" + id + '\'' +
                '}';
    }


}
