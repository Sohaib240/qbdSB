package com.hourtimesheet.config;

import com.amazonaws.auth.BasicAWSCredentials;
import com.hourtimesheet.encryption.Encryptor;
import com.ourtimesheet.notification.factory.EmbeddedMailUrlFactory;
import com.ourtimesheet.notification.helper.AmazonEmailCreator;
import com.ourtimesheet.notification.helper.AmazonEmailSender;
import com.ourtimesheet.notification.helper.EmailSender;
import com.ourtimesheet.notification.helper.EmailTemplateResolver;
import com.ourtimesheet.notification.service.NotificationService;
import com.ourtimesheet.notification.service.NotificationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.thymeleaf.TemplateEngine;

@Configuration
@PropertySources({
        @PropertySource("classpath:amazonses-${envTarget:local}.properties"),
        @PropertySource("classpath:kickbox.properties")
})
@Import({ThymeleafConfiguration.class, ThreadPoolConfiguration.class, EmailConfiguration.class, EncryptionConfig.class})
public class NotificationConfiguration {

    @Autowired
    Encryptor amazonEncryptor;

    @Value("${FROM_ADDRESS}")
    private String fromAddress;
    @Value("${aws_ses_access_key_id}")
    private String amazonSESAccessKeyId;
    @Value("${aws_ses_secret_access_key}")
    private String amazonSESSecretAccessKey;
    @Value("${aws_ses_region}")
    private String amazonSESRegion;
    @Value("${kickBox_email_verify_url}")
    private String kickBoxUrl;
    @Value("${static-resources-url}")
    private String resourcesUrl;

    @Bean
    @Autowired
    public EmailSender emailSender(TemplateEngine templateEngine) {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(amazonEncryptor.decrypt(amazonSESAccessKeyId), amazonEncryptor.decrypt(amazonSESSecretAccessKey));
        return new AmazonEmailSender(awsCreds, amazonSESRegion, new AmazonEmailCreator(emailTemplateResolver(templateEngine), fromAddress));
    }

    private EmailTemplateResolver emailTemplateResolver(TemplateEngine templateEngine) {
        return new EmailTemplateResolver(templateEngine);
    }

    @Bean
    @Autowired
    public NotificationService notificationService(ThreadPoolTaskExecutor threadPoolTaskExecutor, EmailSender emailSender, EmbeddedMailUrlFactory embeddedMailUrlFactory) {
        return new NotificationServiceImpl(threadPoolTaskExecutor, emailSender, embeddedMailUrlFactory, resourcesUrl);
    }
}
