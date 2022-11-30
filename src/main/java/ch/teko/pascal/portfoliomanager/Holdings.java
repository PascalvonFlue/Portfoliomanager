/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ch.teko.pascal.portfoliomanager;

import java.util.ArrayList;

/**
 *
 * @author Pasca
 */
public class Holdings {
    ArrayList<Stock> holdings = new ArrayList<Stock>();
    
    public void add(Stock stock){
        this.holdings.add(stock);
    }
}
