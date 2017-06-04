package request.ru.app.requesttoserver;
import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import static org.apache.http.HttpHeaders.USER_AGENT;


public class MainActivity extends Activity {

    Button btnOn;
    Button btnOff;
    TextView textBoxQuery;
    private static final String POST_PARAMS = "light=on";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        btnOn = (Button) findViewById(R.id.btnSend);
        btnOff = (Button) findViewById(R.id.button2);
        textBoxQuery = (TextView) findViewById(R.id.textBoxQuery);

        View.OnClickListener oclSend = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btnSend:
                        sendGet("http://192.168.1.189:8080/smarthome/home.html?lightState=on");
                        textBoxQuery.setText("вкл");
                        break;
                    case R.id.button2:
                        sendGet("http://192.168.1.189:8080/smarthome/home.html?lightState=off");
                        textBoxQuery.setText("выкл");
                        break;
                }
            }

        };

        btnOn.setOnClickListener(oclSend);
        btnOff.setOnClickListener(oclSend);
    }

    private void sendGet(String urlString) {
        try {
            URL url = new URL(urlString);//use a proper url instead of onlineUrl
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("User-Agent", "myApp");
            System.setProperty("http.agent", "");
            connection.setRequestMethod("GET");//we can use POST instead of GET method also.
            connection.setDoInput(true);
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            if (inputStream != null) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";
                StringBuilder result = new StringBuilder();
                while ((line = bufferedReader.readLine()) != null)
                    result.append(line);
                inputStream.close();
                Log.i("1", "The result: " + result.toString());
                //return this result
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("2", "Connection Error !!! - " + e.toString());
        }

    }
}





    /*// // Create GetText Metod
    public  void  GetText() throws UnsupportedEncodingException {
        // Get user defined values
        String Light = "on";

        // Create data variable for sent values to server

        String data = URLEncoder.encode("light", "UTF-8") + "=" + URLEncoder.encode(Light, "UTF-8");

        String text = "";
        BufferedReader reader=null;

        // Send data
        try
        {
            // Defined URL  where to send data
            URL url = new URL("http://192.168.56.1:8080/smarthome/home.html?light=On");

            // Send POST data request

            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();

            // Get the server response

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while((line = reader.readLine()) != null)
            {
                // Append server response in string
                sb.append(line + "\n");
            }


            text = sb.toString();
        }
        catch(Exception ex)
        {

        }
        finally
        {
            try
            {

                reader.close();
            }

            catch(Exception ex) {}
        }

        // Show response on activity
        textBoxQuery.setText(text);

    }

    }*/