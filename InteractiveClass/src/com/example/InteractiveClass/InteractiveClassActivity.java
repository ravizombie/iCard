package com.example.InteractiveClass;

import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import java.util.List;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import com.google.android.maps.MapView.LayoutParams;  

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;


public class InteractiveClassActivity extends Activity {
	
	///////////////////////////
	
	
	MapView mapView;
	View view;
	MapController mc;
	double buildingLat[],buildingLng[];
	private LocationManager locationManager;
	private LocationListener locationListener;
	int showflag=0;
	
	
	
	class MapOverlay extends com.google.android.maps.Overlay
    {
	
	private GeoPoint pointToDraw;
	Paint paint = new Paint();
    
    public void setPointToDraw(GeoPoint point, int color) {
      pointToDraw = point;
      paint.setColor(color);
    }
		
        @Override
        public boolean draw(Canvas canvas, MapView mapView, 
        boolean shadow, long when) 
        {
            super.draw(canvas, mapView, shadow);                   
 
            //---translate the GeoPoint to screen pixels---
            Point screenPts = new Point();
            mapView.getProjection().toPixels(pointToDraw, screenPts);
 
            canvas.drawCircle(screenPts.x, screenPts.y-50, 5, paint);
            return true;
        }
    }
	
	 class GPSLocationListener implements LocationListener 
	    {
		 	 
			
		
		  private double deg2rad(double deg) {
			  return (deg * Math.PI / 180.0);
			}
		  
		  private double rad2deg(double rad) {
			  return (rad * 180.0 / Math.PI);
			}
		  
		  private double distance(double lat1, double lon1, double lat2, double lon2, char unit) {
			  double theta = lon1 - lon2;
			  double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
			  dist = Math.acos(dist);
			  dist = rad2deg(dist);
			  dist = dist * 60 * 1.1515;
			  if (unit == 'K') {
			    dist = dist * 1.609344;
			  } else if (unit == 'N') {
			  	dist = dist * 0.8684;
			    }
			  return (dist);
			}
		  
	      public void onLocationChanged(Location location) {
	        if (location != null) {
	          GeoPoint point = new GeoPoint(
	              (int) (location.getLatitude() * 1E6), 
	              (int) (location.getLongitude() * 1E6));
	        double dist =   distance(location.getLatitude(),location.getLongitude(),buildingLat[0],buildingLng[0],'K');
	          
	        //Toast.makeText(getBaseContext(),"The distance is " + dist + "kilometers", Toast.LENGTH_LONG).show();
	          if(dist<=0.012&&(GlobalVar.showflag==0||GlobalVar.showflag==2)&&GlobalVar.toastflag==1)
	        {     
	       	  Toast.makeText(getBaseContext(),"You have reached near OHE. Go to Check-In", Toast.LENGTH_LONG).show();
	       	  GlobalVar.showflag=1;
	        }
	          else
	        {
	           	       	  
	        	  GlobalVar.showflag=0;

	        }
	          	  
	         // Toast.makeText(getBaseContext(),"My Location : Latitude: " + location.getLatitude() + " Longitude: " + location.getLongitude(), Toast.LENGTH_SHORT).show();
	      /*    mc = mapView.getController();
	          mc.animateTo(point);
	          mc.setZoom(20);
	          MapOverlay mapOverlay = new MapOverlay();
	          mapOverlay.setPointToDraw(point,Color.RED);
	          List<Overlay> listOfOverlays = mapView.getOverlays();
	    //      listOfOverlays.clear();
	          if(listOfOverlays.size()==6)
	          listOfOverlays.remove(5);
	          listOfOverlays.add(mapOverlay);
	       */   //mapView.invalidate();
	        }
	      }

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}
	}
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
       
        /////////////////////////////////////////////
        
        
 locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);  
        
        locationListener = new GPSLocationListener();
        
        locationManager.requestLocationUpdates(
          LocationManager.GPS_PROVIDER, 
          0, 
          0, 
          locationListener);
        
   /*     
        mapView = (MapView) findViewById(R.id.mapView);
        LinearLayout zoomLayout = (LinearLayout)findViewById(R.id.zoom);  
        View zoomView = mapView.getZoomControls(); 
 
        zoomLayout.addView(zoomView, 
            new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, 
                LayoutParams.WRAP_CONTENT));
        mapView.displayZoomControls(true);
        mc = mapView.getController();
     */   //String coordinates[] = {"1.352566007", "103.78921587"};
        buildingLat = new double[5];
        buildingLng = new double[5];
        String coordinates[] = {"34.0207","-118.28946"};
        double lat = Double.parseDouble(coordinates[0]);
        double lng = Double.parseDouble(coordinates[1]);
        
        GeoPoint p = new GeoPoint(
            (int) (lat * 1E6), 
            (int) (lng * 1E6));
        buildingLat[0] = lat;
        buildingLng[0] = lng;
       /*
        *  mc.animateTo(p);
        
        mc.setZoom(20); 
        MapOverlay mapOverlay = new MapOverlay();
        List<Overlay> listOfOverlays = mapView.getOverlays();
        mapOverlay.setPointToDraw(p,Color.BLACK);
        listOfOverlays.clear();
        listOfOverlays.add(mapOverlay);
        
        coordinates[0] = "34.02105";
        coordinates[1] = "-118.28795"; 
        lat = Double.parseDouble(coordinates[0]);
        lng = Double.parseDouble(coordinates[1]);
        
        p = new GeoPoint(
            (int) (lat * 1E6), 
            (int) (lng * 1E6));
        buildingLat[1] = lat;
        buildingLng[1] = lng;
        mc.animateTo(p);
        mc.setZoom(20); 
        mapOverlay = new MapOverlay();
        mapOverlay.setPointToDraw(p,Color.CYAN);
        listOfOverlays.add(mapOverlay);
         
        coordinates[0] = "34.01933";
        coordinates[1] = "-118.28684"; 
        lat = Double.parseDouble(coordinates[0]);
        lng = Double.parseDouble(coordinates[1]);
        
        p = new GeoPoint(
            (int) (lat * 1E6), 
            (int) (lng * 1E6));
        buildingLat[2] = lat;
        buildingLng[2] = lng;
        mc.animateTo(p);
        mc.setZoom(20); 
        mapOverlay = new MapOverlay();
        mapOverlay.setPointToDraw(p,Color.MAGENTA);
        listOfOverlays.add(mapOverlay);
        
        coordinates[0] = "34.01923";
        coordinates[1] = "-118.28946"; 
        lat = Double.parseDouble(coordinates[0]);
        lng = Double.parseDouble(coordinates[1]);
        
        p = new GeoPoint(
            (int) (lat * 1E6), 
            (int) (lng * 1E6));
        buildingLat[3] = lat;
        buildingLng[3] = lng;
        mc.animateTo(p);
        mc.setZoom(20); 
        mapOverlay = new MapOverlay();
        mapOverlay.setPointToDraw(p,Color.GRAY);
        listOfOverlays.add(mapOverlay);
        //mapView.invalidate();
        
        coordinates[0] = "34.01901";
        coordinates[1] = "-118.2863"; 
        lat = Double.parseDouble(coordinates[0]);
        lng = Double.parseDouble(coordinates[1]);
        
        p = new GeoPoint(
            (int) (lat * 1E6), 
            (int) (lng * 1E6));
        buildingLat[4] = lat;
        buildingLng[4] = lng;
        mc.animateTo(p);
        mc.setZoom(20); 
        mapOverlay = new MapOverlay();
        mapOverlay.setPointToDraw(p,Color.YELLOW);
        listOfOverlays.add(mapOverlay);
        */
        
        ////////////////////////////////////////////
        Button next = (Button) findViewById(R.id.button1);
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	Intent myIntent = new Intent(view.getContext(), screen1.class);
                startActivityForResult(myIntent, 0);
            }
        });
            
    }
}