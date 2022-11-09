package by.rss.reader.dao;

import java.util.List;

import by.rss.reader.model.Feed;
import by.rss.reader.model.User;
import by.rss.reader.model.UserFeed;

import com.google.appengine.api.datastore.Key;

public interface UserFeedDAO extends JPAGenericDAO<UserFeed, Long> {
	
	boolean isFeedSubscribed(User user, String url); 
	
	List<Key> getAllFeedIdsSubscribedByUser(User user);
	
	UserFeed subscribe(User user, Feed feed);
	
	UserFeed getFeedSubscribed(User user, Feed feed);
}
