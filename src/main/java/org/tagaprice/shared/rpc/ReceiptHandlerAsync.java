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
 * Filename: ReceiptHandlerAsync.java
 * Date: 30.05.2010
*/
package org.tagaprice.shared.rpc;

import org.tagaprice.shared.ReceiptData;
import org.tagaprice.shared.exception.InvalidLoginException;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ReceiptHandlerAsync {

	void get(Long id, AsyncCallback<ReceiptData> callback) 
		throws IllegalArgumentException;

	void save(ReceiptData data, AsyncCallback<ReceiptData> callback) 
		throws IllegalArgumentException, InvalidLoginException;
}
