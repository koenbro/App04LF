package com.koenbro.android.app04listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by laszlo on 10/24/14.
 */
public class MeterAdapter extends ArrayAdapter<Meter> {

    private final Context context;
    private final ArrayList<Meter> metersArrayList;

    public MeterAdapter(Context context, ArrayList<Meter> metersArrayList) {
        super(context, R.layout.row, metersArrayList);
        this.context = context;
        this.metersArrayList = metersArrayList;
    }

    public View getView (int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater)context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row, parent, false);
        TextView labelView = (TextView)rowView.findViewById(R.id.label);
        TextView valueView = (TextView)rowView.findViewById(R.id.value);
        labelView.setText(metersArrayList.get(position).getMeterName());
        String valueText =
                " result format: " + metersArrayList.get(position).getMeterResultFormat() +
                        " -- id: " + String.valueOf(metersArrayList.get(position).getId());
        valueView.setText(valueText);
        return (rowView);
    }
}
