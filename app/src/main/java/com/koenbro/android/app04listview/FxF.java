package com.koenbro.android.app04listview;

/**
 * Created by laszlo on 2/20/15.
 */
public class FxF {
    private long id;
    private long filmId;
    private long filterId;
    private double factor;

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

    @Override
    public String toString() {
        return "FFPair{" +
                "filmId=" + filmId +
                ", filterId=" + filterId +
                ", factor=" + factor +
                '}';
    }
}
