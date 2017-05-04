package com.capco.e1090384.eagleibank;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ArrayAdapter;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import org.json.JSONTokener;

import java.text.DecimalFormat;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //for Android monitor logging
    static String TAG = "MainActivity";
    //
    private GoogleApiClient client;
    //next 3 lines define references to types of object on the screen
    private TextView displaybalance;
    private Button requestbalance;
    private EditText userid;
    //
    RequestQueue requestQueue;    // set up a RequestQueue object instance
    public static final String stringroot = "http://35.176.3.228:3000/api/transactions?id="; // define the root of the api request payload
    //public static final String stringroot = "http://10.0.2.2:3000/api/transactions?id="; // define the root of the api request payload
    public static String stringurl = null;  // make sure the api request payload is initialised empty

    @Override
    //do the following as part of instantiating the the main page of app
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //log onCreate has been reached for debugging purposes
        //Log.i(TAG, "onCreate happening!");
        // Instantiate GoogleAPIClient using the previously defined reference
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        //instantiate the references as pointing to objects now created on the main screen
        displaybalance = (TextView) (findViewById(R.id.theBalance));
        requestQueue = Volley.newRequestQueue(this);
        requestbalance = (Button) (findViewById(R.id.balancebutton));
        requestbalance.setOnClickListener(this);
    }

    @Override
    // Do this when the button is clicked
    public void onClick(View v) {
        userid = (EditText) (findViewById(R.id.uidText));  // capture the user Text entered in Text edit field
        String stringuserid = userid.getText().toString();  // turn the text into a string
        final String stringurl = stringroot + stringuserid; // concatenate onto the URL root so that the right balance is retrieved
        //Log.i(TAG, "string url before  = " + stringurl);   // output the request payload into the log for debugging purposes
        // build request to API. Note the API request is for all transactions for the user. The balance is then calculated (below)
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, stringurl, (String)null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Log.i(TAG, "array 1  = " + response); //output response into log for debugging purposes
                        try {
                            JSONArray jArray = response;  // load response into an array
                            //Log.i(TAG, "array 1  = " + jArray);  // output array to log for debugging purposes
                            String balance = "0";  // initiate balance calculation variables
                            Double dbalance = 0.0;  // initiate balance calculation variables
                            // For each transaction in array convert transaction amount from array string to number and sum
                            for(int i=0; i<jArray.length(); i++) {
                                JSONObject json_object = (JSONObject)jArray.get(i);
                                String tranamount = json_object.getString("tranamount");
                                Double dtranamount= Double.valueOf(tranamount);
                                dbalance = dbalance + dtranamount;
                                //Log.i(TAG, "tranamount  = " + tranamount);  // show transaction amounts being summed in log for debugging purposes
                                //Log.i(TAG, "dbalance  = " + dbalance);  // show balance changing in log for debugging purposes
                            }
                            //PFormatting and rounding of final balance
                            DecimalFormat df = new DecimalFormat("#.##");
                            Double roundbalance = Double.valueOf(df.format(dbalance));
                            balance = String.valueOf(roundbalance);
                            //Log.i(TAG, "balance  = " + balance); // show formatted balance in log
                            displaybalance.setText("Your current balance is: £   " + balance); // output balance to screen
                        } catch(JSONException e) {
                            System.out.println(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error);
                    }
                }
        );
         RequestQueue requestQueue = Volley.newRequestQueue(this); // reinitialise requestQueue for next request
        requestQueue.add(request);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }
    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }
    @Override
    public void onStop() {
        super.onStop();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
