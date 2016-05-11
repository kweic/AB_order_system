package airbrushordersystem;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class UI_Items {

    final private ArrayList<String> defaultItems;
    final private String itemsListPath;
    final private ArrayList<String> validImageFormats;
    private ArrayList<ABItemObject> currentItems;

    public UI_Items() {
        defaultItems = new ArrayList(Arrays.asList("T-Shirt:Large:Medium:Small", "Trucker Hat:Black:White", "Keychain", "License Plate", "Poster", "Bag:Draw String:Tote"));
        itemsListPath = new File("").getAbsolutePath() + "\\saveSettings\\saved_Items.txt";
        validImageFormats = new ArrayList(Arrays.asList(".jpg", ".gif"));
        currentItems = new ArrayList();
    }

    public void setInitialItems() {
        currentItems = getItemsWIcons();
    }

    public ArrayList<ABItemObject> getCurrentItems() {
        return currentItems;
    }

    public int getCurrentItemsSize() {
        return currentItems.size();
    }

    //public void setNewCurrentItems(ArrayList<ABItemObject> newItems){
    //   currentItems = newItems;
    //}
    public JPanel getItemsImagePanel(JFrame originalFrame, AB_ProgramFlow abMain, ArrayList<ABItemObject> itemObjectsList) {
        System.out.println("UI_Items, getItemsImagePanel, frame width: " + originalFrame.getWidth());

        JPanel itemsPanel = new JPanel();
        itemsPanel.setLayout(null);
        System.out.println("   getting panel for: " + itemObjectsList);
        loadImageIcons(itemObjectsList);
        //ArrayList<ABItemObject> loadedItems = getItemsWIcons();
        int rowSize = getRowSize(itemObjectsList.size());
        ImageIcon itemIcon;
        JLabel imageLabel;
        int y = 0;
        int x = 0;

        int gapSize = getImageGapSize(itemObjectsList);
        System.out.println("   gapSize set to: "+gapSize);
        int rows = 0;
        for (int i = 0; i < itemObjectsList.size(); i++) {
            itemIcon = itemObjectsList.get(i).getImageIcon();
            if (i % rowSize == 0 && i != 0) {
                //y += itemIcon.getIconHeight() + itemIcon.getIconHeight() / 4;
                y+=gapSize+itemIcon.getIconHeight();

                rows++;
                x = 0;
            }

            //imageLabel = new JLabel(itemIcon);
            Item_ClickListener clickListener = new Item_ClickListener(itemObjectsList.get(i), abMain);
            //tweaked here 5-10
            imageLabel = clickListener.addListener(itemIcon);
            imageLabel.setBounds(x, y, itemIcon.getIconWidth(), itemIcon.getIconHeight());

            System.out.println("    added image for: " + itemObjectsList.get(i));
            itemsPanel.add(imageLabel);
           // x += itemIcon.getIconWidth() + itemIcon.getIconWidth() / 4;
           x+= gapSize+itemIcon.getIconWidth();
        }
        rows++;
        JPanel largerPanel = new JPanel();
        largerPanel.setLayout(null);
        int frameWidth = getImageSetWidth(itemObjectsList.get(0).getImageIcon(), gapSize, rowSize);

        int frameHeight = getImageSetHeight(itemObjectsList.get(0).getImageIcon(), gapSize, rows);
        //                   x,  y,  wid, height
        itemsPanel.setBounds(getPanelXorY(frameWidth, originalFrame.getWidth()), getPanelXorY(frameHeight, originalFrame.getHeight()), frameWidth, frameHeight);
        System.out.println("itemsPanel width: " + itemsPanel.getWidth());
        largerPanel.add(itemsPanel);

        itemsPanel.add(new JLabel("testing"));
        System.out.println("returned panel");

        return largerPanel;
    }

    private int getPanelXorY(int frameWidth, int panelWidth) {
        System.out.println("framewid: " + frameWidth + " panel wid: " + panelWidth);
        System.out.println("panel X, returning: " + (panelWidth / 2 - frameWidth / 2));
        return panelWidth / 2 - frameWidth / 2;
    }

    private int getImageGapSize(ArrayList<ABItemObject> items) {
        System.out.println("UI_Items, getImageGapSize: "+items.size()+" items");
        if (items.size() < 9) {
            System.out.println("   returning gap size, less than 9, /4");
            return items.get(0).getImageIcon().getIconWidth() / 4;
        } else if (items.size() < 10) {
            System.out.println("   returning gap size, less than 10, /5");
            return items.get(0).getImageIcon().getIconWidth() / 5;
        }
        System.out.println("   returning gap size, else, /8");
        return items.get(0).getImageIcon().getIconWidth() / 8;
    }

    private int getImageSetWidth(ImageIcon icon, int gap, int rowSize) {
        return ((icon.getIconWidth() + gap) * rowSize) - gap;
    }

    private int getImageSetHeight(ImageIcon icon, int gap, int rowSize) {
        System.out.println("getHeight, gap:" + gap + " row size: " + rowSize);
        return ((icon.getIconHeight() + gap) * rowSize) - gap;
    }

    private int getRowSize(int arraySize) {
        if (arraySize < 6) { //less than 6 is in a straight line on screen
            return arraySize;
        } else if (arraySize == 10) {
            return 5;
        } else if (arraySize > 3 && arraySize < 9) {
            return arraySize / 2;
        } else if (arraySize > 8 && arraySize < 20) {
            return arraySize / 3;
        }
        return arraySize / 4;
    }

    public ArrayList<ABItemObject> getItemsWIcons() {
        System.out.println("UI_Items, getItemsWIcons");
        ArrayList<ABItemObject> loadedItems = loadSavedItems();
        //5-11 bug, setImageIconPaths(loadedItems);
        loadImageIcons(loadedItems);

        return loadedItems;
    }

    private ArrayList<ABItemObject> loadSavedItems() {
        System.out.println("UI_Items, loadSavedItems");
        ArrayList<String> itemStringLoad = new ArrayList();
        File loadFile = new File(itemsListPath);
        if (loadFile.exists()) {
            Scanner reader;
            try {
                reader = new Scanner(loadFile, "UTF-8");
                while (reader.hasNextLine()) {
                    itemStringLoad.add(reader.nextLine());
                }
                reader.close();

            } catch (Exception e) {
                //create the loadfile
                System.out.println("   Items Failed to Load");
                //load default items
            }
        } else {
            System.out.println("   no save file, returning default items");
            return assignItems(defaultItems);
        }
        System.out.println("   loaded Designs size: " + itemStringLoad.size());
        return assignItems(itemStringLoad);
    }

    public void saveItemsList(ArrayList<ABItemObject> abItems) {
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(itemsListPath)));
            for (ABItemObject abItem : abItems) {
                writer.write(makeItemSaveString(abItem) + "\r\n");
            }
            writer.close();
        } catch (Exception e) {
            System.out.println("save failed");
        }
    }

    private String makeItemSaveString(ABItemObject abItem) {
        StringBuilder sb = new StringBuilder();
        sb.append(abItem.getItem());
        for (ABItemObject item : abItem.getSubItems()) {
            sb.append(":");
            sb.append(item);
        }
        return sb.toString();
    }

    private ArrayList<ABItemObject> assignItems(ArrayList<String> loadedStrings) {
        //iterate through string list, send subsets of items to createItemObject for creation
        System.out.println("UI_Items, assignItems");

        ArrayList<ABItemObject> itemList = new ArrayList();
        int startIndex = 0;
        for (int i = 0; i < loadedStrings.size(); i++) {
            //go through the strings with a saved startIndex until hitting the cue ### then send EndIndex 'i' and send to createItemObject
            //reset start index to be i+1 and wait for another ### before sending to create again
            if (loadedStrings.get(i).equals("###")) {
                itemList.add(createItemObject(loadedStrings, startIndex, i - 1));
                startIndex = i + 1;
            }
        }
        return itemList;
        /*
        for (String itemLine : loadedStrings) {
            itemList.add(createItemObject(itemLine));
            System.out.println("   assigned: " + itemList.get(itemList.size() - 1).getItem() + " " + itemList.get(itemList.size() - 1).getSubItems());
        }
        return itemList;
         */
    }

    private ABItemObject createItemObject(ArrayList<String> loadedStrings, int startIndex, int endIndex) {
        //StringBuilder buildItem = new StringBuilder();
        // name, price, icon path
        System.out.println("UI_Items, createItemObject: " + loadedStrings.get(startIndex) + " " + loadedStrings.get(startIndex + 1) + " " + loadedStrings.get(startIndex + 2));
        System.out.println("   startIndex: " + startIndex + " end: " + endIndex + " diff: " + (endIndex - startIndex));
        ABItemObject newItem = new ABItemObject(loadedStrings.get(startIndex), Double.parseDouble(loadedStrings.get(startIndex + 1)), loadedStrings.get(startIndex + 2));
        if (endIndex - startIndex > 3) {
            for (int i = startIndex + 3; i < endIndex; i += 3) {
                System.out.println("   new sub item with: " + loadedStrings.get(i) + " " + loadedStrings.get(i + 1) + " " + loadedStrings.get(i + 2));
                newItem.addSubItem(loadedStrings.get(i), Double.parseDouble(loadedStrings.get(i + 1)), loadedStrings.get(i + 2));
            }
        }
        System.out.println("   made: " + newItem.getItem() + " sub: " + newItem.getSubItems());
        System.out.println("   " + newItem.getIconPath());
        return newItem;

    }

    /*
    private ABItemObject createItemObject(String input) {
        StringBuilder buildItem = new StringBuilder();
        ABItemObject newItem = new ABItemObject();
        boolean initialFound = false;
        for (int i = 0; i < input.length(); i++) {
            //System.out.println("i: "+i+" "+input.length());
            if (input.charAt(i) != ':') {
                buildItem.append(input.charAt(i));
            }

            if ((!initialFound && input.charAt(i) == ':') || (i == input.length() - 1 && !initialFound)) {
                // to do: make price gathering function to replace the 9.99
                newItem.setItem(buildItem.toString(), 9.99);
                buildItem.replace(0, buildItem.length(), ""); //reset SB
                initialFound = true;
            } else if (input.charAt(i) == ':' || i == input.length() - 1) {
                // to do: make price gathering function
                // to do: make iconpath gathering function
                newItem.addSubItem(buildItem.toString(), "iconpath", 9.99);
                buildItem.replace(0, buildItem.length(), "");
            }

        }
        return newItem;
    }
     */
    //5-11-16, bugging my icons, can still be used on setup screen to auto assign
    /*
    private void setImageIconPaths(ArrayList<ABItemObject> listItems) {
        //get a list of all the icons with path to image
        System.out.println("UI_Items, setImageIconPaths");
        HashMap<String, String> iconPaths = getIcons();
        //step through each listItem
        boolean iconAssigned;
        for (ABItemObject item : listItems) {
            System.out.println("   doing: "+item.getItem());
            iconAssigned = false;
            for (String icon : iconPaths.keySet()) {
                if (!iconAssigned && item.getItem().toLowerCase().contains(icon.toLowerCase())) {
                    System.out.println("   setIconPath to: "+iconPaths.get(icon));
                    item.setIconPath(iconPaths.get(icon));
                    //System.out.println(item.getItem()+" "+iconPaths.get(icon));
                    iconAssigned = true;
                }
            }
        }
        //go through list of icons and check if the icon name is contained in the listItem name
        //assign that icon to that item
    }
     */
    public void loadImageIcons(ArrayList<ABItemObject> listItems) {
        System.out.println("UI_Items, loadImageIcons");
        for (ABItemObject item : listItems) {
            System.out.println("setting icon for: " + item.getItem());
            item.setIcon(loadIcon(item.getIconPath()));
        }
    }

    public ImageIcon loadIcon(String path) {
        System.out.println("UI_Items, loadIcon: " + path);
        File file = new File(path);
        BufferedImage img;
        ImageIcon icon = null;
        try {
            img = ImageIO.read(file);
            icon = new ImageIcon(img);
        } catch (Exception e) {
            System.out.println("icon failed to load");
        }
        return icon;
    }

    private HashMap<String, String> getIcons() {
        File folder = new File(new File("").getAbsolutePath() + "\\saveSettings\\ImageAssets");
        File[] listOfFiles = folder.listFiles();
        HashMap<String, String> iconPaths = new HashMap();

        for (File file : listOfFiles) {
            if (file.isFile()) {
                if (validImageFile(file.toString())) {
                    iconPaths.put(removeFormat(file.getName()), file.getAbsolutePath());
                }
            }
        }
        return iconPaths;
    }

    private String removeFormat(String input) {
        String fixedName;
        for (String format : validImageFormats) {
            if (input.toLowerCase().contains(format.toLowerCase())) {
                fixedName = input.substring(0, input.length() - format.length());
                return fixedName;
            }
        }
        return input;
    }

    private boolean validImageFile(String fileName) {
        for (String format : validImageFormats) {
            if (fileName.toLowerCase().contains(format.toLowerCase())) {
                return true;
            }
        }
        return false;
    }


    /*
    public ABItemObject findAssignItem(String input) {
        for (ABItems item : ABItems.values()) {
            if (item.toString().toLowerCase().equals(input.toLowerCase())) {
                return new ABItemObject(item);
            }
        }
        return new ABItemObject(input);
    }
     */
}
