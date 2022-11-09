package by.rss.reader.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import by.rss.reader.model.Entry;
import by.rss.reader.model.Feed;
import by.rss.reader.service.EntryService;
import by.rss.reader.service.FeedService;
import by.rss.reader.service.UserService;

@Controller
@RequestMapping(value = "/feed.html")
public class FeedController extends AbstractPreparedController {
	
	@Autowired
	protected FeedService feedService;
	
	@Autowired
	protected EntryService entryService;
	
	@Autowired
	protected UserService userService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView feedsAction(HttpServletResponse response, @RequestParam(required = false, value = "feedId") String id) {
		if (id == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);

			return null;
		}

		Map<String, Object> model = new HashMap<String, Object>();

		Feed feed = feedService.get(id);
		model.put("feed", feed);
		List<Entry> feedEntries = feedService.get(feed);
		model.put("feedEntries", feedEntries);
		model.put("readEntries", entryService.getRead(userService.getCurrent(), id));

		return new ModelAndView("feed", model);
	}

}
