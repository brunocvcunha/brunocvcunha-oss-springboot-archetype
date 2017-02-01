package ${package}.email;

import ${package}.model.EmailMessage;
import ${package}.repository.EmailMessageRepository;
import ${package}.service.EmailService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Email Sender Component
 * 
 * @author brunovolpato
 *
 */
@Component
public class EmailSenderComponent {

    private static final Logger logger = LoggerFactory.getLogger(EmailSenderComponent.class);

    @Autowired
    private EmailMessageRepository emailMessageRepository;

    @Autowired
    private EmailService emailService;

    @Scheduled(fixedRate = 60000)
    public void sendEmails() {
        logger.info("Checking pending emails...");
        Iterable<EmailMessage> emailsToSend = emailMessageRepository.findAll();

        for (EmailMessage email : emailsToSend) {
            try {
                emailService.sendEmail(email);
                emailMessageRepository.delete(email);
            } catch (Exception e) {
                logger.error("Error sending email to " + email.getTo(), e);
            }
        }
    }
}
