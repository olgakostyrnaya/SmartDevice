/*
package com.smarthome.kostyrnaya.controlyourhome;

import android.os.AsyncTask;
import android.util.Log;

import java.net.HttpURLConnection;
import java.net.URL;

*/
/**
 * Created by Olga on 05.06.2017.
 *//*

public interface AsyncManager extends AsyncTask<String,Void,Integer> {

    @Override
    public int doInBackground(String urlString) {
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

    protected void onPostExecute(String feed) {
        // TODO: check this.exception
        // TODO: do something with the feed
    }
}

*/
