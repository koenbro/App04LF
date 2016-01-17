package com.koenbro.android.app04listview;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by laszlo on 10/23/14.
 */
public class ShotListActivity extends Activity {
    ShotDBAdapter db;
    GearAdapter ShotAdapter;
    ListView ShotListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shot_list);
        db = new ShotDBAdapter(this);
        ShotAdapterLoad();
        ShotListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                //Intent i = new Intent(ShotListActivity.this, ShotAddEditActivity.class);
                Log.d(DBContract.TableShot.TAG, String.valueOf(position));
                ArrayList<Shot> latestShotList = generateData();
                Shot clickedShot = (Shot) latestShotList.get(position);
                long intentId = clickedShot.getId();
                //i.putExtra(ShotAddEditActivity.EXTRA_Shot_ID, intentId); //sql starts at 1; java at 0
                Toast.makeText(getApplicationContext(),
                        "position:" + String.valueOf(position) + "; id:" + String.valueOf(intentId),
                        Toast.LENGTH_SHORT).show();
                //startActivityForResult(i, 0);
            }
        });
    }

    // Reload the latest Shot list after addition/deletion
    public void onResume(){
        super.onResume();
        ShotAdapterLoad();
    }

    /**
     * Create a custom adaptor to connect the Shot list from generateData() with the ShotListView
     */
    public void ShotAdapterLoad(){
        ShotAdapter = new GearAdapter(this, generateData()); //pass context/data to custom adapter
        ShotListView = (ListView) findViewById(R.id.shotListView); //Get ShotListView from
        // activity_Shot_list.xml
        ShotListView.setAdapter(ShotAdapter);
    }

    private ArrayList<Shot> generateData() {
        db.open();
        ArrayList<Shot> allShots = db.getAllShots();
        db.close();
        Collections.reverse(allShots); //show most recent first
        return (allShots);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.Shot_list_activity_actions, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        return true;
    }
}

