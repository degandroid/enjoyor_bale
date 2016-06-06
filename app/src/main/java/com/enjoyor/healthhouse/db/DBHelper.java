package com.enjoyor.healthhouse.db;


import android.content.Context;
import android.util.Log;

import com.enjoyor.healthhouse.bean.BMI;
import com.enjoyor.healthhouse.bean.HealthRecord;
import com.enjoyor.healthhouse.bean.UserInfo;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class DBHelper {

    private DatabaseHelper mDBHelper;

    public DBHelper(Context context) {
        mDBHelper = OpenHelperManager.getHelper(context, DatabaseHelper.class);
    }


    public boolean saveUser(UserInfo user) {
//        clearUser();
        try {
            Dao<UserInfo, Integer> dao = mDBHelper.getDao(UserInfo.class);
            dao.createOrUpdate(user);
            Log.i("sql", "DBHelper--->>---saveUser success");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public UserInfo getUser() {

        try {
            Dao<UserInfo, Integer> dao = mDBHelper.getDao(UserInfo.class);
            UserInfo user = dao.queryForId(1);
            if (user == null) {
                Log.i("sql", "DBHelper getUser null");
                return null;
            }
            Log.i("sql", "getUser toString：" + user.toString());
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean clearUser() {
        try {
            Dao<UserInfo, Integer> dao = mDBHelper.getDao(UserInfo.class);
            dao.delete(getUser());
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean saveHealthReport(HealthRecord healthRecord) {
        try {
            Dao<HealthRecord, Integer> dao = mDBHelper.getDao(HealthRecord.class);
            dao.createOrUpdate(healthRecord);
            Log.i("sql", "DBHelper--->>---saveUser success");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public HealthRecord getHealthRecord() {
        try {
            Dao<HealthRecord, Integer> dao = mDBHelper.getDao(HealthRecord.class);
            HealthRecord healthRecord = dao.queryForId(1);
            if (healthRecord == null) {
                Log.i("sql", "DBHelper getUser null");
                return null;
            }
            Log.i("sql", "getUser toString：" + healthRecord.toString());
            return healthRecord;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }



    public boolean saveBMI(BMI bmi) {
        try {
            Dao<BMI, Integer> dao = mDBHelper.getDao(BMI.class);
            dao.createOrUpdate(bmi);
            Log.i("bmi", "DBHelper--->>---saveUser success");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<BMI> getBMI() {
        try {
            Dao<BMI, Integer> dao = mDBHelper.getDao(BMI.class);
            List<BMI> _bmi = dao.queryForAll();
            if (_bmi == null) {
                Log.i("bmi", "DBHelper getUser null");
                return null;
            }
            Log.i("bmi", "getUser toString：" + _bmi.toString());
            return _bmi;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean clearBMI() {
        try {
            Dao<BMI, Integer> dao = mDBHelper.getDao(BMI.class);
                dao.delete(getBMI());
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
