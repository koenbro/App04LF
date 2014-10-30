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
public class FilterAdapter extends ArrayAdapter<Filter>{

    private final Context context;
    private final ArrayList<Filter> filtersArrayList;

    public FilterAdapter(Context context, ArrayList<Filter> filtersArrayList) {
        super(context, R.layout.row, filtersArrayList);
        this.context = context;
        this.filtersArrayList = filtersArrayList;
    }

    public View getView (int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater)context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row, parent, false);
        TextView labelView = (TextView)rowView.findViewById(R.id.label);
        TextView valueView = (TextView)rowView.findViewById(R.id.value);
        labelView.setText(filtersArrayList.get(position).getFilterName());
        String valueText =
                " ff_bw: " + //filtersArrayList.get(position).getBellowsMax() +
                        " -- id: " + String.valueOf(filtersArrayList.get(position).getId());
        valueView.setText(valueText);
        return (rowView);
    }
}
