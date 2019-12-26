package com.dinesh.automatorandroid.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.dinesh.automatorandroid.data.Automator;
import com.dinesh.automatorandroid.repository.AutomatorRepository;
import com.dinesh.automatorandroid.worker.AutomatorWorker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AutomatorViewModel extends AndroidViewModel {

    private AutomatorRepository automatorRepository;
    private LiveData<List<Automator>> automators;

    private WorkManager mWorkManager;

    public AutomatorViewModel(@NonNull Application application){
        super(application);
        automatorRepository = new AutomatorRepository(application);
        mWorkManager =   WorkManager.getInstance(application);
        automators = automatorRepository.getAllAutomators();
    }

    public void insert(Automator automator) {
        automatorRepository.insert(automator);
    }

    public void update(Automator note) {
        automatorRepository.update(note);
    }

    public void delete(Automator note) {
        automatorRepository.delete(note);
    }

    public void deleteAll() {
        automatorRepository.deleteAll();
    }

    public LiveData<List<Automator>> getAllAutomators() {
        return automators;
    }

    public void callOneTimeAutomator(LifecycleOwner lifecycleOwner,String url,String endTime){
        Constraints.Builder builder = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED);

        // Passing params
        Data.Builder data = new Data.Builder();
        data.putString("url", url);

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Date d1 = null;
        Date d2 = null;
        long diff = 0;

        try {
            d1 = new Date();
            d2 = format.parse(endTime);

            diff = d2.getTime() - d1.getTime();

            Log.d("dinesh","diff="+diff);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        OneTimeWorkRequest workRequest =
                new OneTimeWorkRequest.Builder(AutomatorWorker.class)
                        .setInputData(data.build())
                        .setInitialDelay(diff, TimeUnit.MILLISECONDS)
                        .setConstraints(builder.build())
                        .build();
        mWorkManager.enqueue(workRequest);
    }

    public void callDailyAutomator(LifecycleOwner lifecycleOwner,String url,String endTime){
        Constraints.Builder builder = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED);

        // Passing params
        Data.Builder data = new Data.Builder();
        data.putString("url", url);

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Date d1 = null;
        Date d2 = null;
        long diff = 0;

        try {
            d1 = new Date();
            d2 = format.parse(endTime);

            diff = d2.getTime() - d1.getTime();

            Log.d("dinesh","diff="+diff);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        PeriodicWorkRequest.Builder workRequest = new PeriodicWorkRequest.Builder(AutomatorWorker.class,diff,TimeUnit.MILLISECONDS);
        PeriodicWorkRequest myWork = workRequest.build();
        mWorkManager.enqueueUniquePeriodicWork("jobTag", ExistingPeriodicWorkPolicy.KEEP, myWork);
    }
}
