package com.example.suppliesManagementApp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.suppliesManagementApp.model.Supplies;

@Repository
public interface SuppliesRepository extends JpaRepository<Supplies, Long> {
	@Query("SELECT d FROM Supplies d ORDER BY d.id")
	List<Supplies> findAllOrderById();
	
	@Override
	public Optional<Supplies> findById(Long id);
}
