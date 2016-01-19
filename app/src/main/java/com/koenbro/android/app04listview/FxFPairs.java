package com.koenbro.android.app04listview;

import java.util.ArrayList;

/**
 * @author laszlo
 * @date 1/17/16.
 */
public class FxFPairs {
    FilmDBAdapter filmDB;
    FilterDBAdapter filterDB;
    FxFDBAdapter fxfDB;
    ArrayList<Film> allFilms;
    ArrayList<Filter> allFilters;
    ArrayList<FxF> allPairs;
    private static final String[] filmType = {"bw", "color", "IR"};


    public FxFPairs() {
        allFilms = getAllFilms();
        allFilters = getAllFilters();
        allPairs=getAllPairs();
    }

    private ArrayList<Film> getAllFilms(){
        filmDB = new FilmDBAdapter(ApplicationContextProvider.getContext());
        filmDB.open();
        allFilms = filmDB.getAllFilms();
        filmDB.close();
        return allFilms;
    }

    private ArrayList<Filter> getAllFilters(){
        filterDB = new FilterDBAdapter(ApplicationContextProvider.getContext());
        filterDB.open();
        allFilters = filterDB.getAllFilters();
        filterDB.close();
        return allFilters;
    }

    private ArrayList<FxF> getAllPairs(){
        fxfDB = new FxFDBAdapter(ApplicationContextProvider.getContext());
        fxfDB.open();
        allPairs = fxfDB.getAllFxF();
        fxfDB.close();
        return allPairs;
    }


    public void matchAll(){
        for (int i=0;i<allFilms.size();i++){
            for (int j=0;j<allFilters.size();j++){
                Film iteratedFilm=allFilms.get(i);
                Filter iteratedFilter=allFilters.get(j);
                if (filmMatchesFilter(iteratedFilm,iteratedFilter)){
                    if (isFilmFilterPairNew((int)iteratedFilm.getId(),(int)iteratedFilter.getId())){
                        FxF newPair = createNewPair(iteratedFilm,iteratedFilter);
                        savePair(newPair);
                    }
                }
            }
        }
    }

    private void savePair(FxF newPair) {
        fxfDB = new FxFDBAdapter(ApplicationContextProvider.getContext());
        fxfDB.open();
        fxfDB.addFxF(newPair);
        fxfDB.close();
    }

    private FxF createNewPair(Film iteratedFilm, Filter iteratedFilter) {
        FxF newPair = new FxF();
        newPair.setFilmId(iteratedFilm.getId());
        newPair.setFilmName(iteratedFilm.getFilmName());
        newPair.setFilmType(iteratedFilm.getFilmType());
        newPair.setFilterId(iteratedFilter.getId());
        newPair.setFilterName(iteratedFilter.getFilterName());
        newPair.setFactor(getFFforFilm(iteratedFilm, iteratedFilter));
        newPair.setSpecific(false);
        return newPair;
    }

    private double getFFforFilm(Film iteratedFilm, Filter iteratedFilter) {
        double ff =1;
        if (iteratedFilm.getFilmType().matches("bw")) {
            ff=iteratedFilter.getFilterFactorBW();
        }
        else if (iteratedFilm.getFilmType().matches("color")) {
            ff=iteratedFilter.getFilterFactorColor();
        }
        return ff;
    }

    private boolean filmMatchesFilter(Film iteratedFilm, Filter iteratedFilter) {
        Boolean match = false;
        if (iteratedFilm.getFilmType().matches("bw") && iteratedFilter.isFilterForBW()){
            match=true;
        }
        else if (iteratedFilm.getFilmType().matches("color") && iteratedFilter.isFilterForColor()) {
            match=true;
        }
        return match;
    }



    private boolean isFilmFilterPairNew(int filmID, int filterID) {
        boolean answer = true;
        for (int i = 0; i < allPairs.size(); i++) {
            if (allPairs.get(i).getFilmId() == filmID & allPairs.get(i).getFilterId() == filterID) {
                answer = false;
            }
        }
        return answer;
    }

}
