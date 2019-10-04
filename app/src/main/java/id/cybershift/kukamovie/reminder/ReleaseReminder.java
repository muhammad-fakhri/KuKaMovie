package id.cybershift.kukamovie.reminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;

import java.util.Calendar;

import id.cybershift.kukamovie.R;

public class ReleaseReminder extends BroadcastReceiver {
    private static int notifId = 100;
    FirebaseJobDispatcher dispatcher;
    private String DISPATCHER_TAG = "mydispatcher";

    public ReleaseReminder() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(context));
        startDispacher();
    }

    private void startDispacher() {
        Job job = dispatcher.newJobBuilder()
                .setService(ReleaseReminderService.class)
                .setTag(DISPATCHER_TAG)
                .setRecurring(false)
                .setLifetime(Lifetime.UNTIL_NEXT_BOOT)
                .setTrigger(Trigger.executionWindow(0, 1))
                .setReplaceCurrent(true)
                .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
                .setConstraints(
                        Constraint.ON_ANY_NETWORK
                )
                .build();

        dispatcher.mustSchedule(job);
    }

    private void stopDispacher() {
        dispatcher.cancel(DISPATCHER_TAG);
    }

    public void setAlarmRelease(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Intent intent = new Intent(context, ReleaseReminder.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, notifId, intent, 0);

        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
        Toast.makeText(context, context.getResources().getString(R.string.set_release_reminder_on), Toast.LENGTH_SHORT).show();
    }

    public void cancelAlarm(Context context) {
//        stopDispacher();
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReleaseReminder.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, notifId, intent, 0);
        alarmManager.cancel(pendingIntent);
        Toast.makeText(context, context.getResources().getString(R.string.set_release_reminder_off), Toast.LENGTH_SHORT).show();
    }
}
