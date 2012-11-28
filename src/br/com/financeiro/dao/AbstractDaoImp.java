package br.com.financeiro.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;

public class AbstractDaoImp<T> {

	private Class<T> clazz;
	@Inject
	private SessionFactory sessionFactory;
	protected Session s;
	protected Criteria criteria;
	protected Transaction tx;

	@SuppressWarnings("unchecked")
	public AbstractDaoImp() {
		clazz = (Class<T>) getType();
	}
	
	public void beginTransaction(){
		s = sessionFactory.openSession();
		tx = s.getTransaction();
		tx.begin();
	}
	
	public void joinSession(Session session){
		s = session;
		tx = session.getTransaction();
	}
	
	public void commit(){
		//s.getTransaction().commit();
		tx.commit();
	}
	
	public void rollback(){
		//s.getTransaction().rollback();
		tx.rollback();
	}
	
	public void closeTransaction(){
		s.close();		
	}
	
	public void commitAndCloseTransaction(){
		commit();
		closeTransaction();
	}
	
	public void flush(){
		s.flush();
	}	
	
	public void save(T entity) {		
		s.save(entity);
	}


	public void update(T entity) {		
		s.update(entity);		
	}

	public void saveOrUpdate(T entity) {		
		s.saveOrUpdate(entity);		
	}


	public void delete(T entity) {		
		s.delete(entity);		
	}

	@SuppressWarnings("unchecked")
	public T find(long id) {		
		T claz = (T) s.get(clazz, id);		
		return claz;
	}

	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		List<T> retorno;		
		criteria = s.createCriteria(clazz);
		criteria.addOrder(Order.asc("id"));
		retorno = (List<T>) criteria.list();		
		return retorno;
	}

	private Type getType() {
		ParameterizedType superclass = (ParameterizedType) getClass()
				.getGenericSuperclass();
		return superclass.getActualTypeArguments()[0];
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public Session getSession() {
		return s;
	}
}
