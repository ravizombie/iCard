package com.example.InteractiveClass;




import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class screen1 extends Activity {
	int localflag = 0;
	GridView grid_main;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_switch);
        grid_main = (GridView)findViewById(R.id.GridView01);
		grid_main.setAdapter(new ImageAdapter(this));
		grid_main.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, final View v, int position, long id) {
	        
	        	if(position==0)
	            {
	        	//Toast.makeText(MenuActivity.this, "" + position, Toast.LENGTH_SHORT).show();
	        	Intent myIntent = new Intent(v.getContext(), profile.class);
                startActivityForResult(myIntent, 0);
	            }
	            if(position==1)
	            {
	        	//Toast.makeText(MenuActivity.this, "" + position, Toast.LENGTH_SHORT).show();
	        	Intent myIntent = new Intent(v.getContext(), CourseActivity.class);
                startActivityForResult(myIntent, 0);
	            }
	            if(position==2)
	            {
	        	//Toast.makeText(MenuActivity.this, "" + position, Toast.LENGTH_SHORT).show();
	            	try {
	             	   
	            		AlertDialog.Builder alt_bld = new AlertDialog.Builder(screen1.this);  	
				    	alt_bld.setMessage("Do you want to check in for CSCI588 at OHE 122?")
				    		       	.setCancelable(false)
				    		       	.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				    		       	public void onClick(DialogInterface dialog, int id) {
				    		       	// Action for 'Yes' Button
				    		       		Intent myIntent = new Intent(v.getContext(), FingerPaint.class);
				    	                startActivityForResult(myIntent, 0);
				    		          
				    		       	}
				    		       	})
				    		       	.setNegativeButton("No", new DialogInterface.OnClickListener() {
				    		       	public void onClick(DialogInterface dialog, int id) {
				    		       	//  Action for 'NO' Button
				    		       	dialog.cancel();
				    		       	}
				    		       	});
				    		       	
				    		       	AlertDialog alertDialog = alt_bld.create();
				    		       	alertDialog.setTitle("Check-In");
				    		       	
				    		       	if(GlobalVar.showflag==1)
				    		       	{
				    		       		GlobalVar.showflag=2;
				    		       alertDialog.show();
				    		       	}
				    	} catch (Exception e) {
				        e.printStackTrace();
				    }
			}
	            if(position==5)
	            {
	        	//Toast.makeText(MenuActivity.this, "" + position, Toast.LENGTH_SHORT).show();
	            	GlobalVar.toastflag = 0;
	    	        Intent myIntent = new Intent(v.getContext(), mapclass.class);
	                startActivityForResult(myIntent, 0);
	            }
	            
	            if(position==6)
	            {
	        	//Toast.makeText(MenuActivity.this, "" + position, Toast.LENGTH_SHORT).show();
	        	Intent myIntent = new Intent(v.getContext(), InteractiveClassActivity.class);
                startActivityForResult(myIntent, 0);
	            }
	            if(position==7)
	            {
	        	//Toast.makeText(MenuActivity.this, "" + position, Toast.LENGTH_SHORT).show();
	        	Intent myIntent = new Intent(v.getContext(), help.class);
                startActivityForResult(myIntent, 0);
	            }
	            if(position==8)
	            {
	        	//Toast.makeText(MenuActivity.this, "" + position, Toast.LENGTH_SHORT).show();
	        	Intent myIntent = new Intent(v.getContext(), about.class);
                startActivityForResult(myIntent, 0);
	            }
	            if(position==3)
	            {
	        	//Toast.makeText(MenuActivity.this, "" + position, Toast.LENGTH_SHORT).show();
	        	Intent myIntent = new Intent(v.getContext(), misc.class);
                startActivityForResult(myIntent, 0);
	            }
	            if(position==4)
	            {
	        	//Toast.makeText(MenuActivity.this, "" + position, Toast.LENGTH_SHORT).show();
	        	Intent myIntent = new Intent(v.getContext(), screen3.class);
                startActivityForResult(myIntent, 0);
	            }
	        }
	    });
        
            
            
            
    }
    
    public class ImageAdapter extends BaseAdapter{
		Context mContext;
		public static final int ACTIVITY_CREATE = 10;
		public ImageAdapter(Context c){
			mContext = c;
		}
		public int getCount() {
			// TODO Auto-generated method stub
			return 9;
		}
		int i;
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View v;
			if(convertView==null){
				LayoutInflater li = getLayoutInflater();
				v = li.inflate(R.layout.icon, null);
				TextView tv = (TextView)v.findViewById(R.id.icon_text);
				
				if(position==0)
				{
				tv.setText("    Profile");
				}
				
				if(position==1)
				{
				tv.setText("Registered Classes");
				}
				if(position==2)
				{
				tv.setText("   Check In");
				}
				if(position==3)
				{
				tv.setText("   Misc Info");
				}
				if(position==4)
				{
				tv.setText("     Quiz");
				}
				if(position==5)
				{
				tv.setText("Locate Class");
				}
				if(position==6)
				{
				tv.setText("   Logout");
				}
				if(position==7)
				{
				tv.setText("     Help");
				}
				if(position==8)
				{
				tv.setText("  About Us");
				}
				ImageView iv = (ImageView)v.findViewById(R.id.icon_image);
				iv.setImageResource(R.drawable.a1+i);
				i++;

			}
			else
			{
				v = convertView;
			}
			return v;
		}
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}
	}
}