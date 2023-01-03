
package ch.teko.pascal.portfoliomanager;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 *
 * @author Pasca
 */
public class Stock {
    public final String symbol;
    public final String name;
    public float currentPrice;
    public float purchasePrice;
    public float mouvment;
    public int shares;
    public APIController DTO = null;
    
    public Stock(String _symbol, float _purchasePrice, int _shares) throws IOException, NullPointerException{
        this.symbol = _symbol;
        this.purchasePrice = _purchasePrice;
        this.shares = _shares;
        
        this.DTO = new APIController(this.symbol);
        this.name = this.DTO.getName();
        this.currentPrice = this.DTO.getPrice();
        this.mouvment = this.DTO.getMouvment();
    }
    
    public float calcROI_curreny(){
        return this.currentPrice*this.shares - this.purchasePrice*this.shares;
    }
    
    public float calcROI_percent(){
        return (this.currentPrice/this.purchasePrice)*100 - 100;
    }
    
    public float calcPosition(){
        return this.currentPrice*this.shares;
    }
    
    public float calcInvestment(){
        return this.purchasePrice*this.shares;
    }
    
    public LinkedHashMap getHistory() throws IOException{
        LinkedHashMap<Date, List> history = new LinkedHashMap<Date, List>();
        history = DTO.getOHLCdata();
        return history;
    }
    
    public void update() throws IOException{
        this.currentPrice = this.DTO.getPrice();
    }
}
