package by.rss.reader.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.rss.reader.dao.EntryDAO;
import by.rss.reader.dao.EntryUserDAO;
import by.rss.reader.dao.FeedDAO;
import by.rss.reader.model.Entry;
import by.rss.reader.model.Feed;
import by.rss.reader.model.User;
import by.rss.reader.service.EntryService;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@Service(value = "entryService")
@Transactional
public class EntryServiceImpl implements EntryService {

	private Logger logger = Logger.getLogger(EntryServiceImpl.class);
	
	@Autowired
	protected EntryDAO entryDAO;
	
	@Autowired
	protected EntryUserDAO entryUserDAO;
	
	@Autowired
	protected FeedDAO feedDAO;
	
	public void markAsRead(User user, String entryId) {
		Key key = KeyFactory.stringToKey(entryId);
		Entry entry = entryDAO.get(key);
		
		if (logger.isDebugEnabled()) {
			logger.debug("Trying to mark entry [entryId=" + entryId + "] read");
		}
		
		if (!entryUserDAO.getAllUserIdsReadedEntry(entry).contains(user.getId())) {
			
			entryUserDAO.read(user, entry);
			
			if (logger.isDebugEnabled()) {
				logger.debug("Entry [entryId=" + entryId + "] marked as read");
			}
		} else if (logger.isDebugEnabled()) {
			logger.debug("Entry [entryId=" + entryId + "] already read");
		}
	}

	public void markAsUnread(User user, String entryId) {
		Key key = KeyFactory.stringToKey(entryId);
		Entry entry = entryDAO.get(key);
		
		if (logger.isDebugEnabled()) {
			logger.debug("Trying to mark entry [entryId=" + entryId + "] unread");
		}
		
		if (entryUserDAO.getAllUserIdsReadedEntry(entry).contains(user.getId())) {
		
			entryUserDAO.unread(user, entry);
			
			if (logger.isDebugEnabled()) {
				logger.debug("Entry [entryId=" + entryId + "] marked as unread");
			}
		} else if (logger.isDebugEnabled()) {
			logger.debug("Entry [entryId=" + entryId + "] already unread");
		}
	}

	public List<Entry> getRead(User user, String feedId) {
		Key key = KeyFactory.stringToKey(feedId);
		Feed feed = feedDAO.get(key);
		
		List<Entry> read = new ArrayList<Entry>();
		
		if (feed == null) {
			return read;
		}
		
		for (Entry entry : entryDAO.getByFeed(feed)) {
			if (entryUserDAO.getAllUserIdsReadedEntry(entry).contains(user.getId())) {
				read.add(entry);
			}
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("Entries marked as read in feed [feedId=" + feedId + "] [" + read + "]");
		}
		
		return read;
	}

	public void markAllAsRead(User user, String feedId) {
		if (logger.isDebugEnabled()) {
			logger.debug("Trying to mark all entries in feed [feedId=" + feedId + "] read");
		}
		
		Key key = KeyFactory.stringToKey(feedId);
		Feed feed = feedDAO.get(key);
		
		for (Entry entry : entryDAO.getByFeed(feed)) {
			if (!entryUserDAO.getAllUserIdsReadedEntry(entry).contains(user.getId())) {
	
				if (logger.isDebugEnabled()) {
					logger.debug("Entry [entryId=" + entry.getId() + "] in feed [feedId=" + feedId + "] marked read");
				}
				
				entryUserDAO.read(user, entry);
			}
		}
	}

	public void markAllAsUnread(User user, String feedId) {
		if (logger.isDebugEnabled()) {
			logger.debug("Trying to mark all entries in feed [feedId=" + feedId + "] unread");
		}
		
		Key key = KeyFactory.stringToKey(feedId);
		Feed feed = feedDAO.get(key);
		
		for (Entry entry : entryDAO.getByFeed(feed)) {
			if (entryUserDAO.getUsersReadedEntry(entry).contains(user)) {
				
				if (logger.isDebugEnabled()) {
					logger.debug("Entry [entryId=" + entry.getId() + "] in feed [feedId=" + feedId + "] marked unread");
				}
				
				entryUserDAO.unread(user, entry);
			}
		}	
	}

	public List<Entry> getUnread(User user) {
		return entryDAO.getUnread(user);
	}
	
}
