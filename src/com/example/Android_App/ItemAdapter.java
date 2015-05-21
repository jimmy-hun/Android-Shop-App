package com.example.Android_App;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import Models.Item;
import java.util.ArrayList;

public class ItemAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Item> items;

    public ItemAdapter(Context context, ArrayList<Item> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Item getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        // Check if the view has been created for the row. If not, lets inflate it
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.main, null); // List layout here
        }

        // Grab the TextViews in our custom layout
        TextView itemName = (TextView)view.findViewById(R.id.itemNameText);
        TextView itemPrice = (TextView)view.findViewById(R.id.PriceNameText);

        // Assign values to the TextViews using the Monster object
        itemName.setText(items.get(i).getName());
        itemPrice.setText("Price: " + items.get(i).getPrice());

        // Return the completed View of the row being processed
        return view;
    }
}
