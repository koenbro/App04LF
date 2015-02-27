package com.koenbro.android.app04listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * Created by laszlo on 10/24/14.
 */
public class ShotAdapter extends ArrayAdapter<Shot> {
    private final Context context;
    private final ArrayList<Shot> ShotsArrayList;

    public ShotAdapter(Context context, ArrayList<Shot> ShotsArrayList) {
        super(context, R.layout.row, ShotsArrayList);
        this.context = context;
        this.ShotsArrayList = ShotsArrayList;
    }

    public View getView (int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater)context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row, parent, false);
        TextView labelView = (TextView)rowView.findViewById(R.id.label);
        TextView valueView = (TextView)rowView.findViewById(R.id.value);
        String labelText = String.valueOf(ShotsArrayList.get(position).getId()) +
                ". Film: " + ShotsArrayList.get(position).getFilmName() +
                ", EI:" + String.valueOf(ShotsArrayList.get(position).getFilmEi()) +
                "";
        labelView.setText(labelText);
        NumberFormat oneDec = new DecimalFormat("#0.0");
        String valueText =
                "Lens: " + ShotsArrayList.get(position).getLensName() +
                "; f/" + String.valueOf(oneDec.format(ShotsArrayList.get(position).getAperture())) +
                "; " + ShotsArrayList.get(position).getPrettyShutter();

        valueView.setText(valueText);
        return (rowView);
    }
}
