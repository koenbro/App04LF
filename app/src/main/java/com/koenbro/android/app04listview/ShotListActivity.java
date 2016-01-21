package com.koenbro.android.app04listview;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by laszlo on 10/23/14.
 */
public class ShotListActivity extends Activity {
    ShotDBAdapter db;
    GearAdapter ShotAdapter;
    ListView ShotListView;
    DBUtil dbUtil;
    ShotMetaInfo shotMetaInfo;
    private String emailedFilename;// = getResources().getString(R.string.file_to_email);
    private String emailAddress;// = getResources().getString(R.string.email_address);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shot_list);
        db = new ShotDBAdapter(this);
        dbUtil = new DBUtil();
        shotMetaInfo = new ShotMetaInfo();
        ShotAdapterLoad();
        ShotListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent i = new Intent(ShotListActivity.this, ShotDetailActivity.class);
                Log.d(DBContractShots.TableShot.TAG, String.valueOf(position));
                ArrayList<Shot> latestShotList = generateData();
                Shot clickedShot = (Shot) latestShotList.get(position);
                long intentId = clickedShot.getId();
                i.putExtra(ShotDetailActivity.EXTRA_SHOT_ID, intentId); //sql starts at 1; java at 0
                Toast.makeText(getApplicationContext(),
                        "position:" + String.valueOf(position) + "; id:" + String.valueOf(intentId),
                        Toast.LENGTH_SHORT).show();
                startActivityForResult(i, 0);
            }
        });
    }

    // Reload the latest Shot list after addition/deletion
    public void onResume(){
        super.onResume();
        ShotAdapterLoad();
    }

    /**
     * Create a custom adaptor to connect the Shot list from generateData() with the ShotListView
     */
    public void ShotAdapterLoad(){
        ShotAdapter = new GearAdapter(this, generateData()); //pass context/data to custom adapter
        ShotListView = (ListView) findViewById(R.id.shotListView); //Get ShotListView from
        // activity_Shot_list.xml
        ShotListView.setAdapter(ShotAdapter);
    }

    private ArrayList<Shot> generateData() {
        db.open();
        ArrayList<Shot> allShots = db.getAllShots();
        db.close();
        Collections.reverse(allShots); //show most recent first
        return (allShots);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.shot_list_activity_actions, menu);
        return true;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_send_data:
                emailedFilename = getResources().getString(R.string.file_to_email);
                emailAddress = getResources().getString(R.string.email_address);
                emailDataBase(emailedFilename, emailAddress);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void emailDataBase(String emailedFilename, String emailAddress) {
        //dbIndex is:
        //0 - first db; 1 - journal of 1st db  here: lfgear
        //2 - 2nd db                           here: lfshots
        int dbIndex = 2;
        dbUtil.exportDatabase(dbIndex, emailedFilename); //export lfshot
        //email the exported file
        startActivityForResult(
                Intent.createChooser(sendEmailIntent(emailAddress, emailedFilename),
                        "Send mail..."),
                1222);
    }

    public Intent sendEmailIntent (String email, String fileToSend) {
        File file = new File(Environment.getExternalStorageDirectory(), fileToSend);
        Uri path = Uri.fromFile(file);
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.setType("application/octet-stream");
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "lf-db-backup_" +
                shotMetaInfo.getDay() + "_" + shotMetaInfo.getTime());
        String to[] = { email };
        intent.putExtra(Intent.EXTRA_EMAIL, to);
        intent.putExtra(Intent.EXTRA_TEXT, "Here is the db.");
        intent.putExtra(Intent.EXTRA_STREAM, path);
        return intent;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data, String emailedFilename) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1222) {
            File file = new File(Environment.getExternalStorageState() +
                    "/folderName/" + emailedFilename + ".xml");
            file.delete();
        }
    }
}

