package com.dinesh.automatorandroid.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.dinesh.automatorandroid.R;

import java.util.Calendar;

public class AddEditAutomatorActivity extends AppCompatActivity {

    private EditText text_url;
    private EditText text_time;

    private RadioGroup radioGroup;
    private RadioButton radioButton;

    private int mYear, mMonth, mDay, mHour, mMinute;

    String scheduleTime = "";

    int selectedValue = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_automator);

        text_url = findViewById(R.id.text_url);
        text_time = findViewById(R.id.text_time);

        radioGroup = findViewById(R.id.radioGroup);

       // getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow);

        Intent intent = getIntent();
        if (intent.hasExtra("id")) {
            setTitle("Edit Automator");
            text_url.setText(intent.getStringExtra("url").replace("http://demo0017680.mockable.io/",""));
            text_time.setText(intent.getStringExtra("time"));
        } else {
            setTitle("Add Automator");
        }

        text_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                radioButton = (RadioButton) findViewById(checkedId);

                if(radioButton.getText().equals("OneTime")){
                    selectedValue = 1;
                }else if(radioButton.getText().equals("Daily")){
                    selectedValue = 2;
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.automator_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.save_note) {
            saveAutomator();
        }else if(id == android.R.id.home){
            onBackPressed();
        }
        return true;
    }

    private void saveAutomator() {
        String url = "http://demo0017680.mockable.io/"+text_url.getText().toString();
        String time = text_time.getText().toString();

        Intent intent = new Intent();
        intent.putExtra("url", url);
        intent.putExtra("time", time);
        int id = getIntent().getIntExtra("id",-1);
        if (id != -1){
            intent.putExtra("id",id);
        }
        intent.putExtra("scheduler",selectedValue);
        setResult(RESULT_OK, intent);
        finish();
    }

    void showDatePicker(){
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        scheduleTime = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        timePicker();
                        //txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    void timePicker(){

        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        scheduleTime = scheduleTime + " " + hourOfDay + ":" + minute;
                        text_time.setText(scheduleTime);
                        //txtTime.setText(hourOfDay + ":" + minute);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }
}
