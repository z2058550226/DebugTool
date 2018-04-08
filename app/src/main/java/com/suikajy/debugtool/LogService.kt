package com.suikajy.debugtool

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.suikajy.debugtool.db.LogBean

/**
 *
 * @author zjy
 * @date 2018/1/31
 */
class LogService : Service() {

    private var mLogCallback: ILogCallback? = null

    private val mBinder = object : ILogAppInterface.Stub() {
        override fun onSendLog(log: String?) {
            if (mLogCallback != null) {
                mLogCallback!!.onLogCallback(log)
            }
            val logBean = LogBean(null, log, System.currentTimeMillis())
            App.insertLog(logBean)
            App.daoSession.logBeanDao.queryBuilder().list()
        }

        override fun setLogCallback(callback: ILogCallback?) {
            mLogCallback = callback
        }
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onBind(intent: Intent?): IBinder {
        return mBinder
    }
}