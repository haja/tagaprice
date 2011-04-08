package org.tagaprice.client.features.shopmanagement.listShops;

import java.util.List;

import org.tagaprice.client.generics.IView;

import com.google.gwt.place.shared.Place;

/**
 * The interface ListProductsView<T> defines the ListProducts View
 * This interface implements the interface isWidget and also defines the interface Presenter
 * which allows bidirectional communication with the ListProducts view
 * 
 * @param <T>
 */
public interface ListShopsView<T> extends IView {

	public interface Presenter {
		public void goTo(Place place);

		public void onAddShop();

		/**
		 * 
		 * @param index the item in the ArrayList<T> data at position index
		 */
		public void onEditShop(int index);

		public void onSearch(String searchtext);
	}

	public void setPresenter(Presenter presenter);

	public void setData(List<T> data);

}