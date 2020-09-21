package com.borisem.borisemair;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

import static com.borisem.borisemair.MainActivity.NeedRecreate;
import static com.borisem.borisemair.MainActivity.sPref;
import static com.borisem.borisemair.MainActivity.time;

public class Settings extends AppCompatActivity {
    private Spinner spinnerChangeTheme, chooseSec;


    EditText et1, et2, et3, et4, etp;
    private String ip_address;
    private CheckBox checkBox_Up, checkBox_Down;
    int port;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(MainActivity.theme);
        setContentView(R.layout.activity_settings);


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

        checkBox_Up = (CheckBox) findViewById(R.id.up);
        checkBox_Down= (CheckBox)findViewById(R.id.down);




        String u = sPref.getString("ip_address", "");


        int port=  sPref.getInt("port", 80);
        checkBox_Up.setChecked(sPref.getBoolean("check_up",false));
        checkBox_Down.setChecked(sPref.getBoolean("check_down", false));

        try {
            assert u != null;
            String[] ips = u.split("\\.");

            et1.setText(ips[0]);
            et2.setText(ips[1]);
            et3.setText(ips[2]);
            et4.setText(ips[3]);

            etp.setText(String.valueOf(port));
        }

        catch (Exception e){ }

        chooseSec = (Spinner)findViewById(R.id.whatTime);
        ArrayList<String> seconds = new ArrayList<>();
        seconds.add(0, "2 сек.");
        seconds.add(1, "3 сек.");
        seconds.add(2, "4 сек.");

        seconds.add(3, "5 сек.");
        seconds.add(4, "10 сек.");
        seconds.add(5, "15 сек.");
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, R.layout.adapter, seconds);
        chooseSec.setAdapter(adapter1);

        time=sPref.getInt("time", 2);

        switch (time)
        {

            case 2:
                chooseSec.setSelection(0);
                break;

            case 3:
                chooseSec.setSelection(1);
                break;

            case 4:
                chooseSec.setSelection(2);
                break;
            case 5:
                chooseSec.setSelection(3);
                break;

            case 10:
                chooseSec.setSelection(4);
                break;

            case 15:
                chooseSec.setSelection(5);
                break;
        }


        switch (sPref.getInt("THEME", R.style.AppThemeLight))
        {
            case R.style.AppThemeLight:
                spinnerChangeTheme.setSelection(0);
                NeedRecreate = true;
            break;

            case R.style.AppThemeDark:
                spinnerChangeTheme.setSelection(1);
                NeedRecreate = true;
                break;
        }

        chooseSec.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences.Editor editor =  sPref.edit();
                switch (position){
                    case 0:
                        time=2;
                        editor.putInt("time", 2).apply();
                        break;
                    case 1:
                        time=3;
                        editor.putInt("time", 3).apply();
                        break;
                    case 2:
                        time=4;
                        editor.putInt("time", 4).apply();
                        break;

                    case 3:
                        time = 5;
                        editor.putInt("time", 5).apply();
                        break;
                    case 4:
                        time =10;
                        editor.putInt("time", 10).apply();
                        break;
                    case 5:
                        time =15;
                        editor.putInt("time", 15).apply();
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });

        spinnerChangeTheme.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            switch (i){
                case 0:
                    setTheme(R.style.AppThemeLight);
                    SharedPreferences.Editor editor =  sPref.edit();
                    editor.putInt("THEME", R.style.AppThemeLight).apply();
                    MainActivity.theme = R.style.AppThemeLight;
                break;

                case 1:
                    setTheme(R.style.AppThemeDark);
                    SharedPreferences.Editor editor2 =  sPref.edit();
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
            Log.d("TAG", "Error  " + e.getMessage());
            return false;
        }

        if (Integer.valueOf(okt1)>255 || Integer.valueOf(okt2)>255 || Integer.valueOf(okt3)>255 || Integer.valueOf(okt4)>255 || port>65355) {
            Log.d("TAG", "Error>>>" );
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
                view.setOnClickListener(null);
                if(ip_address!=null&port!=0)
                {
                    SharedPreferences.Editor sE = sPref.edit();
                    sE.putString("ip_address", ip_address);
                    sE.putInt("port", port);
                    sE.putBoolean("check_up", checkBox_Up.isChecked());
                    sE.putBoolean("check_down", checkBox_Down.isChecked());


                    sE.apply();


                    Toast toast = Toast.makeText(getApplicationContext(), "Сохранено", Toast.LENGTH_SHORT);
                    toast.show();

                    finish();
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
