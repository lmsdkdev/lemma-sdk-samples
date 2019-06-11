package app.test.lm.lemmatestapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";
    private static final String[] PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        RecyclerView recyclerView = findViewById(R.id.ad_list);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        final List<AdFormatListAdapter.LIST_ITEM> itemList = new ArrayList<>(Arrays.asList(AdFormatListAdapter.LIST_ITEM.values()));

        AdFormatListAdapter adFormatListAdapter = new AdFormatListAdapter(this, itemList);
        adFormatListAdapter.setItemClickListener(new AdFormatListAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                if (itemList.get(position).getActivity() != null) {
                    Intent intent = new Intent(MainActivity.this, itemList.get(position).getActivity());
                    MainActivity.this.startActivity(intent);
                }
            }
        });
        recyclerView.setAdapter(adFormatListAdapter);

    }

}
