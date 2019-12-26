package com.dinesh.automatorandroid.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.dinesh.automatorandroid.R;
import com.dinesh.automatorandroid.adapter.AutomatorAdapter;
import com.dinesh.automatorandroid.data.Automator;
import com.dinesh.automatorandroid.viewmodel.AutomatorViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class AutomatorActivity extends AppCompatActivity implements View.OnClickListener, AutomatorAdapter.onItemClickListner{


    AutomatorViewModel automatorViewModel;
    RecyclerView recyclerView;
    AutomatorAdapter automatorAdapter;
    FloatingActionButton floatingActionButton;

    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_automator);

        initView();
        
        onSwipeDelete();

        automatorAdapter = new AutomatorAdapter();
        recyclerView.setAdapter(automatorAdapter);

        automatorViewModel = ViewModelProviders.of(this).get(AutomatorViewModel.class);

        automatorViewModel.getAllAutomators().observe(this, new Observer<List<Automator>>() {
            @Override
            public void onChanged(@Nullable List<Automator> automators) {
                // update Recycler View
                automatorAdapter.setAutomator(automators);
            }
        });

        floatingActionButton.setOnClickListener(this);
        automatorAdapter.setOnItemClickListner(this);
    }

    private void onSwipeDelete() {

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                automatorViewModel.delete(automatorAdapter.getAutmatorAt(viewHolder.getAdapterPosition()));
                Toast.makeText(AutomatorActivity.this, "auomator Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);
    }

    private void initView() {
        floatingActionButton = findViewById(R.id.add_floatingButton);
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.add_floatingButton:
                Intent intent = new Intent(AutomatorActivity.this,AddEditAutomatorActivity.class);
                startActivityForResult(intent,ADD_NOTE_REQUEST);
                break;
        }
    }

    @Override
    public void onItemClick(Automator automator) {
        Intent intent = new Intent(AutomatorActivity.this,AddEditAutomatorActivity.class);
        intent.putExtra("id",automator.getId());
        intent.putExtra("url",automator.getUrl());
        intent.putExtra("time",automator.getScheduleTime());
        startActivityForResult(intent,EDIT_NOTE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK){
            String url = data.getStringExtra("url");
            String time = data.getStringExtra("time");
            int scheduler = data.getIntExtra("scheduler",1);

            automatorViewModel.insert(new Automator(url,time));

            if(scheduler == 1){
                automatorViewModel.callOneTimeAutomator(this,url,time);
            }else if(scheduler == 2){
                automatorViewModel.callDailyAutomator(this,url,time);
            }


            Toast.makeText(this, "Automator saved", Toast.LENGTH_SHORT).show();
        }
        else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK){
            String url = data.getStringExtra("url");
            String time = data.getStringExtra("time");
            int id = data.getIntExtra("id",0);
            int scheduler = data.getIntExtra("scheduler",1);

            Automator note = new Automator(url,time);
            note.setId(id);
            automatorViewModel.update(note);

            if(scheduler== 1){
                automatorViewModel.callOneTimeAutomator(this,url,time);
            }else if(scheduler == 2){
                automatorViewModel.callDailyAutomator(this,url,time);
            }

            //automatorViewModel.callOneTimeAutomator(this,url,time);

            Toast.makeText(this, "automator Updated", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "automator not saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.automator_history, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.history) {
            Intent openHistory = new Intent(AutomatorActivity.this,AutomatorHistoryActivity.class);
            startActivity(openHistory);
        }
        return true;
    }

}
