package com.mdcr.wif;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class PlanSQLiteHelper extends SQLiteOpenHelper {
	
	// Database Version
	private static final int DATABASE_VERSION = 1;
	// Database Name
	private static final String DATABASE_NAME = "EXPANSE";

	public PlanSQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// SQL statement to create Plan table
		String CREATE_PLAN_TABLE = "CREATE TABLE PlanDB (id INTEGER PRIMARY KEY AUTOINCREMENT, statDate NUMERIC, endDate NUMERIC, amount INTEGER, status TEXT)";

		// create Plan table
		db.execSQL(CREATE_PLAN_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older plan_db table if existed
		db.execSQL("DROP TABLE IF EXISTS PlanDB");

		// create fresh plan_db table
		this.onCreate(db);
	}

	// ---------------------------------------------------------------------

	/**
	* CRUD operations (create "add", read "get", update, delete) plan + get all
	*/

	// Plan table name
	private static final String TABLE_PLAN = "PlanDB";

	// Plan Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_StartDate = "startDate";
	private static final String KEY_EndDate = "endDate";
	private static final String KEY_Amount = "amount";
	private static final String KEY_Status = "status";
		

	private static final String[] COLUMNS = { KEY_ID, KEY_StartDate, KEY_EndDate, KEY_Amount, KEY_Status};

	public void addCategory(Plan plan) {
		Log.d("addPlan", plan.toString());
		// 1. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();

		// 2. create ContentValues to add key "column"/value
		ContentValues values = new ContentValues();
		values.put(KEY_StartDate, plan.getStartDate().toString()); // get startDate
		values.put(KEY_EndDate, plan.getEndDate().toString());
		values.put(KEY_Amount, plan.getAmount());
		values.put(KEY_Status, plan.getStatus());

		// 3. insert
		db.insert(TABLE_PLAN, // table
				null, // nullColumnHack
				values); // key/value -> keys = column names/ values = column
								// values

		// 4. close
		db.close();
	}

	// Get All Books
	public List<Plan> getAllPlan() {
		List<Plan> plans = new LinkedList<Plan>();

		// 1. build the query
		String query = "SELECT  * FROM " + TABLE_PLAN;

		// 2. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		// 3. go over each row, build category and add it to list
		Plan plan = null;
		if (cursor.moveToFirst()) {
			do {
				plan = new Plan();
				plan.setId(Integer.parseInt(cursor.getString(0)));
				plan.setStartDate(Date.valueOf(cursor.getString(1)));
				plan.setEndDate(Date.valueOf(cursor.getString(2)));
				plan.setAmount(Integer.parseInt(cursor.getString(3)));
				plan.setStatus(cursor.getString(4));

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
		values.put("status", plan.getStatus()); // get title

		// 3. updating row
		int i = db.update(TABLE_PLAN, // table
				values, // column/value
				KEY_ID + " = ?", // selections
				new String[] { String.valueOf(plan.getId()) }); // selection
																	// args

		// 4. close
		db.close();

		return i;

	}

}
