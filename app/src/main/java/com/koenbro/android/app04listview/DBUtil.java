package com.koenbro.android.app04listview;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Database utilities: export, handle, email
 * @author laszlo
 * @date 2/26/15.
 */
public class DBUtil  {

    NumberFormat round;
    NumberFormat oneDec;
    NumberFormat twoDec;

    public DBUtil() {
        //shotMetaInfo = new ShotMetaInfo();
        round =  new DecimalFormat("#0");
        oneDec = new DecimalFormat("#0.0");
        twoDec = new DecimalFormat("#0.00");
    }

    public String round (double d) {
        return round.format(d);
    }

    public String oneDec (double d) {
        return oneDec.format(d);
    }

    public String twoDec (double d) {
        return twoDec.format(d);
    }

    public String leadingZero (int i) {
        return String.format("%02d", i);
    }

    public void exportDatabase(int dbIndex, String backupDBPath) {
        String[] dbList = ApplicationContextProvider.getContext().databaseList();
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();
            if (sd.canWrite()) {

                //dbIndex is:
                //0 - first db; 1 - journal of 1st db  here: lfgear
                //2 - 2nd db                           here: lfshots
                String currentDBPath = "//data//" +
                        ApplicationContextProvider.getContext().getPackageName() +
                        "//databases//" + dbList[dbIndex];

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
