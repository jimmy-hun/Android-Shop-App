package Models;
import Models.Cart;
import Models.Item;

public class ItemCart {
    // Database constants
    public static final String TABLE_NAME = "item_carts";
    public static final String COLUMN_ITEM_ID = "item_id";
    public static final String COLUMN_CART_ID = "cart_id";
    // PRIMARY KEY has own declaration for multiple columns
    public static final String CREATE_STATEMENT =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    COLUMN_ITEM_ID + " INTEGER NOT NULL, " +
                    COLUMN_CART_ID + " INTEGER NOT NULL, " +
                    "PRIMARY KEY (" + COLUMN_ITEM_ID + ", " + COLUMN_CART_ID +
                    "))";
    // Properties
    private Item item;
    private Cart cart;
    public ItemCart(Item item, Cart cart) {
        this.item = item;
        this.cart = cart;
    }
    public Item getItem() {
        return item;
    }
    public void setItem(Item item) {
        this.item = item;
    }
    public Cart getCart() {
        return cart;
    }
    public void setCart(Cart cart) {
        this.cart = cart;
    }
}