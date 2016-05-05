/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package airbrushordersystem;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

/**
 *
 * @author kw
 */
public class UI_Items {

    private ArrayList<String> defaultItems;
    private String itemsListPath;
    private ArrayList<String> validImageFormats;

    public UI_Items() {
        defaultItems = new ArrayList(Arrays.asList("T-Shirt:Large:Medium:Small", "Trucker Hat:Black:White", "Keychain", "License Plate", "Poster", "Bag:Draw String:Tote"));
        itemsListPath = new File("").getAbsolutePath() + "\\saveSettings\\saved_Items.txt";
        validImageFormats = new ArrayList(Arrays.asList(".jpg", ".gif"));
    }

    public JPanel getItemsImagePanel(JFrame originalFrame) {
        //System.out.println("getImagePanel, frame width: "+originalFrame.getWidth());
        JPanel itemsPanel = new JPanel();
        itemsPanel.setLayout(null);
        ArrayList<ABItemObject> loadedItems = getItemsWIcons();
        int rowSize = getRowSize(loadedItems.size());
        ImageIcon itemIcon;
        JLabel imageLabel;
        int y = 0;
        int x = 0;

        int gapSize = getImageGapSize(loadedItems);
        int rows = 0;
        for (int i = 0; i < loadedItems.size(); i++) {
            itemIcon = loadedItems.get(i).getImageIcon();
            if (i % rowSize == 0 && i != 0) {
                y += itemIcon.getIconHeight() + itemIcon.getIconHeight() / 4;

                rows++;
                x = 0;
            }

            imageLabel = new JLabel(itemIcon);
            imageLabel.setBounds(x, y, itemIcon.getIconWidth(), itemIcon.getIconHeight());
            Item_ClickListener clickListener = new Item_ClickListener(loadedItems.get(i));
            clickListener.addListener(imageLabel);

            System.out.println("added image for: " + loadedItems.get(i).getItem());
            itemsPanel.add(imageLabel);
            x += itemIcon.getIconWidth() + itemIcon.getIconWidth() / 4;
        }
        rows++;
        JPanel largerPanel = new JPanel();
        largerPanel.setLayout(null);
        int frameWidth = getImageSetWidth(loadedItems.get(0).getImageIcon(), gapSize, rowSize);

        int frameHeight = getImageSetHeight(loadedItems.get(0).getImageIcon(), gapSize, rows);
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
        if (items.size() < 9) {
            System.out.println("returning gap size: " + items.get(0).getImageIcon().getIconWidth() / 4);
            return items.get(0).getImageIcon().getIconWidth() / 4;
        } else if (items.size() < 16) {
            return items.get(0).getImageIcon().getIconWidth() / 5;
        }
        return items.get(0).getImageIcon().getIconWidth() / 6;
    }

    private int getImageSetWidth(ImageIcon icon, int gap, int rowSize) {
        return ((icon.getIconWidth() + gap) * rowSize) - gap;
    }

    private int getImageSetHeight(ImageIcon icon, int gap, int rowSize) {
        System.out.println("getHeight, gap:" + gap + " row size: " + rowSize);
        return ((icon.getIconHeight() + gap) * rowSize) - gap;
    }

    private int getRowSize(int arraySize) {
        if (arraySize < 4) {
            return arraySize;
        } else if (arraySize > 3 && arraySize < 9) {
            return arraySize / 2;
        } else if (arraySize > 8 && arraySize < 20) {
            return arraySize / 3;
        }
        return arraySize / 4;
    }

    public ArrayList<ABItemObject> getItemsWIcons() {
        ArrayList<ABItemObject> loadedItems = loadSavedItems();
        setImageIconPaths(loadedItems);
        loadImageIcons(loadedItems);

        return loadedItems;
    }

    private ArrayList<ABItemObject> loadSavedItems() {
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
                System.out.println("Items Failed to Load");
                //load default items
            }
        } else {
            return assignItems(defaultItems);
        }
        System.out.println("loaded Designs size: " + itemStringLoad.size());
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
        for (String item : abItem.getSubItems()) {
            sb.append(":");
            sb.append(item);
        }
        return sb.toString();
    }

    private ArrayList<ABItemObject> assignItems(ArrayList<String> loadedStrings) {
        //System.out.println("UI_Items, assignItems");
        ArrayList<ABItemObject> itemList = new ArrayList();
        for (String itemLine : loadedStrings) {
            itemList.add(createItemObject(itemLine));
            //System.out.println(itemList.get(itemList.size() - 1).getItem() + " " + itemList.get(itemList.size() - 1).getSubItems());
        }
        return itemList;
    }

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
                newItem.setItem(buildItem.toString());
                buildItem.replace(0, buildItem.length(), ""); //reset SB
                initialFound = true;
            } else if (input.charAt(i) == ':' || i == input.length() - 1) {
                newItem.addSubItem(buildItem.toString());
                buildItem.replace(0, buildItem.length(), "");
            }

        }
        return newItem;
    }

    private void setImageIconPaths(ArrayList<ABItemObject> listItems) {
        //get a list of all the icons with path to image
        HashMap<String, String> iconPaths = getIcons();
        //step through each listItem
        boolean iconAssigned;
        for (ABItemObject item : listItems) {
            iconAssigned = false;
            for (String icon : iconPaths.keySet()) {
                if (!iconAssigned && item.getItem().toLowerCase().contains(icon.toLowerCase())) {
                    item.assignIconPath(iconPaths.get(icon));
                    //System.out.println(item.getItem()+" "+iconPaths.get(icon));
                    iconAssigned = true;
                }
            }
        }
        //go through list of icons and check if the icon name is contained in the listItem name
        //assign that icon to that item
    }

    public void loadImageIcons(ArrayList<ABItemObject> listItems) {
        for (ABItemObject item : listItems) {
            item.setIcon(loadIcon(item.getIconPath()));
        }
    }

    public ImageIcon loadIcon(String path) {
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
