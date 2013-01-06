package com.example.myicard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.EditText;
import android.widget.TextView;

public class DataServerActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	//EditText editText1;
    	TextView textview1;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_server);
        
       // editText1 = (EditText) findViewById(R.id.editText1);
        textview1 = (TextView) findViewById(R.id.textView1);
        Intent intent1 = getIntent();
        String details = null;
        details = intent1.getStringExtra("details"); 
        
        textview1.setText(details);
        
    }
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_data_server, menu);
        return true;
    }
}
