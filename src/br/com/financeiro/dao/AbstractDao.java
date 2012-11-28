package br.com.financeiro.dao;

import java.util.List;

public interface AbstractDao<T> {
	void save(T entity);
	void update(T entity);
	void saveOrUpdate(T entity);
	void delete(T entity);
	T find(long id);
	List<T> findAll();
}
