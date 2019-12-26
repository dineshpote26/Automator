package com.dinesh.automatorandroid.asynctask;

import android.os.AsyncTask;
import android.util.Log;

import com.dinesh.automatorandroid.data.AutomatorHistory;
import com.dinesh.automatorandroid.data.AutomatorHistoryDao;

public class InsertHistoryAsyncTask extends AsyncTask<AutomatorHistory,Void,Void> {

    private AutomatorHistoryDao automatorDao;

    public InsertHistoryAsyncTask(AutomatorHistoryDao automatorDao){
        this.automatorDao = automatorDao;
    }

    @Override
    protected Void doInBackground(AutomatorHistory... notes) {
        automatorDao.insertHistory(notes[0]);
        Log.d("dinesh","insert table");
        return null;
    }
}