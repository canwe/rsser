package by.rss.reader.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import by.rss.reader.dao.UserDAO;
import by.rss.reader.dao.jpa.GenericDAOJPA;
import by.rss.reader.model.User;

import static by.rss.reader.service.WrapperMethods.$;

@Repository("userDAO")
public class JPAUserDAO extends GenericDAOJPA<User, Long> implements UserDAO {

	@SuppressWarnings("unchecked")
	public User getByLogin(String login) {
		Map<String, Object> params = new HashMap<String, Object>(1);
		params.put("login", login);
		List<User> users = $(getJpaTemplate().findByNamedQueryAndNamedParams("user.by.login", params));
		return users.size() > 0 ? users.get(0) : null;
	}

}
