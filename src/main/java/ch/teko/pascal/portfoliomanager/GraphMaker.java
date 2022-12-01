package ch.teko.pascal.portfoliomanager;

import java.awt.Color;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.CandlestickRenderer;
import org.jfree.data.time.FixedMillisecond;
import org.jfree.data.time.ohlc.OHLCSeries;
import org.jfree.data.time.ohlc.OHLCSeriesCollection;
import org.jfree.data.xy.DefaultHighLowDataset;

/**
 *
 * @author Pasca
 */
public class GraphMaker {
    
    
    public class OHLCGraph{
        private String symbol;
        private DefaultHighLowDataset dataset;
        private JFreeChart chart;
        
        private LinkedHashMap<Date, List> history = new LinkedHashMap<Date, List>();
        public OHLCGraph(Stock obj) throws IOException{
            this.symbol = obj.symbol;
            this.history = obj.getHistory();
            this.dataset = createDataset(this.symbol, this.history);
            this.chart = createChart(this.dataset);
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
        
        private JFreeChart createChart(final DefaultHighLowDataset dataset){
            final JFreeChart chart = ChartFactory.createCandlestickChart(this.symbol, "Time", "Price", dataset, false);
            return chart;
        }
        
        public JFreeChart getChart(){
            return this.chart;
        }
    }
    
}
