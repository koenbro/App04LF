package com.koenbro.android.app04listview;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by laszlo on 10/31/14.
 */
public class Shot {
    private long id;
    private String shotDay;
    private String shotTime;
    private long photoShootId;
    private double aperture;
    private int bellowsExtension;
    private double meterRead;
    private double ff;
    private double bf;
    private double rc;
    private double shutter;
    private String prettyShutter;
    private String comment;
    private double latitude;
    private double longitude;

    private String filmName;
    private int filmEi;
    private String lensName;
    private int lensFocal;
    private String filterName;
    private String cameraName;
    private String meterName;

    //Gear objects
    private Film film;
    private Lens lens;
    private Filter filter;
    private Camera camera;
    private Meter meter;

    //reference values for light meters
    private final double tMeterRef = 15;
    private final double fMeterRef = 8;
    private final double evMeterRef = 10;
    private final double isoMeterRef = 100;
    //TODO get these from db


    Shot() {}
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
        setFilmName(film.getFilmName());
        setFilmEi(film.getFilmEi());
        setLensName(lens.getLensName());
        setLensFocal(lens.getLensFocal());
        setFilterName(filter.getFilterName());
        setCameraName(camera.getCameraName());
        setMeterName(meter.getMeterName());
        setShutter(calcShutter(aperture));
        setPrettyShutter(pretty(shutter));
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
    private double calcShutter(double aperture) {
        double mc = Math.pow(2.0, evMeterRef - meterRead); //meter
        bf = Math.pow((double) bellowsExtension / (double) lens.getLensFocal(), 2.0);
        setBf(bf);
        double ac = Math.pow(aperture / fMeterRef, 2.0); //aperture
        double sc = isoMeterRef / film.getFilmEi(); //film sensitivity
        ff = calcFf(film, filter);
        shutter = (1 / tMeterRef) * mc * bf * ac * sc * ff; //pre-reciprocity
        shutter = shutter * calcRc(shutter); //post-reciprocity correction
        return shutter;
    }
    private String pretty(double shutter) {
        String pretty;
        NumberFormat round = new DecimalFormat("#0");
        NumberFormat twoDec = new DecimalFormat("#0.00");
        //TODO maybe just cast as integer; or round; or set to nearest
        //need to handle fractions as inverse 1/T
        if (shutter <1){
            shutter = 1/shutter;
            pretty = "1/" + round.format(shutter);
            if (shutter < 8) {  //show more details
                pretty = pretty +  " (" + twoDec.format(1/shutter) + "s)";
            }
        } else {
            pretty = round.format(shutter)+"s";
        }
        return pretty;
    }

    public String[] getOneThirdDownUp() {
        String[] adjusted = new String[2];
        double down = calcShutter(aperture / Math.sqrt(Math.pow(2, 0.3333)));
        double up = calcShutter(aperture * Math.sqrt(Math.pow(2, 0.3333)));
        adjusted[0] = pretty(down);
        adjusted[1] = pretty(up);
        return adjusted;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getShotDay() {
        return shotDay;
    }
    public void setShotDay(String shotDay) {
        this.shotDay = shotDay;
    }
    public String getShotTime() {
        return shotTime;
    }
    public void setShotTime(String shotTime) {
        this.shotTime = shotTime;
    }
    public long getPhotoShootId() {
        return photoShootId;
    }
    public void setPhotoShootId(long photoShootId) {
        this.photoShootId = photoShootId;
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
    public void setFf(double ff) {
        this.ff = ff;
    }
    public double getBf() {
        return bf;
    }
    public void setBf(double bf) {
        this.bf = bf;
    }
    public double getRc() {
        return rc;
    }
    public void setRc(double rc) {
        this.rc = rc;
    }
    public double getShutter() {
        return(shutter);
    }
    public void setShutter(double shutter) {
        this.shutter = shutter;
    }
    public String getPrettyShutter(){
        return prettyShutter;
    }
    public void setPrettyShutter(String prettyShutter) {
        this.prettyShutter = prettyShutter;
    }

    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }

    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getFilmName() {
        return filmName;
    }
    public int getFilmEi() {
        return filmEi;
    }
    public String getLensName() {
        return lensName;
    }
    public int getLensFocal() {
        return lensFocal;
    }
    public String getFilterName() {
        return filterName;
    }
    public String getCameraName() {
        return cameraName;
    }
    public String getMeterName() {
        return meterName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }
    public void setFilmEi(int filmEi) {
        this.filmEi = filmEi;
    }
    public void setLensName(String lensName) {
        this.lensName = lensName;
    }
    public void setLensFocal(int lensFocal) {
        this.lensFocal = lensFocal;
    }
    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }
    public void setCameraName(String cameraName) {
        this.cameraName = cameraName;
    }
    public void setMeterName(String meterName) {
        this.meterName = meterName;
    }

    public double gettMeterRef() {
        return tMeterRef;
    }
    public double getfMeterRef() {
        return fMeterRef;
    }
    public double getEvMeterRef() {
        return evMeterRef;
    }
    public double getIsoMeterRef() {
        return isoMeterRef;
    }

    @Override
    public String toString() {
        return "Shot [id=" + id + ", film name=" + filmName  + "]";
    }

}
