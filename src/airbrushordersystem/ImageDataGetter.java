package airbrushordersystem;

import java.util.ArrayList;
import java.util.Arrays;

public class ImageDataGetter {

    ArrayList<Character> removables;
    //ArrayList<ABItems> items;
    

    public ImageDataGetter() {
        removables = new ArrayList(Arrays.asList('_', '-'));
        
    }

    public void getData(AirbrushDesign design) {
        System.out.println("ImageDataGetter, getData: "+design.getImagePath());
        System.out.println("    design name: "+design.getFileName());
        //                                                                   001_Shirt_(Sweet_Design)_$12_block
        String cleanedName = setRemoveItems(design, cleanName(design.getFileName())); // 001 (Sweet Design) $12 block
        cleanedName = setRemoveDesignNumber(design, cleanedName); //             (Sweet Design) $12 block
        cleanedName = setRemoveDesignName(design, cleanedName); //                              $12 block
        cleanedName = setRemoveDesignPrice(design, cleanedName); //                                 block
        setWritingStyles(design, cleanedName);

    }

    private void setWritingStyles(AirbrushDesign design, String name) {
        String nameLower = name.toLowerCase();
        for (ABWritingStyles style : ABWritingStyles.values()) {
            if (nameLower.contains(style.toString().toLowerCase())) {
                design.setWritingStyle(style);
             //   System.out.println("writing style set: " + design.getWritingStyle());
                return;
            }
        }
    }

    private String setRemoveDesignPrice(AirbrushDesign design, String cleanedName) {
        //System.out.println(" price, starting with: "+cleanedName);
        boolean startPrice = false;
        boolean decimalFound = false;
        StringBuilder price = new StringBuilder();
        for (int i = 0; i < cleanedName.length(); i++) {
            if (cleanedName.charAt(i) == '$') {
                //System.out.println(" start price set true");
                startPrice = true;
            }
            //System.out.println("char at: "+cleanedName.charAt(i));
            if (startPrice && (cleanedName.charAt(i) + "").matches("[0-9]")) {
                //System.out.println("starting price");
                if ((!decimalFound && cleanedName.charAt(i) == '.') || cleanedName.charAt(i) != '.') { //won't add 2 decimals
                    //System.out.println("append");
                    if (cleanedName.charAt(i) == '.') {
                        decimalFound = true;
                    }
                    price.append(cleanedName.charAt(i));
                }

            } else if (startPrice && price.length() > 0) { // no match found
                //System.out.println("price to string: "+price.toString());
                design.setPrice(Integer.parseInt(price.toString()));
             //   System.out.println("price set to: " + design.getPrice());
                String nameNoPrice = cleanedName.replace("$" + price, "");
                return nameNoPrice;
            }

        }
        return cleanedName;
    }

    private String setRemoveDesignName(AirbrushDesign design, String cleanedName) {
        boolean nameStart = false;
       // System.out.println("cleaned Start remove design: " + cleanedName);
        StringBuilder designName = new StringBuilder();
        for (int i = 0; i < cleanedName.length(); i++) {
            if (!nameStart && cleanedName.charAt(i) == '(') {
                nameStart = true;
            } else if (cleanedName.charAt(i) == ')') {

                design.setTitle(designName.toString());
               // System.out.println("title set: " + design.getTitle());
                break;
            } else if (nameStart) {

                designName.append(cleanedName.charAt(i));
            }
        }
        //System.out.println("cleanedName: " + cleanedName);
        if (designName.length() != 0) {
            String removedName = cleanedName.replace(design.getTitle(), "");
            removedName = removedName.replace("(", "");
            removedName = removedName.replace(")", "");
            return removedName;
        }else{
        return "Not Found";
        }
    }

    private String setRemoveDesignNumber(AirbrushDesign design, String cleanedName) {
       // System.out.println("remove design start: "+cleanedName);
        int end = 0;
        String designNum = "";
        for (int i = 0; i < cleanedName.length(); i++) {
            if ((cleanedName.charAt(i) + "").matches("[0-9]")) {
                designNum += cleanedName.charAt(i);
            }
            
            if (designNum.length() > 0 && !(cleanedName.charAt(i) + "").matches("[0-9]")) {
                end = i;
                break;
            }
            
            if (cleanedName.charAt(i) == '$') {
                break;
            }
        }
        if (designNum.length() > 0) {
            String nameNoNum = cleanedName.substring(end, cleanedName.length());
            design.setDesignNumber(Integer.parseInt(designNum));
            //System.out.println("design number set: " + design.getDesignNumber());
            return nameNoNum;
        } else {
            design.setDesignNumber(0);
            return cleanedName;
        }
    }

    private String setRemoveItems(AirbrushDesign design, String cleanedName) { //set item types available to design, removes them from string
        String name = cleanedName;
        for (ABItems item : ABItems.values()) {
           // System.out.println("looking for: "+item.toString());
            if (cleanedName.toLowerCase().contains(item.toString().toLowerCase() + " ")) {
               // System.out.println("Item found: " + item.toString());
                design.addItem(item);
                name = name.toLowerCase().replace(item.toString().toLowerCase() + "", "");
            }
        }
        return name;
    }

    private String cleanName(String fileName) {
        System.out.println("ImageDataGetter, cleanName: "+fileName);
        String cleanedName = fileName;
        for (char remove : removables) {
            
            cleanedName = cleanedName.replace(remove, ' ');
            System.out.println("   "+cleanedName);
        }
        //System.out.println("cleaned Name in clean: " + cleanedName);
        return cleanedName;
    }

}
