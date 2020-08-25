package org.db;

import java.io.Serializable;

public class PartBom implements Serializable {
  private final int bomQty;
  private final String bomSKU;
  private final String bomDesc;

  PartBom(int qty, String sku, String desc) {
    bomQty = qty;
    bomSKU = sku;
    bomDesc = desc;
  }

  int getBomQty() { return bomQty; }
  String getBomSKU() { return bomSKU; }
  String getBomDesc() { return bomDesc; }
}
