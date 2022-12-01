package ch.teko.pascal.portfoliomanager;

import java.io.IOException;

/**
 *
 * @author Pasca
 */
public class Portfoliomanager {
    
    public static void main(String[] args) throws IOException {
        User Pascal = new User("Pascal", "von Flüe");
        Stock NIO = new Stock("NIO", 11, 50);
        Stock INTC = new Stock("INTC", 10, 100);
        Pascal.hld.add(NIO);
        Pascal.hld.add(INTC);
        System.out.println(Pascal.hld.getROI_currency());
        System.out.println(Pascal.hld.getROI_percent());
        System.out.println(Pascal.hld.getHoldingsValue());
        Pascal.hld.remove(INTC);
        System.out.println(Pascal.hld.getROI_currency());
        System.out.println(Pascal.hld.getROI_percent());
        System.out.println(Pascal.hld.getHoldingsValue());
        
        GraphMaker graph = new GraphMaker();
        GraphMaker.OHLCGraph test = graph.new OHLCGraph(NIO);
        
        
    }
}
