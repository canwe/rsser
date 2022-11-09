package by.rss.reader.dao.hibernate;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import by.rss.reader.dao.FeedDAO;
import by.rss.reader.dao.UserFeedDAO;
import by.rss.reader.dao.jpa.GenericDAOJPA;
import by.rss.reader.model.Feed;
import by.rss.reader.model.User;
import by.rss.reader.model.UserFeed;

import com.google.appengine.api.datastore.Key;

import static by.rss.reader.service.WrapperMethods.$;

@Repository("userFeedDAO")
public class JPAUserFeedDAO extends GenericDAOJPA<UserFeed, Long> implements UserFeedDAO {

	@Autowired
	private FeedDAO feedDAO;
	
	@Override
	public boolean isFeedSubscribed(User user, String url) {
		
		Feed feed = feedDAO.getByUrl(url);
		if (null == feed) {
			return false;
		}

		Map<String, Object> params = new HashMap<String, Object>(2);
		params.put("userId", user.getId());
		params.put("feedId", feed.getId());
		List<UserFeed> userFeed = $(getJpaTemplate().findByNamedQueryAndNamedParams("feed.subscribed", params));
		return null != userFeed && userFeed.size() > 0;
	}
	
	@Override
	public UserFeed getFeedSubscribed(User user, Feed feed) {
		Map<String, Object> params = new HashMap<String, Object>(2);
		params.put("userId", user.getId());
		params.put("feedId", feed.getId());
		List<UserFeed> userFeed = $(getJpaTemplate().findByNamedQueryAndNamedParams("feed.subscribed", params));
		return null != userFeed && userFeed.size() > 0 ? userFeed.get(0) : null;
	}
	
	@Override
	public List<Key> getAllFeedIdsSubscribedByUser(User user) {
		Map<String, Object> params = new HashMap<String, Object>(1);
		params.put("userId", user.getId());
		List<Key> userFeed = $(getJpaTemplate().findByNamedQueryAndNamedParams("all.feed.ids.subscribed.by.user", params));
		return null != userFeed && userFeed.size() > 0 ? userFeed : Collections.<Key>emptyList();
	}

	@Override
	public UserFeed subscribe(User user, Feed feed) {
		synchronized (user.getId()) {
			UserFeed uf0 = getFeedSubscribed(user, feed);
			if (null == uf0) {
				UserFeed uf = new UserFeed();
				uf.setFeedId(feed.getId());
				uf.setUserId(user.getId());
				return save(uf);
			} else {
				return uf0;
			}
		}
	}


}