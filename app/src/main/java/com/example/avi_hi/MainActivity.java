package com.example.avi_hi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btn_action1,btn_action2,btn_action3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_action1 = (Button)findViewById(R.id.btn1);
        btn_action2 = (Button)findViewById(R.id.btn2);
        btn_action3 = (Button)findViewById(R.id.btn3);

        btn_action1.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(MainActivity.this,hospital_login.class);
                    startActivity(i);
                }
            });
//
//        btn_action2.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(.this, .class);
//                startActivity(i);
//            }
//        });
//
        btn_action3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,ambulance_login.class);
                startActivity(i);
            }
        });
    }
}
