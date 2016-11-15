package by.rss.reader.dao.hibernate;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import by.rss.reader.dao.BracedObjectLiteral;
import by.rss.reader.dao.FeedDAO;
import by.rss.reader.dao.UserFeedDAO;
import by.rss.reader.dao.jpa.GenericDAOJPA;
import by.rss.reader.model.Feed;
import by.rss.reader.model.User;

import com.google.appengine.api.datastore.Key;

import static by.rss.reader.service.WrapperMethods.$;

@Repository("feedDAO")
public class JPAFeedDAO extends GenericDAOJPA<Feed, Key> implements FeedDAO {

	@Autowired
	private UserFeedDAO userFeedDAO;
	
	@SuppressWarnings("unchecked")
	public Feed getByUrl(String url) {
		Map<String, Object> params = new HashMap<String, Object>(1);
		params.put("url", url);
		List<Feed> feeds = $(getJpaTemplate().findByNamedQueryAndNamedParams("feed.by.url", params));
		
		if (feeds != null && feeds.size() > 0) {
			return feeds.get(0);
		}
		
		return null;
	}

	@Override
	public List<Feed> findSubscribed(User user) {
		List<Key> feedIds = userFeedDAO.getAllFeedIdsSubscribedByUser(user);
		BracedObjectLiteral l = new BracedObjectLiteral(feedIds);
		if (l.isEmpty()) {
			return Collections.<Feed>emptyList();
		}
		List<Feed> feeds = $(getJpaTemplate().find("SELECT f FROM Feed f WHERE f.id in " + l.toString()));
		feeds.size();
		return feeds;
	}

}
