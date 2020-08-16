package org.db;

public class RackTotal {

    private int itemTotal;
    private String itemColor;

    RackTotal(String color) {
        itemTotal = 0;
        itemColor = color;

    }
    public int getItemTotal() { return itemTotal; }
    public String getItemColor() { return itemColor; }
    void increment(int add) { itemTotal += add; }
    void decrement(int minus ) { itemTotal -= minus; }
    void setItemTotal(int total) { itemTotal = total; }
    void setItemColor(String color) { itemColor = color; }
}
