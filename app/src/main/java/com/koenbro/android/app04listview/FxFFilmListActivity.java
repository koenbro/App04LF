package com.koenbro.android.app04listview;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class FxFFilmListActivity extends ListActivity {
    FilmDBAdapter db;
    ListView filmListView;
    ArrayList<Film> allFilms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setTitle(R.string.fxf_choose_film);

        db = new FilmDBAdapter(this);
        filmListView = getListView();

        //To add a header, generate a layout.xml for it, then inflate and
        // add to the list view.
        View header = (View)getLayoutInflater().inflate(R.layout.header,null);
        filmListView.addHeaderView(header);
//        View footer = (View)getLayoutInflater().inflate(R.layout.footer,null);
//        filmListView.addFooterView(footer);

        ArrayAdapter<Film>  listAdapter = new ArrayAdapter<Film>(this,
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
        /*
        Next, you would normally use Film clickedFilm = generateData().get(position);
        But because you have already attached the header view to the list view, the first film
        is not the first, rather, it is the second item in the list.
        To verify this, evaluate filmListView.getCount() and it will be filmcount+1
         */
        Film clickedFilm = generateData().get(position-1);
        intent.putExtra(FxFFilterListActivity.EXTRA_FILM_ID,  clickedFilm.getId());
        Toast.makeText(getApplicationContext(),
                "position:" + String.valueOf(position-1) + "; id:" + clickedFilm.getId(),
                Toast.LENGTH_SHORT).show();
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
