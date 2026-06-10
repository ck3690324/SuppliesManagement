package com.example.suppliesManagementApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.suppliesManagementApp.model.SuppliesCategory;

@Repository
public interface SuppliesCategoryRepository extends JpaRepository<SuppliesCategory, Long>{
}
