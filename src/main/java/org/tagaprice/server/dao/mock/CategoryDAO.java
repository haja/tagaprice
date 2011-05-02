package org.tagaprice.server.dao.mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.tagaprice.server.dao.ICategoryDAO;
import org.tagaprice.server.dao.IDaoFactory;
import org.tagaprice.shared.entities.categorymanagement.Category;
import org.tagaprice.shared.logging.LoggerFactory;
import org.tagaprice.shared.logging.MyLogger;

public class CategoryDAO implements ICategoryDAO {
	HashMap<String, Category> categories = new HashMap<String, Category>();
	MyLogger logger = LoggerFactory.getLogger(CategoryDAO.class);
	Random rand = new Random(15645651);


	CategoryDAO(IDaoFactory daoFactory) {
		logger.log("Start CategoryDao");


	}

	@Override
	public Category create(Category category) {
		String id = ""+rand.nextLong();
		category.setId(id);
		category.setRevision(""+1);

		categories.put(id, category);


		logger.log("create category: "+category);

		return category;
	}

	@Override
	public Category get(String id, String revision) {
		Category rc = null;

		if (id != null && categories.containsKey(id)) {
			rc = categories.get(id);
		}

		return rc;
	}

	@Override
	public Category get(String id) {
		return get(id, null);
	}

	@Override
	public Category update(Category category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Category category) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Category> find(Category searchPattern) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Category> list() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<Category> getChildren(Category parent){
		return getChildren(parent.getId());
	}

	@Override
	public List<Category> getChildren(String id) {
		logger.log("childs: "+id);

		ArrayList<Category> rc = new ArrayList<Category>();
		//return root elements
		if(id==null){
			for(String key: categories.keySet()){
				if(categories.get(key).getParentCategory()==null){
					rc.add(categories.get(key));
				}
			}
		}else{


			for(String key: categories.keySet()){

				if(categories.get(key).getParentCategory()!=null){
					if(categories.get(key).getParentCategory().getId().equals(id))
						rc.add(categories.get(key));
				}

			}

		}

		return rc;
	}

}
