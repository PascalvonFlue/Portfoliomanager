/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ch.teko.pascal.portfoliomanager;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    
    public LinkedHashMap getOHLCdata() throws IOException{
        LinkedHashMap<Date, List> historyStock = new LinkedHashMap<Date, List>();
        Date date;
        
        Calendar from = Calendar.getInstance();
        Calendar to = Calendar.getInstance();
        from.add(Calendar.YEAR, -1);
        
        Stock stock = YahooFinance.get(this.symbol, from, to, Interval.DAILY);
        List<HistoricalQuote> stockHistQuotes = stock.getHistory();
        for(HistoricalQuote element : stockHistQuotes){
            ArrayList <Object> stockData = new ArrayList <>();
            stockData.add(0, element.getHigh().doubleValue());
            stockData.add(1, element.getLow().doubleValue());
            stockData.add(2, element.getOpen().doubleValue());
            stockData.add(3, element.getClose().doubleValue());
            stockData.add(4, element.getVolume().doubleValue());
            date = element.getDate().getTime();
            historyStock.put(date, stockData);
        }
        return historyStock;
    }
}
