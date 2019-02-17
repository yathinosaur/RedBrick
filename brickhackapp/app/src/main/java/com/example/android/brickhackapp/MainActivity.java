package com.example.android.brickhackapp;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    String phoneNumber = null;
    final String url ="http://129.21.70.129:8081/mason/get";
    //final String url = "http://10.0.2.124:8081/mason/get";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Allergy Alert");

        EditText phoneNumberView = (EditText)findViewById(R.id.phone);
        phoneNumber = phoneNumberView.getText().toString();

    }

    /**
     * Performs requests to the URL every 5 seconds to update the UI information using the PeriodicTask handler
     * @param view
     */

    public void buttonClick(View view) {
        final TextView bloodPressureView = (TextView) findViewById(R.id.bloodPressureText);
        final TextView histamineView = (TextView) findViewById(R.id.histamineText);
        final TextView bodyTempView = (TextView) findViewById(R.id.bodyTempText);
        final TextView safeView = (TextView) findViewById(R.id.safeText);

        //Instatiate the request queue
        final RequestQueue queue = Volley.newRequestQueue(this);

        /*
        //  -------NONE AUTO UPDATE
        //instantiate JsonObject
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                // TODO Auto-generated method stub
                try {

                    //In Case: No wifi
                    if( bloodPressureView.getText().toString().equals("Blood Pressure: " + response.getString("blood_pressure"))) {
                        bloodPressureView.setText("Blood Pressure: " + response.getString("blood_pressure")
                        + "    No Update Recently: Potential Loss of Connection");
                    } else {
                        bloodPressureView.setText("Blood Pressure: " + response.getString("blood_pressure"));

                    }
                    histamineView.setText("Histamine Concentration: " + response.getString("histamine_concentration"));
                    bodyTempView.setText("Core Body Temperature: " + response.getString("core_body_temperature"));
                    safeView.setText("Safe: " + response.getString("safe"));

                    //call if not safe
                    //getText returns a charSequence
                    if(safeView.getText().toString().equals("Safe: false")) {
                        safeView.setTextColor(Color.RED);
                        dialPhoneNumber(phoneNumber);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
            }
        });

        queue.add(jsObjRequest);


        */

        /**
         * new PeriodicTask object, run() holds the code to run when startUpdates() is run
         */

        // /*
        PeriodicTask pt = new PeriodicTask(new Runnable() {
            @Override
            public void run() {
                //instantiate JsonObject
                JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        // TODO Auto-generated method stub
                        try {
                            bloodPressureView.setText("Blood Pressure: " + response.getString("blood_pressure"));
                            histamineView.setText("Histamine Concentration: " + response.getString("histamine_concentration"));
                            bodyTempView.setText("Core Body Temperature: " + response.getString("core_body_temperature"));
                            safeView.setText("Safe: " + response.getString("safe"));

                            //call if not safe
                            //getText returns a charSequence
                            if(safeView.getText().toString().equals("Safe: false")) {
                                dialPhoneNumber(phoneNumber);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                    }
                });
                queue.add(jsObjRequest);
            }
        });


        pt.startUpdates();
       // */


    }



    public void dialPhoneNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }


    public void phoneButtonClick(View view) {
        EditText phoneView = (EditText)findViewById(R.id.phone);

        if(phoneView.getText().toString().length() != 9) {
            phoneNumber = "911";
        } else {
            phoneNumber = phoneView.getText().toString();

        }
    }
}