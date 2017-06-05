package com.smarthome.kostyrnaya.controlyourhome;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RoomActivity extends AppCompatActivity implements View.OnClickListener {

    Button switcher;
    String host;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        switcher = (Button) findViewById(R.id.SwitchButton);

        Intent intent = getIntent();

        host = intent.getStringExtra("hostAddress");



    }

    @Override
    public void onClick(View v) {
      /*  switch (v.getId()) {
            case R.id.btnAcceptAddress:
                message.setText("");
                Intent intent = new Intent(this, RoomActivity.class);
                String hostName = hostAddress.getText().toString();
                intent.putExtra("hostAddress", hostName);
                int code = sendGet("http://"+hostName);
                if (code != 404 && code == 200)
                {
                    message.setText(SUCCESS);
                    startActivity(intent);
                } else {
                    message.setText(FAIL);
                }
                break;
            default:
                break;
        }*/
    }
}
