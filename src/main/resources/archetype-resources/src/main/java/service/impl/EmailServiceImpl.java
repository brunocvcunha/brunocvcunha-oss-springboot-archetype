/**
 * 
 */
package ${package}.service.impl;

import ${package}.model.EmailMessage;
import ${package}.service.EmailService;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * Email Service Implementation
 * 
 * @author brunovolpato
 *
 */
@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendEmail(EmailMessage message) throws Exception {

        MimeMessage mail = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mail, true);
        helper.setTo(message.getTo());
        helper.setFrom(message.getFrom(), message.getFromName());
        helper.setSubject(message.getSubject());
        helper.setText(message.getContent(), true);
        helper.addInline("logoApp", new ClassPathResource("/static/images/logo.png"), "image/png");

        if (message.getAttachment() != null && !message.getAttachment().isEmpty()) {
            String attachmentPath = message.getAttachment();

            // get only the name part of the file
            String[] fileNameSplit = attachmentPath.split("[\\\\/]");

            String fileName = fileNameSplit[fileNameSplit.length - 1];
            helper.addAttachment(fileName, new FileSystemResource(attachmentPath));
        }

        javaMailSender.send(mail);

    }

}
