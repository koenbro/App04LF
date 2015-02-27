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
public class CameraAdapter extends ArrayAdapter<Camera>{

    private final Context context;
    private ArrayList<Camera> camerasArrayList;

    public CameraAdapter(Context context, ArrayList<Camera> camerasArrayList) {
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

        TextView valueView = (TextView)rowView.findViewById(R.id.value);
        String valueText =
                " bellows_max: " + camerasArrayList.get(position).getBellowsMax() +
                        " -- id: " + String.valueOf(camerasArrayList.get(position).getId());
        valueView.setText(valueText);

        return (rowView);
    }
}

