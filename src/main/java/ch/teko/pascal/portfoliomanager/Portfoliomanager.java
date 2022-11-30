
package ch.teko.pascal.portfoliomanager;

import java.io.IOException;

/**
 *
 * @author Pasca
 */
public class Portfoliomanager {
    
    public static void main(String[] args) throws IOException {
        Holdings h1 = new Holdings();
        Stock NIO = new Stock("NIO", 11, 50);
        Stock INTC = new Stock("INTC", 10, 100);
        h1.add(NIO);
        h1.add(INTC);
        System.out.println(h1.getROI_currency());
        System.out.println(h1.getROI_percent());
        System.out.println(h1.getHoldingsValue());
        h1.remouve(INTC);
        System.out.println(h1.getROI_currency());
        System.out.println(h1.getROI_percent());
        System.out.println(h1.getHoldingsValue());
    }
}
