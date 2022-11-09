package by.rss.reader.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

import by.rss.reader.model.User;
import by.rss.reader.service.EntryService;
import by.rss.reader.service.UserService;

public class AbstractPreparedController {
	@Autowired
	private EntryService entryService;
	
	@Autowired
	private UserService userService;
	
    @ModelAttribute("unreadEntries")
    public Integer populateUnreadEntriesCounter() {
    	User user = userService.getCurrent();
    	
        return user != null ? entryService.getUnread(user).size() : 0;
    }
}
