package com.koenbro.android.app04listview;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by laszlo on 10/24/14.
 */
public class Filter {
    private long id;
    private String filterName;
    private boolean filterForBW;
    private boolean filterForColor;
    private double filterFactorBW;
    private double filterFactorColor;

    public Filter() {}

    public Filter(String filterName, boolean filterForBW, boolean filterForColor,
                  double filterFactorBW, double filterFactorColor) {
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

    public double getFilterFactorBW() {
        return filterFactorBW;
    }

    public void setFilterFactorBW(double filterFactorBW) {
        this.filterFactorBW = filterFactorBW;
    }

    public double getFilterFactorColor() {
        return filterFactorColor;
    }

    public void setFilterFactorColor(double filterFactorColor) {
        this.filterFactorColor = filterFactorColor;
    }

    @Override
    public String toString() {
        NumberFormat oneDec = new DecimalFormat("#0.0");
        return "Filter [" + filterName  + ", FFbw=" + oneDec.format(getFilterFactorBW()) +
                ", FFcolor=" + oneDec.format(getFilterFactorColor()) +
                ", id=" + id + "]";
    }
    public String toStringShort() {
        return  filterName  + " (id=" + id +")";
    }

}
