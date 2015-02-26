package com.koenbro.android.app04listview;

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
}
