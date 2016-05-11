/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package airbrushordersystem;

import java.io.File;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author kw
 */
public class AB_ProgramFlow {

    String directory;
    //String directory = "E:\\Pictures";
    OrderSystemUI airbrushScreen;
    ImageFileLoader loader;
    ImageDataGetter dataGetter = new ImageDataGetter();
    SaveLoadSettings saveLoader = new SaveLoadSettings();
    UI_Items uiItems;
    //airbrushScreen.run();

    ArrayList<AirbrushDesign> designs;
    int width;

    public AB_ProgramFlow() {
        //find save file of items available and load them into list
        directory = new File("").getAbsolutePath() + "\\AirbrushDesigns";
        airbrushScreen = new OrderSystemUI(new JPanel());
        uiItems = new UI_Items();
        designs = new ArrayList();
        width = 400;
        loader = new ImageFileLoader(designs, width);
    }

    public ABItemObject runItemScreen(ArrayList<ABItemObject> itemObjects){
        //user selects item
        System.out.println("AB_ProgramFlow, runItemScreen");
        
        
        //item selected displays sub items or if none exits loop returning the selected item
        
        airbrushScreen.setPanel(uiItems.getItemsImagePanel(airbrushScreen.getFrame(), this, itemObjects));
        airbrushScreen.refreshPanel();
        
        
        System.out.println("   returned icon");
        //return uiItems.loadIcon("F:\\Programming\\Java\\AirbrushOrderSystem\\saveSettings\\ImageAssets\\Shirt.jpg");
        return itemObjects.get(0);
    }
    
    public void runMainDisplay(ImageIcon itemSelect){
        UI_Designs uiDesigns = new UI_Designs();
        System.out.println("designs size: "+designs.size());
        
        //code below for testing Main display UI section
        
        int printAreaWidth = airbrushScreen.getWidth() - itemSelect.getIconWidth();
        int printAreaHeight = airbrushScreen.getHeight();
        JScrollPane displaysScrollPane = uiDesigns.getScrollPane(printAreaWidth, printAreaHeight, designs);

        System.out.println ("Main, got scrollPane, size H W: "+displaysScrollPane.getHeight()+" "+displaysScrollPane.getWidth());
        
        JTextArea priceTextArea = new JTextArea();
        JPanel categoriesPanel = new JPanel();

        priceTextArea.setText (" Your Order: \n -----------------------\n    Shirt  $9.99 \n \n    Total: $9.99");
        priceTextArea.setEditable (false);
        
        System.out.println ("itemIcon size: "+itemSelect.getIconHeight());
        airbrushScreen.setMainLayout (airbrushScreen.getHeight(),airbrushScreen.getWidth(), displaysScrollPane, itemSelect, priceTextArea, categoriesPanel);
        airbrushScreen.refreshPanel ();
    }
    
    public void run() {
        
        
        //     send item price to pricecontrol class that generates the needed JTextArea with price total
        //when design is clicked show bigger version, pop over, has a time out to keep too many from being opened
        //   or closes previous version before opening new one
        
        //find save file of designs and load them all into list

        designs = saveLoader.loadDesigns();
        
        airbrushScreen.run ();
        //printGathered(designs); //for debugging, stdout all designs
        
        //run the item screen
        uiItems.setInitialItems();
        ABItemObject itemSelect = runItemScreen(uiItems.getCurrentItems());
        //runMainDisplay(itemSelect.getImageIcon());

    }

    public static void printGathered(ArrayList<AirbrushDesign> designs) { //for debugging
        int i = 0;
        for (AirbrushDesign design : designs) {
            System.out.println(i + ". " + design.getTitle() + " price: " + design.getPrice() + " category: " + design.getCategories()
                    + " items: " + design.getItems() + " #: " + design.getDesignNumber() + " style: " + design.getWritingStyle());
            i++;
        }
    }

    //saveLoader.saveDesigns (designs); //saves all loaded designs
    //testLoader tester = new testLoader();
    //tester.runSequence();
    
    //testing item loading

   
    

 
        //ArrayList<ABItemObject> loadedItems = uiItems.loadSavedItems();

        //uiItems.setImageIconPaths(loadedItems);
        //uiItems.saveItemsList(loadedItems);

        //printItems(loadedItems);

        //airbrushScreen.setPanel(uiItems.getImagePanel(airbrushScreen.getFrame()));
        //airbrushScreen.refreshPanel();
        
    public static void printItems(ArrayList<ABItemObject> loadedItems) { //for debugging
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

}
