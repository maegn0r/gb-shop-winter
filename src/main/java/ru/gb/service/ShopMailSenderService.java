package ru.gb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import ru.gb.common.pojo.MailMessage;

@Component
@RequiredArgsConstructor
public class ShopMailSenderService {
    private final JavaMailSender javaMailSender;

    public void sendMail(MailMessage mailMessage){
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom(mailMessage.getFrom());
        mail.setReplyTo(mailMessage.getReplyTo());
        mail.setTo(mailMessage.getTo());
        mail.setCc(mailMessage.getCc());
        mail.setBcc(mailMessage.getBcc());
        mail.setSentDate(mailMessage.getSentDate());
        mail.setSubject(mailMessage.getSubject());
        mail.setText(mailMessage.getText());
        javaMailSender.send(mail);
    }
}
