package com.example.a15017206.p09_ps_gettingmylocations;

import android.Manifest;
import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.io.File;
import java.io.FileWriter;

public class MyService extends Service implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;
    String folderLocation;
    boolean started;
    String TAG = "MyService: >>";

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

//    @Override
//    public void onCreate() {
//        super.onCreate();
//        Log.i("Service onCreate: ", "Created");
//    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (started == false) {
            started = true;
            Log.i("Service onStartCmd: ", "Started");
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
            mGoogleApiClient.connect();

        } else {
            Log.i("Service", "Still running");
        }
mGoogleApiClient.connect();
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.i("Service", "Exited");
        super.onDestroy();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i(TAG, "onConnected: ");

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "onConnected: all permissions accepted");
            mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLocation != null) {
                Toast.makeText(this, "Lat : " + mLocation.getLatitude() + " Lng : " + mLocation.getLongitude(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Location not Detected", Toast.LENGTH_SHORT).show();
            }

            LocationRequest mLocationRequest = LocationRequest.create();
            mLocationRequest.setPriority(LocationRequest
                    .PRIORITY_BALANCED_POWER_ACCURACY);
            mLocationRequest.setInterval(10000);
            mLocationRequest.setFastestInterval(5000);
            mLocationRequest.setSmallestDisplacement(100);
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
//
//            folderLocation = Environment.getExternalStorageDirectory().getAbsolutePath() + "/P09";
//            File folder = new File(folderLocation);
//            if (folder.exists() == false){
//                boolean result = folder.mkdir();
//                if (result == true){
//                    Log.d("File Read/Write", "Folder created");
//                }
//            }
//
//            File targetFile = new File(folderLocation, "data.txt");
//            try {
//                FileWriter writer = new FileWriter(targetFile, false);
//                Log.i(TAG, String.valueOf(mLocation.getLatitude() + mLocation.getLongitude()));
//                writer.write(mLocation.getLatitude() + mLocation.getLongitude() +"\n");
//                writer.flush();
//                writer.close();
//            } catch (Exception e) {
//                Toast.makeText(MyService.this, "Failed to write!", Toast.LENGTH_LONG).show();
//                Log.i(TAG, "onConnected: Cannot Write to file!");
//                e.printStackTrace();
//            }
//
//            return;
        }



    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    public void onLocationChanged(Location location) {
//        Toast.makeText(this, "Lat : " + location.getLatitude() + " Lng : " + location.getLongitude(), Toast.LENGTH_SHORT).show();
        Log.i(TAG, "onLocationChanged: " + location);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "onConnected: all permissions accepted");
            mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLocation != null) {
                Toast.makeText(this, "Lat : " + mLocation.getLatitude() + " Lng : " + mLocation.getLongitude(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Location not Detected", Toast.LENGTH_SHORT).show();
            }

//            LocationRequest mLocationRequest = LocationRequest.create();
//            mLocationRequest.setPriority(LocationRequest
//                    .PRIORITY_BALANCED_POWER_ACCURACY);
//            mLocationRequest.setInterval(1000);
//            mLocationRequest.setFastestInterval(500);
//            mLocationRequest.setSmallestDisplacement(100);
//            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

//            Toast.makeText(this, mLocationRequest.toString(), Toast.LENGTH_SHORT).show();

            folderLocation = Environment.getExternalStorageDirectory().getAbsolutePath() + "/P09";
            File folder = new File(folderLocation);
            if (folder.exists() == false){
                boolean result = folder.mkdir();
                if (result == true){
                    Log.d("File Read/Write", "Folder created");
                }
            }

            File targetFile = new File(folderLocation, "data.txt");
            try {
                FileWriter writer = new FileWriter(targetFile, true);
                Log.i(TAG, String.valueOf(mLocation.getLatitude() + mLocation.getLongitude()));
                writer.write(mLocation.getLatitude() + ", " + mLocation.getLongitude() +"\n");
                writer.flush();
                writer.close();
            } catch (Exception e) {
                Toast.makeText(MyService.this, "Failed to write!", Toast.LENGTH_LONG).show();
                Log.i(TAG, "onConnected: Cannot Write to file!");
                e.printStackTrace();
            }

            return;
        }

    }


}
