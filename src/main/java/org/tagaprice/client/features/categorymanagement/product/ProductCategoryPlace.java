package org.tagaprice.client.features.categorymanagement.product;

import org.tagaprice.client.generics.TokenCreator;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class ProductCategoryPlace extends Place {


	private String _id = null;
	private String _rev = null;
	private String _lat=null;
	private String _lon=null;
	private String _zoom=null;
	
	
	public ProductCategoryPlace() {
	}
	
	
	public ProductCategoryPlace(String id, String revision, String lat, String lon, String zoom){

		_id=id;
		_rev=revision;
		_lat=lat;
		_lon=lon;
		_zoom=zoom;
	}
	
	
	


	/**
	 * @return the id
	 */
	public String getId() {
		return _id;
	}


	/**
	 * @return the rev
	 */
	public String getRev() {
		return _rev;
	}


	/**
	 * @return the lat
	 */
	public String getLat() {
		return _lat;
	}


	/**
	 * @return the lon
	 */
	public String getLon() {
		return _lon;
	}


	/**
	 * @return the zoom
	 */
	public String getZoom() {
		return _zoom;
	}




	@Prefix("productCategory")
	public static class Tokenizer implements PlaceTokenizer<ProductCategoryPlace>{

		@Override
		public ProductCategoryPlace getPlace(String token) {
			Log.debug("Tokenizer token " + token);
			TokenCreator.Exploder e = TokenCreator.getExploder(token);

				
			return new ProductCategoryPlace(
					e.getNode("id"), 
					e.getNode("rev"), 
					e.getNode("lat"),
					e.getNode("lon"),
					e.getNode("zoom"));
		}

		@Override
		public String getToken(ProductCategoryPlace place) {
			String rc = null;
			
			
			TokenCreator.Imploder t = TokenCreator.getImploder();
			t.addNode("id", ""+place.getId());
			if (place.getRev()!=null){
				t.addNode("rev", ""+place.getRev());
			}
			t.addNode("lat", place.getLat());
			t.addNode("lon", place.getLon());
			t.addNode("zoom", place.getZoom());
			place.getZoom();
			rc = t.getToken();
			
			
			
			
			return rc;
		}
		
	}
	
}
