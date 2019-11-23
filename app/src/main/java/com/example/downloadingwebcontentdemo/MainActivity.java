package com.example.downloadingwebcontentdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    //  1st is url , 2nd is progress and 3rd is return type
    public class DownloadTask extends AsyncTask<String , Void , String>{

        @Override
        protected String doInBackground(String... urls) {
            Log.d("website" , "website is: "+urls[0]);

            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try{
                //  Setting up url
                url = new URL(urls[0]);

                //  Setting up connection
                urlConnection = (HttpURLConnection) url.openConnection();

                //  Get input stream
                InputStream in = urlConnection.getInputStream();

                //  Read the data
                InputStreamReader reader = new InputStreamReader(in);

                //  Position of current data
                int data = reader.read();

                while (data != -1){
                    char current = (char) data;

                    //  Append character to result
                    result += current;

                    //  Move to next position
                    data = reader.read();
                }

                return result;
            } catch (Exception e){
                Log.d("error" , "error during read" + e);
                e.printStackTrace();
            }

            return "Done";
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DownloadTask task = new DownloadTask();
        String result = null;
        try {
            result = task.execute("https://www.ecowebhosting.co.uk/").get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Log.d("result" , " " + result);
    }
}
