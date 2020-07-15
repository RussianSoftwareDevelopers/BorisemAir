package com.borisem.borisemair;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

import static com.borisem.borisemair.MainActivity.NeedRecreate;
import static com.borisem.borisemair.MainActivity.sPref;

public class Settings extends AppCompatActivity {
    private Spinner spinnerChangeTheme, chooseSec;
    SharedPreferences sp ;
    Button oKbtn;
    EditText et1, et2, et3, et4, etp;
    private String ip_address;
    int port;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(MainActivity.theme);
        setContentView(R.layout.activity_settings);

        sp =  PreferenceManager.getDefaultSharedPreferences(this);
        spinnerChangeTheme=(Spinner)findViewById(R.id.chooseTheme);
        ArrayList<String> list = new ArrayList<>();
        list.add(0, "Светлая");
        list.add(1, "Темная");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.adapter, list);
        spinnerChangeTheme.setAdapter(adapter);


        //Сетевые настройки
        et1 = (EditText) findViewById(R.id.editText1);
        et2 = (EditText) findViewById(R.id.editText2);
        et3 = (EditText) findViewById(R.id.editText3);
        et4 = (EditText) findViewById(R.id.editText4);
        etp = (EditText) findViewById(R.id.editText_Port);


        chooseSec = (Spinner)findViewById(R.id.whatTime);
        ArrayList<String> seconds = new ArrayList<>();
        seconds.add(0, "Выкл.");
        seconds.add(1, "5 сек.");
        seconds.add(2, "10 сек.");
        seconds.add(3, "15 сек.");
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, R.layout.adapter, seconds);
        chooseSec.setAdapter(adapter1);

        oKbtn = (Button)findViewById(R.id.applyButton);

        oKbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NeedRecreate = true;
                finish();
            }
        });


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



    public boolean IpCheck() {
        String okt1 = et1.getText().toString();
        String okt2 = et2.getText().toString();
        String okt3 = et3.getText().toString();
        String okt4 = et4.getText().toString();

        try {
            port = Integer.valueOf(etp.getText().toString());
        }
        catch (Exception e) {
            return false;
        }

        if (Integer.valueOf(okt1)>255 || Integer.valueOf(okt2)>255 || Integer.valueOf(okt3)>255 || Integer.valueOf(okt4)>255 || port>65355) {
            return false;
        }

        ip_address = okt1+"."+okt2+"."+okt3+"."+okt4;
        Log.d("TAG", "Settings: ip == " + ip_address);
        Log.d("TAG", "Settings: port == " + port);
        return true;
    }


    public void Set(View view) {
        if (view.getId() == R.id.applyButton) {
            if (IpCheck()) {
                if(ip_address!=null&port!=0)
                {
                    SharedPreferences.Editor sE = sPref.edit();
                    sE.putString("ip_address", ip_address);
                    sE.putInt("port", port);
                    sE.apply();

                    Toast toast = Toast.makeText(getApplicationContext(), "Сохранено", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Не корректные данные", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
            else {
                Toast toast = Toast.makeText(getApplicationContext(), "Не корректные данные", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }


}
