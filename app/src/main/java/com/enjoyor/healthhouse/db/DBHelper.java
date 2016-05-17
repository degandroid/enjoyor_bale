package com.enjoyor.healthhouse.db;


import android.content.Context;
import android.util.Log;

import com.enjoyor.healthhouse.bean.UserInfo;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import java.sql.SQLException;
public class DBHelper {

	private DatabaseHelper mDBHelper;
	public DBHelper(Context context) {
		mDBHelper = OpenHelperManager.getHelper(context, DatabaseHelper.class);
	}


    public boolean saveUser(UserInfo user){
        try {
            Dao<UserInfo,Integer> dao = mDBHelper.getDao(UserInfo.class);
            dao.createOrUpdate(user);
            Log.i("sql", "DBHelper--->>---saveUser success");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public UserInfo getUser(){
        try {
            Dao<UserInfo,Integer> dao = mDBHelper.getDao(UserInfo.class);
            UserInfo user = dao.queryForId(1);
            if (user == null) {
                Log.i("sql","DBHelper getUser null");
                return null;
            }
            Log.i("sql","getUser toString："+user.toString());
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
