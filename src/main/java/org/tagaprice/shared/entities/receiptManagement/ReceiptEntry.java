package org.tagaprice.shared.entities.receiptManagement;

import org.tagaprice.shared.entities.productmanagement.Package;

public class ReceiptEntry {
	private static final long serialVersionUID = 1L;

	private Package _package;
	private Price _price;
	private Receipt _receipt;

	/**
	 * This constructor is used by the serialization algorithm
	 */
	public ReceiptEntry() {
		super();
	}

	/**
	 * <b>NEW</b>
	 * Create a new ReceiptEntry. Method setReceipt() must be called
	 * @param price the price of the {@link ReceiptEntry}
	 * @param ipackage the related package
	 */
	public ReceiptEntry(Price price, Package ipackage) {
		setPrice(price);
		setPackage(ipackage);
	}

	/**
	 * @return the related {@link IPackage}
	 */
	public Package getPackage() {
		return _package;
	}

	/**
	 * returns the price per {@link Currency} in cent
	 * @return per {@link Currency}  in cent
	 */
	public Price getPrice() {
		return _price;
	}

	/**
	 * @return the related {@link IReceipt}
	 */
	public Receipt getReceipt() {
		return _receipt;
	}

	/**
	 * defines the {@link Package} (Product)
	 * @param prackage the {@link Package} (Product)
	 */
	public void setPackage(Package ipackage) {
		_package = ipackage;
	}

	/**
	 * sets the price per {@link Currency}  in cent
	 * @param price  per {@link Currency}  in cent
	 */
	public void setPrice(Price price) {
		_price = price;
	}

	/**
	 * The related receipt
	 * @param receipt the related receipt
	 */
	public void setReceipt(Receipt receipt) {
		_receipt = receipt;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ReceiptEntry [_package=" + _package + ", _receipt=" + _receipt + ", _price=" + _price + "]";
	}



}
