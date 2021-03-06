package org.tagaprice.server.dao;

import java.util.List;

import org.tagaprice.shared.entities.BoundingBox;
import org.tagaprice.shared.entities.Document;
import org.tagaprice.shared.exceptions.dao.DaoException;

public interface ISearchDao {
	public List<Document> search(String query, BoundingBox bbox, int limit) throws DaoException;
	public List<Document> searchProduct(String query, int limit) throws DaoException;
	public List<Document> searchShop(String query, BoundingBox bbox, int limit) throws DaoException;
}
