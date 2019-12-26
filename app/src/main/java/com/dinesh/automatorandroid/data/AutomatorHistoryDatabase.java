package com.dinesh.automatorandroid.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {AutomatorHistory.class}, version = 1, exportSchema = false)
public abstract class AutomatorHistoryDatabase extends RoomDatabase {

    public abstract AutomatorHistoryDao automatorDao();

    private static AutomatorHistoryDatabase instance;

    public static synchronized AutomatorHistoryDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, AutomatorHistoryDatabase.class, "automator_history")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
