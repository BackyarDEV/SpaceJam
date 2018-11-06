package com.backyardev.spacejam;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class HomeActivity extends AppCompatActivity {

    FloatingActionButton fab;
    BottomAppBar bottomAppBar;
    BottomNavigationDrawerFragment fragment = new BottomNavigationDrawerFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setTheme( R.style.AppTheme );
        setContentView( R.layout.activity_home );

        fab = findViewById( R.id.fab );
        bottomAppBar = findViewById( R.id.bottom_app_bar );
        setSupportActionBar( bottomAppBar );

        fab.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText( HomeActivity.this, "FAB Clicked!", Toast.LENGTH_SHORT ).show();
            }
        } );
        bottomAppBar.setNavigationOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fragment.show( getSupportFragmentManager(), fragment.getTag() );

            }
        } );
        bottomAppBar.setOnMenuItemClickListener( new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bottom_app_profile:
                        profileBtnAction();
                        break;
                    case R.id.bottom_app_discover:
                        discoverBtnAction();
                        break;
                    case R.id.bottom_app_notifs:
                        notifsBtnAction();
                        break;
                }
                return false;
            }
        } );
    }

    private void notifsBtnAction() {
        Toast.makeText( HomeActivity.this, "Notifications Clicked!", Toast.LENGTH_SHORT ).show();

    }

    private void discoverBtnAction() {
        Toast.makeText( HomeActivity.this, "Discover Clicked!", Toast.LENGTH_SHORT ).show();
    }

    private void profileBtnAction() {
        Toast.makeText( HomeActivity.this, "Profile Clicked!", Toast.LENGTH_SHORT ).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.bottom_app_bar, menu );
        return true;
    }
}