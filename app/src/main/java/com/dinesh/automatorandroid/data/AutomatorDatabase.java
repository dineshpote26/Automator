package com.dinesh.automatorandroid.data;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Automator.class}, version = 1, exportSchema = false)
public abstract class AutomatorDatabase extends RoomDatabase {

    public abstract AutomatorDao automatorDao();

    private static AutomatorDatabase instance;

    public static synchronized AutomatorDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context,AutomatorDatabase.class,"automator_list")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void> {
        private AutomatorDao automatorDao;
        private PopulateDbAsyncTask(AutomatorDatabase noteDatabase){
            this.automatorDao = noteDatabase.automatorDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            automatorDao.insert(new Automator("Url 1","12-02-2019 20:10"));
            return null;
        }
    }

}
