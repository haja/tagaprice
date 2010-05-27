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
 * Filename: TaPManager.java
 * Date: 18.05.2010
*/
package org.tagaprice.shared;

import java.util.ArrayList;
import java.util.Date;
import org.tagaprice.client.InfoBox;
import org.tagaprice.client.ProductPreview;
import org.tagaprice.client.SearchWidget;
import org.tagaprice.client.TypeDraftService;
import org.tagaprice.client.TypeDraftServiceAsync;
import org.tagaprice.client.UIManager;



import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;


/**
 * DAO Manger
 *
 */
public class TaPManagerImpl implements TaPManager {
	
	private static TaPManager TaPMng;	
	private static UIManager uiMng = new UIManager();
	TypeDraftServiceAsync greetingService = GWT.create(TypeDraftService.class);
	
	
	public static TaPManager getInstance(){
		if(TaPMng==null){
			TaPMng=new TaPManagerImpl();
			init();
		}
		return TaPMng;
	}
	
	private static void init(){
		History.addValueChangeHandler(new ValueChangeHandler<String>() {
			
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				String[] historyToken = event.getValue().split("&");
				//TODO Create a Split "=" parser
				if(historyToken[0].equals("receipt/edit")){
					String[] equalToken = historyToken[1].split("=");
					ReceiptData emptyData = TaPMng.getReceipt(Long.parseLong(equalToken[1]));
					uiMng.showReceipt(emptyData);
				}else if(historyToken[0].equals("product/get")){
					String[] equalToken = historyToken[1].split("=");
					TaPMng.showProductPage(Long.parseLong(equalToken[1]));
					
					
				} else if(historyToken[0].equals("shop/get")){
					System.out.println("show shop/get");
				} else{
					uiMng.showHome();
				}
				
				
			}
		});
		
	}
	
	
	public void showProductPage(final Long id){
		
		TaPMng.getType(id, new AsyncCallback<Type>() {
			
			@Override
			public void onSuccess(Type result) {
				uiMng.showProduct(TaPMng.getProduct(id), result);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	
	
	@Override
	public ReceiptData getReceipt(Long id) {
		// TODO Auto-generated method stub
		//Server Communication
		
		ReceiptData receiptContainer;
		
		if(id==0){
			ArrayList<ProductData> myProducts = new ArrayList<ProductData>();
			receiptContainer = new ReceiptData(
					15, 
					true,
					"Default title", 
					new Date(), 
					0, 
					null, 
					myProducts);
		}else if(id==15) {
			ArrayList<ProductData> myProducts = new ArrayList<ProductData>();
			myProducts.add(new ProductData(152, 15, 16, "Grouda geschnitten", "logo.png", 20, 80, new Price(325, 23, "€"), new Quantity(260, 23, "g"),true));
			myProducts.add(new ProductData(120, 15, 16, "Ja!Natürlich Milch 1L", "logo.png", 50, 30, new Price(98, 23, "€"), new Quantity(1, 24, "l"),false));
			myProducts.add(new ProductData(12, 15, 16, "Coca Cola 2L", "logo.png", 50, 100, new Price(230, 23, "€"), new Quantity(2, 25, "l"),true));
			
			receiptContainer = new ReceiptData(
					15, 
					true,
					"Ostern war teuer", 
					new Date(), 
					0, 
					new ShopData(15, "Hofer Taborstrasse", "logo.png", 80, 50, new Address("Flossgasse 1A", "1020 Wien", "Austria")), 
					myProducts);
		}else{
			ArrayList<ProductData> myProducts = new ArrayList<ProductData>();
			myProducts.add(new ProductData(152, 15, 16, "Grouda geschnitten", "logo.png", 20, 80, new Price(325, 23, "€"), new Quantity(260, 23, "g"),true));
			myProducts.add(new ProductData(120, 15, 16, "Ja!Natürlich Milch 1L", "logo.png", 50, 30, new Price(98, 23, "€"), new Quantity(1, 24, "l"),false));
			
			receiptContainer = new ReceiptData(
					18, 
					false,
					"Weihnachts einkauf", 
					new Date(), 
					0, 
					new ShopData(15, "Billa Flossgasse", "logo.png", 80, 50, new Address("Flossgasse 1A", "1020 Wien", "Austria")), 
					myProducts);
		}
		
		
		
		return receiptContainer;
	}

	@Override
	public ProductData getProduct(Long id) {
				
		
		// TODO Auto-generated method stub
		ProductData test = new ProductData(152, 15, 16, "Mousse au Chocolat", "logo.png", 20, 80, new Price(139, 23, "€"), new Quantity(125, 23, "g"),true);
		
		PropertyList properties = new PropertyList();
		properties.add(new PropertyData("energy", "Brennwert", "2109", new Unit(1, "kj")));
		properties.add(new PropertyData("protein", "Eiweiss", "5,3", new Unit(2, "g")));
		properties.add(new PropertyData("carbohydrate", "Kohlenhydrate", "27,5", new Unit(2, "g")));
		properties.add(new PropertyData("fat", "Fett", "41,3", new Unit(2, "g")));
		properties.add(new PropertyData("fiber", "Ballaststoffe", "1,9", new Unit(2, "g")));
		properties.add(new PropertyData("sodium", "Natrium", "0,05", new Unit(2, "g")));
		properties.add(new PropertyData("link", "URL", "tagaprice.com", new Unit(5, "fl")));
		properties.add(new PropertyData("ean", "EAN", "14526486", new Unit(5, "g")));
		
		test.setProperties(properties);
		return test;
	}

	@Override
	public ShopData getShop(Long id) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public UIManager getUIManager() {
		return uiMng;
	}


	@Override
	public void saveReceipt(ReceiptData receiptContainer) {
		// TODO Auto-generated method stub
		if(receiptContainer.getId()>0 && receiptContainer.getDraft()==true){
			System.out.println("saveDraft");
		}else if(receiptContainer.getId()>0 && receiptContainer.getDraft()==false){
			System.out.println("saveReceipt or change Draft to Receipt");
		}else{
			System.out.println("SaveRecei_superTypept-Error");
		}
		
	}
	
	
	public void search(String userInput, SearchWidget sw) {
		// TODO Auto-generated method stub
		// send to server
		
		ArrayList<Entity> tmp= new ArrayList<Entity>();
			tmp.add(new ShopData(15, "Billa Flossgasse", "logo.png", 80, 50, new Address("Flossgasse 1A", "1020 Wien", "Austria")));
			tmp.add(new ShopData(12, "Amazon.de", "logo.png", 80, 3));
			tmp.add(new ProductData(13, 15, 16, "Gouda Kaese", "logo.png", 50, 50, new Price(1200, 23, "€"), new Quantity(250, 23, "g")));
		
		sw.setSuggestions(tmp);
		
	}

	@Override
	public InfoBox getInfoBox() {
		return uiMng.getInfoBox();
	}

	@Override
	public void getType(long id, AsyncCallback<Type> response) {
		greetingService.getType(id,response);
		
		
		greetingService.getType(id, new AsyncCallback<Type>() {
			
			@Override
			public void onSuccess(Type result) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		/*
		Type type=new Type("Food");
		
		PropertyGroup pg =new PropertyGroup("NutritionFacts", PropertyGroup.GroupType.LIST);
		pg.addGroupElement(new PropertyDefinition(2, "energy", "Brennwert", PropertyDefinition.Datatype.DOUBLE,new Unit(15, "g"),true)); 
		pg.addGroupElement(new PropertyDefinition(3, "protein", "Eiweiss", PropertyDefinition.Datatype.DOUBLE,new Unit(15, "g"),true));
		pg.addGroupElement(new PropertyDefinition(4, "link", "URL", PropertyDefinition.Datatype.STRING,new Unit(15, "g"),false));
		type.addPropertyGroup(pg);
		
		
		PropertyGroup pg2 =new PropertyGroup("SachenEben", PropertyGroup.GroupType.LIST);
		pg2.addGroupElement(new PropertyDefinition(7, "fiber", "Ballaststoffe", PropertyDefinition.Datatype.DOUBLE,new Unit(15, "g"),true));
		pg2.addGroupElement(new PropertyDefinition(5, "carbohydrate", "Kohlenhydrate", PropertyDefinition.Datatype.DOUBLE,new Unit(15, "g"),true)); 
		pg2.addGroupElement(new PropertyDefinition(6, "fat", "Fett", PropertyDefinition.Datatype.DOUBLE,new Unit(15, "g"),true));
		type.addPropertyGroup(pg2);
		
		
		PropertyGroup pg3 =new PropertyGroup("Produkt Angaben", PropertyGroup.GroupType.LIST);
		pg3.addGroupElement(new PropertyDefinition(7, "producer", "Hersteller/Vertrieb", PropertyDefinition.Datatype.STRING,new Unit(15, "g"),true));
		pg3.addGroupElement(new PropertyDefinition(7, "ingredient", "Inhaltsstoffe", PropertyDefinition.Datatype.STRING,new Unit(15, "g"),false));
		pg3.addGroupElement(new PropertyDefinition(7, "ean", "EAN-Nummer", PropertyDefinition.Datatype.INT,new Unit(15, "g"),false));
		type.addPropertyGroup(pg3);
		
		PropertyGroup pg4 =new PropertyGroup("Versionskontrolle", PropertyGroup.GroupType.LIST);
		pg4.addGroupElement(new PropertyDefinition(7, "created", "Erfasst", PropertyDefinition.Datatype.STRING,new Unit(15, "g"),true));
		pg4.addGroupElement(new PropertyDefinition(7, "lastChange", "Letzte Änderung", PropertyDefinition.Datatype.STRING,new Unit(15, "g"),true));
		type.addPropertyGroup(pg4);
		
		return type;
		*/
	}

	
	
}
