package com.koenbro.android.app04listview;

/**
 * Created by laszlo on 2/20/15.
 */
public class FxF {
    private long id;
    private long filmId;
    private String filmName;
    private String filmType;
    private long filterId;
    private String filterName;
    private double factor;
    private boolean specific;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getFilmId() {
        return filmId;
    }

    public void setFilmId(long filmId) {
        this.filmId = filmId;
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

    public String getFilterName() {
        return filterName;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    public long getFilterId() {
        return filterId;
    }

    public void setFilterId(long filterId) {
        this.filterId = filterId;
    }

    public double getFactor() {
        return factor;
    }

    public void setFactor(double factor) {
        this.factor = factor;
    }

    public boolean isSpecific() {
        return specific;
    }

    public void setSpecific(boolean specific) {
        this.specific = specific;
    }

    @Override
    public String toString() {
        return "FFPair{" +
                "filmId=" + filmId +
                ", filterId=" + filterId +
                ", factor=" + factor +
                '}';
    }
}
