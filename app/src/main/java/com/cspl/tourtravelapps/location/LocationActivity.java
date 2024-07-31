package com.cspl.tourtravelapps.location;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cspl.tourtravelapps.MyApplication;
import com.cspl.tourtravelapps.R;
import com.cspl.tourtravelapps.activity.MainActivity;
import com.cspl.tourtravelapps.helper.SessionManager;
import com.cspl.tourtravelapps.receiver.ConnectivityReceiver;

public class LocationActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener{
    final String FRAG_TAG_SEARCH_LOCATION = "fragSearchLocation";
    private ImageView background;
    private ImageView splashLogo;
    private Button locationNew;
    private Button locationSubmit;
    private SessionManager sessionmanager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        initilizecontrols();

        Glide.with(this).load(R.drawable.back5).into(background);
        Glide.with(this).load(R.drawable.chef_logo).into(splashLogo);

        locationNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isConnected = ConnectivityReceiver.isConnected();
                if (!isConnected) {
                    Snackbar.make(findViewById(R.id.mainFrame),R.string.no_internet,Snackbar.LENGTH_LONG)
                            .show();
                } else {
                    loadFragment(new LocationFragment());
                }
            }
        });

        locationSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isConnected = ConnectivityReceiver.isConnected();
                if (!isConnected) {
                    Snackbar.make(findViewById(R.id.mainFrame),R.string.no_internet,Snackbar.LENGTH_LONG)
                            .show();
                }
                else {
                    Intent intent = new Intent(LocationActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                }

            }
        });
    }

    private void loadFragment(Fragment fragment) {
        boolean isConnected = ConnectivityReceiver.isConnected();

        if(!isConnected){
            Snackbar.make(findViewById(R.id.mainFrame),R.string.no_internet,Snackbar.LENGTH_LONG)
                    .show();
        }else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.bottom_up, R.anim.bottom_down, R.anim.bottom_up, R.anim.bottom_down)
                    .add(R.id.mainFrame, fragment, FRAG_TAG_SEARCH_LOCATION)
                    .addToBackStack(FRAG_TAG_SEARCH_LOCATION)
                    .commit();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        MyApplication.getInstance().setConnectivityListener(this);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        checkConnection();
    }

    private void initilizecontrols(){
        sessionmanager = new SessionManager(this);
        background = (ImageView) findViewById(R.id.background);
        splashLogo = (ImageView) findViewById(R.id.splashLogo);
        locationNew = (Button) findViewById(R.id.locationNew);
        locationSubmit = (Button) findViewById(R.id.locationSubmit);
        if(sessionmanager.isCityPresent()){
            locationNew.setText(sessionmanager.getKeyCityName());
        }
    }

    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        if (!isConnected) {
            Snackbar.make(findViewById(R.id.mainFrame),R.string.no_internet,Snackbar.LENGTH_LONG)
                    .show();
        }
    }
}
