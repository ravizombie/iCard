package com.example.InteractiveClass;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class help extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.help);
        TextView next1 = (TextView) findViewById(R.id.textView1);
        next1.setMovementMethod(new ScrollingMovementMethod());
        }
}