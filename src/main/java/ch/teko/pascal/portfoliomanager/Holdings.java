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
    private float realROI = 0;
    
    public void add(Stock stock) throws IOException{
        this.holdings.add(stock);
        updatePortfolio();
    }
    
    public void remove(Stock stock) throws IOException{
        this.holdings.remove(stock);
        this.realROI = stock.calcROI_curreny() + this.realROI;
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
        for (Stock stobj : this.holdings){
            this.roi_curreny = this.roi_curreny + stobj.calcROI_curreny();
            this.roi_percent = this.roi_percent + stobj.calcROI_percent();
            this.totalholdings = this.totalholdings + stobj.calcPosition();
        }
        this.roi_percent = this.roi_percent/holdings.size();
    }
    
}
