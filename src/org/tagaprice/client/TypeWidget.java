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
 * Filename: TypeWidget.java
 * Date: 02.07.2010
*/
package org.tagaprice.client;

import java.util.ArrayList;

import org.tagaprice.client.InfoBox.BoxType;
import org.tagaprice.shared.Type;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Create a type selection widget optimized for finger clicking.  
 *
 */
public class TypeWidget extends Composite{

	TaPManager TaPMng = TaPManagerImpl.getInstance();
	Type type;
	HorizontalPanel hoPa1=new HorizontalPanel();
	PopupPanel typeItems = new PopupPanel(true);
	TypeWidgetHandler handler;
	
	public TypeWidget(Type type, TypeWidgetHandler handler) {
		this.type=type;
		this.handler=handler;
				
		//hoPa1.setWidth("100%");
		initWidget(hoPa1);
		
		createMenu();
	}
	
	
	private void createMenu(){
		//Type
		Type iterType = type;
		hoPa1.clear();
		do{
			final Type innerType = iterType;
			Button typeButton = new Button(iterType.getTitle());
			hoPa1.insert(typeButton, 0);
			final Button arrow = new Button(">");
			hoPa1.insert(arrow,1);			
			final String sType=iterType.getTitle();	
			
			//Open Product by Type
			typeButton.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					System.out.println("Open Type: "+sType);
					
				}
			});
			
					
			
			arrow.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					typeItems.setWidget(new Label("Loading..."));					
					TaPMng.getTypeList(innerType, new AsyncCallback<ArrayList<Type>>() {
						
						@Override
						public void onSuccess(ArrayList<Type> result) {
							
							VerticalPanel vePa1 = new VerticalPanel();
							//vePa1.add(new Button("---"));
							for(final Type ty:result){
								Button tsb= new Button(ty.getTitle());
								tsb.setWidth("100%");
								tsb.addClickHandler(new ClickHandler() {									
									@Override
									public void onClick(ClickEvent event) {
										handler.onChange(ty);
									}
								});
								
								vePa1.add(tsb);
							}
							typeItems.setWidget(vePa1);
						}
						
						@Override
						public void onFailure(Throwable caught) {
							TaPMng.getInfoBox().showInfo("typeWidget Error", BoxType.WARNINGBOX);
						}
					});				
					
					typeItems.showRelativeTo(arrow);
				}
			});
			
		}while((iterType=iterType.getSuperType())!=null);
		

		//Root elem
		final Button rootB = new Button(">");
		rootB.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				typeItems.setWidget(new Label("Loading..."));					
				TaPMng.getTypeList(new Type("root", 2), new AsyncCallback<ArrayList<Type>>() {
					
					@Override
					public void onSuccess(ArrayList<Type> result) {
						
						VerticalPanel vePa1 = new VerticalPanel();
						//vePa1.add(new Button("---"));
						for(final Type ty:result){
							Button tsb= new Button(ty.getTitle());
							tsb.setWidth("100%");
							tsb.addClickHandler(new ClickHandler() {									
								@Override
								public void onClick(ClickEvent event) {
									handler.onChange(ty);
								}
							});
							
							vePa1.add(tsb);
						}
						typeItems.setWidget(vePa1);
					}
					
					@Override
					public void onFailure(Throwable caught) {
						TaPMng.getInfoBox().showInfo("typeWidget Error", BoxType.WARNINGBOX);
					}
				});				
				
				typeItems.showRelativeTo(rootB);
			}
		});
		hoPa1.insert(rootB,0);	
	}
}