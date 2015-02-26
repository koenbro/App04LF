package com.koenbro.android.app04listview;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.Time;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * Main activity for the Large Format Photography Assistant app.<br/>
 *     This app has three main goals: (1) manages LF gear; (2) calculates exposure in the field,
 *     adjusting for bellows draw, filter factor, and reciprocity correction; and (3) stores
 *     information on a shot, including meta information (time stamp, GPS location, comment,
 *     dictaphone note, snapshot of subject, etc).<p>
 *         Beyond the end user functionality this complex app serves as a learning project for Java
 *         and Android programming and a test bed for several techniques, like (1) SQLite
 *         databases with multiple tables, (2) application wide contexts, and (3) general OOP
 *         techniques.
 *     </p>
 */
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
    private RadioGroup mThirdEV;
    private RadioButton mFullEV;
    private RadioButton mOneThirdEV;
    private RadioButton mTwoThirdEV;
    private TextView mExposureLo;
    private TextView mExposure;
    private TextView mExposureHi;
    private Spinner mMeterReadF;
    private Spinner mMeterReadT;
    private Spinner mMeterChoice;
    private Spinner mCameraChoice;
    private EditText mCommentShot;

    //user choices in the UI
    private int filmId;
    private int lensId;
    private double aperture;
    private int filterId;
    private int bellowsExtension;
    private double meterReadValue;
    private int meterId;
    private int cameraId;
    private Film filmChosen;
    private Lens lensChosen;
    private Filter filterChosen;
    private Meter meterChosen;
    private Camera cameraChosen;
    private String comment;

    //inventory
    private ArrayList<Film> allFilms;
    private ArrayList<Filter> allFilters;
    private ArrayList<Lens> allLenses;
    private ArrayList<Meter> allMeters;
    private ArrayList<Camera> allCameras;

    private Shot liveShot;
    private DBAdapter db;
    private String emailedFilename;
    private GPSTracker gps;  // GPSTracker class
    private Gear gear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gear = new Gear();
        gear.tryDatabase(); //maybe remove from here and use only in Gear constructor?
        loadGear();
        createWidgets();
        refreshDynamicContent();
    }

    // Set up the screen
    private void loadGear(){
        allFilms = gear.getAllFilms();
        allFilters = gear.getAllFilters();
        allLenses = gear.getAllLenses();
        allMeters = gear.getAllMeters();
        allCameras = gear.getAllCameras();
    }
    private void createWidgets(){
        mFilmChoice = (Spinner)findViewById(R.id.main_film_spinner);
        mLensChoice = (Spinner)findViewById(R.id.main_lens_spinner);
        mLensAperture = (Spinner)findViewById(R.id.main_aperture_spinner);

        mThirdStops = (RadioGroup)findViewById(R.id.radioGroupf);
        mFullStop = (RadioButton)findViewById(R.id.radioButtonf0);
        mOneThirdStop = (RadioButton)findViewById(R.id.radioButtonf1);
        mTwoThirdStop = (RadioButton)findViewById(R.id.radioButtonf2);

        mFilterChoice =(Spinner)findViewById(R.id.main_filter_spinner);
        mBellowsText = (EditText)findViewById(R.id.main_set_be);
        mMeterReadEV = (Spinner)findViewById(R.id.main_ev_spinner);

        mThirdEV = (RadioGroup)findViewById(R.id.radioGroupMeterRead);
        mFullEV = (RadioButton)findViewById(R.id.radioButtonMeter0);
        mOneThirdEV = (RadioButton)findViewById(R.id.radioButtonMeter1);
        mTwoThirdEV = (RadioButton)findViewById(R.id.radioButtonMeter2);

        mExposureLo = (TextView)findViewById(R.id.textViewExpLo);
        mExposure = (TextView)findViewById(R.id.textViewExp);
        mExposureHi = (TextView)findViewById(R.id.textViewExpHi);
        mMeterChoice = (Spinner)findViewById(R.id.main_choose_meter);

        mCameraChoice = (Spinner)findViewById(R.id.main_choose_camera);
        mCommentShot = (EditText)findViewById(R.id.main_comment);

        //equipment inventory spinner is immutable, no need for dynamic reload; others are dynamic
        equipmentSelectWidget();
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
        mLensAperture.setSelection(adapterAperture.getPosition(
                getResources().getString(R.string.default_fstop))); //"11"

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

        //show EV values; static from resource
        ArrayAdapter<CharSequence> adapterMeterReadEV =
                ArrayAdapter.createFromResource(MainActivity.this,
                        R.array.ev_values,
                        android.R.layout.simple_spinner_item);
        adapterMeterReadEV.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mMeterReadEV.setAdapter(adapterMeterReadEV);
        mMeterReadEV.setSelection(adapterMeterReadEV.getPosition(
                getResources().getString(R.string.default_ev))); //"10"

        //show meter names from db
        ArrayList<String> metersNames = new ArrayList<String>();
        for (int i=0; i<allMeters.size(); i++){
            metersNames.add(allMeters.get(i).getMeterName());
        }
        ArrayAdapter<String> metersNamesAdapter;
        metersNamesAdapter = new ArrayAdapter<String>( this,
                android.R.layout.simple_spinner_item, metersNames);
        metersNamesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mMeterChoice.setAdapter(metersNamesAdapter);

        //show camera names from db
        ArrayList<String> camerasNames = new ArrayList<String>();
        for (int i=0; i<allCameras.size(); i++){
            camerasNames.add(allCameras.get(i).getCameraName());
        }
        ArrayAdapter<String> camerasNamesAdapter;
        camerasNamesAdapter = new ArrayAdapter<String>( this,
                android.R.layout.simple_spinner_item, camerasNames);
        camerasNamesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCameraChoice.setAdapter(camerasNamesAdapter);
    }
    // Done setting up the screen


    public void getUserChoices(){
        filmChosen = allFilms.get(mFilmChoice.getSelectedItemPosition());
        lensChosen = allLenses.get(mLensChoice.getSelectedItemPosition());

        //aperture depends on spinner + radio buttons
        aperture = Double.parseDouble(String.valueOf(mLensAperture.getSelectedItem()));
        int radioButtonID = mThirdStops.getCheckedRadioButtonId();
        View radioButton = mThirdStops.findViewById(radioButtonID);
        int idx = mThirdStops.indexOfChild(radioButton);
        if (idx == 1)  {
            aperture = aperture * Math.sqrt(Math.pow(2, 0.3333)); // 1/3 stop up
        }
        else if (idx ==2) {
            aperture = aperture * Math.sqrt(Math.pow(2, 0.6666)); // 2/3 stop up
        }

        filterChosen = allFilters.get(mFilterChoice.getSelectedItemPosition());

        //if bellows extension field is empty, enter lens focal length
        if (mBellowsText.getText().toString().trim().equals("")) {
            //Toast.makeText(this, "No BE given; default chosen",Toast.LENGTH_SHORT).show();
            bellowsExtension = lensChosen.getLensFocal();
        } else {
            bellowsExtension = Integer.parseInt(mBellowsText.getText().toString());
            // bellows extension cannot be < focal
            if (bellowsExtension < lensChosen.getLensFocal() ){
                bellowsExtension = lensChosen.getLensFocal();
            }
        }
        //the meter read (like the aperture) also depends on spinner + radio buttons
        meterReadValue = Double.parseDouble(String.valueOf(mMeterReadEV.getSelectedItem()));
        int radioButtonMeterID = mThirdEV.getCheckedRadioButtonId();
        View radioButtonMeter = mThirdEV.findViewById(radioButtonMeterID);
        int idxMeter = mThirdEV.indexOfChild(radioButtonMeter);
        if (idxMeter == 1)  {
            meterReadValue = meterReadValue + 0.3333;  // 1/3 stop up
        }
        else if (idxMeter ==2) {
            meterReadValue = meterReadValue + 0.6666;   // 2/3 stop up
        }

        meterChosen = allMeters.get(mMeterChoice.getSelectedItemPosition());
        cameraChosen = allCameras.get(mCameraChoice.getSelectedItemPosition());
        comment = mCommentShot.getText().toString();
    }
    public void calcExposure(){
        liveShot = new Shot(filmChosen, lensChosen, filterChosen, cameraChosen, meterChosen,
                aperture, bellowsExtension, meterReadValue);
        mExposure.setText(liveShot.getPrettyShutter()); //pretty format shutter
    }


    //Housekeeping
    public void onResume(){
        super.onResume();
        loadGear();
        refreshDynamicContent();
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
                getUserChoices();
                calcExposure();
                NumberFormat twoDec = new DecimalFormat("#0.00");
                String exposureCompensations =
                        "BF: " + twoDec.format( liveShot.getBf())+
                                ";  FF: " + twoDec.format(liveShot.getFf())+
                                ";  RC: " + twoDec.format(liveShot.getRc());
                Toast.makeText(MainActivity.this,
                        exposureCompensations, Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_save_shot:
                addMetaInformation(liveShot);
                saveShot (liveShot);
                return true;
            case R.id.action_send_data:
                emailedFilename = getResources().getString(R.string.file_to_email);
                String EmailId = getResources().getString(R.string.email_address);
                exportTableToCSV(DBContract.DB_NAME, DBContract.TableFilm.TABLE_NAME);
                exportDatabase(DBContract.DB_NAME, emailedFilename);
                sendEmail(EmailId, emailedFilename);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1222) {
            File file = new File(Environment.getExternalStorageState()+"/folderName/" +
                    emailedFilename+ ".xml");
            file.delete();
        }
    }


    private void equipmentSelectWidget(){
        //equipment inventory spinner is immutable, no need for dynamic reload; others are dynamic
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



    private void addMetaInformation(Shot shot){
        shot.setShotDay(timeStamp()[0]); //day
        shot.setShotTime(timeStamp()[1]); //time
        shot.setLatitude(locationStamp()[0]); //latitude
        shot.setLongitude(locationStamp()[1]); //longitude
        shot.setComment(comment);
    }

    private void saveShot(Shot shot){
        ShotDBAdapter shotDb = new ShotDBAdapter(this);
        shotDb.open();
        shotDb.addShot(shot);
        shotDb.close();
    }

    private String[] timeStamp(){
        Time now = new Time(Time.getCurrentTimezone());
        now.setToNow();
        String day =  now.year +"-"+(now.month+1)+"-"+now.monthDay;
        String time = now.format("%k:%M:%S");
        String[] timeStamp = new String[2];
        timeStamp[0] = day;
        timeStamp[1] = time;
        return (timeStamp);
    }

    private Double[] locationStamp(){
        Double[] location = new Double[2];
        gps = new GPSTracker(MainActivity.this);
        // check if GPS enabled
        if(gps.canGetLocation()){
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            location[0] = latitude;
            location[1] = longitude;
            // \n is for new line
            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude +
                    "\nLong: " + longitude, Toast.LENGTH_SHORT).show();
        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }
        return (location);
    }


    private void sendEmail(String email, String fileToSend) {
        File file = new File(Environment.getExternalStorageDirectory(), fileToSend);
        Uri path = Uri.fromFile(file);
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.setType("application/octet-stream");
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "lf-db-backup_" +
                timeStamp()[0] + "_" + timeStamp()[1]);
        String to[] = { email };
        intent.putExtra(Intent.EXTRA_EMAIL, to);
        intent.putExtra(Intent.EXTRA_TEXT, "Here is the db.");
        intent.putExtra(Intent.EXTRA_STREAM, path);
        startActivityForResult(Intent.createChooser(intent, "Send mail..."),
                1222);
    }

    public void exportTableToCSV (String databaseName, String tableName){
        //TODO add logic


    }

    public void exportDatabase(String databaseName, String backupDBPath) {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();
            if (sd.canWrite()) {
                String currentDBPath = "//data//"+getPackageName()+"//databases//"+databaseName+"";
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);
                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }
            }
        } catch (Exception e) {        }
    }



}
