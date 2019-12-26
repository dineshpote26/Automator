package com.dinesh.automatorandroid.asynctask;

import android.os.AsyncTask;

import com.dinesh.automatorandroid.data.AutomatorDao;

public class DeleteAllAsyncTask  extends AsyncTask<Void,Void,Void> {

    private AutomatorDao automatorDao;

    public DeleteAllAsyncTask(AutomatorDao automatorDao){
        this.automatorDao = automatorDao;
    }


    @Override
    protected Void doInBackground(Void... voids) {
        automatorDao.deleteAll();
        return null;
    }
}
