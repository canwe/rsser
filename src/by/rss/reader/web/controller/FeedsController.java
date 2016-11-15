package by.rss.reader.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import by.rss.reader.service.FeedService;
import by.rss.reader.service.UserService;

@Controller
@RequestMapping(value = "/feeds.html")
public class FeedsController extends AbstractPreparedController {
	
	@Autowired
	protected FeedService feedService;
	
	@Autowired
	protected UserService userService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView feedsAction() {
		Map<String, Object> model = new HashMap<String, Object>();
		
		model.put("feeds", feedService.findSubscribed(userService.getCurrent()));
		
		return new ModelAndView("feeds", model);
	}
	
}
