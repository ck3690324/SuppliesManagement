package com.example.suppliesManagementApp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.suppliesManagementApp.model.SuppliesCategory;

@Repository
public interface SuppliesCategoryRepository extends JpaRepository<SuppliesCategory, Long>{
	@Query("SELECT d FROM SuppliesCategory d ORDER BY d.id")
	List<SuppliesCategory> findAllOrderById();
	
	@Override
	public Optional<SuppliesCategory> findById(Long id);
}
