/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ch.teko.pascal.portfoliomanager;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

/**
 *
 * @author Pasca
 */
public class APIController {
    public final String symbol;
    
    public APIController(String _symbol){
        this.symbol = _symbol;
    }
    
    public float getPrice() throws IOException{
        Stock stock = YahooFinance.get(this.symbol);
        BigDecimal price = stock.getQuote().getPrice();
        System.out.println(price);
        return price.floatValue();
    }
    
    public float getMouvment()throws IOException{
        Stock stock = YahooFinance.get(this.symbol);
        BigDecimal delta = stock.getQuote().getChangeInPercent();
        System.out.println(delta);
        return delta.floatValue();
    }
    
    public void getOHLCdata() throws IOException{
        LinkedHashMap<String, BigDecimal[]> historyStock = new LinkedHashMap<String, BigDecimal[]>();
        BigDecimal stockData [] = null; //1: High, 2: Low, 3: Open, 4: Close
        Calendar from = Calendar.getInstance();
        Calendar to = Calendar.getInstance();
        from.add(Calendar.YEAR, -1);
        Stock stock = YahooFinance.get(symbol, from, to, Interval.WEEKLY);
        List<HistoricalQuote> stockHistQuotes = stock.getHistory();
        System.out.println(stockHistQuotes);
        
        for(HistoricalQuote element : stockHistQuotes){
            stockData[0] = element.getHigh();
            stockData[1] = element.getLow();
            stockData[2] = element.getOpen();
            stockData[3] = element.getClose();
            historyStock.put(symbol, stockData);
            stockData = null;
        }
    }
}
