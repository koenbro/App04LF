package com.koenbro.android.app04listview;

/**
 * Created by laszlo on 10/26/14.
 */
public class Meter {
    private long id;
    private String mMeterName;
    private String mMeterResultFormat;
    private int mMeterISO;
    private double mMeterCompensation;

    public Meter() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMeterName() {
        return mMeterName;
    }

    public void setMeterName(String meterName) {
        mMeterName = meterName;
    }

    public String getMeterResultFormat() {
        return mMeterResultFormat;
    }

    public void setMeterResultFormat(String meterResultFormat) {
        mMeterResultFormat = meterResultFormat;
    }

    public int getMeterISO() {
        return mMeterISO;
    }

    public void setMeterISO(int meterISO) {
        mMeterISO = meterISO;
    }

    public double getMeterCompensation() {
        return mMeterCompensation;
    }

    public void setMeterCompensation(double meterCompensation) {
        mMeterCompensation = meterCompensation;
    }

    @Override
    public String toString() {
        return "Meter [" + mMeterName  +  ", id=" + id +  "]";
    }
    public String toStringShort() {
        return  mMeterName  +  " (id=" + id +")" ;
    }
}
