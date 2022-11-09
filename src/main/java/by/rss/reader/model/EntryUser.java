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
@Table(name = "entry_read_by_user")
@NamedQueries ( {
		@NamedQuery (name = "all.user.ids.readed.entry", query = "SELECT eu.userId FROM EntryUser eu WHERE eu.entryId = :entryId"),
		@NamedQuery (name = "all.entry.ids.readed.by.user", query = "SELECT eu.entryId FROM EntryUser eu WHERE eu.userId = :userId"),
		@NamedQuery (name = "entry.readed", query = "SELECT eu FROM EntryUser eu WHERE eu.userId = :userId AND eu.entryId = :entryId")
})
public class EntryUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;
	
	private Key entryId;
	
	private Long userId;

	public EntryUser() {
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
}
