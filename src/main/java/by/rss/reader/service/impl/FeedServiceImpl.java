package by.rss.reader.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.rss.reader.dao.EntryDAO;
import by.rss.reader.dao.FeedDAO;
import by.rss.reader.dao.FeedEntryDAO;
import by.rss.reader.dao.RemoteFeedDAO;
import by.rss.reader.dao.UserDAO;
import by.rss.reader.dao.UserFeedDAO;
import by.rss.reader.exception.InvalidFeedException;
import by.rss.reader.model.Entry;
import by.rss.reader.model.Feed;
import by.rss.reader.model.RemoteEntry;
import by.rss.reader.model.RemoteFeed;
import by.rss.reader.model.User;
import by.rss.reader.service.FeedService;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Text;

@Service("feedService")
@Transactional
public class FeedServiceImpl implements FeedService {

	private Logger logger = Logger.getLogger(FeedServiceImpl.class);
	
	@Autowired
	protected RemoteFeedDAO remoteFeedDAO;
	
	@Autowired
	protected FeedDAO feedDAO;
	
	@Autowired
	protected EntryDAO entryDAO;
	
	@Autowired
	protected FeedEntryDAO feedEntryDAO;
	
	@Autowired
	protected UserDAO userDAO;
	
	@Autowired
	protected UserFeedDAO userFeedDAO;
	
	public Feed subscribe(User user, String url) throws InvalidFeedException {
		Feed feed = feedDAO.getByUrl(url);
		
		if (feed == null) {
			RemoteFeed remoteFeed = remoteFeedDAO.fetch(url);
			feed = new Feed();
			
			feed.setDescription(new Text(remoteFeed.getDescription()));
			feed.setLastUpdate(new Date());
			feed.setTitle(new Text(remoteFeed.getTitle()));
			feed.setFeedUrl(url);
			feed.setUrl(remoteFeed.getUrl());
			feed = feedDAO.save(feed);
			
			if (logger.isDebugEnabled()) {
				logger.debug("Retrieved feed from [url=" + url + "] - [title=" + feed.getTitle() + "]");
			}
			
			for (RemoteEntry remoteEntry : remoteFeed.getEntries()) {
				Entry entry = convert(remoteEntry);

				if (logger.isDebugEnabled()) {
					logger.debug("Retrieved entry from feed from [title=" + feed.getTitle() + "] - [title=" + remoteEntry.getTitle() + "], [url="
							+ remoteEntry.getUrl() + "]");
				}
				
				entry = entryDAO.save(entry);
				feedEntryDAO.save(feed, entry);
			}
		} else if (logger.isDebugEnabled()) {
			logger.debug("Retrieved feed from [url=" + url + "] - [title=" + feed.getTitle() + "]");
		}
		
		
		userFeedDAO.subscribe(user, feed);
		
		return feed;
	}

	public boolean isFeedSubscribed(User user, String url) {
		return userFeedDAO.isFeedSubscribed(user, url);
	}
	
	public List<Feed> findSubscribed(User user) {
		return feedDAO.findSubscribed(user);
	}

	public Feed get(String id) {
		Key key = KeyFactory.stringToKey(id);
		return feedDAO.get(key);
	}

	public void updateAll() {
		// TODO
	}
	
	private Entry convert(RemoteEntry remoteEntry) {
		Entry entry = new Entry();
		
		entry.setDate(remoteEntry.getDate());
		entry.setDescription(new Text(remoteEntry.getDescription()));
		entry.setTitle(new Text(remoteEntry.getTitle()));
		entry.setUrl(remoteEntry.getUrl());
		
		return entry;
	}

	@Override
	public List<Entry> get(Feed feed) {
		return entryDAO.getByFeed(feed);
	}
}
