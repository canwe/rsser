package by.rss.reader.dao;

import java.util.List;

public interface GenericDAO<E> {
	E get(Integer id);
	List<E> findAll();
	List<E> findByExample(E example);
	void save(E o);
	void delete(E o);
}
