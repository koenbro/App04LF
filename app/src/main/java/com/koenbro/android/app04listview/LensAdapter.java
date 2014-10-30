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
public class LensAdapter extends ArrayAdapter<Lens> {

    private final Context context;
    private final ArrayList<Lens> lensesArrayList;

    public LensAdapter(Context context, ArrayList<Lens> lensesArrayList) {
        super(context, R.layout.row, lensesArrayList);
        this.context = context;
        this.lensesArrayList = lensesArrayList;
    }

    public View getView (int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater)context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row, parent, false);
        TextView labelView = (TextView)rowView.findViewById(R.id.label);
        TextView valueView = (TextView)rowView.findViewById(R.id.value);
        labelView.setText(lensesArrayList.get(position).getLensName());
        String valueText =
                " focal: " + lensesArrayList.get(position).getLensFocal() +
                        " -- id: " + String.valueOf(lensesArrayList.get(position).getId());
        valueView.setText(valueText);
        return (rowView);
    }
}
