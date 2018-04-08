package com.suikajy.debugtool.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author zjy
 * @date 2018/1/31
 */
@Entity
public class LogBean {

    @Id(autoincrement = true)
    private Long id;

    private String log;

    private Long createTime;

    @Generated(hash = 662581681)
    public LogBean(Long id, String log, Long createTime) {
        this.id = id;
        this.log = log;
        this.createTime = createTime;
    }

    @Generated(hash = 1457050494)
    public LogBean() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLog() {
        return this.log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public Long getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}
