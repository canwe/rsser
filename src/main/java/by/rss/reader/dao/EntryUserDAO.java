package by.rss.reader.dao;

import java.util.List;

import by.rss.reader.model.Entry;
import by.rss.reader.model.EntryUser;
import by.rss.reader.model.User;

import com.google.appengine.api.datastore.Key;

public interface EntryUserDAO extends JPAGenericDAO<EntryUser, Long> {
	
	boolean isEntryReaded(User user, Entry entry); 
	
	EntryUser getEntryReaded(User user, Entry entry);
	
	List<Key> getAllEntryIdsReadedByUser(User user);
	
	List<Long> getAllUserIdsReadedEntry(Entry entry);
	
	EntryUser read(User user, Entry entry);
	
	void unread(User user, Entry entry);
	
	List<User> getUsersReadedEntry(Entry entry);
}
