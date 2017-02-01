package ${package}.service.impl;

import ${package}.controller.PublisherController;
import ${package}.model.EmailMessage;
import ${package}.model.Post;
import ${package}.model.Role;
import ${package}.model.User;
import ${package}.repository.EmailMessageRepository;
import ${package}.repository.PostRepository;
import ${package}.repository.UserRepository;
import ${package}.service.PostService;
import ${package}.service.ServiceException;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.log4j.Logger;
import org.brunocvcunha.doormain.model.EmailMessage;
import org.brunocvcunha.doormain.model.Post;
import org.brunocvcunha.doormain.model.Role;
import org.brunocvcunha.doormain.model.User;
import org.brunocvcunha.doormain.repository.EmailMessageRepository;
import org.brunocvcunha.doormain.repository.PostRepository;
import org.brunocvcunha.doormain.repository.UserRepository;
import org.brunocvcunha.doormain.service.PostService;
import org.brunocvcunha.doormain.service.ServiceException;
import org.brunocvcunha.doormain.service.impl.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

@Service
public class PostServiceImpl implements PostService {

    private final static Logger log = Logger.getLogger(PostServiceImpl.class);

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Autowired
    private EmailMessageRepository emailMessageRepository;

    @Override
    public List<Post> listAll(User user) {
        User persistentUser = userRepository.findOne(user.getId());
        return Collections.EMPTY_LIST;
    }

    @Override
    public Post publish(Post post, Long subscriptionId) throws ServiceException {
        if (post.getUuid() == null || post.getUuid().isEmpty()) {
            throw new ServiceException("Post UUID not specified");
        }

        try {
            Post persistedPost = postRepository.save(post);

            List<User> admins = userRepository.findByRole(Role.ADMIN);

            for (User user : admins) {
                notifyUser(user, persistedPost);
            }

            return persistedPost;
        } catch (DataIntegrityViolationException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    protected void notifyUser(User user, Post post) {

        // consider L10N - localization
        final Context ctx = new Context(Locale.getDefault());
        ctx.setVariable("user", user);
        ctx.setVariable("post", post);

        String htmlContent = this.templateEngine.process("email/post-published", ctx);

        EmailMessage mail = new EmailMessage();
        mail.setFrom("posts@brunocandido.com");
        mail.setFromName("Aviso de Post");
        mail.setSubject("Post publicado");
        mail.setTo(user.getEmail());
        mail.setContent(htmlContent);

        //mail.setAttachment(PublisherController.getFileName(post.getSubscription().getUser().getId(), post.getUuid(), post.getFileName()));

        emailMessageRepository.save(mail);
    }
}
