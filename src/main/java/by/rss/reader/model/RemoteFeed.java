package by.rss.reader.model;

import java.util.List;

public class RemoteFeed {
	protected String url;
	protected String title;
	protected String description;
	protected List<RemoteEntry> entries;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<RemoteEntry> getEntries() {
		return entries;
	}

	public void setEntries(List<RemoteEntry> entries) {
		this.entries = entries;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
