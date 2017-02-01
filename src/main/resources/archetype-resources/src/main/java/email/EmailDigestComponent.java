package ${package}.email;

import ${package}.model.EmailMessage;
import ${package}.model.Post;
import ${package}.model.User;
import ${package}.repository.EmailMessageRepository;
import ${package}.repository.PostRepository;
import ${package}.repository.UserRepository;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

/**
 * Email Digest Component
 * 
 * @author brunovolpato
 *
 */
@Component
public class EmailDigestComponent {

    private static final Logger logger = LoggerFactory.getLogger(EmailDigestComponent.class);

    @Autowired
    private EmailMessageRepository emailMessageRepository;

    @Autowired
    private PostRepository journalRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Value("${email.from}")
    private String emailFrom;
    
    
    @Value("${email.from.name}")
    private String emailFromName;
    
    @Value("${email.subject}")
    private String emailSubject;
    
    /**
     * Star sending digests everyday at 7pm - 0 0 19 * * *
     */
    @Scheduled(cron = "0 0 19 * * *")
    public void sendDigest() {
        logger.info("Sending digest emails...");

        // filter by date
        Calendar cal = Calendar.getInstance();

        // from last day to now
        cal.add(Calendar.DATE, -1);

        List<Post> journals = journalRepository.findByPublishDateGreaterThan(cal.getTime());
        if (!journals.isEmpty()) {
            List<User> users = userRepository.findAll();

            for (User user : users) {

                // consider L10N - localization
                final Context ctx = new Context(Locale.getDefault());
                ctx.setVariable("user", user);
                ctx.setVariable("journals", journals);

                String htmlContent = this.templateEngine.process("email/digest", ctx);

                EmailMessage message = new EmailMessage();
                message.setFrom(emailFrom);
                message.setFromName(emailFromName);
                message.setSubject(emailSubject);
                message.setTo(user.getEmail());
                message.setContent(htmlContent);

                emailMessageRepository.save(message);
            }
        }

    }
}
