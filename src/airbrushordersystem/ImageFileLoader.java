/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package airbrushordersystem;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.imageio.ImageIO;

public class ImageFileLoader {

    //ImageDataGetter dataGetter;
    //private String directoryPath;
    ArrayList<String> filetypes;
    ArrayList<AirbrushDesign> designs;
    ProgressReporter progressWindow;
    private int width;

    public ImageFileLoader(ArrayList<AirbrushDesign> designs, int width) {
        filetypes = new ArrayList(Arrays.asList(".jpg", ".JPG", ".bmp", ".JPEG", ".PNG", ".TIF"));
        //dataGetter = new ImageDataGetter();
        this.designs = designs;
        progressWindow = new ProgressReporter();
        progressWindow.run();
        progressWindow.updateText("test");
        this.width = width;

        //directoryPath = path;
    }

    public void makeIcons() {
        int i = 0;
        for (AirbrushDesign design : designs) {
            sizeSaveImage(design);

            progressWindow.updateProgressBar((int) (((i + 1.00) * 100) / (designs.size())));
            progressWindow.updateText("sizing: " + design.getTitle());
            i++;
        }
    }

    private BufferedImage sizeImage(BufferedImage bi) {
        int height = getNewHeight(bi.getWidth(), bi.getHeight());
        //System.out.println("getW: " + bi.getWidth() + " getH: " + bi.getHeight());
        //System.out.println("wid: " + width + " high: " + height);
        Image sizedBi = bi.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage bImg = new BufferedImage(width, height, BufferedImage.SCALE_SMOOTH);

        Graphics2D g2d = bImg.createGraphics();
        g2d.drawImage(sizedBi, 0, 0, null);
        g2d.dispose();

        return bImg;
    }

    private int getNewHeight(double origWidth, int origHeight) {
        return (int) (origHeight * (width / origWidth));
    }

    private void checkCreateIconFolder(File directory) {
        if (!directory.exists()) {
            //directory.mkdir();
            new File(new File("").getAbsolutePath() + "/icons/").mkdirs();
           // System.out.println("making dir: " + file);
        }
    }

    private boolean iconExists(File file) {
        System.out.println("   exists? "+file.toString()+" "+file.exists());
        return file.exists();
    }

    public void sizeSaveImage(AirbrushDesign design) { //makes the icon sized version of each image
        System.out.println("ImageFileLoader, sizeSaveImage: " + design.getImagePath());
        System.out.println("   title"+design.getTitle());
        try {
            //BufferedImage bi = new BufferedImage(path);
            File outputfile = new File(new File("").getAbsolutePath() + "\\icons\\" + design.getDesignNumber() + "" + design.getTitle() + "_icon_Size"+width+".jpg");
            if (!iconExists(outputfile)) {
                System.out.println("    file not existant, making file");
                BufferedImage bi = ImageIO.read(new File(design.getImagePath()));
                BufferedImage biSized = sizeImage(bi);

                design.setIconPath(outputfile.toString());
                //System.out.println("dir: " + outputfile.toString());
                checkCreateIconFolder(outputfile);
                ImageIO.write(biSized, "jpg", outputfile);
            }
        } catch (IOException e) {
            System.out.println("save image failed");
        }
    }

    public ArrayList<AirbrushDesign> gatherImages(String directory) {
        System.out.println("ImageFileLoader, gatherImages in: "+directory);
        //ArrayList<AirbrushDesign> files = new ArrayList();
        File folder = new File(directory);
        File[] listOfFiles = folder.listFiles();
        AirbrushDesign design;
        int i = 0;
        for (File file : listOfFiles) {
            //System.out.println("doing: "+file.getAbsolutePath());
            if (progressWindow.stillRunning()) {
                if (file.isFile()) {
                    if (fileCheck(file.getName())) {
                        if (noDuplicate(file.getName())) {
                            design = new AirbrushDesign(file.getPath());
                            design.setFileName(file.getName());
                            designs.add(design);
                            System.out.println("    last design found: "+designs.get(designs.size()-1).getFileName());
                        }
                    }
                } else if (file.isDirectory()) {
                    gatherImages(file.getPath());
                }
                i++;
                // System.out.println("setting progress: " + (((i + 1.00) * 100) / (listOfFiles.length + 1)));
                //System.out.println(" "+i / (listOfFiles.length + 1) * 100);
                progressWindow.updateProgressBar((int) (((i + 1.00) * 100) / (listOfFiles.length + 1)));
                progressWindow.updateText(file.getName());
            }
        }
        progressWindow.closeWindow();
        // System.out.println("files gathered: "+designs.size());
        return designs;
    }

    private boolean noDuplicate(String foundPath) {
        for (AirbrushDesign design : designs) {
            if (design.getImagePath().contains(foundPath)) {
                return false;
            }
        }
        return true;
    }

    private boolean fileCheck(String fileName) {
        for (String fileType : filetypes) {
            if (fileName.toLowerCase().contains(fileType.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    /*
    public void handleImage(String directory, AirbrushDesign design) {
        //size the image, send name off to imagedatagetter
        BufferedImage img = null;
//        String noImagePath = "src/images/noImageAvailable.jpg";
        String noImagePath = "noImageAvailable.jpg";
        //System.out.println("trying to get: " + fileName);
        try {
            //img = ImageIO.read(new File(fileName)); //read files
            System.out.println("looking for picture: " + directory + "/" + design.getImagePath());
            img = ImageIO.read(new File(directory + "/" + design.getImagePath()));
            //file.setBufferedImage(img);
            //file.setHasImage(true);
        } catch (IOException e) {
            //img = no cover image
            System.out.println("failed to find image");
            //file.setHasImage(false);
        }
    }
        */

    /*
    public void saveImage(String fileLink) {
        System.out.println("saveImage: " + fileLink);
        //  try {
        //BufferedImage bi = IMDBminer.getImage(fileLink);
        //File outputfile = new File(absolutePath + "" + imageDirectory + "/" + IMDBminer.getSaveLink());

        //ImageIO.write(bi, "jpg", outputfile);
        System.out.println("saved image: " + fileLink);
        //} catch (IOException e) {
        System.out.println("save image failed");

        //   }
    }
    */
}
