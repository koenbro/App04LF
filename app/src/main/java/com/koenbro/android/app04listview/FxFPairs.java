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
    String filmType;
    //private static final String[] filmType = {"bw", "color", "IR"};


    public FxFPairs() {
        allFilms = getAllFilms();
        allFilters = getAllFilters();
        allPairs = getAllPairs();
    }

    public void generateAllPairs(){
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

    public Film getFilmByID (ArrayList<Film> itemList, int id){
        Film item = new Film();
        for (int i=0;i<itemList.size();i++) {
            if ((int)itemList.get(i).getId() == id) {
                item = itemList.get(i);
            }
        }
        return item;
    }
    public Filter getFilterByID (ArrayList<Filter> itemList, int id){
        Filter item = new Filter();
        for (int i=0;i<itemList.size();i++) {
            if ((int)itemList.get(i).getId() == id) {
                item = itemList.get(i);
            }
        }
        return item;
    }

    public ArrayList<Filter> filtersMatchingFilm(int filmID){
        filmType = getFilmByID(allFilms, filmID).getFilmType();
        ArrayList<Filter> filtersMatchingFilm = new ArrayList<Filter>();
        for (int i=0; i<allFilters.size();i++) {
            if(filmType.matches("bw") & allFilters.get(i).isFilterForBW()) {
                filtersMatchingFilm.add(allFilters.get(i));
            }
            if (filmType.matches("color") & allFilters.get(i).isFilterForColor()){
                filtersMatchingFilm.add(allFilters.get(i));
            }
        }
        return filtersMatchingFilm;
    }

    private void savePair(FxF newPair) {
        fxfDB = new FxFDBAdapter(ApplicationContextProvider.getContext());
        fxfDB.open();
        fxfDB.addFxF(newPair);
        fxfDB.close();
    }

    private FxF createNewPair(Film film, Filter filter) {
        FxF newPair = new FxF();
        newPair.setFilmId(film.getId());
        newPair.setFilmName(film.getFilmName());
        newPair.setFilmType(film.getFilmType());
        newPair.setFilterId(filter.getId());
        newPair.setFilterName(filter.getFilterName());
        newPair.setFactor(getGenericFFforFilm(film, filter));
        newPair.setSpecific(false);
        return newPair;
    }

    private double getGenericFFforFilm(Film iteratedFilm, Filter iteratedFilter) {
        double ff = 1;
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

    public double getFF(Film film, Filter filter) {
        double ff = 1;
        for (int i=0;i<allPairs.size();i++ ) {
            if ((int)film.getId()== allPairs.get(i).getFilmId()  &
                    (int)filter.getId() == allPairs.get(i).getFilterId()   ){
                ff= allPairs.get(i).getFactor();
            }
        }
        return ff;
    }
}
