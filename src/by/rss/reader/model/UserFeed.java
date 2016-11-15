package by.rss.reader.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.google.appengine.api.datastore.Key;

@Entity
@Table(name = "user_feed")
@NamedQueries ( {
	@NamedQuery (name = "all.feed.ids.subscribed.by.user", query = "SELECT uf.feedId FROM UserFeed uf WHERE uf.userId = :userId"),
	@NamedQuery (name = "feed.subscribed", query = "SELECT uf FROM UserFeed uf WHERE uf.userId = :userId AND uf.feedId = :feedId")
})
public class UserFeed {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;
	
	private Long userId;
	
	private Key feedId;

	public UserFeed() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Key getFeedId() {
		return feedId;
	}

	public void setFeedId(Key feedId) {
		this.feedId = feedId;
	}

}
