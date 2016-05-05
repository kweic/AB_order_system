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
public class testLoader {

    ProgressReporter progressBar;
    //CancelClickListener listener;

    public testLoader() {
        progressBar = new ProgressReporter();
        progressBar.run();
    }

    public void runSequence() {

        for (int i = 1; i < 200; i++) {
            if (progressBar.stillRunning()) {
                for (int x = 0; x < 100_000_000; x++) {
                    for (int y = 0; y < 100_000_000; y++) {

                    }
                }
                progressBar.updateText(i + "     "+ i+ "test testing bla a b c d e f g h i j k l m n o p q r s t u v w x y z 1 2  3 4 5 6 7 8 9 0    test");
                progressBar.updateProgressBar((int) (((i + 1.00) * 100) / 200.00));
                //System.out.println("i: "+i);
            }
        }
    }
}
