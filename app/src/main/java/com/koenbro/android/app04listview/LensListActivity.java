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
public class LensListActivity extends Activity{
    LensDBAdapter db;
    GearAdapter lensAdapter;
    ListView lensListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lens_list);
        db = new LensDBAdapter(this);
        lensAdapterLoad();
        lensListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent i = new Intent(LensListActivity.this, LensAddEditActivity.class);
                Log.d(DBContract.TableLens.TAG, String.valueOf(position));
                ArrayList<Lens> latestLensList = generateData();
                Lens clickedLens = (Lens) latestLensList.get(position);
                long intentId = clickedLens.getId();
                i.putExtra(LensAddEditActivity.EXTRA_LENS_ID, intentId); //sql starts at 1; java at 0
                Toast.makeText(getApplicationContext(),
                        "position:" + String.valueOf(position) + "; id:" + String.valueOf(intentId),
                        Toast.LENGTH_SHORT).show();
                startActivityForResult(i, 0);
            }
        });
    }
    /**
     * Reload the latest lens list after addition/deletion
     */
    public void onResume(){
        super.onResume();
        lensAdapterLoad();
    }
    /**
     * Create a custom adaptor to connect the lens list from generateData() with the lensListView
     */
    public void lensAdapterLoad(){

        lensAdapter = new GearAdapter(this, generateData()); //pass context/data to custom adapter
        lensListView = (ListView) findViewById(R.id.lensListView); //Get LensListView from
        // activity_lens_list.xml
        lensListView.setAdapter(lensAdapter);

    }

    private ArrayList<Lens> generateData() {
        db.open();
        ArrayList<Lens> allLenses;// = new ArrayList<Lens>();
        allLenses = db.getAllLenses();
        db.close();
        return (allLenses);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.lens_list_activity_actions, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_add_lens:
                Intent intentAddLens = new Intent(LensListActivity.this, LensAddEditActivity.class);
                startActivity(intentAddLens);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
