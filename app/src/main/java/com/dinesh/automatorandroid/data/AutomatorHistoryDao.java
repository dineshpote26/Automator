package com.dinesh.automatorandroid.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AutomatorHistoryDao {

    @Insert
    void insert(AutomatorHistory automatorHistory);

    @Delete
    void delete(AutomatorHistory automatorHistory);

    @Update
    void update(AutomatorHistory automatorHistory);

    @Query("DELETE FROM automator_history")
    void deleteAll();

    @Insert
    void insertHistory(AutomatorHistory automatorHistory);

    @Query("SELECT * FROM automator_history")
    LiveData<List<AutomatorHistory>> getHistoryAutomators();
}
