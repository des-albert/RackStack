package org.db;

public class RackTotal {

    private int itemTotal;
    private final String itemColor;

    RackTotal(String color) {
        itemTotal = 0;
        itemColor = color;

    }
    public int getItemTotal() { return itemTotal; }
    public String getItemColor() { return itemColor; }
    void increment(int add) { itemTotal += add; }
    void decrement() { itemTotal -= 1; }

}
