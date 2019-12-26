package com.dinesh.automatorandroid.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "automator_history")
public class AutomatorHistory {

    @PrimaryKey(autoGenerate = true)
    int id;

    @ColumnInfo(name = "jsonData")
    private String jsonData;

    @ColumnInfo(name = "addedDate")
    private String date;

    public AutomatorHistory(String jsonData,String date) {
        this.jsonData = jsonData;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJsonData() {
        return jsonData;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }
}
