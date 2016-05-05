/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package airbrushordersystem;

/**
 *
 * @author kw
 */
public enum ABItems {
    SHIRT("Shirt"), HAT("Hat"), KEYCHAIN("Keychain"),
    SWEATSHIRT("Sweatshirt"), TANKTOP("Tanktop"), FLATBILL("Flatbill"), 
    POSTER("Poster"), BEANIE("Beanie"), BIB("Bib"), LANYARD("Lanyard"), 
    WRISTBAND("Wristband"), HEADBAND("Headband"), OWN("Own"), ONESIE("Onesie"), OTHER("Other"),
    TINY("Tiny"), SMALL("Small"), MEDIUM("Medium"), LARGE("Large");
    
    private String item;

    private ABItems(String item) {
        this.item = item;
    }

    public String getGenre() {
        return this.item;
    }

    public String toString() {
        return getGenre();
    }
}
