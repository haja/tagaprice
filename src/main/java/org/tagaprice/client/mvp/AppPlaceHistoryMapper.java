package org.tagaprice.client.mvp;

import org.tagaprice.client.features.categorymanagement.product.ProductCategoryPlace;
import org.tagaprice.client.features.categorymanagement.shop.ShopCategoryPlace;
import org.tagaprice.client.features.productmanagement.createProduct.CreateProductPlace;
import org.tagaprice.client.features.productmanagement.listProducts.ListProductsPlace;
import org.tagaprice.client.features.receiptmanagement.createReceipt.CreateReceiptPlace;
import org.tagaprice.client.features.receiptmanagement.listReceipts.ListReceiptsPlace;
import org.tagaprice.client.features.searchmanagement.SearchPlace;
import org.tagaprice.client.features.shopmanagement.createShop.CreateShopPlace;
import org.tagaprice.client.features.shopmanagement.listShops.ListShopsPlace;
import org.tagaprice.client.features.startmanagement.StartPlace;

import com.google.gwt.place.shared.*;

/**
 * Some GWT Magic
 * 
 */

@WithTokenizers({
	ListProductsPlace.Tokenizer.class,
	CreateProductPlace.Tokenizer.class,
	CreateShopPlace.Tokenizer.class,
	ListShopsPlace.Tokenizer.class,
	CreateReceiptPlace.Tokenizer.class,
	ListReceiptsPlace.Tokenizer.class,
	StartPlace.Tokenizer.class,
	SearchPlace.Tokenizer.class,
	ProductCategoryPlace.Tokenizer.class,
	ShopCategoryPlace.Tokenizer.class})
	public interface AppPlaceHistoryMapper extends PlaceHistoryMapper {

}
