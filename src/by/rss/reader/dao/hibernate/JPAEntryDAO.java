package by.rss.reader.dao.hibernate;

import by.rss.reader.dao.EntryDAO;
import by.rss.reader.dao.EntryUserDAO;
import by.rss.reader.dao.FeedEntryDAO;
import by.rss.reader.dao.UserFeedDAO;
import by.rss.reader.dao.jpa.GenericDAOJPA;
import by.rss.reader.model.Entry;
import by.rss.reader.model.Feed;
import by.rss.reader.model.User;
import com.google.appengine.api.datastore.Key;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

import static by.rss.reader.service.WrapperMethods.$;

@Repository("entryDAO")
public class JPAEntryDAO extends GenericDAOJPA<Entry, Key> implements EntryDAO {

	@Autowired
	private EntryUserDAO entryUserDAO;
	
	@Autowired
	private FeedEntryDAO feedEntryDAO;
	
	@Autowired
	private UserFeedDAO userFeedDAO;
	
	@SuppressWarnings("unchecked")
	public List<Entry> getUnread(User user) {
		List<Key> entryIds = entryUserDAO.getAllEntryIdsReadedByUser(user);
		List<Entry> entries = findAllForUser(user); 
		if (null == entryIds || entryIds.isEmpty()) {
			return entries;
		}
		Set<Entry> entrySet = new HashSet<Entry>(entries);
		Map<String, Object> params = new HashMap<String, Object>(1);
		params.put("ids", entryIds);
		List<Entry> readEntries = $(getJpaTemplate().findByNamedQueryAndNamedParams("entries.by.ids", params));
		readEntries.size();
		entrySet.removeAll(readEntries);
		return new ArrayList<Entry>(entrySet);
	}

	@Override
	public List<Entry> getByFeed(final Feed feed) {
		List<Key> entryIds = feedEntryDAO.entryIdsForFeed(feed);
		if (null == entryIds || entryIds.isEmpty()) {
			return Collections.<Entry>emptyList();
		}
		Map<String, Object> params = new HashMap<String, Object>(1);
		params.put("ids", entryIds);
		List<Entry> entries = $(getJpaTemplate().findByNamedQueryAndNamedParams("entries.by.ids", params));
		entries.size();
		return entries;
	}
	
	@Override
	public List<Entry> findAllForUser(User user) {
		List<Key> feedIds = userFeedDAO.getAllFeedIdsSubscribedByUser(user);
		if (null == feedIds || feedIds.isEmpty()) {
			return Collections.<Entry>emptyList();
		}
		Map<String, Object> params = new HashMap<String, Object>(1);
		params.put("feedIds", feedIds);
		List<Key> entryIds = $(getJpaTemplate().findByNamedQueryAndNamedParams("entry.ids.for.feeds", params));
		if (null == entryIds || entryIds.isEmpty()) {
			return Collections.<Entry>emptyList();
		}
		params = new HashMap<String, Object>(1);
		params.put("ids", entryIds);
		List<Entry> entries = $(getJpaTemplate().findByNamedQueryAndNamedParams("entries.by.ids", params));
		return null != entries && entries.size() > 0 ? entries : Collections.<Entry>emptyList();
	}
}
