package com.koenbro.android.app04listview;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author laszlo
 * @date 1/19/16.
 */
public class ShotDetailActivity extends Activity {
    private EditText etComment;
    private TextView tvComment, tvTimePlace, tvLAS, tvFF, tvEvFactors, tvCBMH;
    public static final String EXTRA_SHOT_ID = "com.koenbro.app03.shotid";
    private ShotDBAdapter db;
    private Shot shot;
    private DBUtil dbUtil;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shot_detail);
        db = new ShotDBAdapter(this);
        dbUtil = new DBUtil();
        db.open();
        shot = db.getShot(getIntent().getLongExtra(EXTRA_SHOT_ID,0));
        createWidgets();
        fillWidgets();
        db.close();
    }

    private void createWidgets() {
        etComment = (EditText) findViewById(R.id.editText_comment);
        tvComment = (TextView)findViewById(R.id.textView_comment); //1
        tvTimePlace = (TextView)findViewById(R.id.textView_time_place); //2
        tvFF = (TextView)findViewById(R.id.textView_film_filter); //3
        tvLAS = (TextView)findViewById(R.id.textView_lens_aperture_shutter); //4
        tvEvFactors = (TextView)findViewById(R.id.textView_5); //5
        tvCBMH = (TextView)findViewById(R.id.textView_6);

    }

    private void fillWidgets() {
        //1
        etComment.setText(shot.getComment());

        //2
        String timePlace = "\n\nDate and time: " + shot.getShotDay() + " at " + shot.getShotTime()
                + "\n" + "Location lat: " +shot.getLatitude() + "; lon: " + shot.getLongitude() +
                "\n\n";
        tvTimePlace.setText(timePlace);

        //3
        String filmFilter = "Film: " + shot.getFilmName() + " (EI: " + shot.getFilmEi() + "), " +
                "Filter: " + shot.getFilterName();
        tvFF.setText(filmFilter);

        //4
        String lensApertureBellows = "Lens: " + String.valueOf(shot.getLensFocal()) + "mm f/" +
                dbUtil.oneDec(shot.getAperture()) + " at " + shot.getPrettyShutter();
        tvLAS.setText(lensApertureBellows);

        //5
        String evcf = "BF: " + shot.getBf() + ";  FF: " + shot.getFf() + ";  RC: " + shot.getRc();

        tvEvFactors.setText(evcf) ;

        //6
        String cbmh = "\n\nCamera: " + shot.getCameraName() +
                ", bellows extension: " + shot.getBellowsExtension() + "mm. \n" +
                "Meter: " + shot.getMeterName() + ", EV: " + dbUtil.twoDec(shot.getMeterRead()) +  "\n" +
                "Holder: " + shot.getFilmHolderID() + "\n" +
                "Zone System development: " + shot.getZoneSytemPushPull();
        tvCBMH.setText(cbmh);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.shot_detail_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
        //return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_discard_shot:
                //int thisShotId = (int) getIntent().getLongExtra(EXTRA_SHOT_ID, 0);
                db.open();
                db.deleteShot(shot);
                finish();
                db.close();
                return true;
            case R.id.action_save_shot:
                db.open();
                String newComment = etComment.getText().toString();
                if (!newComment.matches("")) {
                    shot.setComment(newComment);
                }
                db.updateShot(shot);
                Toast.makeText(ShotDetailActivity.this,
                        R.string.saved, Toast.LENGTH_SHORT).show();
                finish();
                db.close();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
