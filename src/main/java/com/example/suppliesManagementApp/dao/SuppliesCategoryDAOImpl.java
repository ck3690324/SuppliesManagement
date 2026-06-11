package com.example.suppliesManagementApp.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.suppliesManagementApp.model.SuppliesCategory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Repository
public class SuppliesCategoryDAOImpl implements SuppliesCategoryDAO<SuppliesCategory> {
	private static final long serialVersionUID = 1L;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public SuppliesCategoryDAOImpl() {}
	
	/**
	 * 全カテゴリ取得
	 */
	@Override
	public List<SuppliesCategory> getAll() {
		// 一覧リストの初期化
		List<SuppliesCategory> list = null;
		
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<SuppliesCategory> query = builder.createQuery(SuppliesCategory.class);
		Root<SuppliesCategory> root = query.from(SuppliesCategory.class);
		query.select(root).orderBy(builder.desc(root.get("id")));
		list = entityManager.createQuery(query).getResultList();
		return list;
	}

	/**
	 * カテゴリID検索
	 */
	@Override
	public SuppliesCategory findById(long id) {
		return (SuppliesCategory) entityManager.createQuery("from SuppliesCategory where id = " + id);
	}
}
