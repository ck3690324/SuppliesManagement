package com.example.suppliesManagementApp.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "supplies_category")
public class SuppliesCategory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	@NotNull
	private long id;
	
	@Column(nullable = false)
	@NotBlank
	private String category_name;
	
	/*
	 * 備品テーブルのカテゴリと紐づけ
	 * リストの中に備品が入っている
	 */
	@OneToMany(mappedBy = "category")
    private List<Supplies> supplies;
	
	// コンストラクタ
	public SuppliesCategory() {}
	
	public SuppliesCategory(String name) {
		super();
		this.category_name = name;
	}

	// ゲッター
	public long getId() {
		return id;
	}

	public String getCategory_name() {
		return category_name;
	}

	// セッター
	public void setId(long id) {
		this.id = id;
	}

	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}
	
	// 備品数用のアクセッサーメソッド
	public List<Supplies> getSupplies() {
        return supplies;
    }

    public void setSupplies(List<Supplies> supplies) {
        this.supplies = supplies;
    }
}
