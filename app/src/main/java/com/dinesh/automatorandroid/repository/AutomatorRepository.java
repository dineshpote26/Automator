package com.dinesh.automatorandroid.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.dinesh.automatorandroid.asynctask.DeleteAllAsyncTask;
import com.dinesh.automatorandroid.asynctask.DeleteAsyncTask;
import com.dinesh.automatorandroid.asynctask.InsertAsyncTask;
import com.dinesh.automatorandroid.asynctask.UpdateAsyncTask;
import com.dinesh.automatorandroid.data.Automator;
import com.dinesh.automatorandroid.data.AutomatorDao;
import com.dinesh.automatorandroid.data.AutomatorDatabase;

import java.util.List;

public class AutomatorRepository {

    private AutomatorDao automatorDao;
    private LiveData<List<Automator>> automators;

    public AutomatorRepository(Application application) {

        AutomatorDatabase automatorDatabase = AutomatorDatabase.getInstance(application);
        automatorDao = automatorDatabase.automatorDao();
        automators = automatorDao.getAllAutomators();
    }

    public void insert(Automator automator){
        new InsertAsyncTask(automatorDao).execute(automator);
    }

    public void update(Automator automator){
        new UpdateAsyncTask(automatorDao).execute(automator);
    }

    public void delete(Automator automator){
        new DeleteAsyncTask(automatorDao).execute(automator);
    }

    public void deleteAll(){
        new DeleteAllAsyncTask(automatorDao).execute();
    }

    public LiveData<List<Automator>> getAllAutomators(){
        return automators;
    }

}
