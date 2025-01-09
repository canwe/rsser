package by.rss.reader.dao.rome;

import by.rss.reader.dao.RemoteFeedDAO;
import by.rss.reader.exception.InvalidFeedException;
import by.rss.reader.model.RemoteEntry;
import by.rss.reader.model.RemoteFeed;
import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;
import org.xml.sax.InputSource;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static org.apache.commons.lang.StringUtils.defaultString;

@Repository("remoteFeedDAO")
public class RomeRemoteFeedDAO implements RemoteFeedDAO {
	
	private String parseContent(SyndContent content) {
		if (content != null) {
			return content.getValue();
		}
		
		return null;
	}

	public RemoteFeed fetch(final String url) throws InvalidFeedException {
		RemoteFeed remoteFeed = new RemoteFeed();
		
		try {
			SyndFeedInput syndInput = new SyndFeedInput();
			SyndFeed feed = syndInput.build(new InputSource(new URL(url).openStream()));
			
			remoteFeed.setUrl(defaultString(feed.getLink(), url));
			remoteFeed.setTitle(defaultString(feed.getTitle(), ""));
			remoteFeed.setDescription(defaultString(feed.getDescription(), ""));

			remoteFeed.setEntries(new ArrayList<>());
			
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
		}
	}

}
