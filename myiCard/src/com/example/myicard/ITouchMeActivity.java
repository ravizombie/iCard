package com.example.myicard;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ITouchMeActivity extends Activity implements OnClickListener{
 
	private Button button1;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_itouch_me);
        
        //String filePath="file:///android_asset/images/background1.jpg";
       // Drawable d = Drawable.createFromPath(filePath);
       // setBackgroundDrawable(d);
        //this.getWindow().setBackgroundDrawableResource(R.id.background1);
        
        button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(this);
         
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_itouch_me, menu);
        return true;
    }
 
    public void onClick (View v)
    {
		if( v == button1)
		{
			//button1.setActivated(false);
			//button1.setText("processing");
			startActivity(new Intent(this, IAcceptActivity.class));
		
		}
    }

     
}

