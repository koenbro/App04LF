package com.koenbro.android.app04listview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

public class GearListActivity extends Activity {
    GearAdapter gearAdapter;
    ListView gearListView;
    Gear gear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gear_list);
        gear = new Gear();
        gearAdapterLoad();
    }

    public void onResume() {
        super.onResume();
        gearAdapterLoad();// Reload the latest camera list after addition/deletion
    }

    private void gearAdapterLoad() {
        gearAdapter = new GearAdapter(this, gear.getAllCameras());
        //Get ListView from activity_gear_list.xml
        gearListView = (ListView) findViewById(R.id.gearListView);
        gearListView.setAdapter(gearAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.gear_list_activity_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_add_camera:
                Intent intentAddCamera = new Intent(this,
                        CameraAddEditActivity.class);
                startActivity(intentAddCamera);
                Toast.makeText(this, R.string.action_add_camera,
                        Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
