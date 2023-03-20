package by.it_academy.fitness.service.mail;

import by.it_academy.fitness.config.EmailSender;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;


public class MailService implements MailSender {
    private final EmailSender emailSender = new EmailSender();
    private final JavaMailSender javaMailSender = emailSender.javaMailSender();
    private String MAIL_SENDER ="i_ivanov_01_01_1980@mail.ru";


    public void sendSimpleMessage(String to, String subject, String message) {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(MAIL_SENDER);
        simpleMailMessage.setTo(MAIL_SENDER);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText("http://localhost:8080/api/v1/users/verification?code=" + message + "&mail=" + to);
        javaMailSender.send(simpleMailMessage);

    }


    @Override
    public void send(SimpleMailMessage simpleMessage) throws MailException {

    }

    @Override
    public void send(SimpleMailMessage... simpleMessages) throws MailException {

    }
}
