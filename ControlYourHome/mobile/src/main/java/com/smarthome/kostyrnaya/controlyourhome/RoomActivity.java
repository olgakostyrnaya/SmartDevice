package com.smarthome.kostyrnaya.controlyourhome;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class RoomActivity extends AppCompatActivity {

    Button tryin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        tryin = (Button) findViewById(R.id.SwitchButton);

        Intent intent = getIntent();

        String hostAddress = intent.getStringExtra("hostAddress");

        tryin.setText(hostAddress);
    }
}
