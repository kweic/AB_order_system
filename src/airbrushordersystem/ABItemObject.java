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
    private ArrayList<String> subItems;
    private String iconPath;
    private ImageIcon icon;

    public ABItemObject(){
        subItems = new ArrayList();
        iconPath = "none";
    }
    
    public void setItem(String input) {
        for (ABItems aItem : ABItems.values()) {
            if (aItem.toString().toLowerCase().equals(input.toLowerCase())) {
                item = aItem;
                return;
            }
        }
        strItem = input;
    }

    //public ABItems getABItem() {
    //    return item;
    //}
    public String getItem() {
        if (item == null) {
            return strItem;
        } else {
            return item.toString();
        }
    }

    public void addSubItem(String itemVariation) {
        if (subItems == null) {
            subItems = new ArrayList();
        }
        subItems.add(itemVariation);
    }

    public ArrayList<String> getSubItems() {
        return subItems;
    }
    
    public void assignIconPath(String path){
        this.iconPath = path;
    }
    
    public String getIconPath(){
        return iconPath;
    }
    
    public void setIcon(ImageIcon icon){
        this.icon = icon;
    }
    
    public ImageIcon getImageIcon(){
        return icon;
    }
}
