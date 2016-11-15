package by.rss.reader.dao;

import java.util.List;

import by.rss.reader.model.Entry;
import by.rss.reader.model.Feed;
import by.rss.reader.model.FeedEntry;

import com.google.appengine.api.datastore.Key;

public interface FeedEntryDAO extends JPAGenericDAO<FeedEntry, Long> {

	List<Key> entryIdsForFeed(Feed feed);
	
	FeedEntry getFeedEntry(Feed feed, Entry entry);
	
	FeedEntry save(Feed feed, Entry entry);
	
}
