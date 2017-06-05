package com.smarthome.kostyrnaya.controlyourhome;

import android.content.Intent;
import android.os.AsyncTask;
import android.renderscript.ScriptGroup;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RoomActivity extends AppCompatActivity implements View.OnClickListener {

    Button switcher;
    RelativeLayout lightSensor;
    String host;
    private static String SUCCESS = "Успешно подключено";
    private static String FAIL = "Невозможно подключиться";
    private static String ERROR = "Произошла ошибка";
    private static String TURNON = "Включить";
    private static String TURNOFF = "Выключить";

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
        Intent intent = getIntent();
        String hostAdd = intent.getStringExtra("hostAddress");
        switch (v.getId()) {
            case R.id.SwitchButton:

                switch (switcher.getText().toString()){
                    case "Выключить":
                        hostAdd += "/ajax/commands.html?op=value_changed&item_id=108&new_value=0";
                        new HttpPostTask().execute(hostAdd);
                        break;
                    case "Включить":
                        hostAdd += "/ajax/commands.html?op=value_changed&item_id=108&new_value=1";
                        new HttpPostTask().execute(hostAdd);
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }

    class HttpRequestTask extends AsyncTask<String,Void,String> {


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
                StringBuffer sb = new StringBuffer();
                InputStream is = new BufferedInputStream(connection.getInputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String inputLine = "";
                while ((inputLine = br.readLine()) != null) {
                    sb.append(inputLine);
                }
               return sb.toString();
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
                    switcher.setText(TURNOFF);
                    break;
                case "0":
                    lightSensor.setBackground(getResources().getDrawable(R.drawable.lightoff));
                    switcher.setText(TURNON);
                    break;
                default:
                    Toast.makeText(getBaseContext(), ERROR, Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    }

    class HttpPostTask extends AsyncTask<String,Void,String> {


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
                case "OK":
                    switch (switcher.getText().toString()) {
                        case "Выключить":
                            lightSensor.setBackground(getResources().getDrawable(R.drawable.lightoff));
                            switcher.setText(TURNON);
                            break;
                        case "Включить":
                            lightSensor.setBackground(getResources().getDrawable(R.drawable.lighton));
                            switcher.setText(TURNOFF);
                            break;
                        default:
                            break;
                    }
                    break;
                default:
                    Toast.makeText(getBaseContext(), ERROR, Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    }
}
