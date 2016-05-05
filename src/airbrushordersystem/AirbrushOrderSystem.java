/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package airbrushordersystem;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.io.File;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class AirbrushOrderSystem {


    public static void main(String[] args) {
        // TODO code application logic here

        String directory = new File("").getAbsolutePath() + "\\AirbrushDesigns";
        //String directory = "E:\\Pictures";
        OrderSystemUI airbrushScreen = new OrderSystemUI(new JPanel());
        //airbrushScreen.run();

        ArrayList<AirbrushDesign> designs = new ArrayList();
        int width = 400;

        
        
        //this part for importing images
        ImageFileLoader loader = new ImageFileLoader(designs, width);
        ImageDataGetter dataGetter = new ImageDataGetter();
        SaveLoadSettings saveLoader = new SaveLoadSettings();

        //loader.gatherImages(directory);
        designs = initialOpenCheckSave(dataGetter, loader, saveLoader, directory);//checks for savefile, if none is found loads all images from directory and makes icons with width
        //getData(designs, dataGetter);
        
        loader.makeIcons();
        saveLoader.saveDesigns(designs); //saves all loaded designs
        //testLoader tester = new testLoader();
        //tester.runSequence();
        printGathered(designs); //for debugging, stdout all designs
        //testing item loading
        airbrushScreen.run();

        UI_Items uiItems = new UI_Items();
        UI_Designs uiDesigns = new UI_Designs();
        System.out.println("designs size: "+designs.size());
        //ArrayList<ABItemObject> loadedItems = uiItems.loadSavedItems();

        //uiItems.setImageIconPaths(loadedItems);
        //uiItems.saveItemsList(loadedItems);

        //printItems(loadedItems);

        //airbrushScreen.setPanel(uiItems.getImagePanel(airbrushScreen.getFrame()));
        //airbrushScreen.refreshPanel();
        
        
        
        
        //code below for testing Main display UI section
        ImageIcon itemIcon = uiItems.loadIcon("F:\\Programming\\Java\\AirbrushOrderSystem\\saveSettings\\ImageAssets\\Shirt.jpg");
        int printAreaWidth = airbrushScreen.getWidth() - itemIcon.getIconWidth();
        int printAreaHeight = airbrushScreen.getHeight();
        JScrollPane displaysScrollPane = uiDesigns.getScrollPane(printAreaWidth, printAreaHeight, designs);
        System.out.println("Main, got scrollPane, size H W: "+displaysScrollPane.getHeight()+" "+displaysScrollPane.getWidth());
        
        JTextArea priceTextArea = new JTextArea();
        JPanel categoriesPanel = new JPanel();
        
        priceTextArea.setText(" Your Order: \n -----------------------\n    Shirt  $9.99 \n \n    Total: $9.99");
        priceTextArea.setEditable(false);
        
        System.out.println("itemIcon size: "+itemIcon.getIconHeight());
        airbrushScreen.setMainLayout(airbrushScreen.getHeight(),airbrushScreen.getWidth(), displaysScrollPane, itemIcon, priceTextArea, categoriesPanel);
        airbrushScreen.refreshPanel();
        
        //above main UI section testing

    }

    public static void printItems(ArrayList<ABItemObject> loadedItems) {
        System.out.println("  printing items");
        for (ABItemObject item : loadedItems) {
            System.out.println(item.getItem() + " " + item.getSubItems());
            System.out.println(item.getIconPath());
        }
    }

    public static ArrayList<AirbrushDesign> initialOpenCheckSave(ImageDataGetter dataGetter, ImageFileLoader imgLoader, SaveLoadSettings saveLoader, String directory) {
        ArrayList<AirbrushDesign> designs = new ArrayList();
        //load airbrushdesigns from save if it exists
        designs = saveLoader.loadDesigns();
        //if load size is 0
        if (designs.isEmpty()) {
            designs = imgLoader.gatherImages(directory);
            getData(designs, dataGetter);
            imgLoader.makeIcons();
        }
        //do a full load
        //gather images
        //get data
        //
        return designs;
    }

    public static void getData(ArrayList<AirbrushDesign> designs, ImageDataGetter getter) {
        System.out.println("Main, getData for # of designs: "+designs.size());
        for (AirbrushDesign design : designs) {
            getter.getData(design);
        }
    }

    public static void printGathered(ArrayList<AirbrushDesign> designs) {
        int i = 0;
        for (AirbrushDesign design : designs) {
            System.out.println(i + ". " + design.getTitle() + " price: " + design.getPrice() + " category: " + design.getCategories()
                    + " items: " + design.getItems() + " #: " + design.getDesignNumber() + " style: " + design.getWritingStyle());
            i++;
        }
    }

}
