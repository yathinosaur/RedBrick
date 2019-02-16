package com.example.android.brickhackapp;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;
import static java.util.concurrent.TimeUnit.*;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.android.brickhackapp.R;

public class MainActivity extends AppCompatActivity {


    private static final String REQUEST_URL = "http://129.21.70.129:8081/get";
    private static String phoneNumber = "4168548536";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AlertAsyncTask task = new AlertAsyncTask();
        task.execute();

        /*
        while(true) {
            //make http request every 10 seconds
            AlertAsyncTask task = new AlertAsyncTask();
            task.execute();
            //TimeUnit.SECONDS.sleep(10);
        }
        */
    }


    public void dialPhoneNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }


    /**
     * {@link AsyncTask} to perform the network request on a background thread, and then
     * update the UI with the first earthquake in the response.
     */
    private class AlertAsyncTask extends AsyncTask<URL, Void, Event> {

        @Override
        protected Event doInBackground(URL... urls) {
            Log.e("RichardTag","RichardTag: doInBackground now running");
            // Create URL object
            URL url = createUrl(REQUEST_URL);

            // Perform HTTP request to the URL and receive a JSON response back
            String jsonResponse = "";
            try {
                jsonResponse = makeHttpRequest(url);
            } catch (IOException e) {
                // TODO Handle the IOException
            }

            // Extract relevant fields from the JSON response and create an {@link Event} object
            Log.e("RichardTag","RichardTag: extractFeatureFromJson about to run");
            Event alert = extractFeatureFromJson(jsonResponse);

            // Return the {@link Event} object as the result fo the {@link TsunamiAsyncTask}
            return alert;
        }

        /**
         * Update the screen with the given alert (which was the result of the
         * {@link AlertAsyncTask}).
         */
        @Override
        protected void onPostExecute(Event alert) {
            Log.e("RichardTag","RichardTag: onPostExecute now running");

            if (alert == null) {
                return;
            }

            if(alert.getTitle().equalsIgnoreCase("true")) {
                //call phone number
                dialPhoneNumber(phoneNumber);
                //TESTING purposes

            }

        }

        /**
         * Returns new URL object from the given string URL.
         */
        private URL createUrl(String stringUrl) {
            URL url = null;
            try {
                url = new URL(stringUrl);
            } catch (MalformedURLException exception) {
                //error
                return null;
            }
            return url;
        }

        /**
         * Make an HTTP request to the given URL and return a String as the response.
         */
        private String makeHttpRequest(URL url) throws IOException {
            Log.e("RichardTag","RichardTag: makeHttpRequest now running");
            String jsonResponse = "";
            HttpURLConnection urlConnection = null;
            InputStream inputStream = null;
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setReadTimeout(10000 /* milliseconds */);
                urlConnection.setConnectTimeout(15000 /* milliseconds */);
                urlConnection.connect();
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } catch (IOException e) {
                // TODO: Handle the exception
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (inputStream != null) {
                    // function must handle java.io.IOException here
                    inputStream.close();
                }
            }
            return jsonResponse;
        }

        /**
         * Convert the {@link InputStream} into a String which contains the
         * whole JSON response from the server.
         */
        private String readFromStream(InputStream inputStream) throws IOException {
            StringBuilder output = new StringBuilder();
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line = reader.readLine();
                while (line != null) {
                    output.append(line);
                    line = reader.readLine();
                }
            }
            return output.toString();
        }

        /**
         * Return an {@link Event} object by parsing out information
         * about the alert from the input JSON string.
         */

        private Event extractFeatureFromJson(String alertJSON) {
            Log.e("RichardTag","RichardTag: extractFeatureFromJson now running");

            try {
                JSONObject baseJsonResponse = new JSONObject(alertJSON);
                String alertValue = baseJsonResponse.getString("Title");

                Log.e("RichardTag","RichardTag: " + alertValue);
                return new Event(alertValue);

            } catch (JSONException e) {
                //error
            }
            return null;
        }
    }

}
