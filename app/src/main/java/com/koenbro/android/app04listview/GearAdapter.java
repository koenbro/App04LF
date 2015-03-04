package com.koenbro.android.app04listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * @author laszlo
 * @date 3/2/15.
 */
public class GearAdapter extends ArrayAdapter {
    private final Context context;
    private ArrayList gearArrayList;

    public GearAdapter(Context context, ArrayList gearArrayList) {
        super(context, R.layout.row, gearArrayList);
        this.context = context;
        this.gearArrayList = gearArrayList;
    }
    public View getView (int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater)context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row, parent, false);
        TextView labelView = (TextView)rowView.findViewById(R.id.label);
        TextView valueView = (TextView)rowView.findViewById(R.id.value);
        labelView.setText(gearArrayList.get(position).toString());
        valueView.setText(gearArrayList.get(position).toString());

        return (rowView);
    }


}

/*public CameraAdapter(Context context, ArrayList<Camera> camerasArrayList) {
        super(context, R.layout.row, camerasArrayList);
        this.context = context;
        this.camerasArrayList = camerasArrayList;
    }

    public View getView (int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater)context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row, parent, false);

        TextView labelView = (TextView)rowView.findViewById(R.id.label);

        labelView.setText(camerasArrayList.get(position).getCameraName());


        String valueText =
                " bellows_max: " + camerasArrayList.get(position).getBellowsMax() +
                        " -- id: " + String.valueOf(camerasArrayList.get(position).getId());
        valueView.setText(valueText);

        return (rowView);
    }*/
