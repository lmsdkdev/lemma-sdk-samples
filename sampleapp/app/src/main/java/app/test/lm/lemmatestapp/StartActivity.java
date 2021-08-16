package app.test.lm.lemmatestapp;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class StartActivity extends Activity {
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        if (checkForAllPermissions()) {
            launchMainActivity();
        } else {
            requestPermission();
        }
    }

    private List<String> permissionList() {

        List<String> listPermissionsNeeded = new ArrayList<>();
        addIfNotGranted(listPermissionsNeeded,Manifest.permission.READ_EXTERNAL_STORAGE);
        addIfNotGranted(listPermissionsNeeded, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        addIfNotGranted(listPermissionsNeeded, Manifest.permission.ACCESS_COARSE_LOCATION);
        addIfNotGranted(listPermissionsNeeded, Manifest.permission.ACCESS_FINE_LOCATION);
        addIfNotGranted(listPermissionsNeeded, Manifest.permission.RECEIVE_BOOT_COMPLETED);
        addIfNotGranted(listPermissionsNeeded, Manifest.permission.INTERNET);
        addIfNotGranted(listPermissionsNeeded, Manifest.permission.ACCESS_WIFI_STATE);
        addIfNotGranted(listPermissionsNeeded, Manifest.permission.ACCESS_NETWORK_STATE);
        return listPermissionsNeeded;
    }

    private void requestPermission() {

        List<String> listPermissionsNeeded = permissionList();
        ActivityCompat.requestPermissions(this, listPermissionsNeeded
                        .toArray(new String[listPermissionsNeeded.size()]),
                REQUEST_ID_MULTIPLE_PERMISSIONS);
    }

    private void addIfNotGranted(List<String> list, String permission) {

        int isAllowed = ContextCompat.checkSelfPermission(this,
                permission);

        if (isAllowed != PackageManager.PERMISSION_GRANTED) {
            list.add(permission);
        }
    }


    private boolean checkForAllPermissions() {
        List<String> listPermissionsNeeded = permissionList();
        return listPermissionsNeeded.isEmpty();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS: {
                launchMainActivity();
            }

        }
    }

    private void launchMainActivity() {
        Intent intent;
        intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}