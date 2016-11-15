package by.rss.reader.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import by.rss.reader.web.validator.profiles.Profiles;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Text;

@Entity
@NamedQueries ({
	@NamedQuery (name = "feed.by.url", query = "SELECT f FROM Feed f WHERE f.feedUrl = :url")
})
public class Feed {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Key id;
	
	protected String url;
	
	@NotNull(errorCode = "feedUrl.notEmpty", profiles = { Profiles.SUBSCRIBE })
	@NotEmpty(errorCode = "feedUrl.notEmpty", profiles = { Profiles.SUBSCRIBE })
	protected String feedUrl;
	
	protected Text title;
	protected Text description;
	protected Date lastUpdate;

	//@OneToMany(fetch = FetchType.LAZY, mappedBy = "feed")
	//@OrderBy(value = "date desc")
	//protected List<Entry> entries;

	//protected List<User> subscribedBy;
	
	public Key getId() {
		return id;
	}
	
	public String getKey() {
		return KeyFactory.keyToString(id);
	}

	public void setId(Key id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Text getTitle() {
		return title;
	}

	public void setTitle(Text title) {
		this.title = title;
	}

	public Text getDescription() {
		return description;
	}

	public void setDescription(Text description) {
		this.description = description;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public String getFeedUrl() {
		return feedUrl;
	}

	public void setFeedUrl(String feedUrl) {
		this.feedUrl = feedUrl;
	}
	
	public String getTitleString() {
		return null == title ? "" : title.getValue();
	}

	public String getDescriptionString() {
		return null == description ? "" : description.getValue();
	}

//	public List<Entry> getEntries() {
//		return entries;
//	}
//
//	public void setEntries(List<Entry> entries) {
//		this.entries = entries;
//	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (id != null ? id.hashCode() : 0);
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		if (obj == null) {
			return false;
		}
		
		if (!(obj instanceof Feed)) {
			return false;
		}
		
		Feed other = (Feed) obj;
		
		if (id == null) {
			if (other.id != null) return false;
		}
		else if (!id.equals(other.id)) return false;
		
		if (url == null) {
			if (other.url != null) return false;
		}
		else if (!url.equals(other.url)) return false;
		
		return true;
	}
	
	@Override
	public String toString() {
		return "id=" + id + ", title=" + title;
	}
}
