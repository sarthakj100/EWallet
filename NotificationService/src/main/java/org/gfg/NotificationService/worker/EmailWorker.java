package org.gfg.NotificationService.worker;

import org.example.UserIdentifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailWorker {

    @Autowired
    JavaMailSender javaMailSender;

    public void sendEmailToOnboardedUser(String name, String email, UserIdentifier userIdentifier){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setSubject("Welcome to E-Wallet App");
        simpleMailMessage.setText("Hi "+name+",\n"+"Thanks for choosing the E-Wallet APP. You have been successfully verified via "+userIdentifier.name());
        simpleMailMessage.setTo(email);

        javaMailSender.send(simpleMailMessage);

        System.out.println("Email sent to user");
    }
}
