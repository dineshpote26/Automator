package com.dinesh.automatorandroid.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AutomatorDao {

    @Insert
    void insert(Automator automator);

    @Delete
    void delete(Automator automator);

    @Update
    void update(Automator automator);

    @Query("DELETE FROM automator_list")
    void deleteAll();

    @Query("SELECT * FROM automator_list")
    LiveData<List<Automator>> getAllAutomators();
}
