/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package airbrushordersystem;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 *
 * @author kw
 */
public class SaveLoadSettings {

    //ArrayList<ABCategory> categories;
    public SaveLoadSettings() {
        //categories
    }
    
    private static void CheckMakeDir(String dir){
        System.out.println("checking for directory: "+dir);
        File directory = new File(dir);
          if (!directory.exists()) {
            //directory.mkdir();
            directory.mkdirs();
        }
    }

    public void saveDesigns(ArrayList<AirbrushDesign> designs) {
        Collections.sort(designs);
        System.out.println("saving: "+designs.size());
        BufferedWriter writer;
        String saveDirectory = new File("").getAbsolutePath()+"\\saveSettings\\";
        try {
            CheckMakeDir(saveDirectory);
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(saveDirectory+"savedDesigns.txt"), "UTF-8"));

            int i = 0;
            for (AirbrushDesign design : designs) {
                writer.write(design.getDesignNumber()+"\r\n");
                writer.write(design.getTitle() + "\r\n");
                writer.write(design.getPrice() + "\r\n");
                writer.write(design.getCategories() + "\r\n");
                writer.write(design.getItems() + "\r\n");
                writer.write(design.getImagePath() + "\r\n");
                //    writer.write(design.getFileName()+"\n");
                writer.write(design.getIconPath() + "\r\n");
                writer.write(design.getNameCount() + "\r\n");
                writer.write(design.getWritingStyle() + "\r\n");
                System.out.println("saving design: "+i);
                i++;
            }
            writer.close();

        } catch (IOException e) {
            System.out.println("save failed");
        }
    }
    
    

    public ArrayList<AirbrushDesign> loadDesigns() {
        System.out.println("SaveLoadSettings, loadDesigns");
        ArrayList<String> abStringLoad = new ArrayList();
        File loadFile = new File(new File("").getAbsolutePath() + "\\saveSettings\\savedDesigns.txt");
        if (loadFile.exists()) {
            Scanner reader;

            try {
                reader = new Scanner(loadFile, "UTF-8");
                int i = 0;
                while (reader.hasNextLine()) {
                    abStringLoad.add(reader.nextLine());
                    i++;
                }
                /*
                System.out.println("loaded file line count: " + i);
                System.out.println("last lines read: "+abStringLoad.get(abStringLoad.size()-4));
                System.out.println("last lines read: "+abStringLoad.get(abStringLoad.size()-3));
                System.out.println("last lines read: "+abStringLoad.get(abStringLoad.size()-2));
                System.out.println("last lines read: "+abStringLoad.get(abStringLoad.size()-1));
                */
                reader.close();

            } catch (Exception e) {
                //create the loadfile

            }
        }
        if(abStringLoad.size() == 0){
            ArrayList<AirbrushDesign> emptyList = new ArrayList();
            return emptyList;
        }
        
        ArrayList<AirbrushDesign> loadedDesigns = interpretLoad(abStringLoad);
        System.out.println("   loaded Designs size: "+loadedDesigns.size());
        return loadedDesigns;
    }

    private ArrayList<AirbrushDesign> interpretLoad(ArrayList<String> loadedStrings) {
        ArrayList<AirbrushDesign> designsLoaded = new ArrayList();
        AirbrushDesign newDesign;
        for (int i = 0; i <= loadedStrings.size() - 9; i+=9) {
            //System.out.println("line i: "+loadedStrings.get(i));
            //System.out.println("line i+1: "+loadedStrings.get(i+1));
            newDesign = new AirbrushDesign(loadedStrings.get(i + 5));

            newDesign.setDesignNumber(Integer.parseInt(loadedStrings.get(i)));
            newDesign.setTitle(loadedStrings.get(i + 1));
            newDesign.setPrice(Integer.parseInt(loadedStrings.get(i + 2)));
            setCategories(newDesign, loadedStrings.get(i + 3));
            setItems(newDesign, loadedStrings.get(i + 4));
            setIconPath(newDesign, loadedStrings.get(i+6));
            //System.out.println("set name count: "+loadedStrings.get(i+7));
            newDesign.setNameCount(Integer.parseInt(loadedStrings.get(i + 7)));
            //System.out.println("writing style: "+loadedStrings.get(i+8));
            setWritingStyle(newDesign, loadedStrings.get(i + 8));

            designsLoaded.add(newDesign);
        }

        return designsLoaded;
    }
    
    private void setIconPath(AirbrushDesign design, String pathLine){
        if(pathLine.equals("null")){ //this happens if icons exist but a new saveFile is created and icon paths aren't gathered
            design.setIconPath(new File("").getAbsolutePath() + "\\icons\\" + design.getDesignNumber() + "" + design.getTitle() + "_icon.jpg");
        }else{
            design.setIconPath(pathLine);
        }
    }

    private void setWritingStyle(AirbrushDesign design, String styleLine) {
        for (ABWritingStyles style : ABWritingStyles.values()) {
            if (style.toString().toLowerCase().equals(styleLine.toLowerCase())) {
                design.setWritingStyle(style);
                return;
            }
        }
    }

    private void setCategories(AirbrushDesign design, String categoryString) {
        for (ABCategory category : ABCategory.values()) {
            if (categoryString.toLowerCase().contains(category.toString().toLowerCase())) {
                design.addCategory(category);
            }
        }
    }

    private void setItems(AirbrushDesign design, String itemsString) {
        for (ABItems item : ABItems.values()) {
            if (itemsString.toLowerCase().contains(item.toString().toLowerCase())) {
                design.addItem(item);
            }
        }
    }

}
