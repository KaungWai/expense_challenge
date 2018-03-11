package com.mdcr.wif;

import com.mdcr.myexpansechallenge.Home;
import com.mdcr.myexpansechallenge.Category;
import com.mdcr.myexpansechallenge.Plan;
import com.mdcr.myexpansechallenge.Log;


import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.webkit.JavascriptInterface;

public class WebAppInterface {
	Context mContext;
	ContentResolver mContentResolver;
	
	public WebAppInterface(Context c,ContentResolver cr) {
		mContext = c;
		mContentResolver = cr;
	}
	
	@JavascriptInterface
	public String clickHome(){
		String ret = "";
		try{
			((Activity)mContext).finish();
		}
		catch(Exception e){
			ret = e.toString();
		}
		return ret;
	}
	@JavascriptInterface
	public String clickCategory(){
		String ret = "";
		try{
			Intent i = new Intent(mContext,Category.class);
			i.setFlags(i.FLAG_ACTIVITY_NO_HISTORY);
			mContext.startActivity(i);
		}
		catch(Exception e){
			ret = e.toString();
		}
		return ret;
	}
	@JavascriptInterface
	public String clickPlan(){
		String ret = "";
		try{
			Intent i = new Intent(mContext,Plan.class);
			i.setFlags(i.FLAG_ACTIVITY_NO_HISTORY);
			mContext.startActivity(i);
		}
		catch(Exception e){
			ret = e.toString();
		}
		return ret;
	}
	@JavascriptInterface
	public String clickLog(){
		String ret = "";
		try{
			Intent i = new Intent(mContext,Log.class);
			i.setFlags(i.FLAG_ACTIVITY_NO_HISTORY);
			mContext.startActivity(i);
		}
		catch(Exception e){
			ret = e.toString();
		}
		return ret;
	}
}