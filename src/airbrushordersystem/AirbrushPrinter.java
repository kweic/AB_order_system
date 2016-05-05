/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package airbrushordersystem;

import java.awt.print.*;
import java.awt.*;

public class AirbrushPrinter
        implements Printable {

    public int print(Graphics g, PageFormat pf, int page)
    //public int print(Graphics g, int page)
            throws PrinterException {

        // We have only one page, and 'page'
        // is zero-based
        if (page > 0) {
            return NO_SUCH_PAGE;
        }

        // User (0,0) is typically outside the
        // imageable area, so we must translate
        // by the X and Y values in the PageFormat
        // to avoid clipping.
        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());

        // Now we perform our rendering
        g.drawString("Hello world!", 100, 100);

        // tell the caller that this page is part
        // of the printed document
        return PAGE_EXISTS;
    }
}
