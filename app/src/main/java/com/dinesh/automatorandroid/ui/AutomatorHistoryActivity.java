package com.dinesh.automatorandroid.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dinesh.automatorandroid.R;
import com.dinesh.automatorandroid.adapter.AutomatorHistoryAdapter;
import com.dinesh.automatorandroid.data.AutomatorHistory;
import com.dinesh.automatorandroid.viewmodel.AutomatorHistoryViewModel;

import java.util.List;

public class AutomatorHistoryActivity extends AppCompatActivity {


    AutomatorHistoryViewModel automatorHistoryViewModel;
    RecyclerView recyclerView;
    AutomatorHistoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_automator_history);

        initView();

        adapter = new AutomatorHistoryAdapter();
        recyclerView.setAdapter(adapter);

        automatorHistoryViewModel = ViewModelProviders.of(this).get(AutomatorHistoryViewModel.class);

        automatorHistoryViewModel.getAllHistoryAutomators().observe(this, new Observer<List<AutomatorHistory>>() {
            @Override
            public void onChanged(@Nullable List<AutomatorHistory> automators) {
                // update Recycler View
                adapter.setAutomatorHistory(automators);
            }
        });
    }

    private void initView() {
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
    }
}
