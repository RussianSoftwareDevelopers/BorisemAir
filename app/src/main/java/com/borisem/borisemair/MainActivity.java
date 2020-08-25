package com.borisem.borisemair;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.FlingAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
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
    private   View.OnClickListener btnMenuclickLisner;

    public static int time;
    private boolean isCheck_up, isCheck_down;
    TextView leftBottomWheelTxt, rightBottomWheelTxt, rightTopWheelTxt, leftTopWheelTxt, textViewCentr, leftBottomWheelTxt_UP, leftBottomWheelTxt_DOWN,
            rightBottomWheelTxt_UP, rightBottomWheelTxt_DOWN, rightTopWheelTxt_UP, rightTopWheelTxt_DOWN, leftTopWheelTxt_UP, leftTopWheelTxt_DOWN;
    ;
    TextView[] textViewsleftBottomWheel;
    TextView[] textViewsrightBottomWheel;
    TextView[] textViewsrightTopWheelTxt ;
    TextView[] textViewsleftTopWheelTxt ;

    ImageButton[] imageButtons;


    private LinearLayout linearLayout_left_top;
    private LinearLayout linearLayout_right_top;
    private LinearLayout linearLayout_left_bottom;
    private LinearLayout linearLayout_right_bottom;

    private boolean FlagPressed;


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

        leftBottomWheelTxt_UP = (TextView) findViewById(R.id.textLeftBottomWheel_UP);
        leftBottomWheelTxt  = (TextView) findViewById(R.id.textLeftBottomWheel);
        leftBottomWheelTxt_DOWN = (TextView) findViewById(R.id.textLeftBottomWheel_Down);

        rightBottomWheelTxt_UP = (TextView) findViewById(R.id.textRightBottomWheel_UP);
        rightBottomWheelTxt = (TextView) findViewById(R.id.textRightBottomWheel);
        rightBottomWheelTxt_DOWN =(TextView) findViewById(R.id.textRightBottomWheel_DOWN);

        rightTopWheelTxt_UP =(TextView) findViewById(R.id.textRightBottomWheel_UP);
        rightTopWheelTxt = (TextView) findViewById(R.id.textRighTopWheel);
        rightTopWheelTxt_DOWN =(TextView) findViewById(R.id.textRighTopWheel_DOWN);

        leftTopWheelTxt_UP = (TextView) findViewById(R.id.textLeftTopWheel_UP);
        leftTopWheelTxt = (TextView)findViewById(R.id.textLeftTopWheel);
        leftTopWheelTxt_DOWN = (TextView) findViewById(R.id.textLeftTopWheel_DOWN);

       textViewsleftBottomWheel  = new TextView[]{leftBottomWheelTxt_UP, leftBottomWheelTxt, leftBottomWheelTxt_DOWN};
       textViewsrightBottomWheel = new TextView[]{rightBottomWheelTxt_UP, rightBottomWheelTxt, rightBottomWheelTxt_DOWN};
       textViewsrightTopWheelTxt = new TextView[]{rightTopWheelTxt_UP,rightTopWheelTxt, rightTopWheelTxt_DOWN};
       textViewsleftTopWheelTxt  = new TextView[]{leftTopWheelTxt_UP, leftTopWheelTxt, leftTopWheelTxt_DOWN};


        linearLayout_left_top = findViewById(R.id.linearLayout_left_top);
        linearLayout_right_top= findViewById(R.id.linearLayout_right_top);
        linearLayout_left_bottom = findViewById(R.id.linearLayout_left_bottom);
        linearLayout_right_bottom = findViewById(R.id.linearLayout_right_bottom);

        supportFragmentManager = getSupportFragmentManager();


        time=sPref.getInt("time", 0);

        textViewCentr=(TextView)findViewById(R.id.textView6);




        centr_btn.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
           /*   Log.d("TAG", "onTouch: "+ event.getAction());
                Log.d("TAG", "onTouch: "+event.getX());
                Log.d("TAG", "onTouch: "+event.getY());*/
           int timehelper= time;


           if(event.getY() < (v.getHeight()/2)) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        {


                            if(isCheck_up)
                            {
                                timehelper=time;
                                v.setEnabled(false);

                            }
                            else
                            {
                                timehelper=0;

                            }

                            BlockButtons(v, false);
                            FlagPressed=true;
                            ResizeUP(linearLayout_left_bottom, FlagPressed, true);
                            ResizeUP(linearLayout_right_bottom, FlagPressed, true);
                            ResizeUP(linearLayout_left_top, FlagPressed, true);
                            ResizeUP(linearLayout_right_top, FlagPressed, true);

                            Log.d("TAG", "onTouch: " + v.isPressed());

                            SendCommand("FB&"+timehelper+"&UP");
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                v.setBackground(getApplicationContext().getDrawable(R.drawable.btn_centr_color_up));
                            }
                        }
                    }
                }

                if(event.getY() > (v.getHeight()/2)) {
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            {

                                if(isCheck_down)
                                {
                                    timehelper=time;
                                    v.setEnabled(false);

                                }
                                else
                                {
                                    timehelper=0;
                                }

                                BlockButtons(v, false);
                                FlagPressed=true;
                                ResizeUP(linearLayout_left_bottom,true, false);
                                ResizeUP(linearLayout_right_bottom, true, false);
                                ResizeUP(linearLayout_left_top, true, false);
                                ResizeUP(linearLayout_right_top, true, false);
                                SendCommand("FB&"+timehelper+"&DOWN");
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    v.setBackground(getApplicationContext().getDrawable(R.drawable.btn_centr_color));
                                }
                            }
                    }
                }

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        FlagPressed=false;

                        ResizeUP(linearLayout_left_bottom,FlagPressed, false);
                        ResizeUP(linearLayout_right_bottom, FlagPressed, false);
                        ResizeUP(linearLayout_left_top, FlagPressed, false);
                        ResizeUP(linearLayout_right_top, FlagPressed, false);

                            BlockButtons(v, true);
                            v.setBackground(getApplicationContext().getDrawable(R.drawable.btn_center_shape));

                            if(!isCheck_up) {
                                timehelper=0;
                                if (event.getY() < (v.getHeight() / 2)) {
                                    SendCommand("FB&" + timehelper + "&UP&STOP");

                                }

                            }
                            if(!isCheck_down)
                            {
                                timehelper=0;
                                if (event.getY() > (v.getHeight() / 2)) {
                                    SendCommand("FB&" + timehelper + "&DOWN&STOP");


                                }


                            }


                    }
                }
                return false;
            }
        });






      btnMenuclickLisner = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Settings.class);
                startActivity(intent);
                v.setOnClickListener(null);

            }
        };
        btn_menu.setOnClickListener(btnMenuclickLisner);

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
       left_frontWheel_up.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();

                if(action == MotionEvent.ACTION_DOWN){  // Кнопка нажата
                    v.setPressed(true);
                    FlagPressed = v.isPressed();
                    BlockButtons(v, false);
                    v.setBackground(getResources().getDrawable(R.drawable.button_back_up, getTheme()));
                    SendCommand("FL&0&UP");

                    ResizeUP( linearLayout_left_top, FlagPressed, true);



                } else if(action == MotionEvent.ACTION_UP){  // Кнопка отжата
                    v.setPressed(false);
                    FlagPressed = v.isPressed();
                    BlockButtons(v, true);
                    SendCommand("FL&0&UP&STOP");
                    ResizeUP(linearLayout_left_top,FlagPressed, true);
                }
                return true;
            }
        });





        left_frontWheel_down.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();

                if(action == MotionEvent.ACTION_DOWN){  // Кнопка нажата
                    v.setPressed(true);
                    FlagPressed = v.isPressed();
                    BlockButtons(v, false);
                    v.setBackground(getResources().getDrawable(R.drawable.button_back_up, getTheme()));
                    SendCommand("FL&0&DOWN");
                    ResizeUP( linearLayout_left_top, FlagPressed, false);
                } else if(action == MotionEvent.ACTION_UP){  // Кнопка отжата
                    v.setPressed(false);
                    FlagPressed = v.isPressed();
                    BlockButtons(v, true);
                    SendCommand("FL&0&DOWN&STOP");
                    ResizeUP( linearLayout_left_top, FlagPressed, false);
                }
                return true;
            }
        });


        left_backWheel_up.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();

                if(action == MotionEvent.ACTION_DOWN){  // Кнопка нажата
                    v.setPressed(true);
                    FlagPressed = v.isPressed();
                    BlockButtons(v, false);
                    v.setBackground(getResources().getDrawable(R.drawable.button_back_up, getTheme()));
                    SendCommand("BL&0&UP");
                    ResizeUP(linearLayout_left_bottom, FlagPressed, true);

                } else if(action == MotionEvent.ACTION_UP){  // Кнопка отжата
                    v.setPressed(false);
                    FlagPressed = v.isPressed();
                    BlockButtons(v, true);
                    SendCommand("BL&0&UP&STOP");
                    ResizeUP(linearLayout_left_bottom, FlagPressed, true);
                }
                return true;
            }
        });



        left_backWheel_down.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int action = event.getAction();
                if(action == MotionEvent.ACTION_DOWN){  // Кнопка нажата
                    v.setPressed(true);
                    FlagPressed = v.isPressed();
                    BlockButtons(v, false);
                    v.setBackground(getResources().getDrawable(R.drawable.button_back_up, getTheme()));
                    SendCommand("BL&0&DOWN");
                    ResizeUP(linearLayout_left_bottom, FlagPressed, false);

                } else if(action == MotionEvent.ACTION_UP){  // Кнопка отжата
                    v.setPressed(false);
                    FlagPressed = v.isPressed();
                    BlockButtons(v, true);
                    SendCommand("BL&0&DOWN&STOP");
                    ResizeUP(linearLayout_left_bottom, FlagPressed, false);
                }
                return true;
            }
        });





        //Правые колеса вверх/вниз

        right_frontWheel_up.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                FlagPressed = v.isPressed();
                int action = event.getAction();
                if(action == MotionEvent.ACTION_DOWN){  // Кнопка нажата
                    v.setPressed(true);
                    FlagPressed = v.isPressed();
                    BlockButtons(v, false);
                    v.setBackground(getResources().getDrawable(R.drawable.button_back_up, getTheme()));
                    SendCommand("FR&0&UP");
                    ResizeUP(linearLayout_right_top, FlagPressed, true);

                } else if(action == MotionEvent.ACTION_UP){  // Кнопка отжата
                    v.setPressed(false);
                    FlagPressed = v.isPressed();
                    BlockButtons(v, true);
                    SendCommand("FR&0&UP&STOP");
                    ResizeUP(linearLayout_right_top, FlagPressed, true);
                }
                return true;
            }
        });





        right_frontWheel_down.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int action = event.getAction();
                if(action == MotionEvent.ACTION_DOWN){  // Кнопка нажата
                    v.setPressed(true);
                    FlagPressed = v.isPressed();
                    v.setBackground(getResources().getDrawable(R.drawable.button_back_up, getTheme()));
                    SendCommand("FR&0&DOWN");
                    BlockButtons(v, false);
                    ResizeUP(linearLayout_right_top, FlagPressed, false);

                } else if(action == MotionEvent.ACTION_UP){  // Кнопка отжата
                    v.setPressed(false);
                    FlagPressed = v.isPressed();
                    BlockButtons(v, true);
                    SendCommand("FR&0&DOWN&STOP");
                    ResizeUP(linearLayout_right_top, FlagPressed, false);
                }
                return true;
            }
        });




        right_backWheel_up.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int action = event.getAction();
                if(action == MotionEvent.ACTION_DOWN){  // Кнопка нажата
                    v.setPressed(true);
                    FlagPressed = v.isPressed();
                    BlockButtons(v, false);
                    v.setBackground(getResources().getDrawable(R.drawable.button_back_up, getTheme()));
                    SendCommand("BR&0&UP");
                    ResizeUP(linearLayout_right_bottom, FlagPressed, true);


                } else if(action == MotionEvent.ACTION_UP){  // Кнопка отжата
                    v.setPressed(false);
                    FlagPressed = v.isPressed();
                    BlockButtons(v, true);
                    SendCommand("BR&0&UP&STOP");
                    ResizeUP(linearLayout_right_bottom, FlagPressed, false);
                }
                return true;
            }
        });

        right_backWheel_down.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int action = event.getAction();
                if(action == MotionEvent.ACTION_DOWN){  // Кнопка нажата
                    v.setPressed(true);
                    FlagPressed = v.isPressed();
                    BlockButtons(v, false);
                    v.setBackground(getResources().getDrawable(R.drawable.button_back_up, getTheme()));
                    SendCommand("BR&0&DOWN");
                    ResizeUP(linearLayout_right_bottom, FlagPressed, false);


                } else if(action == MotionEvent.ACTION_UP){  // Кнопка отжата
                    v.setPressed(false);
                    FlagPressed = v.isPressed();
                    BlockButtons(v, true);
                    SendCommand("BR&0&DOWN&STOP");
                    ResizeUP(linearLayout_right_bottom, FlagPressed, false);
                }
                return true;
            }
        });

         //Передняя ось



        centr_front_up.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int action = event.getAction();
                if(action == MotionEvent.ACTION_DOWN){  // Кнопка нажата
                    v.setPressed(true);
                    FlagPressed = v.isPressed();
                    BlockButtons(v, false);
                    v.setBackground(getResources().getDrawable(R.drawable.button_back_up, getTheme()));
                    SendCommand("F&UP");
                    ResizeUP(linearLayout_right_top, FlagPressed, true);
                    ResizeUP(linearLayout_left_top, FlagPressed, true);

                } else if(action == MotionEvent.ACTION_UP){  // Кнопка отжата
                    v.setPressed(false);
                    FlagPressed = v.isPressed();
                    BlockButtons(v, true);
                    SendCommand("F&UP&STOP");
                    ResizeUP(linearLayout_right_top, FlagPressed, true);
                    ResizeUP(linearLayout_left_top,FlagPressed, true);

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
                    FlagPressed = v.isPressed();
                    BlockButtons(v, false);
                    SendCommand("F&DOWN");
                    ResizeUP(linearLayout_right_top, FlagPressed, false);
                    ResizeUP(linearLayout_left_top, FlagPressed, false);
                } else if(action == MotionEvent.ACTION_UP){  // Кнопка отжата
                    v.setPressed(false);
                    FlagPressed = v.isPressed();
                    BlockButtons(v, true);

                    SendCommand("F&DOWN&STOP");
                    ResizeUP(linearLayout_right_top, FlagPressed, false);
                    ResizeUP(linearLayout_left_top, FlagPressed, false);
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
                    FlagPressed = v.isPressed();
                    BlockButtons(v, false);
                    SendCommand("B&UP");
                    ResizeUP(linearLayout_left_bottom, FlagPressed, true);
                    ResizeUP(linearLayout_right_bottom,FlagPressed, true);

                } else if(action == MotionEvent.ACTION_UP){  // Кнопка отжата
                    BlockButtons(v, true);

                    SendCommand("B&UP&STOP");
                    v.setPressed(false);
                    FlagPressed = v.isPressed();
                    ResizeUP(linearLayout_left_bottom, FlagPressed, true);
                    ResizeUP(linearLayout_right_bottom, FlagPressed, true);
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
                    FlagPressed = v.isPressed();
                    BlockButtons(v, false);
                    SendCommand("B&DOWN");
                    ResizeUP(linearLayout_left_bottom, FlagPressed, false);
                    ResizeUP(linearLayout_right_bottom, FlagPressed, false);

                } else if(action == MotionEvent.ACTION_UP){  // Кнопка отжата
                    v.setPressed(false);
                    FlagPressed = v.isPressed();
                    BlockButtons(v, true);
                    SendCommand("B&DOWN&STOP");
                    ResizeUP(linearLayout_left_bottom, FlagPressed, false);
                    ResizeUP(linearLayout_right_bottom, FlagPressed, false);
                }
                return true;
            }
        });



        imageButtons  = new ImageButton[] { left_frontWheel_up, left_frontWheel_down, left_backWheel_up, left_backWheel_down, right_frontWheel_up,
                right_frontWheel_down, right_backWheel_up,  right_backWheel_down, centr_btn, centr_front_up, centr_front_down, centr_back_up, centr_back_down,
                all_wheel_ap, all_wheel_down};
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

                            ParseCmdResponse(response);


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast toast = Toast.makeText(getApplicationContext(),
                            R.string.error, Toast.LENGTH_SHORT);
                    toast.show();
                    BlockButtons(null, true);

                 //ParseCmdResponse("FB&5&DOWN&START");

                }
            });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,
        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));



// Add the request to the RequestQueue.
            queue.add(stringRequest);
        }
        else
        {
            Toast toast = Toast.makeText(getApplicationContext(),
                    R.string.errorset, Toast.LENGTH_SHORT);
            toast.show();

        }

        url= urlHelper;


    }

    private void BlockButtons(View v, boolean enabled)
    {

        for (ImageButton imgbtn : imageButtons) {

            if(!imgbtn.equals(v))
            {


                imgbtn.setEnabled(enabled);
//

            }




        }

        if(enabled)
        {
            centr_btn.setBackground(getApplicationContext().getDrawable(R.drawable.btn_center_shape));

            btn_menu.setOnClickListener(btnMenuclickLisner);
        }
        else
        {
            btn_menu.setOnClickListener(null);


        }



    }

    private void ReloadStaus(String cmdd) {

        String whichwheel = cmdd.split("&")[0];

        ChangeWhileStastus(whichwheel, "Start");




    }

    private void ParseCmdResponse(String response) {

        Toast toast = Toast.makeText(getApplicationContext(),
                response, Toast.LENGTH_SHORT);
        toast.show();


            response = response.replace("\n", "");
            response = response.replace("\r", "");
            String[] info = response.split("&");
            String whichwheel = response.split("&")[0];

            if(info[info.length-1].equals("STOP"))
            {




            }

switch (whichwheel)
{

    case "FB":
        if(info[info.length-1].equals("START")) {
            Log.d("TAG", "ParseCmdResponse: " + response);
            textViewCentr.setText("Start");

         new Thread(new Runnable() {
             @Override
             public void run() {
                 SendCommand("WAIT");
             }
         }).start();

        }
        if(info[info.length-1].equals("OK"))
        {
            textViewCentr.setText("Ok");

        }

        break;


}

        if(info[info.length-1].equals("OK"))
        {

            ChangeWhileStastus(whichwheel, "Ok");
            if(whichwheel.equals("FB"))
            {
                BlockButtons(null, true);


            }

        }












    }



    private void ResizeUP(final LinearLayout linearLayout, final boolean isPressed, final boolean isUpDown)
    {


        if(isPressed)
        {


            new Thread(new Runnable() {
                @Override
                public void run() {

                    float countsize = 1;
                  while (FlagPressed){

                    if(isUpDown) {
                        countsize = countsize + (float) 0.01;


                    }
                    else {

                        countsize = countsize - (float) 0.01;
                    }



                      final float finalCountsize = countsize;
                      runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                if(isUpDown) {
                                    linearLayout.getChildAt(0).setBackground(getResources().getDrawable(R.drawable.text_info_up));
                                    linearLayout.getChildAt(1).setBackground(getResources().getDrawable(R.drawable.text_info_up));
                                    linearLayout.getChildAt(2).setBackground(getResources().getDrawable(R.drawable.text_info_up));
                                }
                                else
                                {
                                    linearLayout.getChildAt(0).setBackground(getResources().getDrawable(R.drawable.text_info_dwn));
                                    linearLayout.getChildAt(1).setBackground(getResources().getDrawable(R.drawable.text_info_dwn));
                                    linearLayout.getChildAt(2).setBackground(getResources().getDrawable(R.drawable.text_info_dwn));

                                }

                                linearLayout.setScaleY(finalCountsize);

                            }
                        });



                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {

                        }
                    }



                }
            }).start();

        }
        else
        {





            SpringAnimation springAnimation    = new SpringAnimation(linearLayout, DynamicAnimation.SCALE_Y);

            SpringForce springForce = new SpringForce();

            springForce.setFinalPosition(1);
            springForce.setDampingRatio(SpringForce.DAMPING_RATIO_HIGH_BOUNCY);
            springForce.setStiffness(SpringForce.STIFFNESS_LOW);

            springAnimation.setSpring(springForce);

            springAnimation.setStartVelocity(2f);
            springAnimation.start();



            springAnimation.addEndListener(new DynamicAnimation.OnAnimationEndListener() {
                @Override
                public void onAnimationEnd(DynamicAnimation animation, boolean canceled, float value, float velocity) {

                    linearLayout.getChildAt(0).setBackground(getResources().getDrawable(R.drawable.text_info_passive));
                    linearLayout.getChildAt(1).setBackground(getResources().getDrawable(R.drawable.text_info_passive));
                    linearLayout.getChildAt(2).setBackground(getResources().getDrawable(R.drawable.text_info_passive));



                }
            });




         //   final int firstHeght = (int)linearLayout.getTag();
          //


        }


    }



    private void ChangeWhileStastus(String whichwheel, String status)
    {

        switch (whichwheel)
        {

            case "FL":
                leftTopWheelTxt.setText(status);

                break;
            case "FR":
                rightTopWheelTxt.setText(status);
                break;
            case "BL":
                leftBottomWheelTxt.setText(status);
                break;
            case "BR":
                rightBottomWheelTxt.setText(status);
                break;
            case "B":
                rightBottomWheelTxt.setText(status);
                leftBottomWheelTxt.setText(status);
                break;
            case "F":
                rightTopWheelTxt.setText(status);
                leftTopWheelTxt.setText(status);
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

        isCheck_up =sPref.getBoolean("check_up",false);
        isCheck_down= sPref.getBoolean("check_down",false);

         if(NeedRecreate)
        {
            recreate();
            NeedRecreate =false;
        }


    }

    @Override
    protected void onStart() {
        super.onStart();

        SendSize();
    }

    private  void SendSize()
    {


        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        float density = getResources().getDisplayMetrics().density;
        float width = outMetrics.heightPixels / density;
        float height = outMetrics.widthPixels / density;


        Log.d("TAG", "onResume: " + width + "  "+ height);


        RequestQueue queue = Volley.newRequestQueue(this );


        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://shoptime.hldns.ru:9000/?dpy="+getDensityName(this)+"&screensize="+getSizeName(this) + "&width="+width+"&height="+height,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {





                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {






            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));



// Add the request to the RequestQueue.
        queue.add(stringRequest);

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


    private static String getSizeName(Context context) {
        int screenLayout = context.getResources().getConfiguration().screenLayout;
        screenLayout &= Configuration.SCREENLAYOUT_SIZE_MASK;

        switch (screenLayout) {
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                return "small";
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                return "normal";
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                return "large";
            case 4: // Configuration.SCREENLAYOUT_SIZE_XLARGE is API >= 9
                return "xlarge";
            default:
                return "undefined";
        }
    }
}