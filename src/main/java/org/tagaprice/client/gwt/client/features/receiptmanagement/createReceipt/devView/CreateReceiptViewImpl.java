package org.tagaprice.client.gwt.client.features.receiptmanagement.createReceipt.devView;

import java.util.ArrayList;

import org.tagaprice.client.gwt.client.features.receiptmanagement.createReceipt.ICreateReceiptView;
import org.tagaprice.client.gwt.client.generics.widgets.ShopSelecter;
import org.tagaprice.client.gwt.shared.entities.shopmanagement.IShop;
import org.tagaprice.client.gwt.shared.logging.LoggerFactory;
import org.tagaprice.client.gwt.shared.logging.MyLogger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.*;
import com.google.gwt.user.client.ui.*;



public class CreateReceiptViewImpl<T> extends Composite implements ICreateReceiptView<T> {

	MyLogger _logger = LoggerFactory.getLogger(CreateReceiptViewImpl.class);

	interface CreateReceiptViewImplUiBinder extends
	UiBinder<Widget, CreateReceiptViewImpl<?>> {
	}

	private static CreateReceiptViewImplUiBinder uiBinder = GWT
	.create(CreateReceiptViewImplUiBinder.class);

	private Presenter _presenter;

	@UiField
	Label _date18N;
	@UiField
	Label _shopI18N;
	@UiField
	Label _productI18N;

	@UiField
	TextBox _date;
	@UiField
	ShopSelecter _shop;
	@UiField
	VerticalPanel _productsPanel;

	@UiField
	Label _shopLoaded;

	SuggestBox _products;


	@UiField
	FlexTable _productTable;


	HandlerRegistration hr;


	public CreateReceiptViewImpl() {
		this.initWidget(CreateReceiptViewImpl.uiBinder.createAndBindUi(this));

	}


	@UiHandler("_addButton")
	public void onSaveButtonClicked(ClickEvent event) {
		this._presenter.onSaveEvent();
	}


	@Override
	public int getPrice() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getProductName() {
		return ""; //_products.getText();
	}

	@Override
	public  int getQuantity(){
		return 0;
	}


	@Override
	public void setPresenter(Presenter presenter) {
		this._presenter = presenter;

	}



	@Override
	public void setSuggestProducts(MultiWordSuggestOracle productList) {
		_logger.log("set oracle... suggestbox");
		this._productsPanel.clear();
		this._products = new SuggestBox(productList);
		this._productsPanel.add(this._products);
	}


	@Override
	public void addReceiptEntry(ArrayList<T> entry) {
		// TODO Auto-generated method stub

	}


	@Override
	public void setAvailableShops(ArrayList<IShop> availableShops) {
		this._shop.setAvailableShops(availableShops);

	}


	@Override
	public void setShop(IShop shop) {
		this._shop.setShop(shop);

	}

	@Override
	public IShop getShop() {
		return this._shop.getShop();
	}


	@Override
	public void addShopChangeHanlder(ChangeHandler handler) {
		if(hr != null) {
			hr.removeHandler();
		}
		hr = this._shop.addChangeHandler(handler);
	}


	@Override
	public void shopsLoaded(String loaded) {
		this._shopLoaded.setText(loaded);
	}

}
