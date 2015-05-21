package Models;

public class Cart {
    // Database constants
    public static final String TABLE_NAME = "carts";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String CREATE_STATEMENT =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    COLUMN_NAME + " TEXT NOT NULL" +
                    ")";
    // Properties
    private long id;
    private String name;
    // Constructor
    public Cart(long id, String name) {
        this.id = id;
        this.name = name;
    }
    public Cart(String name) {
        this.name = name;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}