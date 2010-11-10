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
 * Filename: InfoBox.java
 * Date: 19.05.2010
*/
package org.tagaprice.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Shows a success, warning or error info.
 * 
 */
public class InfoBox extends Composite {
	SimplePanel infoBox = new SimplePanel();
	public enum BoxType {
		ERRORBOX, SUCCESSBOX, WARNINGBOX
	}
	protected BoxType type;


	public InfoBox() {
		initWidget(infoBox);
		//infoBox.setSize("100%", "auto");
		infoBox.setWidth("100%");
		infoBox.setVisible(false);		
	}

	public void showError(Widget widget) {
		showInfo(widget, BoxType.ERRORBOX);
	}

	public void showError(String message) {
		showInfo(message, BoxType.ERRORBOX);
	}

	public void showSuccess(Widget widget) {
		showInfo(widget, BoxType.SUCCESSBOX);
	}

	public void showSuccess(String message) {
		showInfo(message, BoxType.SUCCESSBOX);
	}

	public void showWarning(Widget widget) {
		showInfo(widget, BoxType.WARNINGBOX);
	}

	public void showWarning(String message) {
		showInfo(message, BoxType.WARNINGBOX);
	}

	public void showInfo(Widget wid, BoxType type){
		this.type=type;
		
		
		if(this.type == BoxType.ERRORBOX){
			infoBox.setStyleName("InfoBox-Error");
		}else if(this.type == BoxType.SUCCESSBOX){
			infoBox.setStyleName("InfoBox-Success");
		}else if(this.type == BoxType.WARNINGBOX){
			infoBox.setStyleName("InfoBox-Warning");
		}else{
			infoBox.setStyleName("InfoBox-Warning");
		}
		
		infoBox.setWidget(wid);
		infoBox.setVisible(true);
		/*
		Timer t = new Timer() {
			public void run() {				
				infoBox.setVisible(false);
			}
		};
		
		t.schedule(1500);
		*/
	}
	
	public void showInfo(String text, BoxType type){
		showInfo(new Label(text), type);
	}
	
	public void hideInfo(){
		infoBox.setVisible(false);		
	}
}
