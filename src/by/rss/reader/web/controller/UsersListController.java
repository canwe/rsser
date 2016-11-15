package by.rss.reader.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import by.rss.reader.service.UserService;

@Controller
@RequestMapping(value = "/admin/users/list.html")
public class UsersListController {
	
	@Autowired
	protected UserService userService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String indexAction(ModelMap model) {
		model.addAttribute("users", userService.findAll());
		
		return "usersList";
	}
}
