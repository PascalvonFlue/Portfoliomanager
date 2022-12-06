package ch.teko.pascal.portfoliomanager;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.DefaultHighLowDataset;

/**
 *
 * @author Pasca
 */
public class GraphMaker extends JPanel{

    
    public class OHLCGraph{
        private String symbol;
        private DefaultHighLowDataset dataset;
        private LinkedHashMap<Date, List> history = new LinkedHashMap<Date, List>();
        
        public OHLCGraph(Stock obj) throws IOException{
            this.symbol = obj.symbol;
            this.history = obj.getHistory();
            this.dataset = createDataset(this.symbol, this.history);
            createChart(this.dataset);
        }
        
        private DefaultHighLowDataset createDataset(String symbol, LinkedHashMap history){
            int size = history.size();
            Date[] date = new Date[size];
            double[] high = new double[size];
            double[] low = new double[size];
            double[] open = new double[size];
            double[] close = new double[size];
            double[] volume = new double[size];
            int i = 0;
            Iterator<Map.Entry<Date, List>> it = history.entrySet().iterator();
            while(it.hasNext()){
                Map.Entry<Date, List> entry = it.next();
                date[i] = entry.getKey();
                high[i] = (double)entry.getValue().get(0);
                low[i] = (double)entry.getValue().get(1);
                open[i] = (double)entry.getValue().get(2);
                close[i] = (double)entry.getValue().get(3);
                volume[i] = (double)entry.getValue().get(4);
                i++;
            }
            DefaultHighLowDataset data = new DefaultHighLowDataset("", date, high, low, open, close, volume);
            return data;
        }
        
        private void createChart(final DefaultHighLowDataset dataset){
            final JFreeChart chart = ChartFactory.createCandlestickChart(this.symbol, "Time", "Price", dataset, false);
            final ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.setHorizontalAxisTrace(true);
            chartPanel.setVerticalAxisTrace(true);
            chartPanel.setVisible(true);
            JFrame frame = new JFrame();
            frame.setBounds(100, 100, 676, 449);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(chartPanel);
            frame.setVisible(true);
        }
    }
    
    
    public class PieChartStats{
        private float value;
        private float roi;
        
        public PieChartStats(Holdings h){
            this.value = h.getHoldingsValue();
            this.roi = h.getROI_currency();
        }
        
        private void createDataset(){
            
        }
        
        private void createChart(){
            
        }
    }
    
    
    public class PieChartHoldings{
        private Holdings h1 = null;
        private DefaultPieDataset dataset = null;
        private ChartPanel chartPanel = null;
        
        public PieChartHoldings(Holdings h){
            this.h1 = h;
            this.dataset = createDataset();
            this.chartPanel = createChart(this.dataset);
        }
        
        private DefaultPieDataset createDataset(){
            DefaultPieDataset dataset = new DefaultPieDataset( );
            List<Stock> stockholdings = this.h1.holdings;
            for(Stock element: stockholdings){
                dataset.setValue(element.symbol, new Double(element.calcPosition()));
            }
            return dataset;
        }
        
        private ChartPanel createChart(final DefaultPieDataset dataset){
            JFreeChart chart = ChartFactory.createPieChart("My Holdings", dataset, true, true, false);
            final ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.setDomainZoomable(true);
            chartPanel.setVisible(true);
            chartPanel.setSize(600, 300);
            /*
            JFrame frame = new JFrame();
            frame.setBounds(100, 100, 676, 449);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(chartPanel);
            frame.setVisible(true);
            */
            return chartPanel;
        }
        
        public ChartPanel getChart(){
            return this.chartPanel;
        }
    }
    
}
