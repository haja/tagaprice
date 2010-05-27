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
 * Filename: ProductWidget.java
 * Date: 19.05.2010
*/
package org.tagaprice.client;

import java.util.ArrayList;

import org.tagaprice.client.propertyhandler.DefaultPropertyHandler;
import org.tagaprice.client.propertyhandler.ListPropertyHandler;
import org.tagaprice.client.propertyhandler.NutritionFactsPropertyHandler;
import org.tagaprice.shared.ProductData;
import org.tagaprice.shared.PropertyGroup;
import org.tagaprice.shared.TaPManager;
import org.tagaprice.shared.TaPManagerImpl;
import org.tagaprice.shared.Type;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ProductPage extends Composite {

	private ProductData productData;
	private Type type;
	private VerticalPanel vePa1 = new VerticalPanel();
	
	public ProductPage(ProductData productData, Type type) {
		initWidget(vePa1);
		this.productData=productData;
		this.type=type;
		
		
		
		//style
		vePa1.setWidth("100%");
		vePa1.add(new ProductPreview(this.productData, false));
		
		
		for(PropertyGroup pg:this.type.getPropertyGroups()){
			registerHandler(pg);
		}
		
		//Is displaying the non uses properties.
		//vePa1.add(new DefaultPropertyHandler(this.productData.getProperties(), null));
		
	}
	
	
	private void registerHandler(PropertyGroup propGroup){
		
		if(propGroup.getType().equals(PropertyGroup.GroupType.NUTRITIONFACTS))
			vePa1.add(new NutritionFactsPropertyHandler(this.productData.getProperties(), propGroup));
		else if (propGroup.getType().equals(PropertyGroup.GroupType.LIST))
			vePa1.add(new ListPropertyHandler(this.productData.getProperties(), propGroup));

		

		
		
	}
	
	
}
