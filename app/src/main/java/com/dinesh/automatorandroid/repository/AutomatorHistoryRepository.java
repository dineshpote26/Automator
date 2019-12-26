package com.dinesh.automatorandroid.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.dinesh.automatorandroid.data.AutomatorHistory;
import com.dinesh.automatorandroid.data.AutomatorHistoryDao;
import com.dinesh.automatorandroid.data.AutomatorHistoryDatabase;

import java.util.List;

public class AutomatorHistoryRepository {

    private AutomatorHistoryDao historyDao;
    private LiveData<List<AutomatorHistory>> listLiveData;

    public AutomatorHistoryRepository(Application application) {

        AutomatorHistoryDatabase automatorDatabase = AutomatorHistoryDatabase.getInstance(application);
        historyDao = automatorDatabase.automatorDao();
        listLiveData = historyDao.getHistoryAutomators();
    }


    public LiveData<List<AutomatorHistory>> getAllHisoryAutomators(){
        return listLiveData;
    }

}
