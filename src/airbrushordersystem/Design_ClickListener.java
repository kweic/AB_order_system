/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package airbrushordersystem;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;

/**
 *
 * @author kw
 */
public class Design_ClickListener {
    
    AirbrushDesign abDesign;
    
    public Design_ClickListener(AirbrushDesign design) {
        abDesign = design;
    }
    
    public JLabel addListener(JLabel itemImage) {

        itemImage.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {

                //System.out.println(message);
                System.out.println("clicked: "+abDesign.getTitle());
            }

        });
        return itemImage;
    }

    public void runMainLayout() {
        
       // setMainLayout(displaysScrollPane, itemImage, priceTextArea, categoriesPanel);
    }
}
