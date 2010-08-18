/*
 * Copyright 2010 TagAPrice.org
 * 
 * Licensed under the Creative Commons License. You may not
 * use this file except in compliance with the License. 
 *
 * http://creativecommons.org/licenses/by-nc/3.0/
*/

/**
 * Project: TagAPrice
 * Filename: ReceiptHandlerImpl.java
 * Date: 30.05.2010
*/
package org.tagaprice.server.rpc;

import java.util.ArrayList;
import java.util.Date;

import org.tagaprice.shared.Address;
import org.tagaprice.shared.Price;
import org.tagaprice.shared.ProductData;
import org.tagaprice.shared.Quantity;
import org.tagaprice.shared.ReceiptData;
import org.tagaprice.shared.ShopData;
import org.tagaprice.shared.Unit;
import org.tagaprice.shared.rpc.ReceiptHandler;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class ReceiptHandlerImpl extends RemoteServiceServlet implements ReceiptHandler {

	@Override
	public ReceiptData get(Long id) throws IllegalArgumentException {
		//MockMock
		ReceiptData receiptData;
		
		if(id==0){
			ArrayList<ProductData> myProducts = new ArrayList<ProductData>();
			receiptData = new ReceiptData(
					15L, 2,
					"Default name", 2,
					new Date(), 
					0, 
					null, 
					myProducts,
					true);
		}else if(id==15) {
			ArrayList<ProductData> myProducts = new ArrayList<ProductData>();	
			receiptData = new ReceiptData(
					15L, 3,
					"Default name", 2,
					new Date(), 
					0, 
					null, 
					myProducts,
					true);
		}else{
			ArrayList<ProductData> myProducts = new ArrayList<ProductData>();
			ProductData p = new ProductData(152L, 8, "Grouda geschnitten", 2, 15L, 16L, "logo.png", new Quantity(260, new Unit(23, 3, "g", 2, null, 0)));
			p.setAvgPrice(new Price(325, 23, 8, "€", 2));
			myProducts.add(p);
			p = new ProductData(120L, 3, "Ja!Natürlich Milch 1L", 2, 15L, 16L, "logo.png", new Quantity(1, new Unit(24, 4, "l", 2, null, 0)));
			p.setAvgPrice(new Price(98, 23, 8, "€", 2));
			myProducts.add(p);
			
			receiptData = new ReceiptData(
					18L, 1,
					"Christmas shopping", 2,
					new Date(), 
					0, 
					new ShopData(15, 9, "Billa Flossgasse", 3, 30l, "logo.png", new Address("Flossgasse 1A", "1020 Wien", "Austria")), 
					myProducts,
					false);
		}
		
		
		
		return receiptData;
	}

}
