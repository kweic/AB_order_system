/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package airbrushordersystem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author kw
 */
public class Item_ClickListener {

    ABItemObject itemObject;
    AB_ProgramFlow abMain;
    ImageIcon itemImage;

    public Item_ClickListener(ABItemObject itemObject, AB_ProgramFlow abMain) {
        this.itemObject = itemObject;
        this.abMain = abMain;
    }

    public JLabel addListener(ImageIcon itemImage) {
        this.itemImage = itemImage;
        JLabel itemImageLabel = new JLabel(itemImage);
        itemImageLabel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {

                //System.out.println(message);
                System.out.println("Clicked: " + itemObject.getItem());
                //System.out.println("sub items to display: " + itemObject.getSubItems().toString());
                // to do: make this run abMain display if no sub items, with selected
                // to do: make this run abMain items if sub items, with sub items
                System.out.println("   subs: "+itemObject.getSubItems());
                if (itemObject.getSubItems() == null) {
                    runMainLayout();
                }else{
                    System.out.println("   sub items to display: " + itemObject.getSubItems().toString());
                    previewLinks(itemObject.getSubItems());
                    runItemsLayout(itemObject.getSubItems());
                }
            }

        });
        return itemImageLabel;
    }
    
    private void previewLinks(ArrayList<ABItemObject> items){
        System.out.println("   Preview Item Links");
        for(ABItemObject item: items){
            System.out.println("    "+item.getIconPath());
        }
    }
    
    public void runItemsLayout(ArrayList<ABItemObject> itemsList){
        abMain.runItemScreen(itemsList);
    }

    public void runMainLayout() {
        abMain.runMainDisplay(itemImage);
        // setMainLayout(displaysScrollPane, itemImage, priceTextArea, categoriesPanel);
    }
}
