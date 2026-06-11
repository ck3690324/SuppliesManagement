package com.example.suppliesManagementApp.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.suppliesManagementApp.dao.SuppliesCategoryDAOImpl;
import com.example.suppliesManagementApp.model.SuppliesCategory;
import com.example.suppliesManagementApp.repository.SuppliesCategoryRepository;

import jakarta.transaction.Transactional;

@Controller
public class SuppliesCategoryController {
	@Autowired
	SuppliesCategoryRepository categoryRepository;
	
	@Autowired
	SuppliesCategoryDAOImpl dao;

	/**
	 * 初期アクセス
	 * カテゴリ一覧
	 * @param category カテゴリ
	 * @param mav ビュー
	 * @return カテゴリ一覧画面
	 */
	@RequestMapping(value = "/category")
	public ModelAndView index(@ModelAttribute("categoryFormModel") SuppliesCategory category, ModelAndView mav) {
		mav.setViewName("category/index");
		mav.addObject("title", "備品カテゴリ管理");
		
		// カテゴリ取得
		Iterable<SuppliesCategory> list = dao.getAll();
		mav.addObject("data", list);
		
		// 遷移
		return mav;
	}
	
	/**
	 * 備品カテゴリ追加
	 * @param category 備品カテゴリ
	 * @param result 実行結果
	 * @param mav ビュー
	 * @return 一覧画面・エラー画面
	 */
	@RequestMapping(value = "/category", method = RequestMethod.POST)
	@Transactional
	public ModelAndView form(@ModelAttribute("categoryFormModel") @Validated SuppliesCategory category, BindingResult result, ModelAndView mav) {
		// 遷移先
		ModelAndView res = null;
		
		// エラーなかった場合はリダイレクト
		if(!result.hasErrors()) {
			categoryRepository.saveAndFlush(category);
			
			res = new ModelAndView("redirect:/category");
		}
		// エラー画面
		else {
			mav.setViewName("category/index");
			mav.addObject("title", "備品カテゴリ管理");
			
			// カテゴリ取得
			Iterable<SuppliesCategory> list = dao.getAll();
			mav.addObject("data", list);
			
			// 遷移先をセット
			res = mav;
		}
		// 遷移先を返す
		return res;
	}
	
	/**
	 * カテゴリ編集画面遷移
	 * @param category カテゴリ
	 * @param id カテゴリID
	 * @param mav ビュー
	 * @return 編集画面
	 */
	@RequestMapping(value = "/category/edit/{id}", method = RequestMethod.GET)
	public ModelAndView edit(@ModelAttribute SuppliesCategory category, @PathVariable int id, ModelAndView mav) {
		mav.setViewName("category/edit");
		mav.addObject("title", "備品カテゴリ編集");
		
		// カテゴリ取得
		Optional<SuppliesCategory> data = categoryRepository.findById((long) id);
		mav.addObject("categoryFormModel", data.get());
		
		// 遷移
		return mav;
	}
	
	/**
	 * 編集送信処理
	 * @param category 変更後の備品カテゴリ
	 * @param result 変更結果
	 * @param mav ビュー
	 * @return 一覧画面・エラー画面
	 */
	@RequestMapping(value = "/category/edit", method = RequestMethod.POST)
	@Transactional
	public ModelAndView update(@ModelAttribute("categoryFormModel") @Validated SuppliesCategory category, BindingResult result, ModelAndView mav) {
		// 遷移先
		ModelAndView res = null;
		
		// 弾かれない場合
		if (!result.hasErrors()) {
			categoryRepository.saveAndFlush(category);
			
			// 遷移先→カテゴリ一覧
			res = new ModelAndView("redirect:/category");
		}
		// 弾かれた場合
		else {
			// 遷移先
			mav.setViewName("/category/edit");			
			mav.addObject("title", "備品カテゴリ編集");
			
			// 遷移先をセット
			res = mav;
		}
		return res;
	}
	
	/**
	 * カテゴリ削除画面遷移
	 * @param id カテゴリID
	 * @param mav ビュー
	 * @return 確認画面
	 */
	@RequestMapping(value = "/category/delete/{id}", method = RequestMethod.GET)
	public ModelAndView delete(@PathVariable int id, ModelAndView mav) {
		// 遷移先
		mav.setViewName("category/delete");
		mav.addObject("title", "備品カテゴリ削除");
		
		// カテゴリ詳細取得
		Optional<SuppliesCategory> data = categoryRepository.findById((long) id);
		mav.addObject("categoryFormModel", data.get());
		
		// 遷移
		return mav;
	}
	
	@RequestMapping(value = "/category/delete", method = RequestMethod.POST)
	@Transactional
	public ModelAndView remove(@RequestParam long id, ModelAndView mav) {
		// 削除処理
		categoryRepository.deleteById(id);
		
		// カテゴリ一覧に戻る
		return new ModelAndView("redirect:/category");
	}
}
