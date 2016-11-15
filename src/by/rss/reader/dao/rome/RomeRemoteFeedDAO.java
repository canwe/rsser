package by.rss.reader.dao.rome;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import by.rss.reader.dao.RemoteFeedDAO;
import by.rss.reader.exception.InvalidFeedException;
import by.rss.reader.model.RemoteEntry;
import by.rss.reader.model.RemoteFeed;
import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.fetcher.FeedFetcher;
import com.sun.syndication.fetcher.FetcherException;
import com.sun.syndication.fetcher.impl.HttpURLFeedFetcher;
import com.sun.syndication.io.FeedException;

@Repository("remoteFeedDAO")
public class RomeRemoteFeedDAO implements RemoteFeedDAO {
	
	private String parseContent(SyndContent content) {
		if (content != null) {
			return content.getValue();
		}
		
		return null;
	}

	public RemoteFeed fetch(String url) throws InvalidFeedException {
		FeedFetcher feedFetcher = new HttpURLFeedFetcher();
		feedFetcher.setUsingDeltaEncoding(true);
		
		RemoteFeed remoteFeed = new RemoteFeed();
		
		try {
			SyndFeed feed = feedFetcher.retrieveFeed(new URL(url));
			
			remoteFeed.setUrl(feed.getLink());
			remoteFeed.setTitle(feed.getTitle());
			remoteFeed.setDescription(feed.getDescription());

			remoteFeed.setEntries(new ArrayList<RemoteEntry>());
			
			for (Object o : feed.getEntries()) {
				SyndEntry entry = (SyndEntry) o;
				
				RemoteEntry remoteEntry = new RemoteEntry();
				remoteEntry.setDate(entry.getPublishedDate());
				remoteEntry.setDescription(parseContent(entry.getDescription()));
				remoteEntry.setTitle(entry.getTitle());
				remoteEntry.setUrl(entry.getLink());
				
				remoteFeed.getEntries().add(remoteEntry);
			}
			
			return remoteFeed;
		} catch (MalformedURLException e) {
			throw new InvalidFeedException("Wrong URL", e);
		} catch (IOException e) {
			throw new InvalidFeedException("Cannot retrieve", e);
		} catch (FeedException e) {
			throw new InvalidFeedException("Cannot prepare feed", e);
		} catch (FetcherException e) {
			throw new InvalidFeedException("Cannot fetch", e);
		}
	}

}
