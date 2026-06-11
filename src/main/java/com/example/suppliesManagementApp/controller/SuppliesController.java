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

import com.example.suppliesManagementApp.dao.SuppliesDAOImpl;
import com.example.suppliesManagementApp.model.Supplies;
import com.example.suppliesManagementApp.repository.SuppliesCategoryRepository;
import com.example.suppliesManagementApp.repository.SuppliesRepository;

import jakarta.transaction.Transactional;

@Controller
public class SuppliesController {
	@Autowired
	SuppliesRepository repository;
	
	@Autowired
	SuppliesCategoryRepository categoryRepository;
	
	@Autowired
	SuppliesDAOImpl dao;
	
	/**
	 * 初期アクセス
	 * 備品一覧
	 * @param Supplies 備品
	 * @param mav ビュー
	 * @return 一覧画面
	 */
	@RequestMapping(value="/")
	public ModelAndView index(@ModelAttribute("formModel") Supplies Supplies, ModelAndView mav) {
		mav.setViewName("index");
		mav.addObject("title", "備品管理");
		
		// カテゴリ一覧取得(セレクトリスト用)
		mav.addObject("categories", categoryRepository.findAll());
		
		// 備品リスト取得
		Iterable<Supplies> list = dao.getAll();
		mav.addObject("data", list);
		
		// 遷移
		return mav;
	}
	
	/**
	 * 備品追加
	 * @param Supplies 備品
	 * @param result 実行結果
	 * @param mav ビュー
	 * @return 一覧画面・エラー画面
	 */
	@RequestMapping(value = "/", method = RequestMethod.POST)
	@Transactional
	public ModelAndView form(@ModelAttribute("formModel") @Validated Supplies Supplies, BindingResult result, ModelAndView mav) {
		// 遷移先
		ModelAndView res = null;
		
		// 登録エラー出力(コンソール)
//		System.out.println(result.getFieldErrors());
		
		// エラーなかった場合リダイレクト
		if (!result.hasErrors()) {
			repository.saveAndFlush(Supplies);
			
			// 遷移先→ルート
			res = new ModelAndView("redirect:/");
		}
		// エラー画面
		else {
			mav.setViewName("index");
			mav.addObject("title", "備品登録");
			
			// カテゴリ一覧取得(セレクトリスト用)
			mav.addObject("categories", categoryRepository.findAll());
			
			// 備品リスト取得
			Iterable<Supplies> list = repository.findAll();
			mav.addObject("data", list);
			
			// 遷移先をセット
			res = mav;
		}
		// 遷移先を返す
		return res;
	}
	
	/**
	 * 編集画面遷移
	 * @param Supplies 備品
	 * @param id 備品ID
	 * @param mav ビュー
	 * @return 編集画面
	 */
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView edit(@ModelAttribute Supplies Supplies, @PathVariable int id, ModelAndView mav) {
		mav.setViewName("edit");
		mav.addObject("title", "備品詳細編集");
		
		// カテゴリ一覧取得(セレクトリスト用)
		mav.addObject("categories", categoryRepository.findAll());
		
		// 備品詳細取得
		Optional<Supplies> data = repository.findById((long) id);
		mav.addObject("formModel", data.get());
		
		// 遷移
		return mav;
	}
	
	/**
	 * 編集送信処理
	 * @param Supplies変更後の備品
	 * @param result 変更結果
	 * @param mav ビュー
	 * @return 一覧画面・エラー画面
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@Transactional
	public ModelAndView update(@ModelAttribute("formModel") @Validated Supplies Supplies, BindingResult result, ModelAndView mav) {
		// 遷移先
		ModelAndView res = null;
		
		// 登録エラー出力(コンソール)
//		System.out.println(result.getFieldErrors());
		
		// 弾かれない場合
		if (!result.hasErrors()) {
			repository.saveAndFlush(Supplies);
			
			// 遷移先→ルート
			res = new ModelAndView("redirect:/");
		}
		// 弾かれた場合
		else {
			// 遷移先
			mav.setViewName("edit");
			
			mav.addObject("title", "備品詳細編集");
			
			// カテゴリ一覧取得(セレクトリスト用)
			mav.addObject("categories", categoryRepository.findAll());
			
			// 備品詳細取得
			mav.addObject("formModel", Supplies);
			
			// 遷移先をセット
			res = mav;
		}
		// 遷移先を返す
		return res;
	}
	
	/**
	 * 削除画面遷移
	 * @param id 備品ID
	 * @param mav ビュー
	 * @return 確認画面
	 */
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public ModelAndView delete(@PathVariable int id, ModelAndView mav) {
		// 遷移先
		mav.setViewName("delete");
		
		mav.addObject("title", "備品削除");
		mav.addObject("msg", "確認してから削除");
		
		// 備品詳細取得
		Optional<Supplies> data = repository.findById((long) id);
		mav.addObject("formModel", data.get());
		
		// 遷移
		return mav;
	}

	/**
	 * 削除送信処理
	 * @param id 備品ID
	 * @param mav ビュー
	 * @return 一覧画面
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@Transactional
	public ModelAndView remove(@RequestParam long id, ModelAndView mav) {
		// 削除処理
		repository.deleteById(id);
		
		//　一覧に戻る
		return new ModelAndView("redirect:/");
	}
	
//	/**
//	 * ダミーデータ初期化
//	 */
//	@PostConstruct
//	public void init() {
//		// カテゴリ
//		SuppliesCategory sc1 = categoryRepository.saveAndFlush(new SuppliesCategory("PC本体"));
//		SuppliesCategory sc2 = categoryRepository.saveAndFlush(new SuppliesCategory("周辺機器"));
//		SuppliesCategory sc3 = categoryRepository.saveAndFlush(new SuppliesCategory("消耗品"));
//		
//		// 備品
//		Supplies s1 = repository.saveAndFlush(new Supplies("PC1", sc1, "テストユーザー0", "20260609001"));
//		Supplies s2 = repository.saveAndFlush(new Supplies("PC1用充電器", sc2, "テストユーザー0", "20260609002"));
//		Supplies s3 = repository.saveAndFlush(new Supplies("PC1用キーボード", sc2, "テストユーザー0", "20260609003"));	
//		Supplies s4 = repository.saveAndFlush(new Supplies("PC1用マウス", sc2, "テストユーザー0", "20260609004"));
//	}
}
