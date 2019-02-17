package com.example.android.brickhackapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    final String phoneNumber = "4168548536";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Allergy Alert");


    }

    public void buttonClick(View view) {
        final TextView bloodPressureView = (TextView) findViewById(R.id.bloodPressureText);
        final TextView histamineView = (TextView) findViewById(R.id.histamineText);
        final TextView bodyTempView = (TextView) findViewById(R.id.bodyTempText);
        final TextView safeView = (TextView) findViewById(R.id.safeText);

        //Instatiate the request queue
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://129.21.70.129:8081/mason/get";
        //String url ="http://google.com";

        //instantiate JsonObject
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                // TODO Auto-generated method stub
                try {
                    bloodPressureView.setText(response.getString("blood_pressure"));
                    histamineView.setText(response.getString("histamine_concentration"));
                    bodyTempView.setText(response.getString("core_body_temperature"));
                    safeView.setText(response.getString("safe"));

                    //call if not safe
                    //getText returns a charSequence
                    if(safeView.getText().toString() == "false") {
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

    public void dialPhoneNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }



}