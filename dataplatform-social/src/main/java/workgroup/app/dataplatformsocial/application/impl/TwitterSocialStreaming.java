package workgroup.app.dataplatformsocial.application.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import workgroup.app.dataplatformsocial.domain.TwitterMessageProducer;


@Component
public class TwitterSocialStreaming {

	private static final Logger LOGGER = LoggerFactory.getLogger(TwitterSocialStreaming.class);

	@Autowired
	private TwitterMessageProducer twitterMessageproducer;
	
	public void startListening() {
		twitterMessageproducer.doStart();
		
	}
}
