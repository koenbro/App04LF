package com.koenbro.android.app04listview;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class FxFAddEditActivity extends Activity {
    private TextView mFilmName;
    private TextView mFilmType;
    private TextView mFilterName;
    private TextView mIsFilterBW;
    private TextView mIsFilterColor;
    private EditText mFF;

    public static final String EXTRA_FILM_ID = "com.koenbro.app03.filmid";
    public static final String EXTRA_FILTER_ID = "com.koenbro.app03.filterid";
    int filmID;
    int filterID;
    FilmDBAdapter filmDB;
    FilterDBAdapter filterDB;
    FxFDBAdapter fxfDB;
    Film film;
    Filter filter;
    FxF fxf;
    ArrayList<FxF> allPairs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setTitle(R.string.fxf_set_custom_ff);
        setContentView(R.layout.activity_film_x_filter);

        fxfDB = new FxFDBAdapter(this);
        fxfDB.open();
        allPairs = fxfDB.getAllFxF();
        fxfDB.close();



        Bundle extras = getIntent().getExtras();
        filmID = Integer.parseInt(extras.getString(EXTRA_FILM_ID));
        filterID = Integer.parseInt(extras.getString(EXTRA_FILTER_ID));

        film = getFilm(filmID);
        filter = getFilter(filterID);
        fxf = getFxF(filmID, filterID);
        createWidgets();
    }

    private Film getFilm(int filmID) {
        filmDB = new FilmDBAdapter(this);
        filmDB.open();
        film = filmDB.getFilm(filmID);
        filmDB.close();
        return film;
    }

    private Filter getFilter(int filterID) {
        filterDB = new FilterDBAdapter(this);
        filterDB.open();
        filter = filterDB.getFilter(filterID);
        filterDB.close();
        return filter;
    }

    private void createWidgets() {
        mFilmName = (TextView) findViewById(R.id.label_film_name);
        mFilmType = (TextView) findViewById(R.id.label_film_type);
        mFilterName = (TextView) findViewById(R.id.label_filter_name);
        mIsFilterBW = (TextView) findViewById(R.id.label_is_filter_bw);
        mIsFilterColor = (TextView) findViewById(R.id.label_is_filter_color);
        mFF = (EditText) findViewById(R.id.editTextFFspecific);

        mFilmName.setText(film.getFilmName());
        mFilmType.setText(film.getFilmType());
        mFilterName.setText(filter.getFilterName());
        if (filter.isFilterForBW()) {
            mIsFilterBW.setText("yes");
        } else {
            mIsFilterBW.setText("no");
        }
        if (filter.isFilterForColor()) {
            mIsFilterColor.setText("yes");
        } else {
            mIsFilterColor.setText("no");
        }
        mFF.setText(String.valueOf(fxf.getFactor()));

    }

    private FxF getFxF(int filmID, int filterID) {
        //double ff = 1.0;
        if (!isFilmFilterPairNew(filmID, filterID)) {
            for (int i = 0; i < allPairs.size(); i++) {
                if (allPairs.get(i).getFilmId() == filmID & allPairs.get(i).getFilterId() == filterID) {
                    fxf = allPairs.get(i);//.getFactor();
                }
            }
        }
        return fxf;
    }

    private boolean isFilmFilterPairNew(int filmID, int filterID) {
        boolean answer = true;
        for (int i = 0; i < allPairs.size(); i++) {
            if (allPairs.get(i).getFilmId() == filmID & allPairs.get(i).getFilterId() == filterID) {
                answer = false;
            }
        }
        return answer;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.film_x_filter_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
        //return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save_fxf_pair:
                fxf.setFilmId(filmID);
                fxf.setFilterId(filterID);
                //get user selection of FF, or, if left blank, add default of 1.0
                if (mFF.getText() == null | mFF.getText().toString().matches("")) {
                    mFF.setText("1.0");
                } else {
                    fxf.setFactor(Double.parseDouble(mFF.getText().toString()));
                    fxf.setSpecific(true);
                }
//                if (isFilmFilterPairNew(filmID, filterID)) {
//                    fxfDB.open();
//                    fxfDB.addFxF(fxf);
//                    fxfDB.close();
//                } else {
                    fxfDB.open();
                    fxfDB.updateFxF(fxf);
                    fxfDB.close();
//                }
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
