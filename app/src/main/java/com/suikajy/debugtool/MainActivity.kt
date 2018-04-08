package com.suikajy.debugtool

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val LOG_CALLBACK = 0x21
    private val mAdapter = LogAdapter()
    private lateinit var mLogInterfaceImp: ILogAppInterface

    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lv_log.adapter = mAdapter
        val lvParent = lv_log.parent as ViewGroup
        val emptyView = layoutInflater.inflate(R.layout.layout_no_log, lvParent,false)
        lvParent.addView(emptyView)
        lv_log.emptyView = emptyView
        btn_clear.setOnClickListener({
            App.clearLog()
            updateLogList()
        })
        bindTheService()
    }

    private fun bindTheService() {
        val intent = Intent(this, LogService::class.java)
        bindService(intent, mConn, Context.BIND_AUTO_CREATE)
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(mConn)
    }

    override fun onResume() {
        super.onResume()
        updateLogList()
    }

    private val mConn = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {

        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            mLogInterfaceImp = ILogAppInterface.Stub.asInterface(service)
            mLogInterfaceImp.setLogCallback(mLogCallback)
        }
    }

    private val mLogCallback = object : ILogCallback.Stub() {

        override fun onLogCallback(log: String?) {
            val message = Message.obtain()
            message.what = LOG_CALLBACK
            message.obj = log
            mHandler.sendMessage(message)
        }
    }

    private val mHandler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message?) {
            if (msg != null) {
                if (msg.what == LOG_CALLBACK) {
                    val log: String = msg.obj as String
                    Log.e("****** TAG ******", "传递过来的log是：$log")
                    updateLogList()
                }
            }
        }
    }

    private fun updateLogList() {
        val list = App.queryAll()
        mAdapter.refreshData(list)
    }
}
