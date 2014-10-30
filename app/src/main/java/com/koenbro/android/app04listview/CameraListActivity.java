package com.koenbro.android.app04listview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class CameraListActivity extends Activity {
    CameraDBAdapter db;
    CameraAdapter cameraAdapter;
    ListView cameraListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_list);
        db = new CameraDBAdapter(this);
        cameraAdapterLoad();
        cameraListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intentEditCamera = new Intent(CameraListActivity.this, CameraAddEditActivity.class);
                Log.d(DBContract.TableCamera.TAG, String.valueOf(position));
                ArrayList<Camera> latestCameraList = generateData();
                Camera clickedCamera = (Camera) latestCameraList.get(position);
                long intentId = clickedCamera.getId();
                intentEditCamera.putExtra(CameraAddEditActivity.EXTRA_CAMERA_ID, intentId); //sql starts at 1; java at 0
                Toast.makeText(getApplicationContext(),
                        "position:" + String.valueOf(position) + "; id:" + String.valueOf(intentId),
                        Toast.LENGTH_SHORT).show();
                startActivityForResult(intentEditCamera, 0);
            }
        });
    }

    // Reload the latest camera list after addition/deletion
    public void onResume(){
        super.onResume();
        cameraAdapterLoad();
    }

    /**
     * Create a custom adaptor to connect the camera list from generateData() with the cameralistview
     */
    public void cameraAdapterLoad(){

        cameraAdapter = new CameraAdapter(this, generateData()); //pass context/data to the custom adapter
        cameraListView = (ListView) findViewById(R.id.cameraListView); //Get ListView from activity_main.xml
        cameraListView.setAdapter(cameraAdapter);

    }

    private ArrayList<Camera> generateData() {
        db.open();
        ArrayList<Camera> allCameras = new ArrayList<Camera>();
        allCameras = db.getAllCameras();
        db.close();
        return (allCameras);
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
