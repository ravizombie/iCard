package com.example.InteractiveClass;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class screen6 extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.screen6);
        Button next1 = (Button) findViewById(R.id.button1);
        next1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	Intent myIntent = new Intent(view.getContext(), Scores.class);
                startActivityForResult(myIntent, 0);
            }
        });
            
        Button next2 = (Button) findViewById(R.id.button2);
        next2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	Intent myIntent = new Intent(view.getContext(), CourseActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });
        }
}