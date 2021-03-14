package ru.gb.androidone.donspb.arithmometer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

public class SettingsActivity extends AppCompatActivity {

    private static final String settings = "settings.xml";
    private static final String darkSet = "Dark";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean isDark = getSharedPreferences(settings,MODE_PRIVATE).getBoolean(darkSet, false);
        themeSetter(isDark);
        setContentView(R.layout.activity_settings);
        Button btnBack = findViewById(R.id.button_back);
        ToggleButton btnTgDark = findViewById(R.id.button_dark_tgl);
        btnTgDark.setChecked(isDark);

        btnBack.setOnClickListener((View v) -> {
            finish();
        });

        btnTgDark.setOnCheckedChangeListener((CompoundButton button, boolean isChecked) -> {
            themeSetter(isChecked);
            SharedPreferences sharedPreferences = getSharedPreferences(settings, MODE_PRIVATE);
            sharedPreferences.edit().putBoolean(darkSet, isChecked).apply();
        });
    }

    private void themeSetter(boolean isDark) {
        if (isDark) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
}