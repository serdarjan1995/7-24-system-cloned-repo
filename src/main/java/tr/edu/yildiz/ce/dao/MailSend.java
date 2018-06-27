package tr.edu.yildiz.ce.dao;

public interface MailSend {
	
	public void sendSimpleMessage(String to, String subject, String text);

}
