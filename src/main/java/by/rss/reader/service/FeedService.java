package by.rss.reader.service;

import java.util.List;

import by.rss.reader.exception.InvalidFeedException;
import by.rss.reader.model.Entry;
import by.rss.reader.model.Feed;
import by.rss.reader.model.User;

public interface FeedService {
	Feed subscribe(User user, String url) throws InvalidFeedException;

	boolean isFeedSubscribed(User user, String url);
	
	void updateAll();

	List<Feed> findSubscribed(User user);

	Feed get(String id);
	
	List<Entry> get(Feed feed);
}
