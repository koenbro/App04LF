package com.koenbro.android.app04listview;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;

/**
 * Created by laszlo on 10/31/14.
 */
public class Shot {
    private long id;
    private Date shotDate;
    //TODO add logic to handle date

    private Film film;
    private Lens lens;
    private Filter filter;
    private Camera camera;
    private Meter meter;

    private double aperture;
    private int bellowsExtension;
    private double meterRead;

    private double ff;
    private double bf;
    private double rc;

    private double shutter;
    private String comment;
    private final double tMeterRef = 15;
    private final double fMeterRef = 8;
    private final double evMeterRef = 10;
    private final double isoMeterRef = 100;
    //TODO get these from db


    public Shot() {}

    public Shot(Film film, Lens lens, Filter filter, Camera camera, Meter meter,
                double aperture, int bellowsExtension, double meterRead) {
        this.film = film;
        this.lens = lens;
        this.filter = filter;
        this.camera = camera;
        this.meter = meter;
        this.aperture = aperture;
        this.bellowsExtension = bellowsExtension;
        this.meterRead = meterRead;
    }

    private double calcShutter(){
        double shutterPreRc;
        double mc = Math.pow(2.0, evMeterRef - meterRead);
        bf = Math.pow((double)bellowsExtension/(double)lens.getLensFocal(), 2.0);
        setBf(bf);
        double ac = Math.pow(aperture/fMeterRef,2.0);
        double sc = isoMeterRef/film.getFilmEi();
        ff = calcFf(film, filter);
        shutterPreRc = (1/tMeterRef) * mc * bf * ac * sc * ff;
        rc = calcRc(shutterPreRc);
        shutter = shutterPreRc * rc;
        return shutter;
    }

    private String pretty(double shutter){
        String pretty;
        NumberFormat round = new DecimalFormat("#0");
        NumberFormat twoDec = new DecimalFormat("#0.00");
        //TODO maybe just cast as integer; or round; or set to nearest
        //need to handle fractions as inverse 1/T
        if (shutter <1){
            shutter = 1/shutter;
            pretty = "1/" + round.format(shutter);
            if (shutter < 5) {  //show more details
                pretty = pretty +  " (" + twoDec.format(1/shutter) + "s)";
            }
        } else {
            pretty = round.format(shutter)+"s";
        }
        return (pretty);

    }

    private double calcFf(Film film, Filter filter) {
        if (film.getFilmType().matches("bw")) ff = filter.getFilterFactorBW();
        if (film.getFilmType().matches("color")) ff = filter.getFilterFactorColor();
        setFf(ff);
        return ff;
    }

    private double calcRc (double shutter){
        if (shutter < 1) rc = 1;
        else if(shutter>=1 & shutter<2) rc = film.getFilmRc1();
        else if(shutter>=2 & shutter<5) rc = film.getFilmRc2();
        else if(shutter>=5 & shutter<10) rc = film.getFilmRc3();
        else if(shutter>=10 & shutter<30) rc = film.getFilmRc4();
        else if(shutter>=30 & shutter<60) rc = film.getFilmRc5();
        else if(shutter>=60 & shutter<100) rc = film.getFilmRc6();
        else if(shutter>=100) rc = film.getFilmRc7();
        setRc(rc);
        return( rc);
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public Date getShotDate() {
        return shotDate;
    }
    public void setShotDate(Date shotDate) {
        this.shotDate = shotDate;
    }
    public Film getFilm() {
        return film;
    }
    public void setFilm(Film film) {
        this.film = film;
    }
    public Lens getLens() {
        return lens;
    }
    public void setLens(Lens lens) {
        this.lens = lens;
    }
    public Filter getFilter() {
        return filter;
    }
    public void setFilter(Filter filter) {
        this.filter = filter;
    }
    public Camera getCamera() {
        return camera;
    }
    public void setCamera(Camera camera) {
        this.camera = camera;
    }
    public Meter getMeter() {
        return meter;
    }
    public void setMeter(Meter meter) {
        this.meter = meter;
    }
    public double getAperture() {
        return aperture;
    }
    public void setAperture(double aperture) {
        this.aperture = aperture;
    }
    public int getBellowsExtension() {
        return bellowsExtension;
    }
    public void setBellowsExtension(int bellowsExtension) {
        this.bellowsExtension = bellowsExtension;
    }
    public double getMeterRead() {
        return meterRead;
    }
    public void setMeterRead(double meterRead) {
        this.meterRead = meterRead;
    }
    public double getFf() {
        return ff;
    }
    private void setFf(double ff) {
        this.ff = ff;
    }
    public double getBf() {
        return bf;
    }
    private void setBf(double bf) {
        this.bf = bf;
    }
    public double getRc() {
        return rc;
    }
    private void setRc(double rc) {
        this.rc = rc;
    }

    public String getShutter() {
        shutter = calcShutter();
        return pretty(shutter);
    }
    private void setShutter(double shutter) {
        this.shutter = shutter;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
}
