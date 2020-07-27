package com.borisem.borisemair;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {
    public static   SharedPreferences sPref;
    ImageButton left_frontWheel_up, left_frontWheel_down, left_backWheel_up, left_backWheel_down, right_frontWheel_up,
            right_frontWheel_down, right_backWheel_up,  right_backWheel_down, centr_btn, centr_front_up, centr_front_down, centr_back_up, centr_back_down,
            all_wheel_ap, all_wheel_down;
    Button btn_menu;
    boolean isPressed = true;
    public static int theme;
    FragmentManager supportFragmentManager;
    public static boolean NeedRecreate = false;
    private String url;
    private String urlHelper;


    public static int time;

    TextView leftBottomWheelTxt, rightBottomWheelTxt, leftTopWheelTxt, rightTopWheelTxt;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sPref = getPreferences(MODE_PRIVATE);

        theme  = sPref.getInt("THEME", R.style.AppThemeLight);
        String  idbrand = sPref.getString("bradnds", "");
        setTheme(theme);
        setContentView(R.layout.activity_main);

        btn_menu = (Button)findViewById(R.id.menu);

        if(idbrand!="" ) {
            btn_menu.setBackgroundResource(getResources().getIdentifier(idbrand,"drawable","com.borisem.borisemair"));
            btn_menu.setText("");
        }

        centr_btn=(ImageButton)findViewById(R.id.centr_btn);
        //Кнопки на главном экране
        //Левые колеса вверх/вниз
        left_frontWheel_up = (ImageButton)findViewById(R.id.Button_LP_Up);
        left_frontWheel_down = (ImageButton)findViewById(R.id.Button_LP_Down);

        left_backWheel_up = (ImageButton)findViewById(R.id.Button_LZ_Up);
        left_backWheel_down = (ImageButton)findViewById(R.id.Button_LZ_Down);

        //Правые колеса вверх/вниз
        right_frontWheel_up = (ImageButton)findViewById(R.id.button_RP_up);
        right_frontWheel_down = (ImageButton)findViewById(R.id.button_RP_down);

        right_backWheel_up = (ImageButton)findViewById(R.id.button_RZ_up);
        right_backWheel_down = (ImageButton)findViewById(R.id.Button_RZ_Down);

        //Передняя и задняя оси
        centr_front_up =(ImageButton)findViewById(R.id.Button_P_Up);
        centr_front_down = (ImageButton)findViewById(R.id.Button_P_Down);

        centr_back_up = (ImageButton)findViewById(R.id.Button_Z_Up);
        centr_back_down =(ImageButton)findViewById(R.id.Button_Z_Down);

        //Все колеса
        all_wheel_ap =(ImageButton)findViewById(R.id.centr_btn);
        all_wheel_down =  (ImageButton)findViewById(R.id.centr_btn);


        leftBottomWheelTxt  = (TextView) findViewById(R.id.textLeftBottomWheel);
        rightBottomWheelTxt = (TextView) findViewById(R.id.textRightBottomWheel);
        leftTopWheelTxt = (TextView) findViewById(R.id.textLeftTopWheel);
        rightTopWheelTxt = (TextView)findViewById(R.id.textRighTopWheel);

        supportFragmentManager = getSupportFragmentManager();

        centr_btn.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
           /*   Log.d("TAG", "onTouch: "+ event.getAction());
                Log.d("TAG", "onTouch: "+event.getX());
                Log.d("TAG", "onTouch: "+event.getY());*/

           if(event.getY() < (v.getHeight()/2)) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        {

                            SendCommand("FB&"+time+"&UP");
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                v.setBackground(getApplicationContext().getDrawable(R.drawable.btn_centr_color_up));
                            }
                        }
                    }
                }

                if(event.getY() > (v.getHeight()/2)) {
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            { SendCommand("FB&"+time+"&DOWM");
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    v.setBackground(getApplicationContext().getDrawable(R.drawable.btn_centr_color));
                                }
                            }
                    }
                }

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {


                        v.setBackground(getApplicationContext().getDrawable(R.drawable.btn_center_shape));
                    }
                }
                return false;
            }
        });



        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Settings.class);
                startActivity(intent);
            }
        });


        btn_menu.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                final FireMissilesDialogFragment schermpje = new FireMissilesDialogFragment(new FireMissilesDialogFragment.OnButtonClick() {
                    @Override
                    public void onDialogClickListener(int id) {
                       btn_menu.setText("");
                       btn_menu.setBackgroundResource(id);
                       SharedPreferences.Editor editor = sPref.edit();

                       editor.putString("bradnds",  getResources().getResourceName(id));
                       editor.apply();

                    }
                });
                Bundle bundle = new Bundle();
                bundle.putInt(FireMissilesDialogFragment.KEY_OK,   R.string.show);
                bundle.putInt(FireMissilesDialogFragment.MESSAGE, R.string.show);
                schermpje.setArguments(bundle);

                schermpje.show(supportFragmentManager, "mydialog");
                return false;

            }
        });

        //Методы
        //Левые колеса вверх/вниз
        left_frontWheel_up.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SendCommand("FL&"+time+"&UP");
                    }
                }
        );

        left_frontWheel_down.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SendCommand("FL&"+time+"&DOWN");
                    }
                }
        );

        left_backWheel_up.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SendCommand("BL&"+time+"&UP");
                    }
                }
        );

        left_backWheel_down.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SendCommand("BL&"+time+"&DOWN");
                    }
                }
        );







        //Правые колеса вверх/вниз
        right_frontWheel_up.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SendCommand("FR&"+time+"&UP");
                    }
                }
        );

        right_frontWheel_down.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SendCommand("FR&"+time+"&DOWN");
                    }
                }
        );

        right_backWheel_up.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SendCommand("BR&"+time+"&UP");
                    }
                }
        );

        right_backWheel_down.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SendCommand("BR&"+time+"&DOWN");
                    }

                }
        );



         //Передняя ось



        centr_front_up.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if(action == MotionEvent.ACTION_DOWN){  // Кнопка нажата
                    v.setPressed(true);
                    v.setBackground(getResources().getDrawable(R.drawable.button_back_up, getTheme()));
                    SendCommand("F&UP&Start");

                } else if(action == MotionEvent.ACTION_UP){  // Кнопка отжата
                    v.setPressed(false);
                    SendCommand("F&UP&Stop");
                }
                return true;
            }
        });




        centr_front_down.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if(action == MotionEvent.ACTION_DOWN){  // Кнопка нажата
                    v.setPressed(true);
                    SendCommand("F&DOWN&Start");
                } else if(action == MotionEvent.ACTION_UP){  // Кнопка отжата
                    v.setPressed(false);
                    SendCommand("F&DOWN&Stop");
                }
                return true;
            }
        });


        //Задняя ось

        centr_back_up.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if(action == MotionEvent.ACTION_DOWN){  // Кнопка нажата
                    v.setPressed(true);
                    SendCommand("B&UP&Start");
                } else if(action == MotionEvent.ACTION_UP){  // Кнопка отжата
                    SendCommand("B&UP&Stop");
                    v.setPressed(false);
                }
                return true;
            }
        });




        centr_back_down.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if(action == MotionEvent.ACTION_DOWN){  // Кнопка нажата
                    v.setPressed(true);
                    SendCommand("B&DOWN&Start");
                } else if(action == MotionEvent.ACTION_UP){  // Кнопка отжата
                    v.setPressed(false);
                    SendCommand("B&DOWN&Stop");
                }
                return true;
            }
        });




    }


    public void SendCommand(String cmd)
    {

        ReloadStaus(cmd);

        if(!url.equals("")) {
            urlHelper=url;
            url += cmd;


            RequestQueue queue = Volley.newRequestQueue(this );


            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the first 500 characters of the response string.
                            Toast toast = Toast.makeText(getApplicationContext(),
                                    response, Toast.LENGTH_SHORT);
                            toast.show();
                            ParseCmdResponse(response);
                            Log.d("TAG", "response: " + response);

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Ошибка передачи: "+error, Toast.LENGTH_SHORT);
                    toast.show();
                    Log.d("TAG", "Error: " + error.getMessage());

                }
            });
stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,
        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        Log.d("TAG", "SendCommand: ."+stringRequest.getTimeoutMs());
// Add the request to the RequestQueue.
            queue.add(stringRequest);
        }
        else
        {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Проверьте настройки подключения", Toast.LENGTH_SHORT);
            toast.show();

        }

        url= urlHelper;


    }

    private void ReloadStaus(String cmd) {

        if(cmd.contains("BL"))
        {

            leftBottomWheelTxt.setText("Start");


        }



    }

    private void ParseCmdResponse(String response) {


            String whichwheel = response.split("&")[0];

            switch (whichwheel)
            {

                case "FL":
                    leftTopWheelTxt.setText("Ok");
                    break;
                case "FR":
                    rightTopWheelTxt.setText("Ok");
                    break;
                case "BL":
                    leftBottomWheelTxt.setText("Ok");
                    break;
                case "BR":
                    rightBottomWheelTxt.setText("Ok");
                    break;
            }











    }

    /*  Обозначение колес:
      FL - Переднее левое
      FR - Переднее правое
      BL - Заднее левое
      BR - Заднее правое
      F - Оба передних колеса
      B - оба задних колеса
      FB - Все колеса

      10 - Время в секундах

      UP - подъем машины
      DOWN - Спуск машины

      Start - начать работу при зажимании кнопки без настройки времени
      Stop - окончить работ при отпускании кнопки без настройки времени

      в ответ отправляю команду +OK.

              Примеры:
      FRT20SUP - поднять переднее правое колесо. время работы 20 сек
      FRT20SUPOK - ответ на команду

      FBT10SDOWN -  опустить все колеса. время работы 10 сек
      FBT10SDOWNОК - ответ на команду

      BUPStart - поднимать оба задних колеса по нажатию на кнопку
      BUPStartОК - ответ на команду

      BUPStop - закончить поднимать оба задних колеса по отжатии кнопки
      BUPStopОК - ответ на команду
  */
    @Override
    protected void onResume() {
        super.onResume();

         String u = sPref.getString("ip_address", "");
         int port=  sPref.getInt("port", 80);
         url= "http://"+ u + ":"+ String.valueOf(port) + "?";

         Log.d("TAG", "Onresume: " + NeedRecreate);
         if(NeedRecreate)
        {
            recreate();
            NeedRecreate =false;
        }
    }

    private static String getDensityName(Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        if (density >= 4.0) {
            return "xxxhdpi";
        }
        if (density >= 3.0) {
            return "xxhdpi";
        }
        if (density >= 2.0) {
            return "xhdpi";
        }
        if (density >= 1.5) {
            return "hdpi";
        }
        if (density >= 1.0) {
            return "mdpi";
        }
        return "ldpi";
    }

}