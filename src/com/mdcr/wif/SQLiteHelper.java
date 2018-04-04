package com.mdcr.wif;

import java.io.PrintWriter;
import java.io.StringWriter;
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
		// Category Table
		String CREATE_CATEGORY_TABLE = "CREATE TABLE CategoryDB (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)";
		// Plan Table
		String CREATE_PLAN_TABLE = "CREATE TABLE PlanDB (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, startDate TEXT, endDate TEXT, amount INTEGER, status INTEGER)";
		// Expense Table
		String CREATE_EXPENSE_TABLE = "CREATE TABLE ExpenseDB (id INTEGER PRIMARY KEY AUTOINCREMENT, categoryId INTEGER, planId INTEGER, date TEXT, time TEXT, amount REAL, remark TEXT, FOREIGN KEY (categoryId) REFERENCES CategoryDB (id) , FOREIGN KEY (PlanId) REFERENCES PlanDB(id))";
		
		// create Category table
		db.execSQL(CREATE_CATEGORY_TABLE);
		// create Plan Table
		db.execSQL(CREATE_PLAN_TABLE);
		// create Expense Table
		db.execSQL(CREATE_EXPENSE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older category_db table if existed
		db.execSQL("DROP TABLE IF EXISTS CategoryDB");
		db.execSQL("DROP TABLE IF EXISTS PlanDB");
		db.execSQL("DROP TABLE IF EXISTS ExpenseDB");

		// create fresh category_db table
		this.onCreate(db);
	}

	// ---------------------------------------------------------------------

	/**
	 * CRUD operations (create "add", read "get", update, delete) category + get
	 * all categories + delete all categories
	 */
//----------------------------------------------------------------------------------------------------------//
	// Category table name
	private static final String TABLE_CATEGORIES = "CategoryDB";
	// Category Columns names
	private static final String KEY_CATEGORY_ID = "id";
	private static final String KEY_CATEGORY_NAME = "name";
//----------------------------------------------------------------------------------------------------------//
	// Plan table name
	private static final String TABLE_PLANS = "PlanDB";
	// Plan Columns names
	private static final String KEY_PLAN_ID = "id";
	private static final String KEY_PLAN_NAME = "name";
	private static final String KEY_PLAN_START_DATE = "startDate";
	private static final String KEY_PLAN_END_DATE = "endDate";
	private static final String KEY_PLAN_AMOUNT = "amount";
	private static final String KEY_PLAN_STATUS = "status";
	//------------------------------------------------------------------------//
	// Expense table name
	private static final String TABLE_EXPENSE = "ExpenseDB";
	// Expense Columns names
	private static final String KEY_EXPENSE_ID = "id";
	private static final String KEY_EXPENSE_PLANID = "planId";
	private static final String KEY_EXPENSE_CATEGORYID = "categoryId";
	private static final String KEY_EXPENSE_DATE = "date";
	private static final String KEY_EXPENSE_TIME = "time";
	private static final String KEY_EXPENSE_AMOUNT = "amount";
	private static final String KEY_EXPENSE_REMARK = "remark";
	// -----------------------------------------------------------------------//
	public void createMiscCategory(){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_CATEGORY_NAME, "Misc");
		db.insert(TABLE_CATEGORIES, null,values);
		db.close();
	}
	
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
		
		// 2.1 move expense log to misc
		String updateQuery = "UPDATE ExpenseDB SET " + KEY_EXPENSE_CATEGORYID + " = 1 WHERE " + KEY_EXPENSE_CATEGORYID + " = "+category.getId();
		db.execSQL(updateQuery);
		
		// 2.2 delete
		db.delete(TABLE_CATEGORIES, KEY_CATEGORY_ID + " = ?",
				new String[] { String.valueOf(category.getId()) });

		// 3. close
		db.close();

		Log.d("deleteCategory", category.toString());

	}
	public List<DoughnutData> getDoughnutDataByPlanId(int planId){
		
		List<DoughnutData> doughnutDataList = new LinkedList<DoughnutData>();
		
		// 1. Build the query
		String query = "SELECT E.categoryId, C.name, SUM(E.amount) FROM " + TABLE_EXPENSE + " E LEFT OUTER JOIN " + TABLE_CATEGORIES + " C ON E.categoryId = C.id WHERE E.planId = " + planId + " GROUP BY E.categoryId";
		
		// 2. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		
		// 3. go over each row, build doughnutData and add it to list
		DoughnutData doughnutData = null;
		if (cursor.moveToFirst()) {
			do {
				doughnutData = new DoughnutData();
				doughnutData.setCategoryId(cursor.getInt(0));
				doughnutData.setCategoryName(cursor.getString(1));
				doughnutData.setTotalUsedAmount(cursor.getFloat(2));
				doughnutDataList.add(doughnutData);
			} while (cursor.moveToNext());
		}
		return doughnutDataList;
	}
//----------------------------------------------------------------------------------------------------------//

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

		// return plans
		return plans;
	}

	public float getAmountOfCurrentPlan(){
		float ret = 0.0f;
		String query = "SELECT " + KEY_PLAN_AMOUNT + " FROM " + TABLE_PLANS + " WHERE status = 0";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		if(cursor.moveToFirst()){
			ret = cursor.getFloat(0);
		}
		return ret;
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
	public int checkCurrentPlan() {
		// 1. build the query
		String query = "SELECT id FROM " + TABLE_PLANS + " WHERE status = '0'";

		// 2. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		if (cursor.moveToFirst()) {
			return cursor.getInt(0);
		} else {
			return 0; // there is no current plan
		}
	}
	//---------------------------------------------------------------------//
	// add expenses
	public String addExpense(Expense expense) {
		String ret = "";
		try{
			// 1. get reference to writable DB
			SQLiteDatabase db = this.getWritableDatabase();

			// 2. create ContentValues to add key "column"/value
			ContentValues values = new ContentValues();
			values.put(KEY_EXPENSE_PLANID, checkCurrentPlan());
			values.put(KEY_EXPENSE_CATEGORYID, expense.getCategoryId());
			values.put(KEY_EXPENSE_DATE, expense.getDate());
			values.put(KEY_EXPENSE_TIME, expense.getTime());
			values.put(KEY_EXPENSE_AMOUNT, expense.getAmount());
			values.put(KEY_EXPENSE_REMARK, expense.getRemark());

			// 3. insert
			db.insert(TABLE_EXPENSE, null, values);

			Float totalUsedAmount = getExpenseAmountTotalByPlanId(checkCurrentPlan());
			Float planedAmount = getAmountOfCurrentPlan();
			if(totalUsedAmount>planedAmount){
				ret = "fail";
				db.execSQL("UPDATE " + TABLE_PLANS + " SET " + KEY_PLAN_STATUS + " = 2 WHERE " + KEY_PLAN_STATUS + " = 0");
			}
			else{
				ret = "keep";
			}
			// 4. close
			db.close();
			return ret;
		}
		catch(Exception e){
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			return errors.toString();
		}
	}
	
	// Get expenses by Plan
		public List<Expense> getExpensesByPlanId(int planId) {
			List<Expense> expenses = new LinkedList<Expense>();

			// 1. build the query
			String query = "SELECT E.id, E.planId, E.categoryId, C.name, E.date, E.time, E.amount, E.remark FROM " + TABLE_EXPENSE + " E LEFT OUTER JOIN " + TABLE_CATEGORIES + " C ON E.categoryId = C.id WHERE E.planId = " + planId;

			// 2. get reference to writable DB
			SQLiteDatabase db = this.getWritableDatabase();
			Cursor cursor = db.rawQuery(query, null);

			// 3. go over each row, build expense and add it to list
			Expense expense = null;
			if (cursor.moveToFirst()) {
				do {
					expense = new Expense();
					expense.setId(cursor.getInt(0));
					expense.setPlanId(cursor.getInt(1));
					expense.setCategoryId(cursor.getInt(2));
					expense.setCategoryName(cursor.getString(3));
					expense.setDate(cursor.getString(4));
					expense.setTime(cursor.getString(5));
					expense.setAmount(cursor.getFloat(6));
					expense.setRemark(cursor.getString(7));

					// Add expand to expands
					expenses.add(expense);
				} while (cursor.moveToNext());
			}
			
			// return expenses
			return expenses;
		}

		public Float getExpenseAmountTotalByPlanId(int planId){
			String query = "SELECT SUM(amount) AS amount FROM ExpenseDB WHERE planId = " + planId ;
			SQLiteDatabase db = this.getWritableDatabase();
			Cursor cursor = db.rawQuery(query, null);
			Float retAmount = 0F;
			if (cursor.moveToFirst()) {
				retAmount = cursor.getFloat(0);
			}
			return retAmount;
		}
		
		public List<GraphData> getGraphDataByPlanId(int planId){
			
			List<GraphData> graphDataList = new LinkedList<GraphData>();
			
			// 1. Build the query
			String query = "SELECT SUM(amount) AS amount, date from ExpenseDB WHERE planId = " + planId + " GROUP BY date";
			
			// 2. get reference to writable DB
			SQLiteDatabase db = this.getWritableDatabase();
			Cursor cursor = db.rawQuery(query, null);
			
			// 3. go over each row, build graphData and add it to list
			GraphData graphData = null;
			if (cursor.moveToFirst()) {
				do {
					graphData = new GraphData();
					
					graphData.setAmount(cursor.getFloat(0));
					graphData.setDate(cursor.getString(1));
					
					graphDataList.add(graphData);
				} while (cursor.moveToNext());
			}
			
			return graphDataList;
		}
}
