package com.mdcr.wif;

import java.util.List;

import org.json.JSONObject;

import android.content.ContentResolver;
import android.content.Context;
import android.webkit.JavascriptInterface;

public class WebAppInterface {
	Context mContext;
	ContentResolver mContentResolver;
	
	public WebAppInterface(Context c,ContentResolver cr) {
		mContext = c;
		mContentResolver = cr;
	}
	
	@JavascriptInterface
	public String addCategory(String categoryName){
		try{
			MySQLiteHelper db = new MySQLiteHelper(mContext);
			db.addCategory(new Category(categoryName));
			return "1";
		}
		catch(Exception e){
			return "0";
		}
	}
	
	@JavascriptInterface
	public String getAllCategories(){
		String ret = "[";
		try{
			MySQLiteHelper db = new MySQLiteHelper(mContext);
			List<Category> cats = db.getAllCategories();
			int i = 1;
			for (Category cat : cats) {
				JSONObject catObject = new JSONObject();
				catObject.put("id", String.valueOf(cat.getId()));
				catObject.put("name", cat.getName());
				
				ret += catObject.toString();
				if(i<cats.size()){
					ret += ",";
				}
				i++;
			}
			ret += "]";
			return ret;
		}
		catch(Exception e){
			return e.toString();
		}
	}
	
	@JavascriptInterface
	public String deleteCategory(String categoryId){
		MySQLiteHelper db = new MySQLiteHelper(mContext);
		Category cat = new Category();
		cat.setId(Integer.parseInt(categoryId));
		db.deleteCategory(cat);
		return "1";
	}
}