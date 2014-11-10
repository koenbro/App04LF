package com.koenbro.android.app04listview;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by laszlo on 10/21/14.
 */
public class FilterAddEditActivity extends Activity {
    private EditText mFilterName;
    private CheckBox mFilterForBW;
    private CheckBox mFilterForColor;
    private Boolean  bFilterForBW;
    private Boolean  bFilterForColor;
    private EditText mFilterFactorBW;
    private EditText mFilterFactorColor;

    private FilterDBAdapter db;
    private Filter mFilter;
    public static final String EXTRA_FILTER_ID = "com.koenbro.app03.filterid";
    private static final int DIALOG_ID = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_add_edit);
        db = new FilterDBAdapter(this);
        bFilterForBW = false;
        bFilterForColor = false;
        db.open();
        createWidgets();
/*      If adding a new filter, the intent does NOT have an extra;
        if editing an existing filter, the intent extra is the 'id' of the filter in database.  */
        if (getIntent().getLongExtra(EXTRA_FILTER_ID, 0) == 0) {
            mFilter = getDummy();
        } else {
            mFilter = db.getFilter(getIntent().getLongExtra(EXTRA_FILTER_ID, 0));
        }
        fillWidgets();
        db.close();
    }

    private void createWidgets(){
        mFilterName = (EditText)findViewById(R.id.editTextFilterName);
        mFilterForBW = (CheckBox)findViewById(R.id.checkBoxBw);
        mFilterForColor = (CheckBox)findViewById(R.id.checkBoxColor);
        mFilterFactorBW = (EditText)findViewById(R.id.editTextFFbw);
        mFilterFactorColor = (EditText)findViewById(R.id.editTextFFcolor);
        //TODO set up listeners for checkboxes

        mFilterForBW.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                bFilterForBW = true;
            }
        });

        mFilterForColor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                bFilterForColor = true;
            }
        });
    }

    public Filter getDummy() {
        mFilter = new Filter();
        mFilter.setFilterName(getString(R.string.hint_filter_name));
        mFilter.setFilterForBW(false);
        mFilter.setFilterForBW(false);
        mFilter.setFilterFactorBW(Float.parseFloat(getString(R.string.hint_ff_bw)));
        mFilter.setFilterFactorColor(Float.parseFloat(getString(R.string.hint_ff_color)));
        return(mFilter);
    }

    private void fillWidgets(){  //Fill in the fields for the first time as the Activity is created
        mFilterName.setText(mFilter.getFilterName());
        mFilterForBW.setChecked(mFilter.isFilterForBW());
        mFilterForColor.setChecked(mFilter.isFilterForColor());
        mFilterFactorBW.setText(String.valueOf(mFilter.getFilterFactorBW()));
        mFilterFactorColor.setText(String.valueOf(mFilter.getFilterFactorColor()));
    }

    public Filter getUserInput (){
        mFilter.setFilterName(mFilterName.getText().toString());
        mFilter.setFilterForBW(mFilterForBW.isChecked());  //(bFilterForBW);
        mFilter.setFilterForColor(mFilterForColor.isChecked()); //(bFilterForColor);
        mFilter.setFilterFactorBW(Float.parseFloat(mFilterFactorBW.getText().toString()));
        mFilter.setFilterFactorColor(Float.parseFloat(mFilterFactorColor.getText().toString()));
        return(mFilter);
    }

    private void fillBlankFields() {
        if (mFilterName.getText() == null | mFilterName.getText().toString().matches("")){
            warnEmptyField();
            mFilterName.setText(getString(R.string.hint_filter_name));
        }
        //TODO do you need validation for checkbox?!
        if (mFilterFactorBW.getText() == null | mFilterFactorBW.getText().toString().matches("")){
            warnEmptyField();
            mFilterFactorBW.setText(getString(R.string.hint_ff_bw));
        }
        if (mFilterFactorColor.getText() == null | mFilterFactorColor.getText().toString().matches("")){
            warnEmptyField();
            mFilterFactorColor.setText(getString(R.string.hint_ff_color));
        }

    }

    private void warnEmptyField(){
        Toast.makeText(FilterAddEditActivity.this,
                R.string.warning_empty_field,  Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.filter_add_edit_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_discard_filter:
                // intent extra of 0 means a new filter is being added. In this case you are
                // not really deleting, bec. it was not yet saved. You are simply canceling.
                if (getIntent().getLongExtra(EXTRA_FILTER_ID, 0) == 0) {
                    finish();
                }
                else {
                    showDialog(DIALOG_ID);
                }
                return true;
            case R.id.action_save_filter:
                db.open();
                fillBlankFields();
                Filter newFilter = getUserInput();
                // intent extra of 0 means a new filter is added
                if (getIntent().getLongExtra(EXTRA_FILTER_ID, 0) == 0) {
                    db.addFilter(newFilter);
                    Toast.makeText(FilterAddEditActivity.this,
                            R.string.filter_added, Toast.LENGTH_SHORT).show();
                } else {
                    db.updateFilter(newFilter);
                    Toast.makeText(FilterAddEditActivity.this,
                            R.string.filter_saved, Toast.LENGTH_SHORT).show();
                }
                finish();
                db.close();
                return true;
            /*case R.id.action_settings:
                openSettings();
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*  Alert dialog using deprecated code. Works but need to replace.
        This dialog adds a layer of safety, preventing accidental deletion of filters.*/
    //TODO replace deprecated Alert dialog
    protected final Dialog onCreateDialog(final int id) {
        Dialog dialog = null;
        switch (id) {
            case DIALOG_ID:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(
                        R.string.dialog_delete_film)
                        .setCancelable(false)
                        .setPositiveButton(R.string.dialog_ok,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        db.open();
                                        db.deleteFilter(mFilter);
                                        finish();
                                        db.close();
                                        Toast.makeText(FilterAddEditActivity.this,
                                                R.string.filter_deleted,
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