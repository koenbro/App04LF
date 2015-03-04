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

public class FilterListActivity extends Activity {
    FilterDBAdapter db;
    GearAdapter filterAdapter;
    ListView filterListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_list);
        db = new FilterDBAdapter(this);
        filterAdapterLoad();
        filterListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intentEditFilter = new Intent(FilterListActivity.this, FilterAddEditActivity.class);
                Log.d(DBContract.TableFilter.TAG, String.valueOf(position));
                ArrayList<Filter> latestFilterList = generateData();
                Filter clickedFilter = (Filter) latestFilterList.get(position);
                long intentId = clickedFilter.getId();
                intentEditFilter.putExtra(FilterAddEditActivity.EXTRA_FILTER_ID, intentId); //sql starts at 1; java at 0
                Toast.makeText(getApplicationContext(),
                        "position:" + String.valueOf(position) + "; id:" + String.valueOf(intentId),
                        Toast.LENGTH_SHORT).show();
                startActivityForResult(intentEditFilter, 0);
            }
        });
    }

    // Reload the latest filter list after addition/deletion
    public void onResume(){
        super.onResume();
        filterAdapterLoad();
    }

    /**
     * Create a custom adaptor to connect the filter list from generateData() with the filterlistview
     */
    public void filterAdapterLoad(){
        filterAdapter = new GearAdapter(this, generateData()); //pass context/data to the custom adapter
        filterListView = (ListView) findViewById(R.id.filterListView); //Get ListView from activity_main.xml
        filterListView.setAdapter(filterAdapter);
    }

    private ArrayList<Filter> generateData() {
        db.open();
        ArrayList<Filter> allFilters = new ArrayList<Filter>();
        allFilters = db.getAllFilters();
        db.close();
        return (allFilters);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.filter_list_activity_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_add_filter:
                Intent intentAddFilter = new Intent(FilterListActivity.this, FilterAddEditActivity.class);
                startActivity(intentAddFilter);
                Toast.makeText(FilterListActivity.this, R.string.action_add_filter,
                        Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
