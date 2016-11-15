package by.rss.reader.web.controller;

import java.util.Map;

import net.sf.json.spring.web.servlet.view.JsonView;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import by.rss.reader.service.EntryService;
import by.rss.reader.service.UserService;

@Controller
public class FeedAjaxController {

	private Logger logger = Logger.getLogger(FeedAjaxController.class);
	
	@Autowired
	protected EntryService entryService;
	
	@Autowired
	protected UserService userService;

	@RequestMapping(value = "/feed/read.json", method = RequestMethod.GET)
	public ModelAndView readAction(@RequestParam(required = true, value = "entryId") String entryId) {
		if (logger.isDebugEnabled()) {
			logger.debug("Marking as read [entryId=" + entryId + "]");
		}
		
		entryService.markAsRead(userService.getCurrent(), entryId);

		return getJsonView(null);
	}

	@RequestMapping(value = "/feed/unread.json", method = RequestMethod.GET)
	public ModelAndView unreadAction(@RequestParam(required = true, value = "entryId") String entryId) {
		if (logger.isDebugEnabled()) {
			logger.debug("Marking as unread [entryId=" + entryId + "]");
		}
		
		entryService.markAsUnread(userService.getCurrent(), entryId);

		return getJsonView(null);
	}

	@RequestMapping(value = "/feed/readAll.json", method = RequestMethod.GET)
	public ModelAndView readAllAction(@RequestParam(required = true, value = "feedId") String feedId) {
		if (logger.isDebugEnabled()) {
			logger.debug("Marking as read [entryId=" + feedId + "]");
		}
		
		entryService.markAllAsRead(userService.getCurrent(), feedId);

		return getJsonView(null);
	}

	@RequestMapping(value = "/feed/unreadAll.json", method = RequestMethod.GET)
	public ModelAndView unreadAllAction(@RequestParam(required = true, value = "feedId") String feedId) {
		if (logger.isDebugEnabled()) {
			logger.debug("Marking as unread [entryId=" + feedId + "]");
		}
		
		entryService.markAllAsUnread(userService.getCurrent(), feedId);

		return getJsonView(null);
	}

	
	protected ModelAndView getJsonView(Map<String, Object> model) {
		JsonView jsonView = new JsonView();
	
		// TODO: add charset
		
		return new ModelAndView(jsonView, model);
	}

}
