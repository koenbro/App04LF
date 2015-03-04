package com.koenbro.android.app04listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * List all filters that match (can be used with) a given film.
 * Film - filters pair are created depending of the type of film, i.e. BW vs color. IR film plays
 * no role in this.
 * @author laszlo
 * @date 2/28/15.
 */
public class FilmxFilterAdapter extends GearAdapter {
    private final Context context;
    private final ArrayList<Filter> filtersArrayList;
    private final Film film;

    public FilmxFilterAdapter(Context context, Film film, ArrayList<Filter> filtersArrayList) {
        super(context, filtersArrayList);
        this.context = context;
        this.filtersArrayList = filtersArrayList;
        this.film = film;
    }
    public View getView (int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater)context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row, parent, false);
        TextView labelView = (TextView)rowView.findViewById(R.id.label);
        TextView valueView = (TextView)rowView.findViewById(R.id.value);

        labelView.setText(filtersArrayList.get(position).getFilterName());
        String valueText =
                " film name: " + film.getFilmName() +
                " -- filter factor: " + String.valueOf(1.0);
        valueView.setText(valueText);
        return (rowView);
    }
}
