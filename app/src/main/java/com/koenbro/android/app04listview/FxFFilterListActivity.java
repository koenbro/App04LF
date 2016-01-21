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

public class FxFFilterListActivity extends Activity {
    FilterDBAdapter db;
    GearAdapter filterAdapter;
    ListView filterListView;
    public static final String EXTRA_FILM_ID = "com.koenbro.app03.filmid";
    int filmID;
    int filterID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setTitle(R.string.fxf_filterlist_title);
        setContentView(R.layout.activity_filter_list);
        filmID = (int) getIntent().getLongExtra(EXTRA_FILM_ID, 0);


        db = new FilterDBAdapter(this);
        filterAdapterLoad();
        filterListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intentEditFilter = new Intent(FxFFilterListActivity.this, FxFAddEditActivity.class);
                Log.d(DBContract.TableFilter.TAG, String.valueOf(position));
                ArrayList<Filter> latestFilterList = generateData();
                Filter clickedFilter = (Filter) latestFilterList.get(position);
                filterID = (int) clickedFilter.getId();

                Bundle extras = new Bundle();
                extras.putString(FxFAddEditActivity.EXTRA_FILTER_ID, String.valueOf(filterID));
                extras.putString(FxFAddEditActivity.EXTRA_FILM_ID, String.valueOf(filmID));
                intentEditFilter.putExtras(extras);

                Toast.makeText(getApplicationContext(),
                        "position:" + String.valueOf(position) +
                                "\n filter id:" + String.valueOf(filmID) +
                                "\n filter id:" + String.valueOf(filterID),
                        Toast.LENGTH_SHORT).show();
                startActivity(intentEditFilter);
            }
        });
    }

    // Reload the latest filter list after addition/deletion
    public void onResume() {
        super.onResume();
        filterAdapterLoad();
    }

    /**
     * Create a custom adaptor to connect the filter list from generateData() with the
     * filterlistview
     */
    public void filterAdapterLoad() {
        filterAdapter = new GearAdapter(this, generateData()); //pass context/data to the custom adapter
        filterListView = (ListView) findViewById(R.id.filterListView); //Get ListView from activity_main.xml
        filterListView.setAdapter(filterAdapter);
    }

    private ArrayList<Filter> generateData() {
        db.open();
        FxFPairs fmf = new FxFPairs();
        ArrayList<Filter> filtersMatchingFilm = fmf.filtersMatchingFilm(filmID);
        db.close();
        return (filtersMatchingFilm);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.filter_list_activity_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        return (true);
    }
}
