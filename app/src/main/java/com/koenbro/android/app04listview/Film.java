package com.koenbro.android.app04listview;

/**
 * Created by laszlo on 10/21/14.
 */
public class Film {
    private long id;
    private String filmName;
    private String filmType;
    private int filmEi;
    private double filmRc1;
    private double filmRc2;
    private double filmRc3;
    private double filmRc4;
    private double filmRc5;
    private double filmRc6;
    private double filmRc7;

    public Film() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public String getFilmType() {
        return filmType;
    }

    public void setFilmType(String filmType) {
        this.filmType = filmType;
    }

    public int getFilmEi() {
        return filmEi;
    }

    public void setFilmEi(int filmEi) {
        this.filmEi = filmEi;
    }

    public double getFilmRc1() {
        return filmRc1;
    }

    public void setFilmRc1(double filmRc1) {
        this.filmRc1 = filmRc1;
    }

    public double getFilmRc2() {
        return filmRc2;
    }

    public void setFilmRc2(double filmRc2) {
        this.filmRc2 = filmRc2;
    }

    public double getFilmRc3() {
        return filmRc3;
    }

    public void setFilmRc3(double filmRc3) {
        this.filmRc3 = filmRc3;
    }

    public double getFilmRc4() {
        return filmRc4;
    }

    public void setFilmRc4(double filmRc4) {
        this.filmRc4 = filmRc4;
    }

    public double getFilmRc5() {
        return filmRc5;
    }

    public void setFilmRc5(double filmRc5) {
        this.filmRc5 = filmRc5;
    }

    public double getFilmRc6() {
        return filmRc6;
    }

    public void setFilmRc6(double filmRc6) {
        this.filmRc6 = filmRc6;
    }

    public double getFilmRc7() {
        return filmRc7;
    }

    public void setFilmRc7(double filmRc7) {
        this.filmRc7 = filmRc7;
    }

    @Override
    public String toString() {
        return "Film [id=" + id + ", name=" + filmName + ", ei=" +
                filmEi + "]";
    }
}
