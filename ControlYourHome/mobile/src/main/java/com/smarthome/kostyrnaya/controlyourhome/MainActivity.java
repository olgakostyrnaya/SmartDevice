package com.smarthome.kostyrnaya.controlyourhome;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button enteranceKey;
    EditText hostAddress;
    private String hostName = "";
    private static String SUCCESS = "Успешно подключено";
    private static String FAIL = "Невозможно подключиться";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        enteranceKey = (Button) findViewById(R.id.btnAcceptAddress);
        hostAddress = (EditText) findViewById(R.id.editTextAddress);
        enteranceKey.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAcceptAddress:
                hostName = "http://" + hostAddress.getText().toString();
                new HttpRequestTask().execute(hostName);
            default:
                break;
        }
    }

    class HttpRequestTask extends AsyncTask<String,RoomActivity,Integer>{


        @Override
        protected Integer doInBackground(String... params) {
            String urlString = "";
            if( params.length > 0 ){
                urlString = params[0];
            }
            try {
                URL url = new URL(urlString);//use a proper url instead of onlineUrl
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("User-Agent", "myApp");
                System.setProperty("http.agent", "");
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                connection.connect();
                return connection.getResponseCode();
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("2", "Connection Error !!! - " + e.toString());
            }
            return 0;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            if (result == 200)
            {
                Toast.makeText(getBaseContext(), SUCCESS, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getBaseContext(), RoomActivity.class);
                intent.putExtra("hostAddress", hostName);
                startActivity(intent);

            } else {
                Toast.makeText(getBaseContext(), FAIL, Toast.LENGTH_SHORT).show();
            }
        }
    }

}
