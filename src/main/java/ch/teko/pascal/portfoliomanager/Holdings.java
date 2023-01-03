/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ch.teko.pascal.portfoliomanager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 *
 * @author Pasca
 */
public class Holdings {
    List<Stock> holdings = new ArrayList<Stock>();
    private float roi_curreny; //Total amount gain or loss in Currency
    private float roi_percent; // Average gain or loss in percent
    private float totalholdings;
    private float investment;
    private float realROI = 0;
    
    public void add(Stock stock) throws IOException{
        this.holdings.add(stock);
        this.investment = this.investment + stock.purchasePrice;
        updatePortfolio();
    }
    
    public void remove(Stock stock, float price) throws IOException{
        this.holdings.remove(stock);
        this.realROI = this.realROI + (stock.shares * price) - (stock.purchasePrice * stock.shares);
        updatePortfolio();
    }
    
    public void adjustStock(Stock stock,float price, int amount) throws IOException{
        stock.shares = stock.shares - amount;
        this.realROI = this.realROI + (price * amount) - (stock.purchasePrice * amount);
        updatePortfolio();
    }
    
    public void updatePortfolio() throws IOException{ //Update through gui, leave public
        for (Stock stobj : holdings){
            stobj.update();
        }
        calcROI();
    }
    
    public float getROI_currency(){
        return this.roi_curreny;
    }
    
    public float getROI_percent(){
        return this.roi_percent;
    }
    
    public float getHoldingsValue(){
        return this.totalholdings;
    }
    
    public float getTotalInvestment(){
        return this.investment;
    }
    
    public float getRealROI(){
        return realROI;
    }
    
    public LinkedHashMap getHodingValuebyStock(){
        LinkedHashMap<String, Float> stats = new LinkedHashMap<String, Float>();
        for(Stock obj: this.holdings){
            stats.put(obj.symbol, obj.currentPrice * obj.shares);
        }
        return stats;
    }
    
    private void calcROI(){ 
        this.roi_curreny = 0;
        this.roi_percent = 0;
        this.totalholdings = 0;
        this.investment = 0;
        for (Stock stobj : this.holdings){
            this.totalholdings = this.totalholdings + stobj.calcPosition();
            this.investment = this.investment + stobj.calcInvestment();
        }
        this.roi_percent = (100/this.investment * this.totalholdings) - 100;
        this.roi_curreny = this.investment/100*this.roi_percent;
    }
    
}
