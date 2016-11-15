package by.rss.reader.dao;

import by.rss.reader.model.User;

public interface UserDAO extends JPAGenericDAO<User, Long> {

	User getByLogin(String login);
	
}
