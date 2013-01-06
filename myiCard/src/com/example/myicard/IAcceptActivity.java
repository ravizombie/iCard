package com.example.myicard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;


public class IAcceptActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iaccept);
         

       try {
    	
    	   /*
    	   URL url = new URL("http://ghsc-2012.com/trial_dr/");
      		HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
      
      		InputStream in = new BufferedInputStream(urlConnection.getInputStream());
      	     readStream(in);
      	   urlConnection.disconnect();
    	   */
    
    	   ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
 
    	   StrictMode.setThreadPolicy(policy); 
    	   
    	   /*
    	   HttpGet method = new HttpGet("http://ghsc-2012.com/trial_dr/");
    	   HttpClient client = new DefaultHttpClient();
       		StringBuilder total = new StringBuilder();
       
  
    	   HttpResponse response =  client.execute(method);
           
           HttpEntity ht = response.getEntity();

           BufferedHttpEntity buf = new BufferedHttpEntity(ht);

           InputStream is = buf.getContent();

           BufferedReader r = new BufferedReader(new InputStreamReader(is));

           String line;
           while ((line = r.readLine()) != null) {
               total.append(line);
           }
          
        Log.d(getPackageName(), "onclick(),"+total );
           */

        AuthenticateLogin authlogin = new AuthenticateLogin();
        authlogin.execute();
		
       } catch (Exception e) {
           // TODO Auto-generated catch block
           e.printStackTrace();
       }
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_iaccept, menu);
        return true;
    }
    
    
    class AuthenticateLogin extends AsyncTask<String, Integer, String> {

    	ProgressDialog dialog;
    	String jsonReturnData = new String();
    	JSONObject myJSON = null;

    	@Override
    	protected String doInBackground(String... loginHandle) {
    	publishProgress();

         
    	 
    	//String checkInUrl = "http://ghsc-2012.com/trial_dr/";
    	//String checkInUrl  = "https://atthackathon-sandbox.axeda.com/services/v1/rest/Scripto/execute/Team31YourApp?username=team31user&password=team31pass";
    	String checkInUrl  = "http://ajinkyad.com/brandy/ajinkya.php";
    	System.out.println(" URL: " + checkInUrl);
    	HttpResponse httpResponse;

    	try {
    	HttpClient httpClient = new DefaultHttpClient();
    	HttpGet getMethod = new HttpGet(checkInUrl);
    	System.out.println("GETMETHOD: "+getMethod.getRequestLine().toString());

    	httpResponse = httpClient.execute(getMethod);
    	Log.d("GETRESPONSE", httpResponse.getStatusLine().toString());
    	if(httpResponse != null){
    	HttpEntity entity = httpResponse.getEntity();
    	InputStream instream = entity.getContent();
    	BufferedReader reader = new BufferedReader(
    	new InputStreamReader(instream));
    	StringBuilder sb = new StringBuilder();
    	String line = null;

    	while ((line = reader.readLine()) != null) {
    	sb.append(line + "\n");
    	}
    	jsonReturnData = sb.toString();
    	}
    	} catch (Exception e) {
    	e.printStackTrace();
    	}
    	 
    	try{
    	int i=1; 
    	while(i > 0)
    	{ 	Thread.sleep(1000); i--;}
    	}catch (Exception e){
    		e.printStackTrace();	
    	}
    	
    	
    	return jsonReturnData;
    	}

    	public String getDataFromResponse(HttpResponse httpResponse) throws IllegalStateException, IOException{
    	HttpEntity entity = httpResponse.getEntity();
    	InputStream instream = entity.getContent();
    	BufferedReader reader = new BufferedReader(
    	new InputStreamReader(instream));
    	StringBuilder sb = new StringBuilder();
    	String line = null;

    	while ((line = reader.readLine()) != null) {
    	sb.append(line + "\n");
    	}
    	return sb.toString();
    	}
    	@Override
    	protected void onProgressUpdate(Integer... values) {
    	// TODO Auto-generated method stub
    	dialog = ProgressDialog.show(IAcceptActivity.this,
    	"Processing Request", "Please Hang On...", true);
    	dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    	dialog.setCancelable(false);
    	}

    	@Override
    	protected void onPostExecute(String result) {
    	dialog.cancel();
    	Log.d("httpresult", result);
    	
    	if(result.length() != 0){
    	Intent intent = new Intent(IAcceptActivity.this, DataServerActivity.class);
    	intent.putExtra("details", result.toString());
    	Toast.makeText(IAcceptActivity.this, "Checked In Sucessfully", Toast.LENGTH_LONG).show();
    	startActivity(intent);
    	}else{
    	Toast.makeText(IAcceptActivity.this, "Check In Failed: You are faking it !!!!", Toast.LENGTH_LONG).show();
    	}
    	}
    	}
    
    
}
