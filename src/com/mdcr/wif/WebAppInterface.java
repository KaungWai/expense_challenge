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
			SQLiteHelper db = new SQLiteHelper(mContext);
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
			SQLiteHelper db = new SQLiteHelper(mContext);
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
		SQLiteHelper db = new SQLiteHelper(mContext);
		Category cat = new Category();
		cat.setId(Integer.parseInt(categoryId));
		db.deleteCategory(cat);
		return "1";
	}
	
	@JavascriptInterface
	public void updateCategory(String categoryId,String categoryNewName){
		SQLiteHelper db = new SQLiteHelper(mContext);
		Category cat = new Category();
		cat.setId(Integer.valueOf(categoryId));
		cat.setName(categoryNewName);
		db.updateCategory(cat);
	}
//----------------------------------------------------------------------------------------------------------//
	@JavascriptInterface
	public String addPlan(String name,String startDate, String endDate, int amount){
		SQLiteHelper db = new SQLiteHelper(mContext);
		Plan p = new Plan();
		p.setName(name);
		p.setStartDate(startDate);
		p.setEndDate(endDate);
		p.setAmount(amount);
		p.setStatus(0);
		return db.addPlan(p);
	}
	
	@JavascriptInterface
	public String getCurrentPlan(){
		String ret = "[";
		try{
			SQLiteHelper db = new SQLiteHelper(mContext);
			List<Plan> ps = db.getCurrentPlan();
			int i = 1;
			for (Plan p : ps) {
				JSONObject pObject = new JSONObject();
				pObject.put("id", String.valueOf(p.getId()));
				pObject.put("name", p.getName());
				pObject.put("amount", p.getAmount());
				pObject.put("startDate",p.getStartDate());
				pObject.put("endDate",p.getEndDate());
				
				ret += pObject.toString();
				if(i<ps.size()){
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
	public String getOlderPlans(){
		String ret = "[";
		try{
			SQLiteHelper db = new SQLiteHelper(mContext);
			List<Plan> ps = db.getOlderPlans();
			int i = 1;
			for (Plan p : ps) {
				JSONObject pObject = new JSONObject();
				pObject.put("id", String.valueOf(p.getId()));
				pObject.put("name", p.getName());
				pObject.put("startDate",p.getStartDate());
				pObject.put("endDate",p.getEndDate());
				pObject.put("status", p.getStatus());
				
				ret += pObject.toString();
				if(i<ps.size()){
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
	public String checkCurrentPlan(){
		try{
			SQLiteHelper db = new SQLiteHelper(mContext);
			return String.valueOf(db.checkCurrentPlan());
		}
		catch(Exception e){
			return e.toString();
		}
	}
//----------------------------------------------------------------------------------------------------------//
	@JavascriptInterface
	public String addExpense(String amount, String date, String time, String categoryId, String remark){
		Expense e = new Expense();
		e.setAmount(Float.valueOf(amount));
		e.setDate(date);
		e.setTime(time);
		e.setCategoryId(Integer.valueOf(categoryId));
		e.setRemark(remark);
		
		SQLiteHelper db = new SQLiteHelper(mContext);
		return db.addExpense(e);
	}
	
	@JavascriptInterface
	public String getExpensesByPlanId(String planId){
		String ret = "[";
		try{
			SQLiteHelper db = new SQLiteHelper(mContext);
			List<Expense> es = db.getExpensesByPlanId(Integer.valueOf(planId));
			int i = 1;
			for (Expense e : es) {
				JSONObject eObject = new JSONObject();
				eObject.put("id", String.valueOf(e.getId()));
				eObject.put("amount", e.getAmount());
				eObject.put("date", e.getDate());
				eObject.put("time", e.getTime());
				eObject.put("categoryName", e.getCategoryName());
				eObject.put("remark", e.getRemark());
				
				ret += eObject.toString();
				if(i < es.size()){
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
	public String getGraphDataByPlanId(String planId) throws Exception{
		String ret = "[";
		SQLiteHelper db = new SQLiteHelper(mContext);
		List<GraphData> graphDataList = db.getGraphDataByPlanId(Integer.valueOf(planId));
		
		int i = 1;
		for (GraphData graphData : graphDataList) {
			JSONObject graphDataObject = new JSONObject();
			graphDataObject.put("date", graphData.getDate());
			graphDataObject.put("amount", graphData.getAmount());
			
			ret += graphDataObject.toString();
			if(i < graphDataList.size()){
				ret += ",";
			}
			i++;
		}
		ret += "]";
		return ret;
	}
}