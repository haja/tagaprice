package org.tagaprice.client.gwt.client;

import org.tagaprice.client.gwt.client.features.accountmanagement.login.ILoginView;
import org.tagaprice.client.gwt.client.features.accountmanagement.login.ILogoutView;
import org.tagaprice.client.gwt.client.features.accountmanagement.register.IRegisterView;
import org.tagaprice.client.gwt.client.features.productmanagement.createProduct.*;
import org.tagaprice.client.gwt.client.features.productmanagement.listProducts.ListProductsView;
import org.tagaprice.client.gwt.client.features.receiptmanagement.createReceipt.ICreateReceiptView;
import org.tagaprice.client.gwt.client.features.shopmanagement.createShop.ICreateShopView;
import org.tagaprice.client.gwt.client.features.shopmanagement.listShops.ListShopsView;
import org.tagaprice.client.gwt.shared.entities.Address;
import org.tagaprice.client.gwt.shared.entities.productmanagement.IProduct;
import org.tagaprice.client.gwt.shared.entities.receiptManagement.IReceiptEntry;
import org.tagaprice.client.gwt.shared.entities.shopmanagement.IShop;
import org.tagaprice.client.gwt.shared.rpc.accountmanagement.ILoginServiceAsync;
import org.tagaprice.client.gwt.shared.rpc.categorymanagement.ICategoryServiceAsync;
import org.tagaprice.client.gwt.shared.rpc.productmanagement.IProductServiceAsync;
import org.tagaprice.client.gwt.shared.rpc.receiptmanagement.IReceiptServiceAsync;
import org.tagaprice.client.gwt.shared.rpc.searchmanagement.ISearchServiceAsync;
import org.tagaprice.client.gwt.shared.rpc.shopmanagement.IShopServiceAsync;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;

public interface ClientFactory {
	/**
	 * GWT Magic EventBus. Is never used programmatically.
	 * 
	 * @return
	 */
	EventBus getEventBus();

	/**
	 * GWT Magic PlaceController. Is used to change the Place (-> Activity ->
	 * Data -> View)
	 * 
	 * @return
	 */
	PlaceController getPlaceController();


	/**
	 * Singleton for ListProductsView
	 * 
	 * @return
	 */
	ListProductsView<IProduct> getListProductsView();

	/**
	 * Locale Dispatch for remote RPC Service
	 * 
	 * @return
	 */
	IProductServiceAsync getProductService();


	IReceiptServiceAsync getReceiptService();


	ILoginServiceAsync getLoginService();

	IShopServiceAsync getShopService();

	ICategoryServiceAsync getCategoryService();

	ISearchServiceAsync getSearchService();


	/****************** VIEWS ***********************/

	/**
	 * Returns the CreateProductView for the selected screen
	 * @return the CreateProductView for the selected screen
	 */
	ICreateProductView getCreateProductView();

	/**
	 * Singleton for EditProductView
	 * 
	 * @return
	 */
	ICreateProductView getEditProductView();


	/**
	 * Returns the LoginView
	 * @return the LoginView
	 */
	ILoginView getLoginView();


	/**
	 * This view displays a logout button
	 * @return ILogoutView
	 */
	ILogoutView getLogoutView();


	/**
	 * Returns the CreateShopView
	 * @return the CreateShopView
	 */
	ICreateShopView<IReceiptEntry> getCreateShopView();

	ListShopsView<IShop> getListShopsView();

	ICreateReceiptView getCreateReceiptView();


	IRegisterView getRegisterView();


	/****************** GlobalAddress ***********************/
	/**
	 * Returns global Address
	 */
	Address getAddress();

	/**
	 * Set Global Address
	 * @param address setGlobalAddress
	 */
	public void setAddress(Address address);
}
