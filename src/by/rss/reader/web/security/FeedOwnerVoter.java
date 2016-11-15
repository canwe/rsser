package by.rss.reader.web.security;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.Authentication;
import org.springframework.security.ConfigAttribute;
import org.springframework.security.ConfigAttributeDefinition;
import org.springframework.security.intercept.web.FilterInvocation;
import org.springframework.security.vote.AccessDecisionVoter;
import org.springframework.web.bind.ServletRequestUtils;

import by.rss.reader.model.Feed;
import by.rss.reader.model.User;
import by.rss.reader.service.FeedService;
import by.rss.reader.service.UserService;

public class FeedOwnerVoter implements AccessDecisionVoter {

	private static final String REQUEST_FEED_ID = "feedId";
	
	private Logger logger = Logger.getLogger(FeedOwnerVoter.class);

	@Autowired
	private UserService userService;
	
	@Autowired
	private FeedService feedService;
	
	public int vote(Authentication authentication, Object object, ConfigAttributeDefinition cad) {
        FilterInvocation filter = (FilterInvocation) object;
        
        String id = ServletRequestUtils.getStringParameter(filter.getRequest(), REQUEST_FEED_ID, "");
		
        Feed feed = feedService.get(id);
        User user = userService.getCurrent();
        
        if (feed != null && feedService.findSubscribed(user).contains(feed)) {
        	if (logger.isDebugEnabled()) {
        		logger.debug("Access granted for user [" + user + "] to feed [" + feed + "]");
        	}
        	
        	return ACCESS_GRANTED;
        } else {
        	if (logger.isDebugEnabled()) {
        		logger.debug("Access abstained for user [" + user + "] to feed [" + feed + "]");
        	}
        	
        	return ACCESS_ABSTAIN;
        }
	}

	public boolean supports(ConfigAttribute attribute) {
		return Roles.ROLE_FEED_OWNER.equals(attribute.getAttribute());
	}

	public boolean supports(Class clazz) {
		return true;
	}

}