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
public class FilmListActivity extends Activity{
    FilmDBAdapter db;
    //FilmAdapter filmAdapter;
    GearAdapter filmAdapter;
    ListView filmListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_list);
        db = new FilmDBAdapter(this);
        filmAdapterLoad();
        filmListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intentEditFilm = new Intent(FilmListActivity.this,
                        FilmAddEditActivity.class);
                Log.d(DBContract.TableFilm.TAG, String.valueOf(position));
                ArrayList<Film> latestFilmList = generateData();
                Film clickedFilm = (Film) latestFilmList.get(position);
                long intentId = clickedFilm.getId();
                intentEditFilm.putExtra(FilmAddEditActivity.EXTRA_FILM_ID, intentId);
                Toast.makeText(getApplicationContext(),
                        "position:" + String.valueOf(position) + "; id:" + String.valueOf(intentId),
                        Toast.LENGTH_SHORT).show();
                startActivityForResult(intentEditFilm, 0);
            }
        });
    }

    // Reload the latest filter list after addition/deletion
    public void onResume(){
        super.onResume();
        filmAdapterLoad();
    }

    /**
     * Create a custom adaptor to connect the film list from generateData() with the filmlistview
     */
    public void filmAdapterLoad(){
        filmAdapter = new GearAdapter(this, generateData()); //pass context/data to the custom adapter
        filmListView = (ListView) findViewById(R.id.filmListView); //Get ListView from activity_main.xml
        filmListView.setAdapter(filmAdapter);
    }

    private ArrayList<Film> generateData() {
        db.open();
        ArrayList<Film> allFilms = new ArrayList<Film>();
        allFilms = db.getAllFilms();
        db.close();
        return (allFilms);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.film_list_activity_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_add_film:
                Intent intentAddFilm = new Intent(FilmListActivity.this, FilmAddEditActivity.class);
                startActivity(intentAddFilm);
                Toast.makeText(FilmListActivity.this, R.string.action_add_film,
                        Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
