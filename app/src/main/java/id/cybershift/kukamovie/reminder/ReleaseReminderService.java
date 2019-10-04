package id.cybershift.kukamovie.reminder;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import id.cybershift.kukamovie.MainActivity;
import id.cybershift.kukamovie.R;
import id.cybershift.kukamovie.entity.Movie;

import static id.cybershift.kukamovie.BuildConfig.API_KEY;

public class ReleaseReminderService extends JobService {
    public static int notifId = 0;
    public static final List<Movie> stackNotif = new ArrayList<>();
    private final static int NOTIFICATION_REQUEST_CODE = 200;
    private final static String GROUP_KEY_MOVIES = "group_key_movies";
    private static final int MAX_NOTIFICATION = 2;
    private static final CharSequence CHANNEL_NAME = "kukamovie channel";

    @Override
    public boolean onStartJob(JobParameters job) {
        getReleaseToday(job);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return true;
    }

    private void getReleaseToday(final com.firebase.jobdispatcher.JobParameters job) {
        Date nowDate = Calendar.getInstance().getTime();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(nowDate);

        String url = "https://api.themoviedb.org/3/discover/movie?api_key=" + API_KEY + "&primary_release_date.gte=" + date + "&primary_release_date.lte=" + date;
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject movie = list.getJSONObject(i);
                        Movie movieItem = new Movie(movie);
                        stackNotif.add(movieItem);
                        String title = movieItem.getTitle();
                        String message = movieItem.getTitle() + " Rilis Hari ini";
                        sendNotifications(getApplicationContext(), title, message, notifId);
                        notifId++;
                    }

                    jobFinished(job, false);
                } catch (Exception e) {
                    jobFinished(job, true);
                    Log.d("Fakhri Exception Movie", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("Fakhri onFailure", error.getMessage());
            }
        });
    }

    private void sendNotifications(Context context, String title, String message, int notifId) {
        String CHANNEL_ID = "Channel_2";

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(context, MainActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, NOTIFICATION_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder;

        if (notifId < MAX_NOTIFICATION) {
            builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_local_movies_24dp)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setContentIntent(pendingIntent)
                    .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                    .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                    .setSound(alarmSound);
        } else {
            NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle()
                    .addLine(stackNotif.get(notifId).getTitle() + " Rilis Hari Ini")
                    .addLine(stackNotif.get(notifId - 1).getTitle() + " Rilis Hari Ini")
                    .setBigContentTitle(notifId + " film baru yang rilis hari ini")
                    .setSummaryText("Film Rilis Hari Ini");
            builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_local_movies_24dp)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setGroup(GROUP_KEY_MOVIES)
                    .setGroupSummary(true)
                    .setContentIntent(pendingIntent)
                    .setStyle(inboxStyle)
                    .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                    .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                    .setSound(alarmSound);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});
            builder.setChannelId(CHANNEL_ID);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }

        Notification notification = builder.build();
        if (notificationManager != null) {
            notificationManager.notify(notifId, notification);
        }
    }
}
