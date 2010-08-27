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
package org.tagaprice.client;

import java.util.ArrayList;

import org.tagaprice.client.InfoBox.BoxType;
import org.tagaprice.client.PriceMapWidget.PriceMapType;
import org.tagaprice.client.SearchWidget.SearchType;
import org.tagaprice.shared.Address;
import org.tagaprice.shared.BoundingBox;
import org.tagaprice.shared.Country;
import org.tagaprice.shared.Entity;
import org.tagaprice.shared.PriceData;
import org.tagaprice.shared.ProductData;
import org.tagaprice.shared.ReceiptData;
import org.tagaprice.shared.ShopData;
import org.tagaprice.shared.Type;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;


/**
 * DAO Manger
 *
 */
public class TaPManager {

	private static TaPManager TaPMng;	
	private static UIManager uiMng = new UIManager();
	

	public static TaPManager getInstance(){
		if(TaPMng==null){
			TaPMng=new TaPManager();
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
				if(historyToken[0].equals("receipt/get")){
					String[] equalToken = historyToken[1].split("=");
					TaPMng.showReceiptPage(Long.parseLong(equalToken[1]));
				}else if(historyToken[0].equals("product/get")){
					String[] equalToken = historyToken[1].split("=");
					TaPMng.showProductPage(Long.parseLong(equalToken[1]));					
				}else if(historyToken[0].equals("product/new")){
					//String[] equalToken = historyToken[1].split("=");
					//TaPMng.showProductPage(Long.parseLong(equalToken[1]));
					//TODO Get Product name from GET parameters 
					
					TaPMng.newProductPage(null);
				}else if(historyToken[0].equals("shop/get")){
					String[] equalToken = historyToken[1].split("=");
					TaPMng.showShopPage(Long.parseLong(equalToken[1]));
				}else if(historyToken[0].equals("shop/new")){
					
					//TODO get title parameter from GET parameters 
					TaPMng.newShopPage(null);
					
				}else if(historyToken[0].equals("user/registration/new")){ 
					TaPMng.showUserRegistrationPage();
				}else if(historyToken[0].equals("user/registration/confirm")){ 
										
					if(historyToken.length>1){	
						String[] equalToken = historyToken[1].split("=");
						if(equalToken[0].equals("confirm") && !equalToken[1].isEmpty())
							uiMng.showConfirmRegistartionPage(equalToken[1]);
					}else{
						uiMng.showConfirmRegistartionPage(null);
					}
					
				}else if(historyToken[0].equals("user/login") ){ 
					uiMng.showUserLogin(false);
				}else if(historyToken[0].equals("user/logout")){ 
					uiMng.showUserLogin(true);
				}else{
					uiMng.showHome();
				}


			}
		});

	}

	/**
	 * Starts Product Page with id
	 * @param id
	 */
	public void showProductPage(final Long id){
		uiMng.waitingPage();

		TaPMng.getProduct(id, new AsyncCallback<ProductData>() {

			@Override
			public void onSuccess(final ProductData pResult) {

				TaPMng.getType(pResult.getTypeId(), new AsyncCallback<Type>() {

					@Override
					public void onSuccess(Type tResult) {
						uiMng.showProduct(pResult, tResult);
					}

					@Override
					public void onFailure(Throwable caught) {
						uiMng.showInfo("Fail type: "+caught, BoxType.WARNINGBOX);
					}
				});

			}

			@Override
			public void onFailure(Throwable caught) {
				uiMng.showInfo("Fail product: "+caught, BoxType.WARNINGBOX);

			}
		});	
		
	}
	

	/**
	 * Creates empty productPage
	 * @param title
	 */
	public void newProductPage(String title) {
		uiMng.waitingPage();
		
		if(title==null) title="Default Title "; //Change this to language
		ProductData pd3 = new ProductData(title , 1, 1l, 2l, 0l, "logo.png", null);

		uiMng.showProduct(pd3, new Type("root", 2, 1, null));		
		
	}
	
	/**
	 * Starts Shop Page with id
	 * @param id
	 */
	public void showShopPage(final Long id){
		//Create Page
		uiMng.waitingPage();
				
		
		TaPMng.getShop(id, new AsyncCallback<ShopData>() {
			
			@Override
			public void onSuccess(final ShopData result) {
				TaPMng.getType(result.getTypeId(), new AsyncCallback<Type>() {

					@Override
					public void onSuccess(Type tResult) {
						uiMng.showShop(result, tResult);
					}

					@Override
					public void onFailure(Throwable caught) {
						uiMng.showInfo("Fail type: "+caught, BoxType.WARNINGBOX);
					}
				});
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				uiMng.showInfo("Fail shop: "+caught, BoxType.WARNINGBOX);
				
			}
		});

	}
	
	
	/**
	 * Creates empty shopPage. 
	 * @param title
	 */
	public void newShopPage(String title) {	
		uiMng.waitingPage();
		
		if(title==null) title="Default Title"; //Change this to language
		ShopData sd = new ShopData(title, 1, 1l, 0l, "logo.png", new Address("Park Avenue 23", "New York", new Country("us", "USA", null)));
		
		uiMng.showShop(sd,new Type("root", 2, 1, null));
		
		
	}
	
	/**
	 * Starts Receipt Page
	 * @param id
	 */
	public void showReceiptPage(final Long id){
		uiMng.waitingPage();

		getReceipt(id, new AsyncCallback<ReceiptData>() {

			@Override
			public void onSuccess(ReceiptData result) {
				uiMng.showReceipt(result);				
			}

			@Override
			public void onFailure(Throwable caught) {
				uiMng.showInfo("Fail: "+caught, BoxType.WARNINGBOX);				
			}
		});
	}


	/**
	 *  
	 * @param id Unique Receipt Id (If Id=0 you get an empty Draft-Container with a new draft-id )
	 * @param draft Is receipt a draft.
	 * @return Returns a ReceiptContainer
	 */
	public void getReceipt(Long id, AsyncCallback<ReceiptData> response) {		
		HandlerManager.getReceiptHandler().get(id, response);		
	}

	/**
	 * Returns product by ID.
	 * @param id
	 * @return
	 */
	public void getProduct(Long id, AsyncCallback<ProductData> response) {
		HandlerManager.getProductHandler().get(id, response);
	}
	


	/**
	 * Save, create or update a product.
	 * @param data
	 * @param response
	 * @return
	 */
	public void saveProduct(ProductData data,AsyncCallback<ProductData> response) {
		HandlerManager.getProductHandler().save(data, response);
	}

	/**
	 * Get price by Shop, Product, ProductGroup, ShopGroup
	 * @param id
	 * @param bbox
	 * @param type
	 * @param response
	 */
	public void getPrice(Long id, BoundingBox bbox, PriceMapType type, AsyncCallback<ArrayList<PriceData>> response){
		HandlerManager.getPriceHandler().get(id, bbox, type, response);
	}

	/**
	 * 
	 * @param id
	 * @param response
	 */
	public void getShop(Long id, AsyncCallback<ShopData> response) {
		HandlerManager.getShopHandler().get(id, response);		
	}
	
	/**
	 * 
	 * @param data
	 * @param response
	 */
	public void saveShop(ShopData data, AsyncCallback<ShopData> response) {
		HandlerManager.getShopHandler().save(data, response);
	}


	/**
	 * Returns the UIManager
	 * @return
	 */
	public UIManager getUIManager() {
		return uiMng;
	}


	/**
	 * 
	 * @param receiptContainer
	 * @param draft
	 */
	public void saveReceipt(ReceiptData receiptContainer) {
		if(receiptContainer.getId()>0 && receiptContainer.getDraft()==true){
			System.out.println("saveDraft");
		}else if(receiptContainer.getId()>0 && receiptContainer.getDraft()==false){
			System.out.println("saveReceipt or change Draft to Receipt");
		}else{
			System.out.println("SaveRecei_superTypept-Error");
		}

	}




	/**
	 * 
	 * @param id
	 * @return
	 */
	public void getType(long id, AsyncCallback<Type> response) {
		HandlerManager.getTypeHandler().get(id,response);
	}
	
	/**
	 * 
	 * @param type
	 * @param response
	 */
	public void getTypeList(Type type, AsyncCallback<ArrayList<Type>> response){
		HandlerManager.getTypeHandler().getTypeList(type, response);
	}

	

	/**
	 * Start a new user Registration
	 * @param varificationCode If not null user has just being verified.
	 */
	public void showUserRegistrationPage() {
		uiMng.showUserRegistrationPage();		
	}
	
	/**
	 * 
	 */
	public void showUserLogin() {
		//uiMng.showUserLogin();		
	}

	/**
	 * 
	 * @param sText
	 * @param callback
	 */
	public void search(String sText, SearchType searchType, AsyncCallback<ArrayList<Entity>> callback) {
		HandlerManager.getSearchHandler().search(sText, searchType, callback);
		
	}

	/**
	 * 
	 * @param sText
	 * @param bbox
	 * @param callback
	 */
	public void search(String sText, SearchType searchType, BoundingBox bbox,
			AsyncCallback<ArrayList<Entity>> callback) {
		HandlerManager.getSearchHandler().search(sText, searchType, bbox, callback);
		
	}

	/**
	 * 
	 * @param sText
	 * @param shopData
	 * @param callback
	 */
	public void search(String sText, ShopData shopData,
			AsyncCallback<ArrayList<Entity>> callback) {
		HandlerManager.getSearchHandler().search(sText, shopData, callback);
		
	}

	



}
