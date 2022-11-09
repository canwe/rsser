package by.rss.reader.service;

import java.util.List;

import by.rss.reader.model.Entry;
import by.rss.reader.model.User;

public interface EntryService {

	void markAsRead(User user, String entryId);
	void markAsUnread(User user, String entryId);
	
	void markAllAsRead(User current, String feedId);
	void markAllAsUnread(User current, String feedId);
	
	List<Entry> getRead(User user, String feedId);

	List<Entry> getUnread(User User);
}
