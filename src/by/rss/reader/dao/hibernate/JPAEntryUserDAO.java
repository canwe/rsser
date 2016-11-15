package by.rss.reader.dao.hibernate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import by.rss.reader.dao.BracedObjectLiteral;
import by.rss.reader.dao.EntryUserDAO;
import by.rss.reader.dao.jpa.GenericDAOJPA;
import by.rss.reader.model.Entry;
import by.rss.reader.model.EntryUser;
import by.rss.reader.model.User;

import com.google.appengine.api.datastore.Key;

import static by.rss.reader.service.WrapperMethods.$;

@Repository("entryUserDAO")
public class JPAEntryUserDAO extends GenericDAOJPA<EntryUser, Long> implements EntryUserDAO {

	@Override
	public boolean isEntryReaded(User user, Entry entry) {
		Map<String, Object> params = new HashMap<String, Object>(2);
		params.put("userId", user.getId());
		params.put("entryId", entry.getId());
		List<EntryUser> entryUser = $(getJpaTemplate().findByNamedQueryAndNamedParams("entry.readed", params));
		return null != entryUser && entryUser.size() > 0;
	}
	
	@Override
	public EntryUser getEntryReaded(User user, Entry entry) {
		Map<String, Object> params = new HashMap<String, Object>(2);
		params.put("userId", user.getId());
		params.put("entryId", entry.getId());
		List<EntryUser> entryUser = $(getJpaTemplate().findByNamedQueryAndNamedParams("entry.readed", params));
		return null != entryUser && entryUser.size() > 0 ? entryUser.get(0) : null;
	}
	
	@Override
	public List<Key> getAllEntryIdsReadedByUser(User user) {
		Map<String, Object> params = new HashMap<String, Object>(1);
		params.put("userId", user.getId());
		List<Key> entryUser = $(getJpaTemplate().findByNamedQueryAndNamedParams("all.entry.ids.readed.by.user", params));
		return null != entryUser && entryUser.size() > 0 ? new ArrayList<Key>(entryUser) : Collections.<Key>emptyList();
	}
		
	@Override
	public List<Long> getAllUserIdsReadedEntry(Entry entry) {
		Map<String, Object> params = new HashMap<String, Object>(1);
		params.put("entryId", entry.getId());
		List<Long> entryUser = $(getJpaTemplate().findByNamedQueryAndNamedParams("all.user.ids.readed.entry", params));
		return null != entryUser && entryUser.size() > 0 ? new ArrayList<Long>(entryUser) : Collections.<Long>emptyList();
	}

	@Override
	public EntryUser read(User user, Entry entry) {
		synchronized (user.getId()) {
			EntryUser eu0 = getEntryReaded(user, entry);
			if (null == eu0) {
				EntryUser eu = new EntryUser();
				eu.setEntryId(entry.getId());
				eu.setUserId(user.getId());
				return save(eu);
			} else {
				return eu0;
			}
		}
	}

	@Override
	public void unread(User user, Entry entry) {
		synchronized (user.getId()) {
			EntryUser eu0 = getEntryReaded(user, entry);
			if (null != eu0) {
				remove(eu0);
			}
		}
	}

	@Override
	public List<User> getUsersReadedEntry(Entry entry) {
		List<Long> userIds = getAllUserIdsReadedEntry(entry);
		BracedObjectLiteral l = new BracedObjectLiteral(userIds);
		if (l.isEmpty()) {
			return Collections.<User>emptyList();
		}
		List<User> users = $(getJpaTemplate().find("SELECT u FROM User u WHERE u.id in " + l.toString()));
		return users;
	}
}
