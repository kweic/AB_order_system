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

    public Item_ClickListener(ABItemObject itemObject) {
        this.itemObject = itemObject;
    }

    public JLabel addListener(JLabel itemImage) {

        itemImage.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {

                //System.out.println(message);
                System.out.println("Clicked: " + itemObject.getItem());
                System.out.println("sub items to display: " + itemObject.getSubItems());
                runMainLayout();
            }

        });
        return itemImage;
    }

    public void runMainLayout() {
        
       // setMainLayout(displaysScrollPane, itemImage, priceTextArea, categoriesPanel);
    }
}
