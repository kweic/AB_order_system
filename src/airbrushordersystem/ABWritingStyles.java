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
public enum ABWritingStyles {
        Print("print"), Script("script"), Scratch("scratch"),
        Block("block"), SWIRL("swirl"), OLDENGLISH("old english"),
        NONE("none");
    
    private String style;

    private ABWritingStyles(String style) {
        this.style = style;
    }

    public String getGenre() {
        return this.style;
    }

    public String toString() {
        return getGenre();
    }
}
