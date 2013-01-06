package com.example.InteractiveClass;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class screen2 extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen2);
        Button next = (Button) findViewById(R.id.button1);
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	Intent myIntent = new Intent(view.getContext(), screen3.class);
                startActivityForResult(myIntent, 0);
            }
        });
            
        Button next1 = (Button) findViewById(R.id.button2);
        next1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	Intent myIntent = new Intent(view.getContext(), screen4.class);
                startActivityForResult(myIntent, 0);
            }
        });
            
        Button next2 = (Button) findViewById(R.id.button3);
        next2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	Intent myIntent = new Intent(view.getContext(), screen5.class);
                startActivityForResult(myIntent, 0);
            }
        });
        Button next3 = (Button) findViewById(R.id.button4);
        next3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	Intent myIntent = new Intent(view.getContext(), InteractiveClassActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });
        
        Button next4 = (Button) findViewById(R.id.button5);
        next4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	Intent myIntent = new Intent(view.getContext(), screen1.class);
                startActivityForResult(myIntent, 0);
            }
        });
            
            
    }
}