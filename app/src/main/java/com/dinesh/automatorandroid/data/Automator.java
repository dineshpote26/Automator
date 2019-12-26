package com.dinesh.automatorandroid.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "automator_list")
public class Automator {

    @PrimaryKey(autoGenerate = true)
    int id;

    @ColumnInfo(name = "url")
    private String url;

    @ColumnInfo(name = "scheduleTime")
    private String scheduleTime;

    public Automator(String url, String scheduleTime) {
        this.url = url;
        this.scheduleTime = scheduleTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getScheduleTime() {
        return scheduleTime;
    }

    public void setScheduleTime(String scheduleTime) {
        this.scheduleTime = scheduleTime;
    }
}
