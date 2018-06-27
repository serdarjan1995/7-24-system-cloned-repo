package tr.edu.yildiz.ce.dao.impl;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import tr.edu.yildiz.ce.dao.MailSend;

public class MailSendImpl implements MailSend {
	
	@Autowired
    public JavaMailSender emailSender;
	
	
	public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage(); 
        message.setTo(to); 
        message.setSubject(subject); 
        message.setText(text);
        System.out.println("to: "+to+subject+text);
        emailSender.send(message);
    }
	 
	
	
}
