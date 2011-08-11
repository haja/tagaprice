package org.tagaprice.client.features.shopmanagement.createShop;

import java.util.Date;
import java.util.List;

import org.tagaprice.shared.entities.Address;
import org.tagaprice.shared.entities.BoundingBox;
import org.tagaprice.shared.entities.searchmanagement.StatisticResult;
import org.tagaprice.shared.entities.shopmanagement.Shop;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;

public interface ICreateShopView extends IsWidget {

	/**
	 * 
	 *
	 */
	public interface Presenter {
		public void brandingSearch(String search);


		/**
		 * Is used by the {@link org.tagaprice.client.mvp.AppActivityMapper} to display a new place in the
		 * browser window.
		 * 
		 * @param place
		 *            The {@link Place} which should be displayed next.
		 */
		public void goTo(Place place);


		/**
		 * This event is called when the user has CHANGED/CREATED a
		 * shop.
		 * 
		 * @param event
		 *            is called when the user has CHANGED/CREATED a
		 *            shop.
		 */
		public void onSaveEvent();

		/**
		 * 	This event is called when the user has changed something at the statistic widget
		 * @param bbox BBox to search for Shop
		 * @param begin start Date
		 * @param end end Date
		 */
		public void onStatisticChangedEvent(BoundingBox bbox, Date begin, Date end);

	}


	public Address getAddress();

	public Shop getBranding();

	/**
	 * Set current address (Position of the user)
	 * @param address address (position) of the user
	 */
	public void setAddress(Address address);

	public void setBranding(Shop branding);

	public void setBrandingSearchResults(List<Shop> results);


	/**
	 * Sets the {@link Presenter} which implements the {@link ICreateShopView} to control this view. It is also necessary
	 * for the {@link ICreateShopView} to communicate with the {@link Presenter}
	 * 
	 * @param presenter
	 *            Sets the {@link Presenter} which implements the {@link ICreateShopView} to control this view.
	 */
	public void setPresenter(Presenter presenter);


	/**
	 * Set statistic results
	 * @param results statistic results.
	 */
	public void setStatisticResults(List<StatisticResult> results);

	public void setTitle(String title);

	public String getTitle();
	
	/**
	 * Set the readmode of the view
	 * @param read
	 */
	public void setReadOnly(boolean read);

}
