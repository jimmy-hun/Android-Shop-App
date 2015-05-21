package Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Item implements Parcelable {
    // Database constants
    public static final String TABLE_NAME = "items";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PRICE = "price";
    public static final String CREATE_STATEMENT =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    COLUMN_NAME + " TEXT NOT NULL, " +
                    COLUMN_PRICE + " INTEGER NOT NULL, " +
                    ")";
    // Properties
    private long id;
    private String name;
    private double price;

    // Static method to create Parcelable object (required)
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };
    // Default constructor with required params
    public Item(long id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
    public Item(String name, double price) {
        this.name = name;
        this.price = price;
    }
    // Parcel constructor, reads in values in the order they were written
    public Item(Parcel in) {
        this.id = in.readLong();
        this.name = in.readString();
        this.price = in.readDouble();
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
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public String toString() {
        return name;
    }
    @Override
    public int describeContents() {
        return 0;
    }
    // Method to write values to parcel in a specific order
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(name);
        parcel.writeDouble(price);
    }
}