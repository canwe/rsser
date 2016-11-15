package by.rss.reader.dao;

import java.util.List;

import com.google.appengine.api.datastore.Key;

import by.rss.reader.model.Feed;
import by.rss.reader.model.User;

public interface FeedDAO extends JPAGenericDAO<Feed, Key> {

	Feed getByUrl(String url);
	
	List<Feed> findSubscribed(User user);
	
}
