package org.tagaprice.server.dao.couchdb;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.svenson.JSONParser;
import org.tagaprice.server.dao.ISearchDao;
import org.tagaprice.shared.entities.BoundingBox;
import org.tagaprice.shared.entities.Document;

public class SearchDao implements ISearchDao {
	private ElasticSearchClient m_searchClient = null;
	
	public SearchDao(CouchDbDaoFactory daoFactory) {
		m_searchClient = daoFactory.getElasticSearchClient();
	}
	
	@Override
	public List<Document> search(String query, BoundingBox bbox, int limit) {
		return _returnResultList(m_searchClient.find(query, bbox, 0, limit, Document.Type.PRODUCT, Document.Type.SHOP));
	}
	
	@Override
	public List<Document> searchProduct(String query, int limit) {
		return _returnResultList(m_searchClient.findProduct(query, limit));
	}
	
	@Override
	public List<Document> searchShop(String query, BoundingBox bbox, int limit) {
		return _returnResultList(m_searchClient.findShop(query, bbox, limit));
	}

	public List<Document> _returnResultList(SearchResponse searchResponse) {
		List<Document> rc = new ArrayList<Document>();

		for (SearchHit hit: searchResponse.getHits().getHits()) {
			String json = org.svenson.JSON.defaultJSON().forValue(hit.getSource());
			Document item = JSONParser.defaultJSONParser().parse(Document.class, json);
			if (item != null) rc.add(item);
		}
		
		return rc;
	}
}
