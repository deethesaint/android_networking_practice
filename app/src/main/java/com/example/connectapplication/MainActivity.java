package com.example.connectapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        checkInternetConnection();
        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE}, 251);
        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 253);
//        new ConnectThread().execute();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        APIServer apiServer = retrofit.create(APIServer.class);
        apiServer.getUser().enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                Log.e("response Username", user.getUsername());
                Log.e("response Password", user.getPassword());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

//    class ConnectThread extends AsyncTask<Void, Integer, Void> {
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            try {
//                URL url = new URL("http://10.0.2.2:8000/path");
//                URLConnection urlConnection = url.openConnection();
//                HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
//                Log.e("Status code", String.valueOf(httpURLConnection.getResponseCode()));
//                Log.e("Status message", String.valueOf(httpURLConnection.getResponseMessage()));
//
//                InputStream inputStream = httpURLConnection.getInputStream();
//                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//                StringBuilder stringBuilder = new StringBuilder();
//                String line;
//                while ((line = bufferedReader.readLine()) != null)
//                {
//                    stringBuilder.append(line);
//                }
////                Log.e("Response body", stringBuilder.toString());
//
//                ObjectMapper objectMapper = new ObjectMapper();
//                User user = objectMapper.readValue(stringBuilder.toString(), User.class);
//
//                Log.e("Username", user.getUsername());
//                Log.e("Password", user.getPassword());
//
//            } catch (MalformedURLException e) {
//                throw new RuntimeException(e);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//            return null;
//        }
//    }

    private Boolean checkInternetConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null) {
            Toast.makeText(this, "No default network is currently active", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!networkInfo.isConnected()) {
            Toast.makeText(this, "Network is not connected", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!networkInfo.isAvailable()) {
            Toast.makeText(this, "Network is not available", Toast.LENGTH_SHORT).show();
            return false;
        }
        Toast.makeText(this, "Network OK", Toast.LENGTH_SHORT).show();
        return true;
    }

}