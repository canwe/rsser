package by.rss.reader.service;

import java.util.List;

import by.rss.reader.model.User;

public interface UserService {
	void save(User user);
	
	List<User> findAll();

	User getCurrent();
}
