package com.suikajy.debugtool

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.suikajy.debugtool.db.LogBean
import java.text.SimpleDateFormat
import java.util.*

/**
 *
 * @author zjy
 * @date 2018/1/31
 */
class LogAdapter : BaseAdapter() {

    private val mDataSet = ArrayList<LogBean>()

    override fun getItem(position: Int): Any {
        return mDataSet[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return mDataSet.size
    }

    fun refreshData(logList: List<LogBean>) {
        mDataSet.clear()
        mDataSet.addAll(logList)
        notifyDataSetChanged()
    }

    fun loadMoreData(logList: List<LogBean>) {
        mDataSet.addAll(logList)
        notifyDataSetChanged()
    }


    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val holder: ViewHolder
        val returnView: View
        if (convertView == null) {
            returnView = LayoutInflater.from(parent!!.context).inflate(R.layout.item_log, parent, false)
            holder = ViewHolder()
            holder.tvLogNum = returnView.findViewById(R.id.tv_log_num)
            holder.tvLogContent = returnView.findViewById(R.id.tv_log_content)
            holder.tvLogTime = returnView.findViewById(R.id.tv_log_time)
            returnView.tag = holder
        } else {
            returnView = convertView
            holder = returnView.tag as ViewHolder
        }
        val bean = mDataSet[position]
        holder.tvLogNum!!.text = "log NO.$position"
        holder.tvLogTime!!.text = formatTime(bean.createTime)
        holder.tvLogContent!!.text = bean.log
        return returnView
    }

    class ViewHolder {
        var tvLogNum: TextView? = null
        var tvLogTime: TextView? = null
        var tvLogContent: TextView? = null
    }

    @SuppressLint("SimpleDateFormat")
    private fun formatTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("MM-dd hh:mm:ss")
        return format.format(date)
    }
}