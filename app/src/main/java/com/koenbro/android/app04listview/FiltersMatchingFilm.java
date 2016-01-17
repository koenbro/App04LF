package com.koenbro.android.app04listview;

import java.util.ArrayList;

/**
 * A subset of filters that match a certain film. There are 3 types of film: color, b&w, IR. Some
 * filters are only used for color (e.g. KR 1, KR 3), others only for b&w (#11, #16) and some can be
 * used for both (CP, ND). IR film use IR filters that have no use with other films.
 *
 * @author laszlo
 * @date 1/9/16.
 */
public class FiltersMatchingFilm {
    private Gear gear;
    ArrayList<Filter> allFilters;
    ArrayList<Filter> matchingFilters;
    ArrayList<Film> allFilms;
    String filmType;

    public FiltersMatchingFilm() {
        gear = new Gear();
        allFilters = gear.getAllFilters();
        allFilms = gear.getAllFilms();
        matchingFilters = new ArrayList<Filter>();
    }

    public ArrayList<Filter> getMatchingFilters(int filmID) {
        return selectFilters(filmID);
    }

    private String getFilmType(int filmID, ArrayList<Film> allFilms) {
        int i = 0;
        while (filmID != allFilms.get(i).getId()) {
            i++;
        }
        return allFilms.get(i).getFilmType();
    }

    private ArrayList<Filter> selectFilters(int filmID) {
        filmType = getFilmType(filmID, allFilms);
        for (int i = 0; i < allFilters.size(); i++) {
            if (filmType.equalsIgnoreCase("bw") & allFilters.get(i).isFilterForBW()) {
                matchingFilters.add(allFilters.get(i));
            }
            if (filmType.equalsIgnoreCase("color") & allFilters.get(i).isFilterForColor()) {
                matchingFilters.add(allFilters.get(i));
            }
        }
        return matchingFilters;
    }





}
