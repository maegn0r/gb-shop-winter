package ru.gb.common.builders;

import ru.gb.common.pojo.MailMessage;

import java.util.Date;

public class MailMessageBuilder {

    private MailMessage mailMessage;

    public MailMessage build (){
        return mailMessage;
    }

    public MailMessageBuilder() {
        this.mailMessage = new MailMessage();
    }

    public MailMessageBuilder from (String from){
        this.mailMessage.setFrom(from);
        return this;
    }
    public MailMessageBuilder replyTo (String replyTo){
        this.mailMessage.setReplyTo(replyTo);
        return this;
    }
    public MailMessageBuilder to (String[] to){
        this.mailMessage.setTo(to);
        return this;
    }
    public MailMessageBuilder to (String to){
        this.mailMessage.setTo(new String[] {to});
        return this;
    }
    public MailMessageBuilder cc (String[] cc){
        this.mailMessage.setCc(cc);
        return this;
    }
    public MailMessageBuilder bcc (String[] bcc){
        this.mailMessage.setBcc(bcc);
        return this;
    }
    public MailMessageBuilder sentDate (Date sentDate){
        this.mailMessage.setSentDate(sentDate);
        return this;
    }
    public MailMessageBuilder subject (String subject){
        this.mailMessage.setSubject(subject);
        return this;
    }
    public MailMessageBuilder text (String text){
        this.mailMessage.setText(text);
        return this;
    }
}
