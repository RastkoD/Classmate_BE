package com.iktpreobuka.classmate.services;

import java.util.List;

public interface Dao<T> {
	
	T getById(Long id);
	
	List<T> getAll();
	
	void save(T t);
	
	void create(T t);
	
	void update(T t);
	
	void delete(T t);
}
