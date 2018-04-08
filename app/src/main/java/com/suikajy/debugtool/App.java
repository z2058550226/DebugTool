package com.suikajy.debugtool;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.suikajy.debugtool.db.DaoMaster;
import com.suikajy.debugtool.db.DaoSession;
import com.suikajy.debugtool.db.LogBean;

import java.util.List;

/**
 * @author zjy
 * @date 2018/1/31
 */

public class App extends Application {

    public static App instance;
    public static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initDataBase();
    }

    private void initDataBase() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "lvshige.db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public static void insertLog(LogBean bean) {
        daoSession.getLogBeanDao().insert(bean);
    }

    public static List<LogBean> queryAll() {
        return daoSession.getLogBeanDao().queryBuilder().list();
    }

    public static void clearLog() {
        daoSession.getLogBeanDao().deleteAll();
    }
}
