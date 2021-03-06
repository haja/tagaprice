package org.tagaprice.client.generics.widgets;

import org.tagaprice.shared.entities.Quantity;
import org.tagaprice.shared.entities.Unit;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;

/**
 * This is an QuantitySelecter wrapper class. This class implements the right screen design.
 * Is is also possible to use this class in UIBuilder
 * 
 */
public class QuantitySelecter extends Composite implements IQuantitySelecter {

	private IQuantitySelecter quantitySelecter = GWT.create(IQuantitySelecter.class);

	public QuantitySelecter() {
		initWidget(quantitySelecter.asWidget());
	}

	@Override
	public void setQuantity(Quantity quantity) {
		quantitySelecter.setQuantity(quantity);
	}

	@Override
	public Quantity getQuantity() {
		return quantitySelecter.getQuantity();
	}

	@Override
	public void setRelatedUnit(Unit unit) {
		quantitySelecter.setRelatedUnit(unit);
	}

	@Override
	public void addQuantityChangeHandler(IQuantityChangeHandler quantityChangeHandler) {
		quantitySelecter.addQuantityChangeHandler(quantityChangeHandler);

	}

}
