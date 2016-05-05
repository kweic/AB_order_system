/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package airbrushordersystem;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author kw
 */
public class UI_Categories {

    private int width;
    private ArrayList<ABCategoryObject> categoryList;

    public UI_Categories(int width, ArrayList<ABCategoryObject> categoryList) {
        this.width = width;
        this.categoryList = categoryList;
    }

    public JPanel getCategoryPanel() {
        JPanel catPanel = new JPanel();

        return catPanel;
    }

    //load category icons
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
    //resize to same width as item icon
}
