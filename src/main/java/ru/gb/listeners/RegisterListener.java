package ru.gb.listeners;


import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.gb.config.JmsConfig;
import ru.gb.events.UserRegisterEvent;
import ru.gb.pojo.RegisterMessage;
import ru.gb.service.ShopMailSenderService;

@RequiredArgsConstructor
@Component
public class RegisterListener {

    private final ShopMailSenderService mailSender;

    @JmsListener(destination = JmsConfig.USER_REGISTER)
    public void listen(@Payload UserRegisterEvent event) {
        RegisterMessage registerMessage = event.getRegisterMessage();
        mailSender.sendMail(registerMessage.getMailTo(), registerMessage.getSubject(), registerMessage.getCode());
    }
}