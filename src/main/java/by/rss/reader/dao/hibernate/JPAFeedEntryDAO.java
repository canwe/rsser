package by.rss.reader.dao.hibernate;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.google.appengine.api.datastore.Key;

import by.rss.reader.dao.FeedEntryDAO;
import by.rss.reader.dao.jpa.GenericDAOJPA;
import by.rss.reader.model.Entry;
import by.rss.reader.model.Feed;
import by.rss.reader.model.FeedEntry;

import static by.rss.reader.service.WrapperMethods.$;

@Repository("feedEntryDAO")
public class JPAFeedEntryDAO extends GenericDAOJPA<FeedEntry, Long> implements FeedEntryDAO {

	@Override
	public FeedEntry getFeedEntry(Feed feed, Entry entry) {
		Map<String, Object> params = new HashMap<String, Object>(2);
		params.put("entryId", entry.getId());
		params.put("feedId", feed.getId());
		List<FeedEntry> feedEntry = $(getJpaTemplate().findByNamedQueryAndNamedParams("feed.entry", params));
		return null != feedEntry && feedEntry.size() > 0 ? feedEntry.get(0) : null;
	}
	
	@Override
	public FeedEntry save(Feed feed, Entry entry) {
		synchronized (feed.getId()) {
			FeedEntry fe0 = getFeedEntry(feed, entry);
			if (null == fe0) {
				FeedEntry fe = new FeedEntry();
				fe.setFeedId(feed.getId());
				fe.setEntryId(entry.getId());
				return save(fe);
			} else {
				return fe0;
			}
		}
	}

	@Override
	public List<Key> entryIdsForFeed(Feed feed) {
		Map<String, Object> params = new HashMap<String, Object>(1);
		params.put("feedId", feed.getId());
		List<Key> feedEntry = $(getJpaTemplate().findByNamedQueryAndNamedParams("entry.ids.for.feed", params));
		return null != feedEntry ? feedEntry : Collections.<Key>emptyList();
	}
	
}
