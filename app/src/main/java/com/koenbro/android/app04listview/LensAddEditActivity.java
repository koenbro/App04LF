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


public class LensAddEditActivity extends Activity {
    private EditText mLensName;
    private EditText mLensBrand;
    private EditText mLensFocal;
    private EditText mLensFilterDiam;
    private Spinner mLensOpen;
    private Spinner mLensClosed;
    private List<String> mLensApertures;

    private LensDBAdapter db;
    private Lens mLens;
    public static final String EXTRA_LENS_ID = "com.koenbro.app03.lensid";
    private static final int DIALOG_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lens_add_edit);
        db = new LensDBAdapter(this);
        db.open();
        if (getIntent().getLongExtra(EXTRA_LENS_ID, 0) == 0) {
            mLens = getDummy();
        } else {
            mLens = db.getLens(getIntent().getLongExtra(EXTRA_LENS_ID, 0));
        }
        createWidgets();
        fillWidgets();
        db.close();
    }

    private Lens getDummy(){
        mLens = new Lens();
        mLens.setLensName(getString(R.string.hint_lens_nickname));
        mLens.setLensBrand(getString(R.string.hint_lens_brand));
        mLens.setLensFocal(Integer.parseInt(getString(R.string.hint_lens_focal)));
        mLens.setLensFilterDiam(Integer.parseInt(getString(R.string.hint_lens_filter_dia)));
        //spinner does not need to be set
        return(mLens);
    }

    private void createWidgets(){
        mLensName = (EditText)findViewById(R.id.editTextLensName);
        mLensBrand = (EditText)findViewById(R.id.editTextLensBrand);
        mLensFocal = (EditText)findViewById(R.id.editTextLensFocal);
        mLensFilterDiam =(EditText)findViewById(R.id.editTextLensFilterDia);
        mLensOpen = (Spinner)findViewById(R.id.spinnerApertureOpen);
        mLensClosed = (Spinner)findViewById(R.id.spinnerApertureClosed);
        // set up listeners for spinners
        ArrayAdapter<CharSequence> adapterLensOpen =
                ArrayAdapter.createFromResource(LensAddEditActivity.this,
                        R.array.lens_aperture,
                        android.R.layout.simple_spinner_item);
        adapterLensOpen.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mLensOpen.setAdapter(adapterLensOpen);
        ArrayAdapter<CharSequence> adapterLensClosed =
                ArrayAdapter.createFromResource(LensAddEditActivity.this,
                        R.array.lens_aperture,
                        android.R.layout.simple_spinner_item);
        adapterLensClosed.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mLensClosed.setAdapter(adapterLensClosed);
    }

    private void fillWidgets(){
        mLensName.setText(mLens.getLensName());
        mLensBrand.setText(mLens.getLensBrand());
        mLensFocal.setText(String.valueOf(mLens.getLensFocal()));
        mLensFilterDiam.setText(String.valueOf(mLens.getLensFilterDiam()));
        mLensApertures = Arrays.asList(getResources().getStringArray(R.array.lens_aperture));
        mLensOpen.setSelection(mLensApertures.
                indexOf(String.valueOf(mLens.getLensApertureOpen()).replaceAll("\\.0*$", "")));
        //str.replaceAll("\\.0*$", "")  drops the trailing zeros so 8.0 -> 8, but keeps 5.6
        mLensClosed.setSelection(mLensApertures.
                indexOf(String.valueOf(mLens.getLensApertureClosed()).replaceAll("\\.0*$", "")));
        /*mFilmTypes = Arrays.asList(getResources().getStringArray(R.array.film_type));
        mFilmType.setSelection(mFilmTypes.
                indexOf(String.valueOf(mFilm.getFilmType())));*/
    }

    private Lens getUserInput(){
        mLens.setLensName(mLensName.getText().toString());
        mLens.setLensBrand(mLensBrand.getText().toString());
        mLens.setLensFocal(Integer.parseInt(mLensFocal.getText().toString()));
        mLens.setLensFilterDiam(Integer.parseInt(mLensFilterDiam.getText().toString()));
        mLens.setLensApertureOpen(Double.parseDouble(mLensOpen.getSelectedItem().toString()));
        mLens.setLensApertureClosed(Double.parseDouble(mLensClosed.getSelectedItem().toString()));
        /*mFilm.setFilmType(mFilmType.getSelectedItem().toString());*/
        return(mLens);
    }

    private void fillBlankFields(){
        if (mLensName.getText() == null | mLensName.getText().toString().matches("")){
            warnEmptyField();
            mLensName.setText(getString(R.string.hint_lens_nickname));
        }
        if (mLensBrand.getText() == null | mLensBrand.getText().toString().matches("")){
            warnEmptyField();
            mLensBrand.setText(getString(R.string.hint_lens_brand));
        }
        if (mLensFocal.getText() == null | mLensFocal.getText().toString().matches("")){
            warnEmptyField();
            mLensFocal.setText(getString(R.string.hint_lens_focal));
        }
        if (mLensFilterDiam.getText() == null | mLensFilterDiam.getText().toString().matches("")){
            warnEmptyField();
            mLensFilterDiam.setText(getString(R.string.hint_lens_filter_dia));
        }
    }

    private void warnEmptyField(){
        Toast.makeText(LensAddEditActivity.this,
                R.string.warning_empty_field, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.lens_add_edit_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
        //return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_discard_lens:
                if (getIntent().getLongExtra(EXTRA_LENS_ID,0) == 0){
                    finish();
                } else {
                    showDialog(DIALOG_ID);
                }
                return true;
            case R.id.action_save_lens:
                db.open();
                fillBlankFields();
                Lens newLens = getUserInput();

                if (getIntent().getLongExtra(EXTRA_LENS_ID,0) == 0){
                    db.addLens(newLens);
                    Toast.makeText(LensAddEditActivity.this,
                            R.string.added, Toast.LENGTH_SHORT).show();
                } else {
                    int i = db.updateLens(newLens);
                    Toast.makeText(LensAddEditActivity.this,
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
                                        db.deleteLens(mLens);
                                        finish();
                                        db.close();
                                        Toast.makeText(LensAddEditActivity.this,
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
