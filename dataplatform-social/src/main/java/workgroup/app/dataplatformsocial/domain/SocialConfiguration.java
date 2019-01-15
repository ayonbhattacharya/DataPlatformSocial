package workgroup.app.dataplatformsocial.domain;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.messaging.MessageChannel;
import twitter4j.Status;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;


@Configuration
public class SocialConfiguration {

  private static final Logger LOGGER = LoggerFactory.getLogger(SocialConfiguration.class);
	
  @Bean
  TwitterStreamFactory twitterStreamFactory() {
    return new TwitterStreamFactory();
  }

  @Bean
  TwitterStream twitterStream(TwitterStreamFactory twitterStreamFactory) {
    return twitterStreamFactory.getInstance();
  }

  @Bean
  MessageChannel outputChannel() {
    return MessageChannels.direct().get();
  }

  @Bean
  TwitterMessageProducer twitterMessageProducer(
      TwitterStream twitterStream, MessageChannel outputChannel) {

    TwitterMessageProducer twitterMessageProducer =
        new TwitterMessageProducer(twitterStream, outputChannel);

    twitterMessageProducer.setTerms(Arrays.asList("CitiBank", "Citi","Citibank","Citigroup"));

    return twitterMessageProducer;
  }

  @Bean
  IntegrationFlow twitterFlow(MessageChannel outputChannel) {
    return IntegrationFlows.from(outputChannel)
        .transform(Status::getText)
        .handle(m -> LOGGER.info(m.getPayload().toString()))
        .get();
  }

}
