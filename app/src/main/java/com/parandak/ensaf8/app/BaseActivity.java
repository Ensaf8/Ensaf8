package com.parandak.ensaf8.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parandak.ensaf8.R;
import com.parandak.ensaf8.homePage.HomePageActivity;
import com.parandak.ensaf8.mapPage.MapActivity;
import com.parandak.ensaf8.searchPage.SearchPageActivity;

public abstract class BaseActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    protected BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());

        navigationView = (BottomNavigationView) findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateNavigationBarState();
    }

    // Remove inter-activity transition to avoid screen tossing on tapping bottom navigation items
    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
        navigationView.postDelayed(new Runnable() {
            @Override
            public void run() {
                int itemId = item.getItemId();
                if (itemId == R.id.navigation_home) {
                    BaseActivity.this.startActivity(new Intent(BaseActivity.this, HomePageActivity.class));
                } else if (itemId == R.id.navigation_map) {
                    BaseActivity.this.startActivity(new Intent(BaseActivity.this, MapActivity.class));
                } else if (itemId == R.id.navigation_search) {
                    BaseActivity.this.startActivity(new Intent(BaseActivity.this, SearchPageActivity.class));
                }
                BaseActivity.this.finish();
            }
        }, 300);
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private void updateNavigationBarState(){
        int actionId = getNavigationMenuItemId();
        selectBottomNavigationBarItem(actionId);
    }

    void selectBottomNavigationBarItem(int itemId) {
        MenuItem item = navigationView.getMenu().findItem(itemId);
        item.setChecked(true);
    }

    public abstract int getContentViewId();

    public abstract int getNavigationMenuItemId();
}
