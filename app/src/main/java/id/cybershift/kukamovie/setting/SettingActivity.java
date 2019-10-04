package id.cybershift.kukamovie.setting;

import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import id.cybershift.kukamovie.R;
import id.cybershift.kukamovie.reminder.DailyReminder;
import id.cybershift.kukamovie.reminder.ReleaseReminder;

public class SettingActivity extends AppCompatActivity {
    public static final String SHARED_PREF_NAME = "sharedprefsetting";
    public static final String KEY_DAILY = "key_daily";
    public static final String KEY_RELEASE = "key_release";
    Switch switchRelease, switchDaily;
    Button btnChangeLanguage;
    DailyReminder dailyReminder;
    ReleaseReminder releaseReminder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getResources().getString(R.string.Setting));
        }

        switchRelease = findViewById(R.id.switch_release);
        switchDaily = findViewById(R.id.switch_daily);
        btnChangeLanguage = findViewById(R.id.change_language_settings);
        dailyReminder = new DailyReminder();
        releaseReminder = new ReleaseReminder();

        final SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

        if (sharedPreferences.getString(KEY_DAILY, null) != null) {
            switchDaily.setChecked(true);
        } else {
            switchDaily.setChecked(false);
        }

        if (sharedPreferences.getString(KEY_RELEASE, null) != null) {
            switchRelease.setChecked(true);
        } else {
            switchRelease.setChecked(false);
        }

        btnChangeLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(intent);
            }
        });

        switchDaily.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    dailyReminder.setRepeatingAlarm(SettingActivity.this);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(KEY_DAILY, "Reminder Daily");
                    editor.apply();
                } else {
                    dailyReminder.cancelAlarm(SettingActivity.this);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove(KEY_DAILY);
                    editor.apply();
                }
            }
        });

        switchRelease.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    releaseReminder.setAlarmRelease(SettingActivity.this);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(KEY_RELEASE, "Release");
                    editor.apply();
                } else {
                    releaseReminder.cancelAlarm(SettingActivity.this);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove(KEY_RELEASE);
                    editor.apply();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
