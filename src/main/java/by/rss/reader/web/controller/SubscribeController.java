package by.rss.reader.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import by.rss.reader.exception.InvalidFeedException;
import by.rss.reader.model.Feed;
import by.rss.reader.model.User;
import by.rss.reader.service.EntryService;
import by.rss.reader.service.FeedService;
import by.rss.reader.service.UserService;

@RequestMapping(value = "/subscribe.html")
public class SubscribeController extends SimpleFormController {
	
	@Autowired
	protected FeedService feedService;
	
	@Autowired
	protected EntryService entryService;	
	
	@Autowired
	protected UserService userService;
	
	@Override
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
		super.initBinder(request, binder);
		
		binder.setAllowedFields(new String[] {
				"feedUrl"
		});
	}
	
	@Override
	protected ModelAndView onSubmit(Object command, BindException errors) throws Exception {
		Feed feed = (Feed) command;
	
		try {
			feed = feedService.subscribe(userService.getCurrent(), feed.getFeedUrl());
		} catch (InvalidFeedException e) {
			errors.reject("subscribe.url.invalid");
			
			return new ModelAndView(getFormView(), errors.getModel());
		}
		
		return new ModelAndView("redirect:feed.html?feedId=" + feed.getKey());
	}
	
	@Override
	protected Map referenceData(HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		
		User user = userService.getCurrent();
		model.put("unreadEntries", user != null ? entryService.getUnread(user).size() : 0);
		
		return model;
	}
	
}
