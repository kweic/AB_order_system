/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package airbrushordersystem;

import java.util.ArrayList;
import javax.swing.ImageIcon;

/**
 *
 * @author kw
 */
public class ABItemObject {

    private ABItems item;
    private String strItem;
    private ArrayList<ABItemObject> subItems;
    private String iconPath;
    private ImageIcon icon;
    private double price;

    public ABItemObject(String itemName, double price, String iconPath) {
        //subItems = new ArrayList();
        //iconPath = "none";

        this.iconPath = iconPath;
        this.price = price;
        strItem = itemName;
        for (ABItems aItem : ABItems.values()) {
            if (aItem.toString().toLowerCase().equals(itemName.toLowerCase())) {
                item = aItem;
                return;
            }
        }

    }

    /*
    public void setItem(String input, String iconPath, double price) {
        for (ABItems aItem : ABItems.values()) {
            if (aItem.toString().toLowerCase().equals(input.toLowerCase())) {
                item = aItem;
                return;
            }
        }
        this.iconPath = iconPath;
        this.price = price;
        strItem = input;
    }
     */
    public double getPrice() {
        return price;
    }

    public String getItem() {
        if (item == null) {
            return strItem;
        } else {
            return item.toString();
        }
    }

    public void addSubItem(String itemVariation, double price, String itemVariationPath) {
        if (subItems == null) {
            subItems = new ArrayList();
        }
        ABItemObject subItem = new ABItemObject(itemVariation, price, itemVariationPath);
        //subItem.setItem(itemVariation, itemVariationPath, price);
        //subItem.setIconPath(itemVariationPath);

        subItems.add(subItem);
    }

    public ArrayList<ABItemObject> getSubItems() {
        return subItems;
    }

    //public void setIconPath(String path) {
    //    this.iconPath = path;
    //}
    public String getIconPath() {
        return iconPath;
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }

    public ImageIcon getImageIcon() {
        return icon;
    }

    @Override
    public String toString() {
        return getItem();
    }
}
