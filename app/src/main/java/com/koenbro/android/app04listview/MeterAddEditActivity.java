package com.koenbro.android.app04listview;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;


public class MeterAddEditActivity extends Activity {
    private EditText mMeterName;
    private Spinner mMeterResultsFormat;
    private EditText mMeterISO;
    private EditText mMeterCompensation;
    private List<String> mMeterResultsFormatOptions;

    private MeterDBAdapter db;
    private Meter mMeter;
    public static final String EXTRA_METER_ID = "com.koenbro.app03.meterid";
    private static final int DIALOG_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meter_add_edit);
        db = new MeterDBAdapter(this);
        db.open();
        if (getIntent().getLongExtra(EXTRA_METER_ID, 0) == 0) {
            mMeter = getDummy();
        } else {
            mMeter = db.getMeter(getIntent().getLongExtra(EXTRA_METER_ID, 0));
        }
        createWidgets();
        fillWidgets();
        db.close();
    }

    private Meter getDummy(){
        mMeter = new Meter();
        mMeter.setMeterName(getString(R.string.hint_meter_name));
        //spinner does not need to be set
        mMeter.setMeterISO(Integer.parseInt(getString(R.string.hint_meter_iso)));
        mMeter.setMeterCompensation(Double.parseDouble(getString(R.string.hint_meter_compensation)));
        return(mMeter);
    }

    private void createWidgets(){
        mMeterName = (EditText)findViewById(R.id.editTextMeterName);
        mMeterISO = (EditText)findViewById(R.id.editTextMeterIso);
        mMeterCompensation = (EditText)findViewById(R.id.editTextMeterCompensation);
        mMeterResultsFormat = (Spinner)findViewById(R.id.spinnerMeterResultsFormat);
        // set up listeners for spinners
        ArrayAdapter<CharSequence> adapterMeterResultsFormat =
                ArrayAdapter.createFromResource(MeterAddEditActivity.this,
                        R.array.meter_results_format,
                        android.R.layout.simple_spinner_item);
        adapterMeterResultsFormat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mMeterResultsFormat.setAdapter(adapterMeterResultsFormat);
    }

    private void fillWidgets(){
        mMeterName.setText(mMeter.getMeterName());
        mMeterResultsFormatOptions = Arrays.asList(getResources().
                getStringArray(R.array.meter_results_format));
        mMeterResultsFormat.setSelection(mMeterResultsFormatOptions.
                indexOf(String.valueOf(mMeter.getMeterResultFormat())));
        mMeterISO.setText(String.valueOf(mMeter.getMeterISO()));
        mMeterCompensation.setText(String.valueOf(mMeter.getMeterCompensation()));
    }

    private Meter getUserInput(){
        mMeter.setMeterName(mMeterName.getText().toString());
        mMeter.setMeterResultFormat((mMeterResultsFormat.getSelectedItem().toString()));
        mMeter.setMeterISO(Integer.parseInt(mMeterISO.getText().toString()));
        mMeter.setMeterCompensation(Double.parseDouble(mMeterCompensation.getText().toString()));
        return(mMeter);
    }

    private void fillBlankFields(){
        if (mMeterName.getText() == null | mMeterName.getText().toString().matches("")){
            warnEmptyField();
            mMeterName.setText(getString(R.string.hint_meter_name));
        }
        if (mMeterISO.getText() == null | mMeterISO.getText().toString().matches("")){
            warnEmptyField();
            mMeterISO.setText(getString(R.string.hint_meter_iso));
        }
        if (mMeterCompensation.getText() == null | mMeterCompensation.getText().toString().matches("")){
            warnEmptyField();
            mMeterCompensation.setText(getString(R.string.hint_meter_compensation));
        }
    }

    private void warnEmptyField(){
        Toast.makeText(MeterAddEditActivity.this,
                R.string.warning_empty_field, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.meter_add_edit_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
        //return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_discard_meter:
                if (getIntent().getLongExtra(EXTRA_METER_ID,0) == 0){
                    finish();
                } else {
                    showDialog(DIALOG_ID);
                }
                return true;
            case R.id.action_save_meter:
                db.open();
                fillBlankFields();
                Meter newMeter = getUserInput();

                if (getIntent().getLongExtra(EXTRA_METER_ID,0) == 0){
                    db.addMeter(newMeter);
                    Toast.makeText(MeterAddEditActivity.this,
                            R.string.added, Toast.LENGTH_SHORT).show();
                } else {
                    int i = db.updateMeter(newMeter);
                    Toast.makeText(MeterAddEditActivity.this,
                            R.string.saved, Toast.LENGTH_SHORT).show();
                }
                finish();
                db.close();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected final Dialog onCreateDialog(final int id) {
        Dialog dialog = null;
        switch (id) {
            case DIALOG_ID:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(
                        R.string.dialog_delete)
                        .setCancelable(false)
                        .setPositiveButton(R.string.dialog_ok,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        db.open();
                                        db.deleteMeter(mMeter);
                                        finish();
                                        db.close();
                                        Toast.makeText(MeterAddEditActivity.this,
                                                R.string.deleted,
                                                Toast.LENGTH_SHORT).show();
                                    }
                                })
                        .setNegativeButton(R.string.dialog_cancel,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alert = builder.create();
                dialog = alert;
                break;
            default:
        }
        return dialog;
    }

}
