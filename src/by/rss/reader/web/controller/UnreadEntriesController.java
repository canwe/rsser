package by.rss.reader.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import by.rss.reader.service.EntryService;
import by.rss.reader.service.UserService;

@Controller
@RequestMapping(value = "/unread.html")
public class UnreadEntriesController extends AbstractPreparedController {

	@Autowired
	protected EntryService entryService;
	
	@Autowired
	protected UserService userService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView unreadAction() {
		Map<String, Object> model = new HashMap<String, Object>();

		model.put("entries", entryService.getUnread(userService.getCurrent()));

		return new ModelAndView("unread-entries", model);
	}

}
