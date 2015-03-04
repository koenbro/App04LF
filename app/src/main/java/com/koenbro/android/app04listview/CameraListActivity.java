package com.koenbro.android.app04listview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class CameraListActivity extends Activity {
    //CameraAdapter cameraAdapter;
    GearAdapter cameraAdapter;
    ListView cameraListView;
    Gear gear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_list);
        gear = new Gear();
        cameraAdapterLoad();
        cameraListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intentEditCamera = new Intent(CameraListActivity.this, CameraAddEditActivity.class);
                long intentId = gear.getAllCameras().get(position).getId();
                intentEditCamera.putExtra(CameraAddEditActivity.EXTRA_CAMERA_ID, intentId); //sql starts at 1; java at 0
                startActivityForResult(intentEditCamera, 0);
            }
        });
    }

    public void onResume(){
        super.onResume();
        cameraAdapterLoad();// Reload the latest camera list after addition/deletion
    }

    /**
     * Create a custom adaptor to connect the camera list from generateData() with the cameralistview
     */
    public void cameraAdapterLoad(){
        //cameraAdapter = new CameraAdapter(this, gear.getAllCameras());//pass context/data to the custom adapter
        cameraAdapter = new GearAdapter(this, gear.getAllCameras());
        cameraListView = (ListView) findViewById(R.id.cameraListView); //Get ListView from activity_main.xml
        cameraListView.setAdapter(cameraAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.camera_list_activity_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_add_camera:
                Intent intentAddCamera = new Intent(CameraListActivity.this,
                        CameraAddEditActivity.class);
                startActivity(intentAddCamera);
                Toast.makeText(CameraListActivity.this, R.string.action_add_camera,
                        Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
