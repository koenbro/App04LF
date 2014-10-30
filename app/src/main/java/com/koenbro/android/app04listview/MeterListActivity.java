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

/**
 * Created by laszlo on 10/23/14.
 */
public class MeterListActivity extends Activity{
    MeterDBAdapter db;
    MeterAdapter meterAdapter;
    ListView meterListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meter_list);
        db = new MeterDBAdapter(this);
        meterAdapterLoad();
        meterListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent i = new Intent(MeterListActivity.this, MeterAddEditActivity.class);
                Log.d(DBContract.TableMeter.TAG, String.valueOf(position));
                ArrayList<Meter> latestMeterList = generateData();
                Meter clickedMeter = (Meter) latestMeterList.get(position);
                long intentId = clickedMeter.getId();
                i.putExtra(MeterAddEditActivity.EXTRA_METER_ID, intentId); //sql starts at 1; java at 0
                Toast.makeText(getApplicationContext(),
                        "position:" + String.valueOf(position) + "; id:" + String.valueOf(intentId),
                        Toast.LENGTH_SHORT).show();
                startActivityForResult(i, 0);
            }
        });
    }

     // Reload the latest meter list after addition/deletion
    public void onResume(){
        super.onResume();
        meterAdapterLoad();
    }

    /**
     * Create a custom adaptor to connect the meter list from generateData() with the meterListView
     */
    public void meterAdapterLoad(){
        meterAdapter = new MeterAdapter(this, generateData()); //pass context/data to custom adapter
        meterListView = (ListView) findViewById(R.id.meterListView); //Get MeterListView from
        // activity_meter_list.xml
        meterListView.setAdapter(meterAdapter);
    }

    private ArrayList<Meter> generateData() {
        db.open();
        ArrayList<Meter> allMeters = db.getAllMeters();
        db.close();
        return (allMeters);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.meter_list_activity_actions, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_add_meter:
                Intent intentAddMeter = new Intent(MeterListActivity.this, MeterAddEditActivity.class);
                startActivity(intentAddMeter);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
