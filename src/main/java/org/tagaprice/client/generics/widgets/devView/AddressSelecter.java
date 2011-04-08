package org.tagaprice.client.generics.widgets.devView;

import org.gwtopenmaps.openlayers.client.LonLat;
import org.gwtopenmaps.openlayers.client.Map;
import org.gwtopenmaps.openlayers.client.MapOptions;
import org.gwtopenmaps.openlayers.client.MapWidget;
import org.gwtopenmaps.openlayers.client.Pixel;
import org.gwtopenmaps.openlayers.client.Style;
import org.gwtopenmaps.openlayers.client.control.DragFeature;
import org.gwtopenmaps.openlayers.client.control.DragFeature.DragFeatureListener;
import org.gwtopenmaps.openlayers.client.control.DragFeatureOptions;
import org.gwtopenmaps.openlayers.client.feature.VectorFeature;
import org.gwtopenmaps.openlayers.client.geometry.Point;
import org.gwtopenmaps.openlayers.client.layer.OSM;
import org.gwtopenmaps.openlayers.client.layer.Vector;
import org.gwtopenmaps.openlayers.client.layer.VectorOptions;
import org.tagaprice.client.generics.widgets.IAddressSelecter;
import org.tagaprice.shared.entities.Address;
import org.tagaprice.shared.entities.shopmanagement.Shop;
import org.tagaprice.shared.logging.MyLogger;
import org.tagaprice.shared.rpc.searchmanagement.ISearchService;
import org.tagaprice.shared.rpc.searchmanagement.ISearchServiceAsync;
import org.tagaprice.shared.logging.LoggerFactory;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class AddressSelecter extends Composite implements IAddressSelecter {
	private static MyLogger _logger = LoggerFactory.getLogger(AddressSelecter.class);
	ISearchServiceAsync I_SEARCH_SERVICE_ASYNC = GWT.create(ISearchService.class);
	VerticalPanel vePaTemp = new VerticalPanel();

	TextBox _address = new TextBox();
	Label _lat = new Label();
	Label _lng = new Label();
	Shop _shop;


	//OSM
	Map osmMap;
	Point point;
	Vector layer;

	public AddressSelecter() {

		HorizontalPanel hoPaTemp = new HorizontalPanel();



		//******** INIT OSM ************/
		MapOptions defaultMapOptions = new MapOptions();
		LonLat lonLat = new LonLat(16.37692,48.21426);
		MapWidget omapWidget = new MapWidget("200px", "200px", defaultMapOptions);
		OSM osm_2 = OSM.Mapnik("Mapnik");   // Label for menu 'LayerSwitcher'
		osm_2.setIsBaseLayer(true);
		osmMap = omapWidget.getMap();
		osmMap.addLayer(osm_2);
		lonLat.transform("EPSG:4326", "EPSG:900913");
		osmMap.setCenter(lonLat, 15);

		//******** INIT OSM Vector ************/
		VectorOptions vectorOptions = new VectorOptions();
		//Style
		Style style = new Style();
		style.setStrokeColor("#000000");
		style.setStrokeWidth(3);
		style.setFillColor("#FF0000");
		style.setFillOpacity(0.5);
		style.setPointRadius(10);
		style.setStrokeOpacity(1.0);
		vectorOptions.setStyle(style);

		// Create the layer
		layer = new Vector("Vector Layer"+Math.random(), vectorOptions);

		point = new Point(lonLat.lon(), lonLat.lat());
		VectorFeature pointFeature = new VectorFeature(point);
		layer.addFeature(pointFeature);
		DragFeatureOptions dragFeatureOptions = new DragFeatureOptions();

		DragFeature dragFeature = new DragFeature(layer, dragFeatureOptions);
		osmMap.addControl(dragFeature);
		dragFeature.activate();

		osmMap.addLayer(layer);

		dragFeatureOptions.onComplete(new DragFeatureListener() {

			@Override
			public void onDragEvent(VectorFeature vectorFeature, Pixel pixel) {
				LonLat l = vectorFeature.getCenterLonLat();
				setLatLng(l);
				l.transform("EPSG:900913","EPSG:4326");

				System.out.println("dragEnd: lat: "+vectorFeature.getCenterLonLat().lat()+", lng: "+vectorFeature.getCenterLonLat().lon());


				I_SEARCH_SERVICE_ASYNC.searchAddress(l.lat(), l.lon(), new AsyncCallback<Address>() {

					@Override
					public void onSuccess(Address address) {
						AddressSelecter._logger.log(address.getAddress());
						_address.setText(address.getAddress());

					}

					@Override
					public void onFailure(Throwable e) {
						AddressSelecter._logger.log(e.toString());

					}
				});


			}
		});

		//*********** END *******

		vePaTemp.add(new HTML("<hr />"));
		Grid tempGrid = new Grid(6, 2);
		tempGrid.setWidget(0, 0, new Label("address"));
		tempGrid.setWidget(4, 0, new Label("lat"));
		tempGrid.setWidget(5, 0, new Label("lng"));

		tempGrid.setWidget(0, 1, _address);
		tempGrid.setWidget(4, 1, _lat);
		tempGrid.setWidget(5, 1, _lng);

		hoPaTemp.add(tempGrid);
		hoPaTemp.add(omapWidget);
		vePaTemp.add(hoPaTemp);

		initWidget(vePaTemp);



	}

	@Override
	public void setAddress(Shop address){
		_shop=address;
		_address.setText(_shop.getAddress().getAddress());

		LonLat l = new LonLat(_shop.getAddress().getLng(), _shop.getAddress().getLat());
		l.transform("EPSG:4326", "EPSG:900913");
		setLatLng(l);
	}

	@Override
	public Shop getAddress(){
		if(_shop==null)_shop = new Shop();
		_shop.getAddress().setAddress(_address.getText());

		LonLat l = osmMap.getCenter();
		l.transform("EPSG:900913","EPSG:4326");

		_shop.getAddress().setLat(l.lat());
		_shop.getAddress().setLng(l.lon());


		return _shop;
	}

	private void setLatLng(LonLat lonLat){
		_lat.setText(""+lonLat.lat());
		_lng.setText(""+lonLat.lon());

		osmMap.setCenter(lonLat);
		point.setXY(lonLat.lon(), lonLat.lat());
		layer.redraw();


	}

	@Override
	public void setCurrentAddress(Address address) {
		_address.setText(address.getAddress());

		LonLat l = new LonLat(address.getLng(),address.getLat());
		l.transform("EPSG:4326", "EPSG:900913");
		setLatLng(l);

	}


}