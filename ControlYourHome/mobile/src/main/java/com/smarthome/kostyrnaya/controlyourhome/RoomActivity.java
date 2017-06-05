package com.smarthome.kostyrnaya.controlyourhome;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.net.HttpURLConnection;
import java.net.URL;

public class RoomActivity extends AppCompatActivity implements View.OnClickListener {

    Button switcher;
    RelativeLayout lightSensor;
    String host;
    private static String SUCCESS = "Успешно подключено";
    private static String FAIL = "Невозможно подключиться";
    private static String ERROR = "Произошла ошибка";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        switcher = (Button) findViewById(R.id.SwitchButton);
        lightSensor = (RelativeLayout) findViewById(R.id.lightSensor);

        Intent intent = getIntent();
        host = intent.getStringExtra("hostAddress")+"/pChart/?op=value&p=Switch1.status";
        new HttpRequestTask().execute(host);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.SwitchButton:
                Intent intent = getIntent();
                String request = intent.getStringExtra("hostAddress");
                break;
            default:
                break;
        }
    }

    class HttpRequestTask extends AsyncTask<String,RoomActivity,String> {


        @Override
        protected String doInBackground(String... params) {
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
                return connection.getResponseMessage();
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("2", "Connection Error !!! - " + e.toString());
            }
            return "";
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            switch (result) {
                case "1":
                    lightSensor.setBackground(getResources().getDrawable(R.drawable.lighton));
                    break;
                case "0":
                    lightSensor.setBackground(getResources().getDrawable(R.drawable.lightoff));
                    break;
                default:
                    Toast.makeText(getBaseContext(), ERROR, Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    }
}
