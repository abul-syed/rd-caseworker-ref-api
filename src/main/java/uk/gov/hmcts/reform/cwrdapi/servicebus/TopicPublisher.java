/*
package uk.gov.hmcts.reform.cwrdapi.servicebus;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.IllegalStateException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.cwrdapi.controllers.advice.CaseworkerMessageFailedException;

import javax.jms.ConnectionFactory;
import javax.jms.Session;

@Slf4j
@Service
public class TopicPublisher {

    private final JmsTemplate jmsTemplate;
    private final String destination;
    private final ConnectionFactory connectionFactory;

    @Value("${loggingComponentName}")
    private String loggingComponentName;

    @Autowired
    public TopicPublisher(JmsTemplate jmsTemplate,
                          @Value("${cwrd.topic}") final String destination,
                          ConnectionFactory connectionFactory) {
        this.jmsTemplate = jmsTemplate;
        this.destination = destination;
        this.connectionFactory = connectionFactory;
    }

    @Retryable(maxAttempts = 5, backoff = @Backoff(delay = 2000, multiplier = 3))
    public void sendMessage(final String message) {
        log.info("{}:: Sending message.", loggingComponentName);
        try {
            jmsTemplate.send(destination, (Session session) -> session.createTextMessage(message));
            log.info("{}:: Message sent.", loggingComponentName);
        } catch (IllegalStateException e) {
            throw new CaseworkerMessageFailedException(e.getMessage());
        }
    }

    @Recover
    public void recoverMessage(Throwable ex) throws Throwable {
        log.error("{}:: TopicPublisher.recover(): Send message failed with exception: {} ", loggingComponentName, ex);
        throw ex;
    }
}

*/