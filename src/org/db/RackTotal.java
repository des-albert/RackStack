package org.db;

public class RackTotal {

    private String itemName;
    private int itemTotal;
    private String itemColor;

    RackTotal(String color) {
        itemTotal = 0;
        itemColor = color;

    }
    public int getItemTotal() { return itemTotal; }
    public String getItemColor() { return itemColor; }
    void increment(int add) { itemTotal += add; }
    void decrement() { itemTotal -= 1; }
    void setItemName(String name) { itemName = name; }
    void setItemTotal(int total) { itemTotal = total; }
    void setItemColor(String color) { itemColor = color; }
}
