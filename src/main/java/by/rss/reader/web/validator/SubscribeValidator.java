package by.rss.reader.web.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import by.rss.reader.model.Feed;
import by.rss.reader.service.FeedService;
import by.rss.reader.service.UserService;

public class SubscribeValidator implements Validator{

	@Autowired
	protected FeedService feedService;
	
	@Autowired
	protected UserService userService;
	
	public boolean supports(Class clazz) {
		return Feed.class.equals(clazz);
	}

	public void validate(Object obj, Errors errors) {
		Feed feed = (Feed) obj;
		
		if (feedService.isFeedSubscribed(userService.getCurrent(), feed.getFeedUrl())) {
			errors.reject("subscription.alreadySubscribed");
		}
	}
	
}
