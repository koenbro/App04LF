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
public class FFPairAdapter extends ArrayAdapter<FFPair>{

    private final Context context;
    private ArrayList<FFPair> camerasArrayList;

    public FFPairAdapter(Context context, ArrayList<FFPair> camerasArrayList) {
        super(context, R.layout.row, camerasArrayList);
        this.context = context;
        this.camerasArrayList = camerasArrayList;
    }

    public View getView (int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater)context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row, parent, false);

        TextView labelView = (TextView)rowView.findViewById(R.id.label);
        //labelView.setText(camerasArrayList.get(position));

        TextView valueView = (TextView)rowView.findViewById(R.id.value);
//        String valueText =
//                " film_id: " + camerasArrayList.get(position).getFilmId() +
//                        " filter_id: " + camerasArrayList.get(position).getFilmId();
        //valueView.setText(valueText);

        return (rowView);
    }
}

