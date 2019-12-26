package com.dinesh.automatorandroid.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.dinesh.automatorandroid.data.AutomatorHistory;
import com.dinesh.automatorandroid.repository.AutomatorHistoryRepository;

import java.util.List;

public class AutomatorHistoryViewModel extends AndroidViewModel {

    private AutomatorHistoryRepository automatorRepository;
    private LiveData<List<AutomatorHistory>> automators;

    public AutomatorHistoryViewModel(@NonNull Application application){
        super(application);
        automatorRepository = new AutomatorHistoryRepository(application);
        automators = automatorRepository.getAllHisoryAutomators();
    }

    public LiveData<List<AutomatorHistory>> getAllHistoryAutomators() {
        return automators;
    }

}
