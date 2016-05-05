/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package airbrushordersystem;

import java.awt.Color;
import javax.swing.JFrame;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

public class OrderSystemUI implements Runnable {

    private JFrame frame;
    JPanel preppedPanel;

    public OrderSystemUI(JPanel panel) {
        preppedPanel = panel;
    }

    @Override
    public void run() {
        System.out.println("started running UI");
        frame = new JFrame();
        //preppedPanel = new JPanel();
        frame.setPreferredSize(new Dimension(400, 400));
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); //fullscreen

        //frame.setUndecorated(true); //no icons
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        createComponents(frame.getContentPane());
        addListeners();

        frame.pack();
        frame.setVisible(true);

        System.out.println("frame size: " + frame.getWidth() + " " + frame.getHeight());

        System.out.println("finished running UI");

    }

    public void refreshPanel() {
        frame.dispose();
        run();
    }

    private void createComponents(Container container) {
        //DrawingBoard drawingboard = new DrawingBoard(figure);
        //container.add(drawingboard);
        //   JLabel text = new JLabel("Hi!");

        JPanel uiPanel = preppedPanel;

        //     uiPanel.add(text);
        //     uiPanel.setBackground(Color.DARK_GRAY);
        container.add(uiPanel);

        //frame.addKeyListener(new KeyboardListener(drawingboard, figure));
    }

    public int getWidth() {
        return frame.getWidth();
    }

    public int getHeight() {
        return frame.getHeight();
    }

    public void setMainLayout(int screenHeight, int screenWidth, JScrollPane displaysScrollPane, ImageIcon itemIcon, JTextArea priceTextArea, JPanel categoriesPanel) {
        System.out.println("OrderSystemUI, setMainLayout sizes H x W:");
        System.out.println("displayScrollPane: " + displaysScrollPane.getHeight() + " " + displaysScrollPane.getWidth());
        System.out.println("itemIcon: " + itemIcon.getIconHeight() + " " + itemIcon.getIconWidth());
        System.out.println("priceTextArea: " + priceTextArea.getHeight() + " " + priceTextArea.getWidth());
        System.out.println("categoriesPanel: " + categoriesPanel.getHeight() + " " + categoriesPanel.getWidth());

        JPanel layoutPanel = new JPanel();
        int edgeGap = 10;
        //layoutPanel.setLayout(new GridLayout(1,2));
        layoutPanel.setLayout(null);
        layoutPanel.setBackground(Color.black);
        //JPanel leftPanel = new JPanel();
        //leftPanel.setLayout(null);
        //leftPanel.setLayout(new GridLayout(3,1));

        JLabel iconLabel = new JLabel(itemIcon);
        iconLabel.setBounds(edgeGap, edgeGap, itemIcon.getIconWidth(), itemIcon.getIconHeight());

        //leftPanel.add(iconLabel);
        layoutPanel.add(iconLabel);
        //leftPanel.add(priceTextArea);

        priceTextArea.setBounds(edgeGap, iconLabel.getHeight() + 5+edgeGap, iconLabel.getWidth(), 250);
        layoutPanel.add(priceTextArea);
        categoriesPanel.setBounds(edgeGap, iconLabel.getHeight() + 10+edgeGap + priceTextArea.getHeight(), itemIcon.getIconWidth(), screenHeight - priceTextArea.getHeight() - iconLabel.getHeight() - 175);
        layoutPanel.add(categoriesPanel);
        System.out.println("categories panel bounds: " + categoriesPanel.getX() + " " + categoriesPanel.getY());
        //leftPanel.add(categoriesPanel);

        //layoutPanel.add(leftPanel); //testing
        //layoutPanel.add(displaysScrollPane); //this is not showing up.. TRASH LINE
        //JPanel scrollHolder = new JPanel();
        //scrollHolder.setLayout(null);
        //scrollHolder.setBackground(Color.black); //test if visible
        System.out.println("putting display into scrollHolder, size: w,h: " + displaysScrollPane.getWidth() + " " + displaysScrollPane.getHeight());

        displaysScrollPane.setBounds(itemIcon.getIconWidth() + 5+edgeGap, edgeGap, screenWidth - (itemIcon.getIconWidth() + 5+edgeGap)-20, screenHeight);
        //scrollHolder.add(displaysScrollPane);

        layoutPanel.add(displaysScrollPane);
        // layoutPanel.add(scrollHolder);

        preppedPanel = layoutPanel;
        //preppedPanel = leftPanel;
    }

    public void setPanel(JPanel panel) {
        System.out.println(" new Panel set");
        preppedPanel = panel;
    }

    private void addListeners() {
    }

    public JFrame getFrame() {
        return frame;
    }
}
