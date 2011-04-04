package org.tagaprice.client.gwt.shared.exceptions;


/**
 * Thrown to indicate that at least one of the revisions of a given product is outdated, i.e. another revision of the product has been saved already.
 * @author "forste"
 *
 */
public class OutdatedRevisionException extends Exception {

	private static final long serialVersionUID = 2814657980704339544L;

	public OutdatedRevisionException(String message) {
		super(message);
	}

}
