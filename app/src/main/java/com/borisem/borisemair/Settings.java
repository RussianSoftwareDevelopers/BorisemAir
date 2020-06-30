package com.borisem.borisemair;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class Settings extends AppCompatActivity {
    private Spinner spinnerChangeTheme;
    SharedPreferences sp ;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme(MainActivity.theme);

        setContentView(R.layout.activity_settings);

      sp =  PreferenceManager.getDefaultSharedPreferences(this);

        spinnerChangeTheme=(Spinner)findViewById(R.id.chooseTheme);



        switch (sp.getInt("THEME", R.style.AppThemeLight))
        {

            case R.style.AppThemeLight:
                spinnerChangeTheme.setSelection(0);
            break;

            case R.style.AppThemeDark:

                spinnerChangeTheme.setSelection(1);
                break;

        }





        spinnerChangeTheme.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


switch (i){

    case 0:




        setTheme(R.style.AppThemeLight);
        SharedPreferences.Editor editor =  sp.edit();
        editor.putInt("THEME", R.style.AppThemeLight).apply();
        MainActivity.theme = R.style.AppThemeLight;
        break;

    case 1:


        setTheme(R.style.AppThemeDark);
        SharedPreferences.Editor editor2 =  sp.edit();
        editor2.putInt("THEME", R.style.AppThemeDark).apply();
        MainActivity.theme = R.style.AppThemeLight;
        break;



}

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
