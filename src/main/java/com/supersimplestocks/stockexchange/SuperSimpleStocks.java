package com.supersimplestocks.stockexchange;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.supersimplestocks.stockexchange.domain.Stock;
import com.supersimplestocks.stockexchange.util.StockUtil;

public class SuperSimpleStocks {
	
	private Stock stock = null;
	
	public static void main(String[] args) {
		SuperSimpleStocks superSimpleStocks = new SuperSimpleStocks();
		superSimpleStocks.promptOperation();
//		DataHolder dataHolder = DataHolder.getInstance();
//		Stock stock = dataHolder.getStock("TEA");
//		System.out.println(stock);
	}
	
	private void promptOperation() {
		
		String stockSymbol = getInput("Please enter the stock symbol : ");
		DataHolder dataHolder = DataHolder.getInstance();
		//Stock stock = null;
		if(stockSymbol != null){
			this.stock = dataHolder.getStock(stockSymbol.toUpperCase());
		}
		
		if(this.stock != null){
			System.out.println(this.stock);
			promptMarketPrice();
		} else {
			System.out.println("Invalid stock symbol! ");
			promptOperation();
		}
		
		fetchStockPerformance();
		
	}
	
	private void promptMarketPrice() {
		String marketPriceStr = getInput("Please enter the market price for " +  this.stock.getStockSymbol() + ": ");
		double marketPrice = 0;
		try {
			marketPrice = Double.parseDouble(marketPriceStr);
			this.stock.setMarketPrice(marketPrice);
		} catch (Exception e) {
			System.out.println("Invalid Market Price!");
			promptMarketPrice();
		}
		
	}

	private void fetchStockPerformance() {
		double dividendYield = 0;
		if("Common".equals(this.stock.getType())){
			dividendYield = StockUtil.getDivYield(this.stock.getLastDividend(), this.stock.getMarketPrice());
		} else if("Preferred".equals(this.stock.getType())){
			dividendYield = StockUtil.getDivYield(this.stock.getFixedDividend(), this.stock.getParValue(), this.stock.getMarketPrice());
		}
		
		double peRatio = StockUtil.getPERatio(this.stock.getLastDividend(), this.stock.getMarketPrice());
		
		System.out.println("Dividend Yield : " + dividendYield);
		System.out.println("P/E Ratio : " + peRatio);
	}

	private String getInput(String description) {
	    System.out.print(description + ": ");
	    String input = null;

	    InputStreamReader stream = null;
	    BufferedReader reader = null;
	    try {
	        // Open a stream to stdin
	        stream = new InputStreamReader(System.in);

	        // Create a buffered reader to stdin
	        reader = new BufferedReader(stream);

	        // Try to read the string
	        input = reader.readLine();           
	    } catch (IOException e) {
	        e.printStackTrace();
	    } 

	    return input;
	}

}
