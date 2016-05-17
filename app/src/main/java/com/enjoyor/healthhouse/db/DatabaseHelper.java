package com.enjoyor.healthhouse.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

import com.enjoyor.healthhouse.bean.UserInfo;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

	private Context mContext;
	public static final String DB_NAME = "balehealth.db";
	
	//	public static final int DB_VERSION = DegApplication.getInstance().getDBVersion();
	public static final int DB_VERSION = 1;

	public DatabaseHelper(Context context) {

		this(context, DB_NAME, null, DB_VERSION);
		this.mContext = context;

	}

	public DatabaseHelper(Context context, String databaseName,
						  CursorFactory factory, int databaseVersion) {
		super(context, databaseName, factory, databaseVersion);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource cs) {
		// TODO Auto-generated method stub
		try {
			TableUtils.createTableIfNotExists(cs, UserInfo.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase sd, ConnectionSource cs, int arg2,
						  int arg3) {
		// TODO Auto-generated method stub
		try {
			Log.i("zxw", "---->onUpgrade>>");
			TableUtils.dropTable(cs, UserInfo.class, true);
			onCreate(sd, cs);

		} catch (Exception e) {
			e.printStackTrace();
		}


	}

}
