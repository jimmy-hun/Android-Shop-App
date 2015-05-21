package com.example.Android_App;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import Models.Item;
import java.util.ArrayList;

public class AddItemActivity extends Activity {
    ListView itemAddList;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        // Get our add monster ListView
        itemAddList = (ListView)findViewById(R.id.addItemList);
        // Create a MonsterAdapter and populate it with some default values
        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
        ArrayList<Item> items = new ArrayList<Item>(dbHelper.getAllItems().values());
        ItemAdapter adapter = new ItemAdapter(this, items);
        // Set adapter to ListView
        itemAddList.setAdapter(adapter);
        // Add a click listener for list items
        itemAddList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override

            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Grab the selected Item
                Item result = (Item)itemAddList.getAdapter().getItem(i);
                // Return the object to the MainActivity and close this activity
                Intent intent = new Intent();
                intent.putExtra("result", result);
                intent.putExtra("itemName", result.getName());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}