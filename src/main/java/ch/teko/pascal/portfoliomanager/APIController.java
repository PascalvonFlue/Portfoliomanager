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

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.jayway.jsonpath.JsonPath;
import java.io.FileInputStream;
import java.util.Properties;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;

/**
 *
 * @author Pasca
 */
public class APIController {
    public final String symbol;
    public final String name;
    
    public APIController(String _symbol) throws IOException{
        this.symbol = _symbol;
        this.name = getName();
    }
    
    public String getName() throws IOException{
        Stock stock = YahooFinance.get(this.symbol);
        return stock.getName();
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
            ArrayList <Double> stockData = new ArrayList <>();
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
    
    public String getStockInfo(){
       Properties properties = new Properties();
       System.out.println(this.name);
       try {
            HttpTransport httpTransport = new NetHttpTransport();
            HttpRequestFactory requestFactory = httpTransport.createRequestFactory();
            JSONParser parser = new JSONParser();
            GenericUrl url = new GenericUrl("https://kgsearch.googleapis.com/v1/entities:search");
            url.put("query", this.name);
            url.put("types", String.format("(Corporation.tickerSymbol(%s),Corporation.keywords(%s))",this.symbol,this.symbol));
            url.put("limit", "10");
            url.put("indent", "true");
            url.put("prefix", "true");
            url.put("key", "AIzaSyCUjpoxJlr6w7na0mMD6A9Cw0TKmRrBaUw");
            HttpRequest request = requestFactory.buildGetRequest(url);
            HttpResponse httpResponse = request.execute();
            JSONObject response = (JSONObject) parser.parse(httpResponse.parseAsString());
            JSONArray elements = (JSONArray) response.get("itemListElement");
            //System.out.println(elements);
            for(Object element : elements){
                String result = JsonPath.read(element, ".detailedDescription.articleBody").toString();
                if(!(result.replace("[]", "").isBlank())){
                    return result.replace("[]", "");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "No Data Found!!!";
    }
    
    public void getWikiInfo(){
        List<String> result = new ArrayList();
        
        final String BASE_URL="https://en.wikipedia.org/api/rest_v1/page/summary/";
        String subject= this.name;
        String displayTitle="";
        String imageURL="";
        String extractText="";
        
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(BASE_URL+subject)
                .get()
                .build();
        try {
            Response response=client.newCall(request).execute();
            String data = response.body().string();
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject)parser.parse(data);
            if(jsonObject != null){
                if (jsonObject.get("displaytitle") != null) displayTitle= (String) jsonObject.get("displaytitle");
                JSONObject jsonObjectOriginalImage = (JSONObject) jsonObject.get("originalimage");
                if (jsonObject.get("source") != null) imageURL = (String) jsonObjectOriginalImage.get("source");
                if (jsonObject.get("extract") != null) extractText = (String)jsonObject.get("extract");
            }
            //return result;
        }
        catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        result.add(displayTitle);
        result.add(imageURL);
        result.add(extractText);
        System.out.println(displayTitle + imageURL + extractText);
    }
}
