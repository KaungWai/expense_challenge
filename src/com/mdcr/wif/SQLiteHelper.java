package com.mdcr.wif;

import java.util.LinkedList;
import java.util.List;

import com.mdcr.wif.Category;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteHelper extends SQLiteOpenHelper {

	// Database Version
	private static final int DATABASE_VERSION = 1;
	// Database Name
	private static final String DATABASE_NAME = "EXPANSE";

	public SQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// SQL statement to create Category table
		String CREATE_CATEGORY_TABLE = "CREATE TABLE CategoryDB (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)";
		String CREATE_PLAN_TABLE = "CREATE TABLE PlanDB (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, startDate TEXT, endDate TEXT, amount INTEGER, status INTEGER)";
		// create category table
		db.execSQL(CREATE_CATEGORY_TABLE);
		db.execSQL(CREATE_PLAN_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older category_db table if existed
		db.execSQL("DROP TABLE IF EXISTS CategoryDB");
		db.execSQL("DROP TABLE IF EXISTS PlanDB");

		// create fresh category_db table
		this.onCreate(db);
	}

	// ---------------------------------------------------------------------

	/**
	 * CRUD operations (create "add", read "get", update, delete) category + get all
	 * categories + delete all categories
	 */
//-----------------------------------------------------------------------//
	// Books table name
	private static final String TABLE_CATEGORIES = "CategoryDB";
	// Books Table Columns names
	private static final String KEY_CATEGORY_ID = "id";
	private static final String KEY_CATEGORY_NAME = "name";
//-----------------------------------------------------------------------//
	// Plan table name
	private static final String TABLE_PLANS = "PlanDB";
	// Plan Table Columns names
	private static final String KEY_PLAN_ID = "id";
	private static final String KEY_PLAN_NAME = "name";
	private static final String KEY_PLAN_START_DATE = "startDate";
	private static final String KEY_PLAN_END_DATE = "endDate";
	private static final String KEY_PLAN_AMOUNT = "amount";
	private static final String KEY_PLAN_STATUS = "status";
//-----------------------------------------------------------------------//
	public void addCategory(Category category) {
		Log.d("addCategory", category.toString());
		// 1. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();

		// 2. create ContentValues to add key "column"/value
		ContentValues values = new ContentValues();
		values.put(KEY_CATEGORY_NAME, category.getName()); // get name

		// 3. insert
		db.insert(TABLE_CATEGORIES, // table
				null, // nullColumnHack
				values); // key/value -> keys = column names/ values = column
							// values

		// 4. close
		db.close();
	}

	// Get All Categories
	public List<Category> getAllCategories() {
		List<Category> categories = new LinkedList<Category>();

		// 1. build the query
		String query = "SELECT  * FROM " + TABLE_CATEGORIES;

		// 2. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		// 3. go over each row, build category and add it to list
		Category category = null;
		if (cursor.moveToFirst()) {
			do {
				category = new Category();
				category.setId(Integer.parseInt(cursor.getString(0)));
				category.setName(cursor.getString(1));

				// Add category to categories
				categories.add(category);
			} while (cursor.moveToNext());
		}

		Log.d("getAllCategories()", categories.toString());

		// return categories
		return categories;
	}

	// Updating single category
	public int updateCategory(Category category) {

		// 1. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();

		// 2. create ContentValues to add key "column"/value
		ContentValues values = new ContentValues();
		values.put("name", category.getName()); // get title

		// 3. updating row
		int i = db.update(TABLE_CATEGORIES, // table
				values, // column/value
				KEY_CATEGORY_ID + " = ?", // selections
				new String[] { String.valueOf(category.getId()) }); // selection
																// args

		// 4. close
		db.close();

		return i;
	}

	// Deleting single category
	public void deleteCategory(Category category) {

		// 1. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();

		// 2. delete
		db.delete(TABLE_CATEGORIES, KEY_CATEGORY_ID + " = ?",
				new String[] { String.valueOf(category.getId()) });

		// 3. close
		db.close();

		Log.d("deleteCategory", category.toString());

	}

	public String addPlan(Plan plan) {
		// 1. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();

		// 2. create ContentValues to add key "column"/value
		ContentValues values = new ContentValues();
		values.put(KEY_PLAN_NAME, plan.getName());
		values.put(KEY_PLAN_START_DATE, plan.getStartDate()); // get startDate
		values.put(KEY_PLAN_END_DATE, plan.getEndDate());
		values.put(KEY_PLAN_AMOUNT, plan.getAmount());
		values.put(KEY_PLAN_STATUS, plan.getStatus());

		// 3. insert
		db.insert(TABLE_PLANS, // table
				null, // nullColumnHack
				values); // key/value -> keys = column names/ values = column
								// values

		// 4. close
		db.close();
		return plan.toString();
	}

	// Get All Plans
	public List<Plan> getCurrentPlan() {
		List<Plan> plans = new LinkedList<Plan>();

		// 1. build the query
		String query = "SELECT  * FROM " + TABLE_PLANS + " WHERE status = 0";

		// 2. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		// 3. go over each row, build category and add it to list
		Plan plan = null;
		if (cursor.moveToFirst()) {
			do {
				plan = new Plan();
				plan.setId(cursor.getInt(0));
				plan.setName(cursor.getString(1));
				plan.setStartDate(cursor.getString(2));
				plan.setEndDate(cursor.getString(3));
				plan.setAmount(cursor.getInt(4));
				plan.setStatus(cursor.getInt(5));

				// Add category to categories
				plans.add(plan);
			} while (cursor.moveToNext());
		}

		Log.d("getAllPlans()", plans.toString());

		// return categories
		return plans;
	}
	
	// Get Older Plans
	public List<Plan> getOlderPlans() {
		List<Plan> plans = new LinkedList<Plan>();

		// 1. build the query
		String query = "SELECT  * FROM " + TABLE_PLANS + " WHERE status != '0'";

		// 2. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		// 3. go over each row, build category and add it to list
		Plan plan = null;
		if (cursor.moveToFirst()) {
			do {
				plan = new Plan();
				plan.setId(cursor.getInt(0));
				plan.setName(cursor.getString(1));
				plan.setStartDate(cursor.getString(2));
				plan.setEndDate(cursor.getString(3));
				plan.setAmount(cursor.getInt(4));
				plan.setStatus(cursor.getInt(5));

				// Add category to categories
				plans.add(plan);
			} while (cursor.moveToNext());
		}

		Log.d("getAllPlans()", plans.toString());

		// return categories
		return plans;
	}	

	// Updating single category
	public int updatePlan(Plan plan) {

		// 1. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();

		// 2. create ContentValues to add key "column"/value
		ContentValues values = new ContentValues();
		values.put("status", plan.getStatus());

		// 3. updating row
		int i = db.update(TABLE_PLANS, // table
				values, // column/value
				KEY_PLAN_ID + " = ?", // selections
				new String[] { String.valueOf(plan.getId()) });

		// 4. close
		db.close();

		return i;

	}
	
	// Check if there is a current plan
	public int checkCurrentPlan(){
		// 1. build the query
		String query = "SELECT  * FROM " + TABLE_PLANS + " WHERE status = '0'";

		// 2. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		
		if(cursor.getCount()>0){
			return 1;
		}
		else{
			return 0;
		}
	}
}