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

public class MySQLiteHelper extends SQLiteOpenHelper {

	// Database Version
	private static final int DATABASE_VERSION = 1;
	// Database Name
	private static final String DATABASE_NAME = "EXPANSE";

	public MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// SQL statement to create Category table
		String CREATE_CATEGORY_TABLE = "CREATE TABLE CategoryDB (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)";

		// create books table
		db.execSQL(CREATE_CATEGORY_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older category_db table if existed
		db.execSQL("DROP TABLE IF EXISTS CategoryDB");

		// create fresh category_db table
		this.onCreate(db);
	}

	// ---------------------------------------------------------------------

	/**
	 * CRUD operations (create "add", read "get", update, delete) category + get all
	 * categories + delete all categories
	 */

	// Books table name
	private static final String TABLE_CATEGORIES = "CategoryDB";

	// Books Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_NAME = "name";

	private static final String[] COLUMNS = { KEY_ID, KEY_NAME};

	public void addCategory(Category category) {
		Log.d("addCategory", category.toString());
		// 1. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();

		// 2. create ContentValues to add key "column"/value
		ContentValues values = new ContentValues();
		values.put(KEY_NAME, category.getName()); // get name

		// 3. insert
		db.insert(TABLE_CATEGORIES, // table
				null, // nullColumnHack
				values); // key/value -> keys = column names/ values = column
							// values

		// 4. close
		db.close();
	}

	// Get All Books
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
				KEY_ID + " = ?", // selections
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
		db.delete(TABLE_CATEGORIES, KEY_ID + " = ?",
				new String[] { String.valueOf(category.getId()) });

		// 3. close
		db.close();

		Log.d("deleteCategory", category.toString());

	}
}