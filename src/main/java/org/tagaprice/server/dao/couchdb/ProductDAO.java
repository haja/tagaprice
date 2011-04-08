package org.tagaprice.server.dao.couchdb;

import java.util.ArrayList;
import java.util.List;

import org.jcouchdb.document.ValueRow;
import org.jcouchdb.document.ViewResult;
import org.tagaprice.server.dao.IProductDAO;
import org.tagaprice.shared.entities.productmanagement.Product;

public class ProductDAO extends DAOClass<Product> implements IProductDAO {
	public ProductDAO() {
		super(Product.class, "product");
	}

	@Override
	public List<Product> find(Product searchPattern) {
		/// TODO implement actual searching here
		ViewResult<?> result = m_db.listDocuments(null, null);
		List<Product> rc = new ArrayList<Product>();
		
		System.out.println("CatList:");
		for (ValueRow<?> row: result.getRows()) {
			Product product = get(row.getId());
			rc.add(product);
		}
		
		return rc;

	}
}