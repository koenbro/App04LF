package com.koenbro.android.app04listview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.sql.SQLException;
import java.util.ArrayList;

public class MainActivity extends Activity {
    private Spinner mEquipmentPage;
    private Spinner mFilmChoice;
    private Spinner mLensChoice;
    private Spinner mLensAperture;
    private RadioGroup mThirdStops;
    private RadioButton mFullStop;
    private RadioButton mOneThirdStop;
    private RadioButton mTwoThirdStop;
    private EditText mBellowsText;
    private Spinner mBellowsSpinner;
    private Spinner mFilterChoice;
    private Spinner mMeterReadEV;
    private Spinner mMeterReadF;
    private Spinner mMeterReadT;

    private ArrayList<Film> allFilms;
    private ArrayList<Filter> allFilters;
    private ArrayList<Lens> allLenses;
    private ArrayList<Meter> allMeters;
    private ArrayList<Camera> allCameras;

    public DBAdapter db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DBAdapter(this);
        try {
            db.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        loadGear();
        createWidgets();
        refreshDynamicContent();
        db.close();
    }
    public void onResume(){
        super.onResume();
        loadGear();
        refreshDynamicContent();
    }

    private void loadGear(){
        FilmDBAdapter filmDB = new FilmDBAdapter(this);
        filmDB.open();
        allFilms = filmDB.getAllFilms();
        filmDB.close();

        FilterDBAdapter filterDB = new FilterDBAdapter(this);
        filterDB.open();
        allFilters = filterDB.getAllFilters();
        filterDB.close();

        LensDBAdapter lensDB = new LensDBAdapter(this);
        lensDB.open();
        allLenses = lensDB.getAllLenses();
        lensDB.close();

        MeterDBAdapter meterDB = new MeterDBAdapter(this);
        meterDB.open();
        allMeters = meterDB.getAllMeters();
        meterDB.close();

        CameraDBAdapter cameraDB = new CameraDBAdapter(this);
        cameraDB.open();
        allCameras = cameraDB.getAllCameras();
        cameraDB.close();
    }

    private void createWidgets(){
        mFilmChoice = (Spinner)findViewById(R.id.main_film_spinner);
        mLensChoice = (Spinner)findViewById(R.id.main_lens_spinner);
        mLensAperture = (Spinner)findViewById(R.id.main_aperture_spinner);
        mFilterChoice =(Spinner)findViewById(R.id.main_filter_spinner);
        mBellowsText = (EditText)findViewById(R.id.main_set_be);

        //equipment inventory spinner is immutable, no need for dynamic reload
        mEquipmentPage = (Spinner) findViewById(R.id.spinner_equipment);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(MainActivity.this,
                R.array.equipment_pages,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mEquipmentPage.setAdapter(adapter);
        mEquipmentPage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            Intent intent;
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view, int position, long row_id) {

                switch(position){
                    case 0:
                        return;
                    case 1:
                        intent = new Intent(MainActivity.this, FilmListActivity.class);
                        break;
                    case 2:
                        intent = new Intent(MainActivity.this, LensListActivity.class);
                        break;
                    case 3:
                        intent = new Intent(MainActivity.this, FilterListActivity.class);
                        break;
                    case 4:
                        intent = new Intent(MainActivity.this, MeterListActivity.class);
                        break;
                    case 5:
                        intent = new Intent(MainActivity.this, CameraListActivity.class);
                        break;
                    case 6:
                        intent = new Intent(MainActivity.this, zzdeleteActivity.class);
                        break;
                }
                startActivity(intent);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                return;
            }
        });


    }

    private void refreshDynamicContent(){

        //show film names from db
        ArrayList<String> filmNames = new ArrayList<String>();
        for (int i=0; i<allFilms.size(); i++) {
            filmNames.add(allFilms.get(i).getFilmName());
        }
        ArrayAdapter<String> filmNamesAdapter =
                new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item, filmNames);
        filmNamesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mFilmChoice.setAdapter(filmNamesAdapter);

        //show lens names from db
        ArrayList<String> lensesNames = new ArrayList<String>();
        for (int i=0; i<allLenses.size(); i++){
            lensesNames.add(allLenses.get(i).getLensName());
        }
        ArrayAdapter<String> lensesNamesAdapter =
                new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item, lensesNames);
        lensesNamesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mLensChoice.setAdapter(lensesNamesAdapter);

        //show lens aperture choices; static from array resource
        ArrayAdapter<CharSequence> adapterAperture =
                ArrayAdapter.createFromResource(MainActivity.this,
                        R.array.lens_aperture,
                        android.R.layout.simple_spinner_item);
        adapterAperture.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mLensAperture.setAdapter(adapterAperture);

        //show filter names from db
        ArrayList<String> filtersNames = new ArrayList<String>();
        for (int i=0; i<allFilters.size(); i++){
            filtersNames.add(allFilters.get(i).getFilterName());
        }
        ArrayAdapter<String> filtersNamesAdapter;
        filtersNamesAdapter = new ArrayAdapter<String>( this,
                android.R.layout.simple_spinner_item, filtersNames);
        filtersNamesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mFilterChoice.setAdapter(filtersNamesAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_calculate_exposure:
                return true;
            case R.id.action_save_shot:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
