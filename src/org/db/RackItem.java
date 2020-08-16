package org.db;

import java.io.Serializable;

public class RackItem implements Serializable {
    private final int id;
    private final String parentId;
    private String itemName;
    private String itemColor;
    private int itemHeight;
    private int itemIndex;

    RackItem (String parent, String name, String color, int height) {
        id = hashCode();
        parentId = parent;
        itemName = name;
        itemColor = color;
        itemHeight = height;
        itemIndex = -1;
    }


    public int getId() { return id; }
    public String getParentId() { return parentId; }
    public String getItemName () { return itemName; }
    public String getItemColor() { return itemColor; }
    public int getItemHeight() { return itemHeight; }
    public int getItemIndex() { return itemIndex; }

    void setItemName(String name) { itemName = name; }
    void setItemColor(String color) { itemColor = color; }
    void setItemHeight(int height) { itemHeight = height; }
    void setItemIndex(int index) { itemIndex = index; }

}
