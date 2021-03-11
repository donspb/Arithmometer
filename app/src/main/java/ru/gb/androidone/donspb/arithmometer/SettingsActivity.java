package ru.gb.androidone.donspb.arithmometer;

import androidx.appcompat.app.AppCompatActivity;

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

    private static boolean themeChanged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean isDark = getSharedPreferences(settings,MODE_PRIVATE).getBoolean(darkSet, false);
        if (isDark) {
            setTheme(R.style.Theme_ArithmometerDark);
        } else {
            setTheme(R.style.Theme_Arithmometer);
        }
        setContentView(R.layout.activity_settings);
        Button btnBack = findViewById(R.id.button_back);
        ToggleButton btnTgDark = findViewById(R.id.button_dark_tgl);
        btnTgDark.setChecked(isDark);

        btnBack.setOnClickListener((View v) -> {
            Intent intentRes = new Intent();
            intentRes.putExtra(darkSet, themeChanged);
            setResult(RESULT_OK, intentRes);
            finish();
        });

        btnTgDark.setOnCheckedChangeListener((CompoundButton button, boolean isChecked) -> {
            SharedPreferences sharedPreferences = getSharedPreferences(settings, MODE_PRIVATE);
            sharedPreferences.edit().putBoolean(darkSet, isChecked).apply();
            recreate();
            themeChanged = true;
        });
    }
}