package workgroup.app.dataplatformsocial.domain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.endpoint.MessageProducerSupport;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.CollectionUtils;
import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusAdapter;
import twitter4j.TwitterStream;
import workgroup.app.dataplatformsocial.datamodels.TwitterDataModel;
import workgroup.app.dataplatformsocial.repo.TwitterDataRepository;


public class TwitterMessageProducer extends MessageProducerSupport {

  private static final Logger LOGGER = LoggerFactory.getLogger(TwitterMessageProducer.class);
	
	
  private final TwitterStream twitterStream;
  
  @Autowired
  private TwitterDataRepository twitterDataRepository;
	
  @Value("#{new java.util.Date()}")
  Date date;

  private List<Long> follows;
  private List<String> terms;

  private StatusListener statusListener;
  private FilterQuery filterQuery;

  public TwitterMessageProducer(TwitterStream twitterStream, MessageChannel outputChannel) {
    this.twitterStream = twitterStream;
    setOutputChannel(outputChannel);
  }

  @Override
  protected void onInit() {
    super.onInit();

    statusListener = new StatusListener();

    long[] followsArray = null;

    if (!CollectionUtils.isEmpty(follows)) {
      followsArray = new long[follows.size()];
      for (int i = 0; i < follows.size(); i++) {
        followsArray[i] = follows.get(i);
      }
    }

    String[] termsArray = null;
    if (!CollectionUtils.isEmpty(terms)) {
      termsArray = terms.toArray(new String[0]);
    }

    filterQuery = new FilterQuery(0, followsArray, termsArray);
  }

  @Override
  public void doStart() {
    twitterStream.addListener(statusListener);
    twitterStream.filter(filterQuery);
  }

  @Override
  public void doStop() {
    twitterStream.cleanUp();
    twitterStream.clearListeners();
  }

  public void setFollows(List<Long> follows) {
    this.follows = follows;
  }

  public void setTerms(List<String> terms) {
    this.terms = terms;
  }

  StatusListener getStatusListener() {
    return statusListener;
  }

  FilterQuery getFilterQuery() {
    return filterQuery;
  }

  class StatusListener extends StatusAdapter {

    @Override
    public void onStatus(Status status) {
      //sendMessage(MessageBuilder.withPayload(status).build());
      LOGGER.info("Tweet is :- "  +  status.toString());
      DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
      twitterDataRepository.save(new TwitterDataModel(terms.toString(), status.toString(), dateFormat.format(date)));
      
    }

    @Override
    public void onException(Exception ex) {
      LOGGER.error(ex.getMessage(), ex);
    }

    @Override
    public void onStallWarning(StallWarning warning) {
      LOGGER.warn(warning.toString());
    }

  }

}
