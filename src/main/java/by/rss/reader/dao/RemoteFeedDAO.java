package by.rss.reader.dao;

import by.rss.reader.exception.InvalidFeedException;
import by.rss.reader.model.RemoteFeed;

public interface RemoteFeedDAO {
	RemoteFeed fetch(String url) throws InvalidFeedException;
}
