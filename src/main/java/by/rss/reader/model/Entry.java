package by.rss.reader.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Text;

@Entity
@NamedQueries ({
	@NamedQuery (name = "entries.for.feed", query = "SELECT e FROM Entry e WHERE e.feed = :feed"),
	@NamedQuery (name = "entries.by.ids", query = "SELECT e FROM Entry e WHERE e.id in (:ids)")
})
public class Entry {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Key id;
	
	protected String url;
	protected Text title;
	protected Text description;
	protected Date date;
	
	//@ManyToOne
	//@JoinColumn(name = "id_feed")
	//protected Feed feed;
	
	//protected List<User> readBy;

	public Key getId() {
		return id;
	}

	public void setId(Key id) {
		this.id = id;
	}
	
	public String getKey() {
		return KeyFactory.keyToString(id);
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

	public Date getDate() {
		return date;
	}
	
	public String getDateString() {
		return "" + date;
	}
	
	public String getTitleString() {
		return null == title ? "" : title.getValue();
	}
	
	public String getDescriptionString() {
		return null == description ? "" : description.getValue();
	}

	public void setDate(Date date) {
		this.date = date;
	}

//	public Feed getFeed() {
//		return feed;
//	}
//
//	public void setFeed(Feed feed) {
//		this.feed = feed;
//	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (id != null ? id.hashCode() : 0);
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
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
		
		if (!(obj instanceof Entry)) {
			return false;
		}
		
		Entry other = (Entry) obj;
		
		if (id == null) {
			if (other.id != null) return false;
		}
		else if (!id.equals(other.id)) return false;
		
		if (url == null) {
			if (other.url != null) return false;
		}
		else if (!url.equals(other.url)) return false;
		
		if (title == null) {
			if (other.title != null) return false;
		}
		else if (!title.equals(other.title)) return false;
		
		if (description == null) {
			if (other.description != null) return false;
		}
		else if (!description.equals(other.description)) return false;
		
		if (date == null) {
			if (other.date != null) return false;
		}
		else if (!date.equals(other.date)) return false;
		
		return true;
	}

	@Override
	public String toString() {
		return "Entry [id=" + id + ", url=" + url + ", title=" + title
				+ ", description=" + description + ", date=" + date + "]";
	}
}
