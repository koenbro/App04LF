package com.koenbro.android.app04listview;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

/**
 * Created by laszlo on 10/21/14.
 */
public class FilmAddEditActivity extends Activity {
    private EditText mFilmName;
    private EditText mFilmEI;
    private Spinner mFilmType;
    private EditText mFilmRC1;
    private EditText mFilmRC2;
    private EditText mFilmRC3;
    private EditText mFilmRC4;
    private EditText mFilmRC5;
    private EditText mFilmRC6;
    private EditText mFilmRC7;
    private List<String> mFilmTypes;
    
    private FilmDBAdapter db;
    private Film mFilm;
    public static final String EXTRA_FILM_ID = "com.koenbro.app03.filmid";
    private static final int DIALOG_ID = 0;
    //public static final String[] mFilmTypesArray  = {"bw", "color neg", "slide", "IR" };

    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_add_edit);
        db = new FilmDBAdapter(this);
        db.open();
        /* If adding a new film, the intent does NOT have an extra;
        if editing an existing film, the intent extra is the 'id' of the film in database.  */
        if (getIntent().getLongExtra(EXTRA_FILM_ID, 0) == 0) {
            mFilm = getDummy();
        } else {
            mFilm = db.getFilm(getIntent().getLongExtra(EXTRA_FILM_ID, 0));
        }
        createWidgets();
        fillWidgets();
        db.close();
    }
    private Film getDummy() {
        mFilm = new Film();
        mFilm.setFilmName(getString(R.string.hint_film_name));
        //mFilm.setFilmType(getString(R.string.hint_film_type)); //spinner does not need set
        mFilm.setFilmEi (Integer.parseInt( getString(R.string.hint_ei)));
        mFilm.setFilmRc1(Double.parseDouble(getString(R.string.hint_rc1)));
        mFilm.setFilmRc2(Double.parseDouble(getString(R.string.hint_rc2)));
        mFilm.setFilmRc3(Double.parseDouble(getString(R.string.hint_rc3)));
        mFilm.setFilmRc4(Double.parseDouble(getString(R.string.hint_rc4)));
        mFilm.setFilmRc5(Double.parseDouble(getString(R.string.hint_rc5)));
        mFilm.setFilmRc6(Double.parseDouble(getString(R.string.hint_rc6)));
        mFilm.setFilmRc7(Double.parseDouble(getString(R.string.hint_rc7)));
        return(mFilm);
    }

    private void createWidgets(){
        mFilmName = (EditText)findViewById(R.id.editTextFilmName);
        mFilmType = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapterFilmType =
                ArrayAdapter.createFromResource(FilmAddEditActivity.this,
                R.array.film_type,
                android.R.layout.simple_spinner_item);
        adapterFilmType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mFilmType.setAdapter(adapterFilmType);
        mFilmEI = (EditText)findViewById(R.id.editTextFilmEi);
        mFilmRC1 = (EditText)findViewById(R.id.editTextRC1);
        mFilmRC2 = (EditText)findViewById(R.id.editTextRC2);
        mFilmRC3 = (EditText)findViewById(R.id.editTextRC3);
        mFilmRC4 = (EditText)findViewById(R.id.editTextRC4);
        mFilmRC5 = (EditText)findViewById(R.id.editTextRC5);
        mFilmRC6 = (EditText)findViewById(R.id.editTextRC6);
        mFilmRC7 = (EditText)findViewById(R.id.editTextRC7);
    }

    private void fillWidgets(){  //Fill in the fields for the first time as the Activity is created
        mFilmName.setText(mFilm.getFilmName());
        mFilmTypes = Arrays.asList(getResources().getStringArray(R.array.film_type));
        mFilmType.setSelection(mFilmTypes.
                indexOf(String.valueOf(mFilm.getFilmType())));
        mFilmEI.setText(String.valueOf(mFilm.getFilmEi()));
        mFilmRC1.setText(String.valueOf(mFilm.getFilmRc1()));
        mFilmRC2.setText(String.valueOf(mFilm.getFilmRc2()));
        mFilmRC3.setText(String.valueOf(mFilm.getFilmRc3()));
        mFilmRC4.setText(String.valueOf(mFilm.getFilmRc4()));
        mFilmRC5.setText(String.valueOf(mFilm.getFilmRc5()));
        mFilmRC6.setText(String.valueOf(mFilm.getFilmRc6()));
        mFilmRC7.setText(String.valueOf(mFilm.getFilmRc7()));
    }

    public Film getUserInput (){
        mFilm.setFilmName(mFilmName.getText().toString());
        mFilm.setFilmType(mFilmType.getSelectedItem().toString());
        mFilm.setFilmEi(Integer.parseInt(mFilmEI.getText().toString()));
        mFilm.setFilmRc1(Double.parseDouble(mFilmRC1.getText().toString()));
        mFilm.setFilmRc2(Double.parseDouble(mFilmRC2.getText().toString()));
        mFilm.setFilmRc3(Double.parseDouble(mFilmRC3.getText().toString()));
        mFilm.setFilmRc4(Double.parseDouble(mFilmRC4.getText().toString()));
        mFilm.setFilmRc5(Double.parseDouble(mFilmRC5.getText().toString()));
        mFilm.setFilmRc6(Double.parseDouble(mFilmRC6.getText().toString()));
        mFilm.setFilmRc7(Double.parseDouble(mFilmRC7.getText().toString()));
        return(mFilm);
    }

    private void fillBlankFields() {
        if (mFilmName.getText() == null | mFilmName.getText().toString().matches("")){
            warnEmptyField();
            mFilmName.setText(getString(R.string.hint_film_name));
        }
        if (mFilmEI.getText() == null | mFilmEI.getText().toString().matches("")){
            warnEmptyField();
            mFilmEI.setText(getString(R.string.hint_ei));
        }
        if (mFilmRC1.getText() == null | mFilmRC1.getText().toString().matches("")){
            warnEmptyField();
            mFilmRC1.setText(getString(R.string.hint_rc1));
        }
        if (mFilmRC2.getText() == null | mFilmRC2.getText().toString().matches("")){
            warnEmptyField();
            mFilmRC2.setText(getString(R.string.hint_rc2));
        }
        if (mFilmRC3.getText() == null | mFilmRC3.getText().toString().matches("")){
            warnEmptyField();
            mFilmRC3.setText(getString(R.string.hint_rc3));
        }
        if (mFilmRC4.getText() == null | mFilmRC4.getText().toString().matches("")){
            warnEmptyField();
            mFilmRC4.setText(getString(R.string.hint_rc4));
        }
        if (mFilmRC5.getText() == null | mFilmRC5.getText().toString().matches("")){
            warnEmptyField();
            mFilmRC5.setText(getString(R.string.hint_rc5));
        }
        if (mFilmRC6.getText() == null | mFilmRC6.getText().toString().matches("")){
            warnEmptyField();
            mFilmRC6.setText(getString(R.string.hint_rc6));
        }
        if (mFilmRC7.getText() == null | mFilmRC7.getText().toString().matches("")){
            warnEmptyField();
            mFilmRC7.setText(getString(R.string.hint_rc7));
        }
    }

    private void warnEmptyField(){
        Toast.makeText(FilmAddEditActivity.this,
                R.string.warning_empty_field,  Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.film_add_edit_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_discard_film:
                // intent extra of 0 means a new film is being added. In this case you are
                // not really deleting, bec. it was not yet saved. You are simply canceling.
                if (getIntent().getLongExtra(EXTRA_FILM_ID, 0) == 0) {
                    finish();
                }
                else {
                    showDialog(DIALOG_ID);
                }
                return true;
            case R.id.action_save_film:
                db.open();
                fillBlankFields();
                Film newFilm = getUserInput();
                // intent extra of 0 means a new film is added
                if (getIntent().getLongExtra(EXTRA_FILM_ID, 0) == 0) {
                    db.addFilm(newFilm);
                    Toast.makeText(FilmAddEditActivity.this,
                            R.string.film_added, Toast.LENGTH_SHORT).show();
                } else {
                    int i = db.updateFilm(newFilm);
                    Toast.makeText(FilmAddEditActivity.this,
                            R.string.film_saved, Toast.LENGTH_SHORT).show();
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
    This dialog adds a layer of safety, preventing accidental deletion of films.*/
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
                                        db.deleteFilm(mFilm);
                                        finish();
                                        db.close();
                                        Toast.makeText(FilmAddEditActivity.this,
                                                R.string.film_deleted,
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