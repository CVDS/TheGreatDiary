package cedric.tom.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteDB extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "diary.db";
	private static final int DATABASE_VERSION = 1;

	// Database creation sql statement
	private static final String DATABASE_CREATE = 
		"CREATE TABLE Entry (ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE, Date DATE NOT NULL, Content TEXT NOT NULL, PhotoID INTEGER REFERENCES Photo (ID));"
		+ "CREATE TABLE Note (ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE, Title TEXT, Content TEXT NOT NULL);"
		+ "CREATE TABLE Photo (ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE, Title TEXT, Source TEXT NOT NULL UNIQUE);"
		+ "CREATE TABLE User (Name TEXT NOT NULL, Password TEXT NOT NULL);";
			  
			  
	public SQLiteDB(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
  	public void onCreate(SQLiteDatabase database) {
		database.execSQL("CREATE TABLE Entry (ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE, Date DATE NOT NULL, Content TEXT NOT NULL, PhotoID INTEGER REFERENCES Photo (ID));");
		database.execSQL("CREATE TABLE Note (ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE, Title TEXT, Content TEXT NOT NULL);");
		database.execSQL("CREATE TABLE Photo (ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE, Title TEXT, Source TEXT NOT NULL UNIQUE);");
		database.execSQL("CREATE TABLE User (Name TEXT NOT NULL, Password TEXT NOT NULL);");
  	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//Niet nodig
	}
}