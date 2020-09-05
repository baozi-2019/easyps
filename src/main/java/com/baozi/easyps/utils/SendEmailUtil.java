package com.baozi.easyps.utils;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.mail.util.MailSSLSocketFactory;
import org.springframework.scheduling.annotation.Async;

public class SendEmailUtil {
	private String mailFrom = null;// 指明邮件的发件人
	private String password_mailFrom = null;// 指明邮件的发件人登陆密码
	private String mailTo = null;	// 指明邮件的收件人
	private String mailTittle = null;// 邮件的标题
	private String mailText =null;	// 邮件的文本内容
	private String mail_host =null;	// 邮件的服务器域名

	@Async("sendMailPoolTaskExecutor")
	public void sendMessage(String checkCode, String emailAddress) throws Exception {
		mailFrom = "bigbaozi@126.com";
		password_mailFrom="ZXYLWFWYGUNEVDJB";
		mailTo = emailAddress;
		mailTittle="easyps验证";
		mailText = "验证码：<a href=''>" + checkCode + "</a>";
		mail_host="smtp.126.com";
		
		Properties prop = new Properties();
		prop.setProperty("mail.host", mail_host);
		prop.setProperty("mail.transport.protocol", "smtp");
		prop.setProperty("mail.smtp.auth", "true");

		prop.setProperty("mail.smtp.port", "465");
		prop.setProperty("mail.smtp.socketFactory.port", "465");
		MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.ssl.socketFactory", sf);

		Session session = Session.getInstance(prop);
		session.setDebug(true);
		Transport ts = session.getTransport();
		ts.connect(mail_host,mailFrom, password_mailFrom);
		Message message = createSimpleMail(session,mailFrom,mailTo,mailTittle,mailText);
		ts.sendMessage(message, message.getAllRecipients());
		ts.close();

	}
	
	/**
	 * @Method: createSimpleMail
	 * @Description: 创建一封只包含文本的邮件
	 */
	public MimeMessage createSimpleMail(Session session, String mailfrom, String mailTo, String mailTittle,
			String mailText) throws Exception {
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(mailfrom));
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(mailTo));
		message.setSubject(mailTittle);
		message.setContent(mailText, "text/html;charset=UTF-8");
		message.saveChanges();
		return message;
	}
}
