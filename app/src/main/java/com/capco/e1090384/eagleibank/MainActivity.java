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
    private TextView displaybalance;
    private Button requestbalance;
    private EditText userid;
    RequestQueue requestQueue;
    public static final String stringroot = "http://10.0.2.2:3000/api/transactions?id=";
    public static String stringurl = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //log onCreate has been reached
        Log.i(TAG, "onCreate happening!");
        //
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        //
        displaybalance = (TextView) (findViewById(R.id.theBalance));
        requestQueue = Volley.newRequestQueue(this);
        requestbalance = (Button) (findViewById(R.id.balancebutton));
        requestbalance.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        userid = (EditText) (findViewById(R.id.uidText));
        String stringuserid = userid.getText().toString();
        final String stringurl = stringroot + stringuserid;
        Log.i(TAG, "string url  = " + stringurl);
      //  sendRequest();
   // }

///    private void sendRequest() {
        Log.i(TAG, "string url before  = " + stringurl);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, stringurl, (String)null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.i(TAG, "array 1  = " + response);
                        try {
                            JSONArray jArray = response;
                            Log.i(TAG, "array 1  = " + jArray);
                            String balance = "0";
                            Double dbalance = 0.0;
                            for(int i=0; i<jArray.length(); i++) {
                                JSONObject json_object = (JSONObject)jArray.get(i);
                                String tranamount = json_object.getString("tranamount");
                                Double dtranamount= Double.valueOf(tranamount);
                                dbalance = dbalance +dtranamount;
                                Log.i(TAG, "tranamount  = " + tranamount);
                                Log.i(TAG, "dbalance  = " + dbalance);
                            }
                            DecimalFormat df = new DecimalFormat("#.##");
                            Double roundbalance = Double.valueOf(df.format(dbalance));
                            balance = String.valueOf(roundbalance);
                            Log.i(TAG, "balance  = " + balance);
                            displaybalance.setText("Your current balance is: £   " + balance);
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
       // JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(stringurl,
         //       new Response.Listener<JSONArray>() {
           //         @Override
       //             public void onResponse(JSONArray response) {
       //                 Log.i(TAG, "string url  = " + response);
       //                 try {
       //                     JSONArray jsonArray = new JSONArray(response);
       //                     Log.i(TAG, "jsonArray  = " + jsonArray);
       //                  //   Log.i(TAG, "jsonArray  = " + response[0]);
       //                 } catch (JSONException e) {
        //                    e.printStackTrace();
        //                }
        //
        //                //    showJSON(response);
        //            }
        //        },
        //        new Response.ErrorListener() {
         //           @Override
         //           public void onErrorResponse(VolleyError error) {
         //               Log.i(TAG, "string url  =" + stringurl + error);
         //               Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
          //          }
          //      });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    private void showJSON(String json) {
        ParseJSON pj = new ParseJSON(json);
        pj.parseJSON();
        CustomList cl = new CustomList(this, ParseJSON.ids, ParseJSON.custrefs, ParseJSON.trandates, ParseJSON.tranamounts);
        Log.i(TAG, "string cl  = " + cl);
      //  theBalance.setAdapter(cl);
    }

   // protected void balance(View v) {
        //Get references (set up local variables) for the input and output controls involved
          // @Override
           // this is what hppens when its clicked
           //instantiate a json object request with GET & API url
           //public void onClick(View v) {

               //Process the input
               //Turn the user ID entered into a string, ready for url concatanation

               //create a variable that is set when balancebutton is clicked
            //   StringRequest stringRequest = new StringRequest(stringurl,
              //         new Response.Listener<String>() {
                //           @Override
                  //         public void onResponse(String response) {
                    //           showJSON(response);
                  //         }
                  //     },
                  //     new Response.ErrorListener() {
                   //        @Override
                   //        public void onErrorResponse(VolleyError error) {
                   //            Toast.makeText(MainActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();
                   //        }
                   //    });


               //JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, stringurl,
                       //then a new listener to wait for the response
                 //      new Response.Listener<JSONObject>() {
                     //      @Override
                           //public TextView payload = (TextView) (findViewById(R.id.theBalance));
                      //     public void onResponse(JSONObject response) {
                               //this what happens when the response arrives
                   //            Log.i(TAG, "string URL  = " + stringurl + "response received"+response);
                     //          double temp = 0.0;


                               //JSONTokener result=null;
                               //JSONArray jArray = null;
                               //try {
                               //    jArray = new JSONArray(result);
                                //     Log.i(TAG, "jArray  = " + jArray);
                               //} catch (JSONException e1) {
                               //    e1.printStackTrace();
                               //}
                               //double balancesum = 0.0;
                               //for (int i = 0; i < jArray.length(); i++) {
                               //    JSONObject jObject = null;
                               //    try {
                               //        jObject = jArray.getJSONObject(i);
                               //    } catch (JSONException e1) {
                               //        e1.printStackTrace();
                               //    }
                               //    double tranamount = 0;
                               //    try {
                               //        tranamount = jObject.getDouble("tranamount");
                               //    } catch (JSONException e1) {
                               //        e1.printStackTrace();
                               //    }
                               //    balancesum = balancesum + tranamount;
                               //}
                               //payload.append(Double.toString(balancesum));
                        //   }

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
//},  new Response.ErrorListener() {
  //                 @Override
    //               public void onErrorResponse(VolleyError error) {
      //                 Log.i(TAG, "string URL 2 = " + error + "response received");
        //           }
          //         public void OnErrorRespone(VolleyError error) {
          //                     Log.e("VOLLEY", "ERROR");
           //                    System.out.println("Please enter your Customer ID");
           //            Log.i(TAG, "string URL 3 = " + error + "response received");
           //                }
        //       }
       //        );
       //        requestQueue.add(jsonObjectRequest);
      //     }
   //   });




        //Output the result
        //payload.setText("localhost:3000/api/transactions?id=" + stringuserid);


 //   FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
   //     fab.setOnClickListener(
     //           new View.OnClickListener() {
//
  //          public void onClick(View view) {
    //            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
      //                  .setAction("Action", null).show();
        //    }
      //  }

        //  Button balancebutton = (Button) (findViewById(R.id.balancebutton));
        //  balancebutton.setOnClickListener(new View.OnClickListener(){
        //      public void  onClick(View v) {
        //          Log.i(TAG, "Button was clicked");
//
        //          }});



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

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
   // public Action getIndexApiAction() {
     //   Thing object = new Thing.Builder()
       //         .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
     //           .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
     //           .build();
     //   return new Action.Builder(Action.TYPE_VIEW)
     //           .setObject(object)
     //           .setActionStatus(Action.STATUS_TYPE_COMPLETED)
     //           .build();
   // }

  //  @Override
  //  public void onStart() {
    //    super.onStart();
//
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
  //      client.connect();
   //     AppIndex.AppIndexApi.start(client, getIndexApiAction());
   // }

//    @Override
 //   public void onStop() {
  //      super.onStop();
//
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
    //    AppIndex.AppIndexApi.end(client, getIndexApiAction());
  //      client.disconnect();
  //  }
}
