package by.rss.reader.dao.jpa;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

import javax.jdo.annotations.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.orm.jpa.JpaTemplate;

import by.rss.reader.EMFService;
import by.rss.reader.dao.JPAGenericDAO;


/**
 * JPA implementation of the GenericDAO
 * 
 * @author <a href="mailto:jeff@firebyte.org">Jeff Beard</a>
 *
 */
@SuppressWarnings("deprecation")
public class GenericDAOJPA<T, ID extends Serializable> implements JPAGenericDAO<T, ID>  {

	private final Class<T> persistentClass;
	
	private final JpaTemplate jpaTemplate;
	
	@SuppressWarnings("unchecked")
	public GenericDAOJPA() {
		this.persistentClass = (Class<T>) ((ParameterizedType) 
			getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		this.jpaTemplate = new JpaTemplate(getEntityManager());
	}

	public GenericDAOJPA(final Class<T> persistentClass) {
		super();
		this.persistentClass = persistentClass;
		this.jpaTemplate = new JpaTemplate(getEntityManager());
	}

	/*
	 * (non-Javadoc)
	 * @see org.firebyte.dao.GenericRepository#countAll()
	 */
	@Override
	public int countAll() {
		List<?> l = jpaTemplate.find("SELECT COUNT (entityClass) FROM " + persistentClass.getName() + " entityClass");
		if (null != l && l.size() == 1) {
			Integer i = (Integer) l.get(0);
			return i == null ? 0 : i; 
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * @see org.firebyte.dao.GenericRepository#findAll()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		return (List<T>) jpaTemplate.find("SELECT  FROM " + persistentClass.getName());
	}

	/*
	 * (non-Javadoc)
	 * @see org.firebyte.dao.GenericRepository#findById(java.io.Serializable)
	 */
	@Override
	public T get(final ID id) {
		final T result = jpaTemplate.find(persistentClass, id);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see org.firebyte.dao.GenericRepository#findByNamedQuery(java.lang.String, java.lang.Object[])
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByNamedQuery(final String name, Object... params) {
		final List<T> result = (List<T>) jpaTemplate.findByNamedQuery(name, params);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see org.firebyte.dao.GenericRepository#findByNamedQueryAndNamedParams(java.lang.String, java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByNamedQueryAndNamedParams(final String name,
			final Map<String, ? extends Object> params) {
		
		EntityManager em = getEntityManager();
		Query query = em.createNamedQuery(name);

		for (final Map.Entry<String, ? extends Object> param : params.entrySet()) {
			query.setParameter(param.getKey(), param.getValue());
		}

		final List<T> result = (List<T>) query.getResultList();
		
		em.close();
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see org.firebyte.dao.GenericRepository#save(java.lang.Object)
	 */
	@Override
	@Transactional
	public T save(T entity) {
		EntityManager em = getEntityManager();
		final T savedEntity = em.merge(entity);
		em.persist(savedEntity);
		em.close();
		return savedEntity;
	}

	/*
	 * (non-Javadoc)
	 * @see org.firebyte.dao.GenericRepository#delete(java.lang.Object)
	 */
	@Override
	public void remove(T entity) {
		EntityManager em = getEntityManager();
		em.remove(entity);
		em.close();
	}

	/*
	 * (non-Javadoc)
	 * @see org.firebyte.repository.GenericRepository#remove(java.io.Serializable)
	 */
	@Override
	public void remove(ID id) {
		EntityManager em = getEntityManager();
		em.remove(this.get(id));
		em.close();
	}

	/*
	 * (non-Javadoc)
	 * @see org.firebyte.dao.GenericRepository#getEntityClass()
	 */
	@Override
	public Class<T> getEntityClass() {
		return persistentClass;
	}

	public EntityManager getEntityManager() {
		return EMFService.get().createEntityManager();
	}
	
	public JpaTemplate getJpaTemplate() {
		return new JpaTemplate(getEntityManager());
	}
}
