package com.parandak.ensaf8.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parandak.ensaf8.R;
import com.parandak.ensaf8.homePage.HomePageActivity;
import com.parandak.ensaf8.homePage.HomePageQuery;
import com.parandak.ensaf8.mapPage.MapActivity;
import com.parandak.ensaf8.searchPage.SearchPageActivity;

public abstract class BaseActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    protected BottomNavigationView navigationView;
    private final String TAG = this.getClass().getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());

        navigationView = (BottomNavigationView) findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(this);
        Toast.makeText(getBaseContext(), TAG + " : onCreate" , Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(getBaseContext(), TAG + " : onStart" , Toast.LENGTH_SHORT).show();
        updateNavigationBarState();
    }

    // Remove inter-activity transition to avoid screen tossing on tapping bottom navigation items
    @Override
    public void onPause() {
        super.onPause();
        Toast.makeText(getBaseContext(), TAG + " : onPause" , Toast.LENGTH_SHORT).show();
        overridePendingTransition(0, 0);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
        navigationView.postDelayed(new Runnable() {
            @Override
            public void run() {
                int itemId = item.getItemId();
                if (itemId == R.id.navigation_home) {
                    if (getNavigationMenuItemId()!=R.id.navigation_home){
                        if (getItemIdBefore() == R.id.navigation_home){
                            finish();
                        }else {
                            Intent intentHome = new Intent(BaseActivity.this, HomePageActivity.class);
                            intentHome.putExtra("itemIdBefore",getNavigationMenuItemId());
                            BaseActivity.this.startActivity(intentHome);
                        }
                    }
                } else if (itemId == R.id.navigation_map) {
                    if (getNavigationMenuItemId()!=R.id.navigation_map){
                        if (getItemIdBefore() == R.id.navigation_map){
                            finish();
                        }else {
                            Intent intentMap = new Intent(BaseActivity.this, MapActivity.class);
                            intentMap.putExtra("itemIdBefore",getNavigationMenuItemId());
                            BaseActivity.this.startActivity(intentMap);
                        }
                    }
                } else if (itemId == R.id.navigation_search) {
                    if (getNavigationMenuItemId()!=R.id.navigation_search){
                        if (getItemIdBefore() == R.id.navigation_search){
                            finish();
                        }else {
                            Intent intentSearch = new Intent(BaseActivity.this, SearchPageActivity.class);
                            intentSearch.putExtra("itemIdBefore",getNavigationMenuItemId());
                            BaseActivity.this.startActivity(intentSearch);
                        }
                    }
                }
                //BaseActivity.this.finish();
            }
        }, 0);
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

    public abstract int getItemIdBefore();
}
