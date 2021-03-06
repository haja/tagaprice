package org.tagaprice.client.features.receiptmanagement.createReceipt;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.tagaprice.client.ClientFactory;
import org.tagaprice.client.features.receiptmanagement.listReceipts.ListReceiptsPlace;
import org.tagaprice.client.features.startmanagement.StartPlace;
import org.tagaprice.client.generics.events.InfoBoxDestroyEvent;
import org.tagaprice.client.generics.events.InfoBoxShowEvent;
import org.tagaprice.client.generics.events.InfoBoxShowEvent.INFOTYPE;
import org.tagaprice.shared.entities.Address;
import org.tagaprice.shared.entities.Quantity;
import org.tagaprice.shared.entities.productmanagement.Package;
import org.tagaprice.shared.entities.productmanagement.Product;
import org.tagaprice.shared.entities.receiptManagement.Currency;
import org.tagaprice.shared.entities.receiptManagement.Price;
import org.tagaprice.shared.entities.receiptManagement.Receipt;
import org.tagaprice.shared.entities.receiptManagement.ReceiptEntry;
import org.tagaprice.shared.entities.shopmanagement.Shop;
import org.tagaprice.shared.exceptions.UserNotLoggedInException;
import org.tagaprice.shared.exceptions.dao.DaoException;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class CreateReceiptActivity implements ICreateReceiptView.Presenter, Activity {

	private CreateReceiptPlace _place;
	private ClientFactory _clientFactory;
	private Receipt _receipt;
	private ICreateReceiptView _createReceiptView;

	private int _productSearchCount=0;
	private int _shopSearchCount=0;

	public CreateReceiptActivity(CreateReceiptPlace place, ClientFactory clientFactory) {
		Log.debug("CreateReceiptActivity created");
		_place = place;
		_clientFactory = clientFactory;
	}

	@Override
	public void goTo(Place place) {

		//Check if this is a draft or existing receipt and save before change view
		if((_place.getId()!=null && _place.getId().equals("draft")) || _place.getId()==null){
			//Get data from View
			_receipt.setTitle(_createReceiptView.getNote());
			_receipt.setDate(_createReceiptView.getDate());
			_receipt.setShop(_createReceiptView.getShop());
			_receipt.setReceiptEntries(_createReceiptView.getReceiptEntries());
			_clientFactory.getAccountPersistor().setReceiptDraft(_receipt);
		}else{
			onSaveEvent();
		}


		this._clientFactory.getPlaceController().goTo(place);
	}
	
	@Override
	public void checkSave() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String mayStop() {
		Log.debug("May stop");
		return null;
	}

	@Override
	public void onCancel() {
	}


	@Override
	public void onSaveEvent() {
		Log.debug("Try Save Receipt");

		//Get data from View
		_receipt.setTitle(_createReceiptView.getNote());
		_receipt.setDate(_createReceiptView.getDate());
		_receipt.setShop(_createReceiptView.getShop());
		_receipt.setReceiptEntries(_createReceiptView.getReceiptEntries());
		
		Log.debug("receiptCount: "+_createReceiptView.getReceiptEntries().size());
		//infox
		//destroy all
		_clientFactory.getEventBus().fireEvent(new InfoBoxDestroyEvent(CreateReceiptActivity.class));
		
		boolean allowSaving = true;
		for(ReceiptEntry re:_receipt.getReceiptEntries()){
			if(re.getPackageId()==null){
				if(re.getPackage().getQuantity().getQuantity().equals(new BigDecimal("0.0")) 
						|| re.getPackage().getQuantity().getQuantity().equals(new BigDecimal("0"))){
					allowSaving=false;
					_clientFactory.getEventBus().fireEvent(new InfoBoxShowEvent(CreateReceiptActivity.class, "package size must not be 0", INFOTYPE.INFO));
				}
			}
		}
		
		if(allowSaving){
			
	
	
			final InfoBoxShowEvent trySaving = new InfoBoxShowEvent(CreateReceiptActivity.class, "saving...", INFOTYPE.INFO,0);
			_clientFactory.getEventBus().fireEvent(trySaving);
	
			_clientFactory.getReceiptService().saveReceipt(_receipt, new AsyncCallback<Receipt>() {
	
				@Override
				public void onFailure(Throwable caught) {
					_clientFactory.getEventBus().fireEvent(new InfoBoxDestroyEvent(trySaving));
					try{
						throw caught;
					}catch (UserNotLoggedInException e){
						Log.warn(e.getMessage());
						_clientFactory.getEventBus().fireEvent(new InfoBoxShowEvent(CreateReceiptActivity.class, "Please login or create new user to save.", INFOTYPE.ERROR));
					}catch (Throwable e){
						Log.error(e.getMessage());
					}
				}
	
				@Override
				public void onSuccess(Receipt response) {
					_clientFactory.getEventBus().fireEvent(new InfoBoxDestroyEvent(trySaving));
					_clientFactory.getEventBus().fireEvent(new InfoBoxShowEvent(CreateReceiptActivity.class, "Receipt saved successful", INFOTYPE.SUCCESS));
	
					Log.debug("Receipt saved: "+_receipt);
					updateView(response);
	
					//delete draft
					_clientFactory.getAccountPersistor().setReceiptDraft(null);
				}
			});
		}
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub

	}

	@Override
	public void productSearchStringHasChanged(String productSearch) {
		_productSearchCount++;
		final int curProductSearchCount=_productSearchCount;
		
		Log.debug("Start productSearch: "+productSearch);

		_clientFactory.getProductService().findProducts(productSearch.trim(), new AsyncCallback<List<Product>>(){

			@Override
			public void onFailure(Throwable caught) {
				try{
					throw caught;
				}catch (DaoException e){
					Log.warn(e.getMessage());
				}catch (Throwable e){
					Log.error(e.getMessage());
				}
				
			}

			@Override
			public void onSuccess(List<Product> response) {
				if(curProductSearchCount==_productSearchCount)
					_createReceiptView.setProductSearchResults(response);
				
			}
			
		});
		
		/*
		_clientFactory.getSearchService().searchProduct(
				productSearch,
				_createReceiptView.getShop(),
				new AsyncCallback<List<Product>>() {

					@Override
					public void onFailure(Throwable caught) {
						try{
							throw caught;
						}catch (DaoException e){
							Log.warn(e.getMessage());
						}catch (Throwable e){
							Log.error(e.getMessage());
						}
					}

					@Override
					public void onSuccess(List<Product> response) {
						if(curProductSearchCount==_productSearchCount)
							_createReceiptView.setProductSearchResults(response);
					}
				});
				*/
	}

	@Override
	public void shopSearchStringHasChanged(String shopSearch) {
		Log.debug("Start shopSearch: "+shopSearch+", "+_createReceiptView.getBoundingBox());
		_shopSearchCount++;
		final int curShopSearchCount=_shopSearchCount;
		
		_clientFactory.getSearchService().searchShop(
				shopSearch,
				_createReceiptView.getBoundingBox(),
				new AsyncCallback<List<Shop>>() {

					@Override
					public void onFailure(Throwable caught) {
						try{
							throw caught;
						}catch (DaoException e){
							Log.warn(e.getMessage());
						}catch (Throwable e){
							Log.error(e.getMessage());
						}
					}

					@Override
					public void onSuccess(List<Shop> response) {
						if(curShopSearchCount==_shopSearchCount){
							Log.debug("ShopSearch successfull: count: "+response.size());
							_createReceiptView.setShopSearchResults(response);
						}
					}
				});

	}


	private void addShopOrProduct(){
		//Add Shop or Product
		if(_place.getAddType()!=null && _place.getAddType().equals("shop") && _place.getAddId()!=null){
			//get from database
			_clientFactory.getShopService().getShop(_place.getAddId(), new AsyncCallback<Shop>() {

				@Override
				public void onSuccess(Shop result) {
					_receipt.setShop(result);
					updateView(_receipt);
				}

				@Override
				public void onFailure(Throwable caught) {
					try{
						throw caught;
					}catch (DaoException e){
						Log.error("DaoException at getShop: "+caught.getMessage());
					}catch (Throwable e){
						Log.error("Unexpected exception: "+caught.getMessage());
						_clientFactory.getEventBus().fireEvent(new InfoBoxShowEvent(CreateReceiptActivity.class, "Unexpected exception: "+caught.getMessage(), INFOTYPE.ERROR,0));
					}

				}
			});

		}else if(_place.getAddType()!=null && _place.getAddType().equals("product") && _place.getAddId()!=null){
			_clientFactory.getProductService().getProduct(_place.getAddId(), new AsyncCallback<Product>() {

				@Override
				public void onSuccess(Product result) {
					Log.debug("Get Product sucessfull id: "+result.getId());
					Package np = new Package(new Quantity(new BigDecimal("0.0"), result.getUnit()));
					np.setProduct(result);
					/*
					List<ReceiptEntry> rt = _receipt.getReceiptEntries();
					rt.add(new ReceiptEntry(new Price(new BigDecimal("0"), Currency.euro), np));
					_receipt.setReceiptEntries(rt);
					*/
					_receipt.addReceiptEntries(new ReceiptEntry(new Price(new BigDecimal("0"), Currency.euro), np));
					updateView(_receipt);
				}

				@Override
				public void onFailure(Throwable caught) {
					try{
						throw caught;
					}catch (DaoException e){
						Log.error("DaoException at getProduct: "+caught.getMessage());
					}catch (Throwable e){
						Log.error("Unexpected exception: "+caught.getMessage());
						_clientFactory.getEventBus().fireEvent(new InfoBoxShowEvent(CreateReceiptActivity.class, "Unexpected exception: "+caught.getMessage(), INFOTYPE.ERROR,0));
					}


				}
			});
		}
	}

	@Override
	public void start(final AcceptsOneWidget panel, final EventBus eventBus) {
		_receipt=new Receipt();
		Log.debug("activity startet");
		
		if(_clientFactory.getCreateReceiptView()==null){
			GWT.runAsync(ICreateReceiptView.class, new RunAsyncCallback() {
				
				@Override
				public void onSuccess() {
					_clientFactory.setCreateReceiptView((ICreateReceiptView)GWT.create(ICreateReceiptView.class));
					viewLoadedStart(panel, eventBus);
				}
				
				@Override
				public void onFailure(Throwable arg0) {
					Log.error("Load ICreateReceiptView error");					
				}
			});
		}else{
			viewLoadedStart(panel, eventBus);
		}
	}

	private void viewLoadedStart(final AcceptsOneWidget panel, EventBus eventBus){
		_createReceiptView = _clientFactory.getCreateReceiptView();
		_createReceiptView.setPresenter(this);

		if(_clientFactory.getAccountPersistor().getAddressList()!=null)
			_createReceiptView.setSelectableAddress(_clientFactory.getAccountPersistor().getAddressList());
		
		
		if(_clientFactory.getAccountPersistor().getCurAddress()!=null)
			_createReceiptView.setAddress(_clientFactory.getAccountPersistor().getCurAddress());
		
		
		if((_place.getId() == null || (_place.getId()!=null && _place.getId().equals("draft")))  && _clientFactory.getAccountPersistor().getReceiptDraft()!=null){
			Log.debug("Create start with draft: ");
			Window.setTitle("Create Receipt");
			_receipt=_clientFactory.getAccountPersistor().getReceiptDraft();
			updateView(_receipt);
			panel.setWidget(_createReceiptView);

			//Add Shop or Product
			addShopOrProduct();


			//check address

		}else if (_place.getId() == null && _clientFactory.getAccountPersistor().getReceiptDraft()==null) {
			Log.debug("Create new Receipt");
			Window.setTitle("Create Receipt");
			_receipt.setDate(new Date());

			//create Draft
			_clientFactory.getEventBus().fireEvent(new InfoBoxShowEvent(CreateReceiptActivity.class, "Draft created.", INFOTYPE.INFO));

			_clientFactory.getAccountPersistor().setReceiptDraft(_receipt);

			updateView(_receipt);
			panel.setWidget(_createReceiptView);


			//check address


		} else {
			Log.debug("Get Receipt: id= "+_place.getId());

			_clientFactory.getReceiptService().getReceipt(_place.getId(), new AsyncCallback<Receipt>() {

				@Override
				public void onFailure(Throwable e) {
					Log.error("ERROR AT Get Receipt: id= "+_place.getId()+"e:"+ e);

				}

				@Override
				public void onSuccess(Receipt response) {

					Window.setTitle("Receipt - "+response.getTimeStamp());
					//Add Shop or Product
					addShopOrProduct();


					Log.debug("Result: "+response);
					Log.debug("entrySize: "+response.getReceiptEntries().size());
					updateView(response);
					panel.setWidget(_createReceiptView);
				}
			});
		}
	}
	
	
	private void updateView(Receipt receipt){
		_receipt=receipt;

		_createReceiptView.setNote(_receipt.getTitle());
		_createReceiptView.setDate(_receipt.getDate());
		_createReceiptView.setShop(_receipt.getShop());
		_createReceiptView.setReceiptEntries(_receipt.getReceiptEntries());
	}

	@Override
	public String getId() {
		return _receipt.getId();
	}

	@Override
	public void onFoundPositionBySearchQuery(Address address){
		_clientFactory.getAccountPersistor().addAddress(address);
		
		_createReceiptView.setSelectableAddress(_clientFactory.getAccountPersistor().getAddressList());
	}

	@Override
	public void onLogout() {
		_clientFactory.getAccountPersistor().logout();
		goTo(new StartPlace());
	}

	@Override
	public void onCancelEvent() {
		goTo(new ListReceiptsPlace());		
	}
	

}
