package by.rss.reader.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.SimpleFormController;

import by.rss.reader.model.User;
import by.rss.reader.service.UserService;

@RequestMapping(value = "/register.html")
public class RegisterController extends SimpleFormController {
	
	@Autowired
	protected UserService userService;
	
	@Override
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
		super.initBinder(request, binder);
		
		binder.setAllowedFields(new String[] {
				"login",
				"password",
				"confirmPassword",
				"email"
		});
		
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	@Override
	protected void doSubmitAction(Object command) throws Exception {
		userService.save((User) command);
	}

}
