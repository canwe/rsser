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
@Table(name = "feed_entry")
@NamedQueries ( {
	@NamedQuery (name = "feed.entry", query = "SELECT fe FROM FeedEntry fe WHERE fe.feedId = :feedId AND fe.entryId = :entryId"),
	@NamedQuery (name = "entry.ids.for.feed", query = "SELECT fe.entryId FROM FeedEntry fe WHERE fe.feedId = :feedId"),
	@NamedQuery (name = "entry.ids.for.feeds", query = "SELECT fe.entryId FROM FeedEntry fe WHERE fe.feedId in (:feedIds)")
})
public class FeedEntry {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;
	
	private Key entryId;
	
	private Key feedId;

	public FeedEntry() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Key getEntryId() {
		return entryId;
	}

	public void setEntryId(Key entryId) {
		this.entryId = entryId;
	}

	public Key getFeedId() {
		return feedId;
	}

	public void setFeedId(Key feedId) {
		this.feedId = feedId;
	}
	
}
