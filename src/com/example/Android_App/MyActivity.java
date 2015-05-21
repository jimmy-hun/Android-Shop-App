package com.example.Android_App;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import Models.Item;
import Models.Cart;
import java.util.ArrayList;
import java.util.List;

public class MyActivity extends Activity {
    public static final int ADD_ITEM_REQUEST = 1;
    private ListView cartList;
    private ArrayList<Item> cartItems;
    private Cart defaultCart;
    private DatabaseHelper dbHelper;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
// Get our database helper
        dbHelper = new DatabaseHelper(getApplicationContext());
// Check if any items exist, if not create dummy data
        if (dbHelper.getAllItems().size() == 0) {
// Create dummy data for our monsters
            Item i1 = new Item("Orange", 10.00);
            Item i2 = new Item("Apple", 8.50);
            Item i3 = new Item("Pear", 5.00);
            Item i4 = new Item("Grape", 12.50);
            Item i5 = new Item("Banana", 10.00);
// Add them to the database
            dbHelper.addItem(i1);
            dbHelper.addItem(i2);
            dbHelper.addItem(i3);
            dbHelper.addItem(i4);
            dbHelper.addItem(i5);
// And add a default cart (which we will use for our main ListView)
            Cart c = new Cart("Default cart");
            dbHelper.addCart(c);
        }
// Get the ListView associated with layout
        cartList = (ListView)findViewById(R.id.cartList);
// Create our adapter and associate ArrayList
        defaultCart = dbHelper.getDefaultCart();
        cartItems = dbHelper.getItemsFromCart(defaultCart);
        ItemAdapter adapter = new ItemAdapter(this, cartItems);
// Associate adapter with ListView
        cartList.setAdapter(adapter);
// Show dialog when holding list item to remove it
        cartList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {
// Build dialog to delete item
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Remove item?");
                builder.setMessage("Are you sure you wish to remove this item from the cart?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Remove item from Database
                        Item it = cartItems.get(position);
                        dbHelper.removeItemFromCart(defaultCart, it);
                        // Update ListView
                        refreshListView();
                        Toast.makeText(getBaseContext(), "Item has been removed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Exit", new
                        DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // Close the dialog
                                dialogInterface.cancel();
                            }
                        });
                // Create and show dialog
                builder.create().show();
                return false;
            }
        });
        // Update item count
        updateItemCount();
    }
    // Creates menu items for ActionBar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    // ActionBar handler for selected items
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                // Move to AddMonsterActivity and await result
                Intent i = new Intent(this, AddItemActivity.class);
                startActivityForResult(i, ADD_ITEM_REQUEST);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    // We override this method when we are expecting a result from an
    // activity we have called.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_ITEM_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
                boolean canAddMonster = true;
// Grab the Monster object out of the intent
                Item i = data.getParcelableExtra("result");
// Check if monster already exists, don't add existing monster
                for (Item existingMonster : cartItems) {
                    if (i.getId() == existingMonster.getId()) {
                        Toast.makeText(this, "Monster already exists in party.",
                                Toast.LENGTH_LONG).show();
                        canAddMonster = false;
                    };
                }
// Add monster to database and update list
                if (canAddMonster) {
                    dbHelper.addItemToCart(defaultCart, i);
                    refreshListView();
                    Toast.makeText(this, "Added item to list.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    private void updateItemCount() {
    // Get total number of monsters
    int totalMonsters = cartItems.size();
    // Update TextView
    TextView monsterCountText = (TextView)findViewById(R.id.priceCountText);
    monsterCountText.setText("Total Monsters: " + totalMonsters);
    }
    private void refreshListView() {
// Get current monsters, update ListView then monster count
        cartItems = dbHelper.getItemsFromCart(defaultCart);
        cartList.setAdapter(new ItemAdapter(this, cartItems));
        updateItemCount();
    }
}