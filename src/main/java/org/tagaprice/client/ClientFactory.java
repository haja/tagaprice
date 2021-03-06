package org.tagaprice.client;

import org.tagaprice.client.features.accountmanagement.inviteFriends.IInviteFriendsView;
import org.tagaprice.client.features.accountmanagement.login.ILoginView;
import org.tagaprice.client.features.accountmanagement.settings.ISettingsView;
import org.tagaprice.client.features.categorymanagement.ICategoryView;
import org.tagaprice.client.features.productmanagement.createProduct.ICreateProductView;
import org.tagaprice.client.features.receiptmanagement.createReceipt.ICreateReceiptView;
import org.tagaprice.client.features.receiptmanagement.listReceipts.IListReceiptsView;
import org.tagaprice.client.features.searchmanagement.ISearchView;
import org.tagaprice.client.features.shopmanagement.createShop.ICreateShopView;
import org.tagaprice.client.features.startmanagement.IStartView;
import org.tagaprice.shared.rpc.accountmanagement.ILoginServiceAsync;
import org.tagaprice.shared.rpc.categorymanagement.ICategoryServiceAsync;
import org.tagaprice.shared.rpc.productmanagement.IProductServiceAsync;
import org.tagaprice.shared.rpc.receiptmanagement.IReceiptServiceAsync;
import org.tagaprice.shared.rpc.searchmanagement.ISearchServiceAsync;
import org.tagaprice.shared.rpc.shopmanagement.IShopServiceAsync;
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
	 * Returns the LoginView
	 * @return the LoginView
	 */
	ILoginView getLoginView();


	/**
	 * This view displays a logout button
	 * @return ILogoutView
	 */
	ILoginView getLogoutView();


	/**
	 * Returns the CreateShopView
	 * @return the CreateShopView
	 */
	ICreateShopView getCreateShopView();
	
	public void setCreateShopview(ICreateShopView createShopView);


	ICreateReceiptView getCreateReceiptView();

	public void setCreateReceiptView(ICreateReceiptView createReceiptView);

	IListReceiptsView getListReceiptsView();

	IStartView getStartView();
	
	ISearchView getSearchView();
	
	ICategoryView getShopCategoryView();

	ICategoryView getProductCategoryView();
	
	ISettingsView getSettingsView();
	
	IInviteFriendsView getInivteFriendsView();
	
	/****************** GlobalAddress ***********************/
	/**
	 * Returns Static {@link AccountPersistor}
	 * @return {@link AccountPersistor}
	 */
	public IAccountPersistor getAccountPersistor();

	

}
