package com.example.gestiondetaches;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomSpinnerAdapter extends BaseAdapter {

    private Context context;
    private String[] items;
    private int[] colors; // array of drawable resource ids

    public CustomSpinnerAdapter(Context context, String[] items, int[] colors) {
        this.context = context;
        this.items = items;
        this.colors = colors;
    }

    @Override
    public int getCount() {
        return items.length;
    }

    @Override
    public Object getItem(int position) {
        return items[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.custom_spinner_item, parent, false);
        }

        TextView textView = convertView.findViewById(R.id.text1);
        ImageView imageView = convertView.findViewById(R.id.color_circle);

        textView.setText(items[position]);
        imageView.setBackgroundResource(colors[position]);

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }
}
