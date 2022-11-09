package by.rss.reader.dao.hibernate;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import by.rss.reader.dao.GenericDAO;

@SuppressWarnings("unchecked")
public class AbstractGenericHibernateDAO<E> extends HibernateDaoSupport implements GenericDAO<E> {

	private Class<E> clazz;

	public AbstractGenericHibernateDAO() {
		ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
		clazz = (Class<E>) parameterizedType.getActualTypeArguments()[0];
	}

	public void delete(E o) {
		getHibernateTemplate().delete(o);
	}

	public List<E> findAll() {
		return getHibernateTemplate().loadAll(clazz);
	}
	
	public List<E> findByExample(E object) {
		return getHibernateTemplate().findByExample(object);
	}

	public E get(Integer id) {
	    return (E) getHibernateTemplate().get(clazz, id);
    }

	public void save(E o) {
		getHibernateTemplate().saveOrUpdate(o);
	}
}
