package com.koenbro.android.app04listview;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * Database utilities: export, handle, email
 * @author laszlo
 * @date 2/26/15.
 */
public class DBUtil {
    ShotMetaInfo shotMetaInfo;

    public DBUtil() {
        shotMetaInfo = new ShotMetaInfo();
    }

    public void exportDatabase(String databaseName, String backupDBPath) {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();
            if (sd.canWrite()) {
                String currentDBPath = "//data//" +
                        ApplicationContextProvider.getContext().getPackageName() +
                        "//databases//" + databaseName;
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

    public void exportTableToCSV (String databaseName, String tableName){
        //TODO add logic
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
}
