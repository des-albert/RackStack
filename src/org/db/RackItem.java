package org.db;

import java.io.Serializable;

public class RackItem implements Serializable {
    private final int id;
    private final String parentId;
    private String itemName;
    private String itemColor;
    private final int itemHeight;
    private int itemIndex;

    RackItem (String parent, String name, String color, int height) {
        id = hashCode();
        parentId = parent;
        itemName = name;
        itemColor = color;
        itemHeight = height;
        itemIndex = -1;
    }

    RackItem(Part part) {
        id = hashCode();
        parentId = null;
        itemName = part.getItemName();
        itemColor = part.getItemColor();
        itemHeight = part.getItemHeight();
    }

    public int getId() { return id; }
    public String getParentId() { return parentId; }
    public String getItemName () { return itemName; }
    public String getItemColor() { return itemColor; }
    public int getItemHeight() { return itemHeight; }
    public int getItemIndex() { return itemIndex; }

    void setItemName(String name) { itemName = name; }
    void setItemColor(String color) { itemColor = color; }
    void setItemIndex(int index) { itemIndex = index; }

}
