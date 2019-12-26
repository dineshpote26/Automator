package com.dinesh.automatorandroid.worker;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.dinesh.automatorandroid.R;
import com.dinesh.automatorandroid.asynctask.InsertHistoryAsyncTask;
import com.dinesh.automatorandroid.data.AutomatorHistory;
import com.dinesh.automatorandroid.data.AutomatorHistoryDao;
import com.dinesh.automatorandroid.data.AutomatorHistoryDatabase;
import com.dinesh.automatorandroid.network.Api;
import com.dinesh.automatorandroid.network.ApiRequest;
import com.dinesh.automatorandroid.reciver.AlarmReceiver;
import com.dinesh.automatorandroid.ui.AutomatorActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AutomatorWorker extends Worker {


    Context context;

    public AutomatorWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    @NonNull
    @Override
    public Result doWork() {

        Log.d("dinesh","doWork called");

        //call post url

        String url = getInputData().getString("url");

        final Api request = ApiRequest.getClient().create(Api.class);
        Call<ResponseBody> call = request.getResponse(url);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    Log.d("dinesh","Api called");
                    AutomatorHistoryDatabase automatorHistoryDatabase = AutomatorHistoryDatabase.getInstance(getApplicationContext());
                    AutomatorHistoryDao automatorDao = automatorHistoryDatabase.automatorDao();
                    try {


                        String result = response.body().string();

                        insert(automatorDao,new AutomatorHistory(result,new SimpleDateFormat("dd-MM-yyyy HH:mm").format(new Date())));

                        JSONObject jsonObject = new JSONObject(result);
                        JSONObject action = jsonObject.getJSONObject("action");

                        if(action.has("send_notification")){

                            JSONObject object = action.getJSONObject("send_notification");
                            String heading = object.optString("heading");
                            String desc = object.optString("description");
                            sendNotification(heading,desc,1);

                        }else if(action.has("send_alarm")){

                            JSONObject object = action.getJSONObject("send_alarm");
                            String time = object.optString("time");
                            String text = object.optString("text");

                            setAlarm(time);

                        }else if(action.has("pop_up_notification")){

                            JSONObject object = action.getJSONObject("pop_up_notification");
                            String heading = object.optString("heading");
                            String description = object.optString("description");
                            sendNotification(heading,description,2);

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Result.failure();
            }
        });

        return Result.success();
    }

    private void sendNotification(String title, String text, int id) {
        Intent intent = new Intent(getApplicationContext(), AutomatorActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        //intent.putExtra(SyncStateContract.Constants.EXTRA_ID, id);

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

        NotificationManager notificationManager = (NotificationManager)getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default", "Default", NotificationManager.IMPORTANCE_DEFAULT);
            Objects.requireNonNull(notificationManager).createNotificationChannel(channel);
        }

        NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext(), "default")
                .setContentTitle(title)
                .setContentText(text)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true);

        Objects.requireNonNull(notificationManager).notify(id, notification.build());
    }

    public void insert(AutomatorHistoryDao automatorDao, AutomatorHistory history){
        new InsertHistoryAsyncTask(automatorDao).execute(history);
    }

    private void setAlarm(String time) {

     //   Toast.makeText(context, "Alarm Set Sucessfully", Toast.LENGTH_SHORT).show();

        Handler handler = new Handler(Looper.getMainLooper());

        handler.post(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(context, "Alarm set successfully", Toast.LENGTH_LONG).show();
            }
        });

        Date date = new Date();
        DateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context, 1, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, date.getTime(),
                pendingIntent);

    }
}
