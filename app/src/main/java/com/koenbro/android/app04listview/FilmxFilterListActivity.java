package com.koenbro.android.app04listview;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;


public class FilmxFilterListActivity extends Activity {
    GearAdapter gearAdapter;
    ListView macthingFilters;
    Gear gear;
    Film film;
    public static final String EXTRA_FILM_TYPE = "com.koenbro.app03.filmtype";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filmx_filter);
        gear = new Gear();
        gearAdapterLoad();
    }

    /**
     * Create a custom adaptor to connect the camera list from generateData() with the
     * cameralistview
     */
    public void gearAdapterLoad() {
        //only list filters that match the given camera
        //gearAdapter = new CameraAdapter(this, gear.getAllCameras());//pass context/data to the custom adapter
        gearAdapter = new GearAdapter(this, gear.getAllFilterxFilms());
        macthingFilters = (ListView) findViewById(R.id.cameraListView); //Get ListView from activity_main.xml
        macthingFilters.setAdapter(gearAdapter);
    }

    public void onResume() {
        super.onResume();
        gearAdapterLoad();// Reload the latest camera list after addition/deletion
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_filmx_filter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
