package com.example.suppliesManagementApp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "supplies")
public class Supplies {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	@NotNull
	private long id;
	
	@Column(nullable = false)
	@NotBlank
	private String supplies_name;
	
	@ManyToOne
	@JoinColumn(name = "category_id", nullable = false)
	private SuppliesCategory category;
	
	@Column
	private String holder;
	
	@Column(nullable = false)
	@NotBlank
	private String assets_number;
	
	// コンストラクタ
	public Supplies() {}

	public Supplies(String name, SuppliesCategory category, String holder, String assetsNumber) {
		super();
		this.supplies_name = name;
		this.category = category;
		this.holder = holder;
		this.assets_number = assetsNumber;
	}

	// ゲッター
	public long getId() {
		return id;
	}

	public String getSupplies_name() {
		return supplies_name;
	}
	
	public SuppliesCategory getCategory() {
		return category;
	}

	public String getHolder() {
		return holder;
	}

	public String getAssets_number() {
		return assets_number;
	}

	// セッター
	public void setId(long id) {
		this.id = id;
	}

	public void setSupplies_name(String supplies_name) {
		this.supplies_name = supplies_name;
	}

	public void setCategory(SuppliesCategory category) {
		this.category = category;
	}

	public void setHolder(String holder) {
		this.holder = holder;
	}

	public void setAssets_number(String assets_number) {
		this.assets_number = assets_number;
	}
}
