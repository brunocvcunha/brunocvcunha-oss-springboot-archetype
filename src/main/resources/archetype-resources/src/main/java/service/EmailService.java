package ${package}.service;

import ${package}.model.EmailMessage;

/**
 * Email Service interface
 * 
 * @author brunovolpato
 *
 */
public interface EmailService {

    /**
     * Send email message
     * 
     * @param message
     *            Message to send
     * @throws Exception
     */
    void sendEmail(EmailMessage message) throws Exception;
}
