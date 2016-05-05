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
public class AirbrushDesign implements Comparable<AirbrushDesign> {

    private String title;
    private int price;
    private int designNumber;

    private ArrayList<ABCategory> categories; //what categories it can be sorted into
    private ArrayList<ABItems> abItems; //what it can be painted on
    private String imagePath; //path to the original image
    private String fileName;
    private String iconPath; //path to the resized image
    private int nameCount; //names on the design, used to track price markups with extra names
    private ABWritingStyles writingStyle; //default style on the design, used to track markup with style change
    private ImageIcon designIcon;

    public AirbrushDesign(String imagePath) {
        this.imagePath = imagePath;
        this.writingStyle = ABWritingStyles.NONE;
        this.price = 0;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
    
    public void setImageIcon(ImageIcon designIcon){
        this.designIcon = designIcon;
    }
    
    public ImageIcon getImageIcon(){
        return designIcon;
    }

    public void setDesignNumber(int number) {
        this.designNumber = number;
    }

    public int getDesignNumber() {
        return designNumber;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public void setFileName(String name) {
        this.fileName = name;
    }

    public String getFileName() {
        return fileName;
    }

    public void setCategories(ArrayList<ABCategory> categories) {
        this.categories = categories;
    }

    public void addCategory(ABCategory category) {
        if (categories == null) {
            categories = new ArrayList();
        }
        categories.add(category);
    }

    public ArrayList<ABCategory> getCategories() {
        if (categories == null) {
            return new ArrayList();
        }
        return categories;
    }

    public void setItems(ArrayList<ABItems> abItems) {
        this.abItems = abItems;
    }

    public void addItem(ABItems item) {
        if (abItems == null) {
            abItems = new ArrayList();
        }
        abItems.add(item);
    }

    public ArrayList<ABItems> getItems() {
        if (abItems == null) {
            return new ArrayList();
        }
        return abItems;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setNameCount(int count) {
        this.nameCount = count;
    }

    public int getNameCount() {
        return nameCount;
    }

    public void setWritingStyle(ABWritingStyles style) {
        this.writingStyle = style;
    }

    public ABWritingStyles getWritingStyle() {
        return writingStyle;
    }

    @Override
    public int compareTo(AirbrushDesign o) {
        if (o.getDesignNumber() == this.getDesignNumber()) {
            return 0;
        } else if (o.getDesignNumber() > this.getDesignNumber()) {
            return -1;
        }
        return 1;
    }

}
