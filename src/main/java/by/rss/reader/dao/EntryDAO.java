package by.rss.reader.dao;

import java.util.List;

import by.rss.reader.model.Entry;
import by.rss.reader.model.Feed;
import by.rss.reader.model.User;

import com.google.appengine.api.datastore.Key;

public interface EntryDAO extends JPAGenericDAO<Entry, Key> {

	List<Entry> getUnread(User user);

	List<Entry> getByFeed(Feed feed);
	
	List<Entry> findAllForUser(User user);
	
}
