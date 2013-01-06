package com.example.InteractiveClass;

	
import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.MapView.LayoutParams;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class mapclass extends MapActivity {
	int localflag = 0;
	MapView mapView;
	View view;
	MapController mc;
	double buildingLat[],buildingLng[];
	private LocationManager locationManager;
	private LocationListener locationListener;
	class MapOverlay extends com.google.android.maps.Overlay
    {
	
	private GeoPoint pointToDraw;
	private int type;
	private Bitmap bitmap1;
	Paint paint = new Paint();
    
    public void setPointToDraw(GeoPoint point, int color,int intype) {
      pointToDraw = point;
      paint.setColor(color);
      type = intype;
    }
		
        @Override
        public boolean draw(Canvas canvas, MapView mapView, 
        boolean shadow, long when) 
        {
            super.draw(canvas, mapView, shadow);                   
 
            //---translate the GeoPoint to screen pixels---
            Point screenPts = new Point();
            mapView.getProjection().toPixels(pointToDraw, screenPts);
            if(type==1)
            {
            bitmap1 = BitmapFactory.decodeResource(getResources(),R.drawable.house_yellow);
            canvas.drawBitmap(bitmap1,screenPts.x-8, screenPts.y-58, paint);
            
            }
            else
            {
            bitmap1 = BitmapFactory.decodeResource(getResources(),R.drawable.manback);
            canvas.drawBitmap(bitmap1,screenPts.x-8, screenPts.y-58, paint);
            
            }
            
           // canvas.drawCircle(screenPts.x, screenPts.y-50, 5, paint);
            return true;
        }
    }

	 class GPSLocationListener implements LocationListener 
	    {
		  private void initiatePopupWindow(final MapView mapView) {
			    try {
			    	AlertDialog.Builder alt_bld = new AlertDialog.Builder(mapclass.this);  	
			    	alt_bld.setMessage("Do you want to check in for CSCI588 at OHE 122?")
			    		       	.setCancelable(false)
			    		       	.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			    		       	public void onClick(DialogInterface dialog, int id) {
			    		       	// Action for 'Yes' Button
			    		      		Intent myIntent = new Intent(mapView.getContext(), FingerPaint.class);
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
			    		       	alertDialog.show();
			    		       	GlobalVar.showflag=2;
			    		           
			    	} catch (Exception e) {
			        e.printStackTrace();
			    }
			}
			 
			
		
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
	          
	          if(dist<=0.012&&localflag==0)
	        {
	       	  initiatePopupWindow(mapView);
	       	  localflag=1;
	        } else if(dist>0.012)
	        {

	        	  GlobalVar.showflag=0;
	        }
	          	  
	         // Toast.makeText(getBaseContext(),"My Location : Latitude: " + location.getLatitude() + " Longitude: " + location.getLongitude(), Toast.LENGTH_SHORT).show();
	          mc = mapView.getController();
	          mc.animateTo(point);
	          mc.setZoom(20);
	          MapOverlay mapOverlay = new MapOverlay();
	          mapOverlay.setPointToDraw(point,Color.RED,2);
	          List<Overlay> listOfOverlays = mapView.getOverlays();
	    //      listOfOverlays.clear();
	          if(listOfOverlays.size()==6)
	          listOfOverlays.remove(5);
	          listOfOverlays.add(mapOverlay);
	          //mapView.invalidate();
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

	  public void onCreate(Bundle savedInstanceState) {
		  
	  super.onCreate(savedInstanceState);
      setContentView(R.layout.newfile);
      locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);  
      
      locationListener = new GPSLocationListener();
      
      locationManager.requestLocationUpdates(
        LocationManager.GPS_PROVIDER, 
        0, 
        0, 
        locationListener);
      
      mapView = (MapView) findViewById(R.id.mapView);
      LinearLayout zoomLayout = (LinearLayout)findViewById(R.id.zoom);  
      View zoomView = mapView.getZoomControls(); 

      zoomLayout.addView(zoomView, 
          new LinearLayout.LayoutParams(
              LayoutParams.WRAP_CONTENT, 
              LayoutParams.WRAP_CONTENT));
      mapView.displayZoomControls(true);
      mc = mapView.getController();
      //String coordinates[] = {"1.352566007", "103.78921587"};
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
      mc.animateTo(p);
      mc.setZoom(20); 
      MapOverlay mapOverlay = new MapOverlay();
      List<Overlay> listOfOverlays = mapView.getOverlays();
      mapOverlay.setPointToDraw(p,Color.BLACK,1);
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
      //mc.animateTo(p);
      //mc.setZoom(20); 
      mapOverlay = new MapOverlay();
      mapOverlay.setPointToDraw(p,Color.CYAN,1);
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
     // mc.animateTo(p);
     // mc.setZoom(20); 
      mapOverlay = new MapOverlay();
      mapOverlay.setPointToDraw(p,Color.MAGENTA,1);
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
     // mc.animateTo(p);
     // mc.setZoom(20); 
      mapOverlay = new MapOverlay();
      mapOverlay.setPointToDraw(p,Color.GRAY,1);
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
     // mc.animateTo(p);
     // mc.setZoom(20); 
      mapOverlay = new MapOverlay();
      mapOverlay.setPointToDraw(p,Color.YELLOW,1);
      listOfOverlays.add(mapOverlay);
      Button next = (Button) findViewById(R.id.button5);
      next.setOnClickListener(new View.OnClickListener() {
          public void onClick(View view) {

        		GlobalVar.toastflag = 1;
          	Intent myIntent = new Intent(view.getContext(), screen1.class);
              startActivityForResult(myIntent, 0);
          }
      });
     
	  }

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
}
