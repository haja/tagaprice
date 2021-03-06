package org.tagaprice.shared.entities.shopmanagement;

import org.svenson.JSONProperty;
import org.tagaprice.shared.entities.Document;
import org.tagaprice.shared.entities.Address;
import org.tagaprice.shared.entities.categorymanagement.Category;

/**
 * Document representing a Shop
 */
public class Shop extends Document {
	private static final long serialVersionUID = 1L;
	private Category _category;
	private Address _address = new Address();
	private Shop _parent;

	/**
	 * This constructor is used by the serialization algorithm
	 */
	public Shop() {
	}



	/**
	 * <b>NEW</b>
	 * Constructor to create a new {@link Shop}
	 * 
	 * @param creator Creator of the current document revision 
	 * @param title Title of the shop
	 */
	public Shop(String creatorId, String title, Category category) {
		super(creatorId, title);
		this._category = category;
	}


	/**
	 * <b>UPDATE and GET</b>
	 * Constructor to update a {@link Shop}
	 * 
	 * @param creator Creator of the current document revision 
	 * @param revisionId
	 * @param title
	 */
	public Shop(String creatorId, String shopId, String revision, String title, Category category) {
		super(creatorId, shopId, revision, title);
		this._category = category;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Shop";
	}

	/**
	 * Set The parent {@link Shop}
	 * @param parent the parent {@link Shop}
	 */
	public void setParent(Shop parent) {
		_parent=parent;
	}

	/**
	 * Returns the {@link Shop} parent
	 * @return the parent. If null no parent.
	 */
	@JSONProperty(ignore=true)
	public Shop getParent() {
		return _parent;
	}


	/**
	 * Set an {@link Address} to the {@link Shop}
	 * @param address {@link Shop} address
	 */
	public void setAddress(Address address) {
		_address=address;
	}

	/**
	 * @return the address of the {@link Shop}
	 */
	public Address getAddress() {
		return _address;
	}

	/**
	 * Sets the depending {@link Category} for a product.
	 * 
	 * @param category
	 *            Is the depending {@link Category} for a product
	 */
	public void setCategory(Category category) {
		_category = category;

	}

	/**
	 * Internal method necessary for the CouchDB injection to work
	 * @param categoryId Category ID
	 */
	public void setCategoryId(String categoryId) {
		setCategory(new Category(null, categoryId, null, null, null));
	}
	
	/**
	 * Returns the {@link Category} which this {@link Product} depends from.
	 * 
	 * @return Returns the {@link Category} which this {@link Product} depends from.
	 */
	@JSONProperty(ignore = true)
	public Category getCategory() {
		return _category;
	}

	/**
	 * Returns CategoryId or null
	 * @return CategoryId or null
	 */
	@JSONProperty(value = "categoryId", ignoreIfNull = true)
	public String getCategoryId() {
		String rc = null;
		if (getCategory() != null)
			rc = getCategory().getId();
		return rc;
	}

	
	/**
	 * Tries to create a Shop from a Document (just copies the most essential stuff!)
	 * @param document Document to copy
	 */
	public static Shop fromDocument(Document document) {
		//return (Shop)document;
		Shop ts = new Shop(document.getCreatorId(), document.getId(), document.getRevision(), document.getTitle(), null);
		ts.setAddress(document.getAddress());
		
		return ts;	
	}

}
