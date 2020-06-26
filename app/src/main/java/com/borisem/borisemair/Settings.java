package com.borisem.borisemair;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class Settings extends AppCompatActivity {
    private Spinner spinnerChangeTheme;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        spinnerChangeTheme=(Spinner)findViewById(R.id.whatTime);

        spinnerChangeTheme.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
switch (i){

    case 0:


        int theme = sp.getInt("THEME", R.style.AppDark);

        setTheme(theme);

      //  setContentView(R.layout.activity_second);
        break;

    case 1:

        theme = sp.getInt("THEME", R.style.AppTheme);
        setTheme(theme);
        break;



}

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
