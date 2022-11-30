/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ch.teko.pascal.portfoliomanager;

import java.io.IOException;

/**
 *
 * @author Pasca
 */
public class Stock {
    public final String symbol;
    public float currentPrice;
    public float purchasePrice;
    public float mouvment;
    public int shares;
    public APIController DTO = null;
    
    public Stock(String _symbol, float _purchasePrice, int _shares) throws IOException{
        this.symbol = _symbol;
        this.purchasePrice = _purchasePrice;
        this.shares = _shares;
        this.DTO = new APIController(this.symbol);
        this.currentPrice = this.DTO.getPrice();
        this.mouvment = this.DTO.getMouvment();
        
    }
    
    public float calcROI_curreny(){
        return this.currentPrice - this.purchasePrice;
    }
    
    public float calcROI_percent(){
        return (this.currentPrice/this.purchasePrice)*100 - 100;
    }
    
    public void update() throws IOException{
        this.currentPrice = this.DTO.getPrice();
    }
}
