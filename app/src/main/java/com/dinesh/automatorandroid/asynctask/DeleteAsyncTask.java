package com.dinesh.automatorandroid.asynctask;

import android.os.AsyncTask;

import com.dinesh.automatorandroid.data.Automator;
import com.dinesh.automatorandroid.data.AutomatorDao;

public class DeleteAsyncTask extends AsyncTask<Automator,Void,Void> {

    private AutomatorDao automatorDao;

    public DeleteAsyncTask(AutomatorDao automatorDao){
        this.automatorDao = automatorDao;
    }

    @Override
    protected Void doInBackground(Automator... notes) {
        automatorDao.delete(notes[0]);
        return null;
    }
}
