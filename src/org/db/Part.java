package org.db;

import java.util.ArrayList;
import java.util.List;

public class Part {

  private String itemName;
  private String itemColor;
  private int itemHeight;
  private List<PartBom> bomItems;

  public String getItemName () { return itemName; }
  public String getItemColor() { return itemColor; }
  public int getItemHeight() { return itemHeight; }
  public List<PartBom> getParts() { return bomItems;}

}
