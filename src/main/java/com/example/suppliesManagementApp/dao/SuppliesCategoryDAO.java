package com.example.suppliesManagementApp.dao;

import java.io.Serializable;
import java.util.List;

public interface SuppliesCategoryDAO<T> extends Serializable {
	public List<T> getAll();
	public T findById(long id);
}
