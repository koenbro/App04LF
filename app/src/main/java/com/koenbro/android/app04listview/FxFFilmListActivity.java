package com.koenbro.android.app04listview;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class FxFFilmListActivity extends ListActivity {
    FilmDBAdapter db;
    ListView filmListView;
    ArrayList<Film> allFilms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new FilmDBAdapter(this);
       filmListView = getListView();
        ArrayAdapter<Film>  listAdapter = new ArrayAdapter<Film>(
           this,
                android.R.layout.simple_list_item_1,
                generateData()
        ) ;
        filmListView.setAdapter(listAdapter);
    }

    @Override
    public void onListItemClick(ListView filmListView,
                                View itemView,
                                int position,
                                long id){
        Intent intent = new Intent(FxFFilmListActivity.this, FxFFilterListActivity.class);
        Film clickedFilm = generateData().get(position);
        intent.putExtra(FxFFilterListActivity.EXTRA_FILM_ID,  clickedFilm.getId());
        startActivity(intent);
    }


    private ArrayList<Film> generateData() {
        db.open();
        allFilms = new ArrayList<Film>();
        allFilms = db.getAllFilms();
        db.close();
        return (allFilms);
    }

}
