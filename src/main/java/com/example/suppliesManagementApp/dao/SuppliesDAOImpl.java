package com.example.suppliesManagementApp.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.suppliesManagementApp.model.Supplies;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Repository
public class SuppliesDAOImpl implements SuppliesDAO<Supplies> {
	private static final long serialVersionUID = 1L;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public SuppliesDAOImpl() {}

	/**
	 * 全備品取得
	 * idはビューに非表示にするため
	 * ソート順は資産番号の降順になります
	 */
	@Override
	public List<Supplies> getAll() {
		// 一覧リストの初期化
		List<Supplies> list = null;
		
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Supplies> query = builder.createQuery(Supplies.class);
		Root<Supplies> root = query.from(Supplies.class);
		
		// 取得 & ソート
		query.select(root).orderBy(builder.desc(root.get("assets_number")));
		list = entityManager.createQuery(query).getResultList();
		return list;
	}

	/**
	 * 備品のID検索
	 */
	@Override
	public Supplies findById(long id) {
		return (Supplies) entityManager.createQuery("from Supplies where id = " + id).getSingleResult();
	}
}
