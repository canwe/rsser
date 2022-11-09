package by.rss.reader.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.rss.reader.dao.UserDAO;
import by.rss.reader.model.User;
import by.rss.reader.service.UserService;

@Transactional
@Service("userService")
public class UserServiceImpl implements UserService, UserDetailsService {

	private Logger logger = Logger.getLogger(UserServiceImpl.class);
	
	@Autowired
	protected UserDAO userDAO;
	
	public void save(User user) {
		userDAO.save(user);
	}
	
	public User getCurrent() {
		Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if (logger.isDebugEnabled()) {
			logger.debug("Getting current user [" + object + "]");
		}
		
		if (object instanceof User) {
			return userDAO.get(((User) object).getId());
		} else {
			return null;
		}
	}

	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException, DataAccessException {
		if (logger.isDebugEnabled()) {
			logger.debug("Trying to authenticate [login=" + login + "]");
		}
		
		User user = userDAO.getByLogin(login);

		if (logger.isDebugEnabled()) {
			logger.debug("Trying to authenticate " + user);
		}
		
		if (user == null) {
			logger.info("User [login=" + login + "] does not exists");
			
			throw new UsernameNotFoundException("User [" + login + "] not found");
		}
		
		user.setAuthorities(new GrantedAuthority[] { new GrantedAuthorityImpl("ROLE_USER") });

		if (logger.isDebugEnabled()) {
			logger.debug("Authorization result [login=" + login + "] roles " + user.getAuthorities());
		}
		
		return user;
	}

	public List<User> findAll() {
		return userDAO.findAll();
	}

}
