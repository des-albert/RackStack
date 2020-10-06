package org.db;

import java.util.ArrayList;

public class Part {

  private final String itemName;
  private final String itemColor;
  private final int itemHeight;
  private final ArrayList<PartBom> bomItems;

  Part (String name, String color, int height) {
    itemName = name;
    itemColor = color;
    itemHeight = height;
    bomItems = new ArrayList<>();
  }

  public String getItemName () { return itemName; }
  public String getItemColor() { return itemColor; }
  public int getItemHeight() { return itemHeight; }
  public ArrayList<PartBom> getParts() { return bomItems;}

  void setBomItems(PartBom bom) { bomItems.add(bom); }


}
