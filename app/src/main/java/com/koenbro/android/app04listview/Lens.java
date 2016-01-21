package com.koenbro.android.app04listview;

/**
 * Created by laszlo on 10/23/14.
 */
public class Lens {
    private long id;
    private String lensName;
    private String lensBrand;
    private int lensFocal;
    private int lensFilterDiam;
    private double lensApertureOpen;
    private double lensApertureClosed;

    public Lens() {}

    public Lens(long id, String lensName, String lensBrand, int lensFocal,
                double lensApertureOpen, double getLensApertureClosed) {
        this.id = id;
        this.lensName = lensName;
        this.lensBrand = lensBrand;
        this.lensFocal = lensFocal;
        this.lensApertureOpen = lensApertureOpen;
        this.lensApertureClosed = getLensApertureClosed;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLensName() {
        return lensName;
    }

    public void setLensName(String lensName) {
        this.lensName = lensName;
    }

    public String getLensBrand() {
        return lensBrand;
    }

    public void setLensBrand(String lensBrand) {
        this.lensBrand = lensBrand;
    }

    public int getLensFocal() {
        return lensFocal;
    }

    public void setLensFocal(int lensFocal) {
        this.lensFocal = lensFocal;
    }

    public int getLensFilterDiam() {
        return lensFilterDiam;
    }

    public void setLensFilterDiam(int lensFilterDiam) {
        this.lensFilterDiam = lensFilterDiam;
    }

    public double getLensApertureOpen() {
        return lensApertureOpen;
    }

    public void setLensApertureOpen(double lensApertureOpen) {
        this.lensApertureOpen = lensApertureOpen;
    }

    public double getLensApertureClosed() {
        return lensApertureClosed;
    }

    public void setLensApertureClosed(double lensApertureClosed) {
        this.lensApertureClosed = lensApertureClosed;
    }
    @Override
    public String toString() {
        return "Lens [" + String.valueOf(getLensFocal()) + "mm f/" +
                String.valueOf(getLensApertureOpen()) + " " + getLensBrand()  +
                ", id=" + id+"]";
    }
    public String toStringShort() {
        return  String.valueOf(getLensFocal()) + "mm f/" +
                String.valueOf(getLensApertureOpen())   + " (id=" + id + ")";
    }
}
