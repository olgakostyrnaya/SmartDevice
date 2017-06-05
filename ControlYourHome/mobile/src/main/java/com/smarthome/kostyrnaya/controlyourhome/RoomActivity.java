package com.smarthome.kostyrnaya.controlyourhome;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.HttpURLConnection;
import java.net.URL;

public class RoomActivity extends AppCompatActivity implements View.OnClickListener {

    Button switcher;
    EditText hostEdit;
    String host;
    private static String SUCCESS = "Успешно подключено";
    private static String FAIL = "Невозможно подключиться";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        switcher = (Button) findViewById(R.id.SwitchButton);
        hostEdit = (EditText) findViewById(R.id.editTextAddress);

        Intent intent = getIntent();

        host = intent.getStringExtra("hostAddress");



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAcceptAddress:
                /*message.setText("");*/
                Intent intent = new Intent(this, RoomActivity.class);
                host = hostEdit.getText().toString();
                intent.putExtra("hostAddress", host);
                int code = sendGet("http://"+host);
                if (code != 404 && code == 200)
                {
                    Toast.makeText(this, SUCCESS, Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                } else {
                    Toast.makeText(this, FAIL, Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    private int sendGet(String urlString){
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
        return 404;
    }
}
