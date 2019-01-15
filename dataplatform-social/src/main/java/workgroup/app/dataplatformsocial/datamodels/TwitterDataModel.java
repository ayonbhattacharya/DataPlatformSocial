package workgroup.app.dataplatformsocial.datamodels;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="twitterdata")
public class TwitterDataModel{
	@Id
	private String docId;
	@Indexed
	private String tweetSearchParams;
	private String tweetContent;
	private String tweetTime;
	
	
	public TwitterDataModel(String tweetSearchParams, String tweetContent, String tweetTime) {
		super();
		this.tweetSearchParams = tweetSearchParams;
		this.tweetContent = tweetContent;
		this.tweetTime = tweetTime;
	}
	public String getDocId() {
		return docId;
	}
	public void setDocId(String docId) {
		this.docId = docId;
	}
	public String getTweetSearchParams() {
		return tweetSearchParams;
	}
	public void setTweetSearchParams(String tweetSearchParams) {
		this.tweetSearchParams = tweetSearchParams;
	}
	public String getTweetContent() {
		return tweetContent;
	}
	public void setTweetContent(String tweetContent) {
		this.tweetContent = tweetContent;
	}
	public String getTweetTime() {
		return tweetTime;
	}
	public void setTweetTime(String tweetTime) {
		this.tweetTime = tweetTime;
	}
	
}
