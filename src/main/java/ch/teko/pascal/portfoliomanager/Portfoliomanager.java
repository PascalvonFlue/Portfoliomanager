package ch.teko.pascal.portfoliomanager;

import java.io.IOException;
import javax.swing.JPanel;

/**
 *
 * @author Pasca
 */
public class Portfoliomanager extends JPanel{
    
    public static void main(String[] args) throws IOException {
        User Pascal = new User("Pascal", "von Flüe");
        Stock NIO = new Stock("NIO", 11, 50);
        Stock INTC = new Stock("INTC", 10, 100);
        Stock TSLA = new Stock("TSLA",100,30);
        Pascal.hld.add(NIO);
        Pascal.hld.add(INTC);
        Pascal.hld.add(TSLA);
        System.out.println(Pascal.hld.getROI_currency());
        System.out.println(Pascal.hld.getROI_percent());
        System.out.println(Pascal.hld.getHoldingsValue());
        
        GraphMaker graph = new GraphMaker();
        GraphMaker.OHLCGraph test = graph.new OHLCGraph(NIO);
        
        GraphMaker.PieChartHoldings test1 = graph.new PieChartHoldings(Pascal.hld);
        
    }
}
