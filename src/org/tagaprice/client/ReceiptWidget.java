/*
 * Copyright 2010 TagAPrice.org
 * 
 * Licensed under the Creative Commons License. You may not
 * use this file except in compliance with the License. 
 *
 * http://creativecommons.org/licenses/by-nc/3.0/
*/

/**
 * Project: TagAPriceUI
 * Filename: ReceiptWidget.java
 * Date: 15.05.2010
*/
package org.tagaprice.client;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Displays editable receipt including shop and product search.
 *
 */
public class ReceiptWidget extends Composite {
	interface MyUiBinder extends UiBinder<Widget, ReceiptWidget>{}
	private MyUiBinder uiBinder = GWT.create(MyUiBinder.class);
	
	
	boolean isEditable=true;
	MorphWidget title = new MorphWidget("Default title", isEditable);
	int bill=0;
	ChangeHandler priceChangeHandler; 
	
	
	@UiField HorizontalPanel HoPa1;
	@UiField HorizontalPanel HoPa2;
	@UiField VerticalPanel VePa1;
	@UiField DateWidget date;
	@UiField SimplePanel titleContainer;
	@UiField SimplePanel shopChooser;
	@UiField SelectiveVerticalPanel productContainer;
	@UiField Label price;
	@UiField Button save;
	
	/**
	 * 
	 * @param receiptContainer
	 */
	public ReceiptWidget(ReceiptContainer receiptContainer, boolean editable){
		this();
		isEditable=editable;
		title.setText(receiptContainer.getName());
		date.setDate(receiptContainer.getDate());
		setShop(receiptContainer.getShopPreviewContainer());
		
		for(int i=0;i<receiptContainer.getProductPreviewContainer().length;i++){
			addProduct(receiptContainer.getProductPreviewContainer()[i]);
		}
		refreshPrice();	
	}
	
	/**
	 * 
	 */
	public ReceiptWidget() {
		initWidget(uiBinder.createAndBindUi(this));
		
		//Style
		//VePa1
		VePa1.setWidth("100%");
		
		//Hopa1
		HoPa1.setWidth("100%");
		titleContainer.setWidget(title);
		
		HoPa1.setCellWidth(date, "50px");
		
		//shopChoolser
		
		//HoPa2
		VePa1.setCellHorizontalAlignment(HoPa2, HasHorizontalAlignment.ALIGN_RIGHT);

		
		//ProductsHandler
		priceChangeHandler = new ChangeHandler() {			
			@Override
			public void onChange(ChangeEvent event) {
				refreshPrice();		
			}
		};
		
		//Products		
		productContainer.addSelectiveVerticalPanelHandler(new SelectiveVerticalPanelHandler() {
			
			@Override
			public void onClick(Widget widget, int index) {
				productContainer.removeWidget(index);
				refreshPrice();
				
			}
		});
		
		
		//Refresh the price
		refreshPrice();		
		
		//Save
		save.setStyleName("Awesome");
		save.setWidth("100%");
		
	}
	
	/**
	 * 
	 */
	private void refreshPrice(){
		bill=0;
		for(int i=0;i<productContainer.getWidgetCount();i++){
			bill+=((ProductPreview)productContainer.getWidget(i)).getProductPreviewContainer().getPrice();
		}
		
		price.setText((bill/100.00)+"");
	}
	
	/**
	 * 
	 * @param shop
	 */
	public void setShop(ShopPreviewContainer shop){
		shopChooser.setWidget(new ShopPreview(shop,isEditable));
	}
	
	/**
	 * 
	 * @param product
	 */
	public void addProduct(ProductPreviewContainer product){
		productContainer.add(new ProductPreview(product, isEditable,priceChangeHandler));
	}
	
}
