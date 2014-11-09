package com.koenbro.android.app04listview;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by laszlo on 10/21/14.
 */
public class CameraAddEditActivity extends Activity {
    private EditText mCameraName;
    private EditText mCameraBEmin;
    private EditText mCameraBEmax;
    private CameraDBAdapter db;
    private Camera mCamera;
    public static final String EXTRA_CAMERA_ID = "com.koenbro.app03.cameraid";
    private static final int DIALOG_ID = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_add_edit);
        db = new CameraDBAdapter(this);
        db.open();
        createWidgets();
/*      If adding a new camera, the intent does NOT have an extra;
        if editing an existing camera, the intent extra is the 'id' of the camera in database.  */
        if (getIntent().getLongExtra(EXTRA_CAMERA_ID, 0) == 0) {
            mCamera = getDummy();
        } else {
            mCamera = db.getCamera(getIntent().getLongExtra(EXTRA_CAMERA_ID, 0));
        }
        fillWidgets();
        db.close();
    }

    private void createWidgets(){
        mCameraName = (EditText)findViewById(R.id.editTextCameraName);
        mCameraBEmin = (EditText)findViewById(R.id.editTextBEmin);
        mCameraBEmax = (EditText)findViewById(R.id.editTextBEmax);

    }

    public Camera getDummy() {
        mCamera = new Camera();
        mCamera.setCameraName(getString(R.string.hint_camera_name));
        mCamera.setBellowsMin(Integer.parseInt(getString(R.string.hint_be_min)));
        mCamera.setBellowsMax(Integer.parseInt(getString(R.string.hint_be_max)));
        return(mCamera);
    }

    private void fillWidgets(){  //Fill in the fields for the first time as the Activity is created
        mCameraName.setText(mCamera.getCameraName());
        mCameraBEmin.setText(String.valueOf(mCamera.getBellowsMin()));
        mCameraBEmax.setText(String.valueOf(mCamera.getBellowsMax()));
    }

    public Camera getUserInput (){
        mCamera.setCameraName(mCameraName.getText().toString());
        mCamera.setBellowsMin(Integer.parseInt(mCameraBEmin.getText().toString()));
        mCamera.setBellowsMax(Integer.parseInt(mCameraBEmax.getText().toString()));
        return(mCamera);
    }

    private void fillBlankFields() {
        if (mCameraName.getText() == null | mCameraName.getText().toString().matches("")){
            warnEmptyField();
            mCameraName.setText(getString(R.string.hint_camera_name));
        }
        if (mCameraBEmin.getText() == null | mCameraBEmin.getText().toString().matches("")){
            warnEmptyField();
            mCameraBEmin.setText(getString(R.string.hint_be_min));
        }
        if (mCameraBEmax.getText() == null | mCameraBEmax.getText().toString().matches("")){
            warnEmptyField();
            mCameraBEmax.setText(getString(R.string.hint_be_max));
        }

    }

    private void warnEmptyField(){
        Toast.makeText(CameraAddEditActivity.this,
                R.string.warning_empty_field,  Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.camera_add_edit_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_discard_camera:
                // intent extra of 0 means a new camera is being added. In this case you are
                // not really deleting, bec. it was not yet saved. You are simply canceling.
                if (getIntent().getLongExtra(EXTRA_CAMERA_ID, 0) == 0) {
                    finish();
                }
                else {
                    showDialog(DIALOG_ID);
                }
                return true;
            case R.id.action_save_camera:
                db.open();
                fillBlankFields();
                Camera newCamera = getUserInput();
                // intent extra of 0 means a new camera is added
                if (getIntent().getLongExtra(EXTRA_CAMERA_ID, 0) == 0) {
                    db.addCamera(newCamera);
                    Toast.makeText(CameraAddEditActivity.this,
                            R.string.camera_added, Toast.LENGTH_SHORT).show();
                } else {
                    boolean i = db.updateCamera(newCamera);
                    Toast.makeText(CameraAddEditActivity.this,
                            R.string.camera_saved, Toast.LENGTH_SHORT).show();
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
        This dialog adds a layer of safety, preventing accidental deletion of cameras.*/
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
                                        db.deleteCamera(mCamera);
                                        finish();
                                        db.close();
                                        Toast.makeText(CameraAddEditActivity.this,
                                                R.string.camera_deleted,
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