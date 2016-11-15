package by.rss.reader.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import by.rss.reader.service.UserService;

@Controller
@RequestMapping(value = "/index.html")
public class IndexController extends AbstractPreparedController {
	
	@Autowired
	protected UserService userService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String indexAction() {
		return userService.getCurrent() != null ? "index-authorized" : "index-guest";
	}
	
}
