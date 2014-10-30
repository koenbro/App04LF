package com.koenbro.android.app04listview;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;
/**
 * Created by laszlo on 10/21/14. http://hmkcode.com/android-simple-sqlite-database-tutorial/
 */
public class FilmAdapter extends ArrayAdapter<Film>{

    private final Context context;
    private final ArrayList<Film> filmsArrayList;

    public FilmAdapter(Context context, ArrayList<Film> filmsArrayList) {
        super(context, R.layout.row, filmsArrayList);
        this.context = context;
        this.filmsArrayList = filmsArrayList;
    }

    public View getView (int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater)context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row, parent, false);
        TextView labelView = (TextView)rowView.findViewById(R.id.label);
        TextView valueView = (TextView)rowView.findViewById(R.id.value);
        labelView.setText(filmsArrayList.get(position).getFilmName());
        String valueText =
                " EI: " + filmsArrayList.get(position).getFilmEi() +
                " -- id: " + String.valueOf(filmsArrayList.get(position).getId());
        valueView.setText(valueText);
        return (rowView);
    }
}
