package com.koenbro.android.app04listview;

/**
 * Created by laszlo on 10/24/14.
 */
public class Filter {
    private long id;
    private String filterName;
    private boolean filterForBW;
    private boolean filterForColor;
    private float filterFactorBW;
    private float filterFactorColor;

    public Filter() {}

    public Filter(String filterName, boolean filterForBW, boolean filterForColor,
                  float filterFactorBW, float filterFactorColor) {
        this.filterName = filterName;
        this.filterForBW = filterForBW;
        this.filterForColor = filterForColor;
        this.filterFactorBW = filterFactorBW;
        this.filterFactorColor = filterFactorColor;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFilterName() {
        return filterName;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    public boolean isFilterForBW() {
        return filterForBW;
    }

    public void setFilterForBW(boolean filterForBW) {
        this.filterForBW = filterForBW;
    }

    public boolean isFilterForColor() {
        return filterForColor;
    }

    public void setFilterForColor(boolean filterForColor) {
        this.filterForColor = filterForColor;
    }

    public float getFilterFactorBW() {
        return filterFactorBW;
    }

    public void setFilterFactorBW(float filterFactorBW) {
        this.filterFactorBW = filterFactorBW;
    }

    public float getFilterFactorColor() {
        return filterFactorColor;
    }

    public void setFilterFactorColor(float filterFactorColor) {
        this.filterFactorColor = filterFactorColor;
    }

    @Override
    public String toString() {
        return "Filter [id=" + id + ", name=" + filterName  + "]";
    }

}
