package com.ourtimesheet.notification.helper;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import com.amazonaws.services.simpleemail.model.*;
import com.ourtimesheet.notification.domain.Email;
import com.ourtimesheet.notification.domain.MailStatus;
import com.ourtimesheet.notification.domain.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by hassan on 10/6/16.
 */
public class AmazonEmailSender implements EmailSender {

    private static final Logger log = LoggerFactory.getLogger(AmazonEmailSender.class);
    private final AmazonSimpleEmailServiceClient amazonSimpleEmailServiceClient;
    private final AmazonEmailCreator amazonEmailCreator;

    public AmazonEmailSender(AWSCredentials awsCredentials, String regionName, AmazonEmailCreator amazonEmailCreator) {
        this.amazonSimpleEmailServiceClient = new AmazonSimpleEmailServiceClient(awsCredentials);
        this.amazonEmailCreator = amazonEmailCreator;
        Region region = Region.getRegion(Regions.fromName(regionName));
        amazonSimpleEmailServiceClient.setRegion(region);
    }

    @Override
    public MailStatus sendMail(Notification notification) {
        Email email = amazonEmailCreator.createEmail(notification);
        Destination destination = new Destination().withToAddresses(new String[]{email.getTo()});
        Content subject = new Content().withData(email.getSubject());
        Content textBody = new Content().withData(email.getBody());
        Body body = new Body().withHtml(textBody);
        Message message = new Message().withSubject(subject).withBody(body);
        SendEmailRequest request = new SendEmailRequest().withSource(email.getFrom()).withDestination(destination).withMessage(message);

        try {
            log.info("Attempting to send an email through Amazon SES");
            amazonSimpleEmailServiceClient.sendEmail(request);
            log.info("Email sent!");
            return MailStatus.SENT;
        } catch (Exception ex) {
            log.error("The email was not sent.");
            log.error("Error message: " + ex.getMessage(), ex);
            return MailStatus.ERROR;
        }
    }
}
