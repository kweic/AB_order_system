/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package airbrushordersystem;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author kw
 */
public class UI_Designs {

    public UI_Designs() {

    }

    public JScrollPane getScrollPane(int printAreaWidth, int printAreaHeight, ArrayList<AirbrushDesign> designs) {
        System.out.println("UI_Designs, getScrollPane, printAreaWidth: " + printAreaWidth+" height: "+printAreaHeight);
        JPanel designsPanel = new JPanel();
        
        designsPanel.setBackground(Color.black);
        
        int x = 0;
        int y = 0;
        int width = loadIcon(designs.get(0).getIconPath()).getIconWidth();
        
        System.out.println("   getScrollPane width set: " + width);
        System.out.println("   called loadIcon for width from UI_Designs, getScrollPane");
        //int imagesAcross = printAreaWidth / width;
        //System.out.println("   imagesAcross set to: " + imagesAcross);
        //int gapSize = setGapSpacer(printAreaWidth, imagesAcross, width);
        //System.out.println("   gapSize set to: " + gapSize);
        int i = 0;

        //Test code below here
        //how to figure out print area height... total images / images across * imageheight
        designsPanel.setPreferredSize(new Dimension(printAreaWidth, designs.size() / (printAreaWidth / width) * width*2));
        //designsPanel.setSize(1000, 1050);
        //test code above here

        for (AirbrushDesign design : designs) {
            ImageIcon designIcon = loadIcon(design.getIconPath());
            JLabel designLabel = new JLabel(designIcon);
            Design_ClickListener designListener = new Design_ClickListener(design);
            designListener.addListener(designLabel);
            
            //designLabel.setBounds(x, y, designIcon.getIconWidth(), designIcon.getIconHeight());
            //System.out.println("    designLabel bounds set X Y: " + x + " " + y);
            designsPanel.add(designLabel);
            //if (i > imagesAcross) {
             //   x = 0;
             //   y += designIcon.getIconHeight() + gapSize;
              //  i = -1;
            //} else {
              //  x += designIcon.getIconWidth() + gapSize;
            //}
            //i++;
        }

        System.out.println("    designsPanel filled size H, W: " + designsPanel.getHeight() + " " + designsPanel.getWidth());
        // designsPanel.setSize(500, 500); //sizing problem test
        JScrollPane scrollPane = new JScrollPane(designsPanel);
        scrollPane.setBackground(Color.red); //sizing problem test
        //scrollPane.setViewportView(designsPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);

        //scrollPane.setSize(designsPanel.getWidth(), designsPanel.getHeight());//sizing problem test
        System.out.println("    setting scrollPane preffered size: ");
        
        
        scrollPane.setPreferredSize(new Dimension(printAreaWidth, printAreaHeight));
        
        System.out.println("    returning scrollPane, size: " + scrollPane.getHeight() + " w: " + scrollPane.getWidth());

        return scrollPane;
    }

    private int setGapSpacer(int panelWidth, int imagesAcross, int imageWidth) {
        // -10 for scrollbar
        return ((panelWidth - 10) - (imagesAcross * imageWidth)) / (imagesAcross + 1);
    }

    private ImageIcon loadIcon(String path) {
        System.out.println("UI_Designs, loadIcon: " + path);

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
}
