package org.tagaprice.client.gwt.server.diplomat.converter;

import java.util.*;

import org.slf4j.*;
import org.tagaprice.client.gwt.shared.entities.*;
import org.tagaprice.client.gwt.shared.entities.dump.*;
import org.tagaprice.client.gwt.shared.entities.productmanagement.IProduct;
import org.tagaprice.core.entities.*;
import org.tagaprice.core.entities.Category;

public class ProductConverter {

	Logger _log = LoggerFactory.getLogger(ProductConverter.class);

	private static final ProductConverter instance = new ProductConverter();

	public static ProductConverter getInstance() {
		return ProductConverter.instance;
	}

	public org.tagaprice.core.entities.Product convertProductToCore(final IProduct productGWT) {
		_log.debug("Convert GWT -> core, id: " + productGWT.getRevisionId());
		CategoryConverter categoryConverter = CategoryConverter.getInstance();

		/*
		 * Default values for new CoreProduct:
		 */
		Long productId = null;
		Integer revisionNumber = 0;

		/*
		 * Check, if product allready existed
		 */
		if (productGWT.getRevisionId() != null) {
			if (productGWT.getRevisionId().getId() != 0L) {
				productId = productGWT.getRevisionId().getId();
				//If product allready existed we increment the revision by 1
				revisionNumber = new Long(productGWT.getRevisionId().getRevision()).intValue();
			}
		}
		String title = productGWT.getTitle();
		String imageUrl = "";

		// TODO Category must never be null!
		Category category = categoryConverter.convertGWTCategoryToCore(productGWT.getCategory());

		Double amount = productGWT.getQuantity().getQuantity();
		Unit unit = productGWT.getQuantity().getUnit();

		ProductRevision revision = new ProductRevision(productId, revisionNumber + 1, title, DefaultValues.defaultDate,
				DefaultValues.defaultCoreAccount, unit, amount, category, "");
		Set<ProductRevision> revisions = new HashSet<ProductRevision>();
		revisions.add(revision);

		// ids must be the same value. if they are null the product must be created as new.

		Product productCore = new Product(productId, DefaultValues.defaultCoreLocale, revisions);
		_log.debug("Converted: " + productGWT + " into " + revision.getTitle() + ", " + revision.getId() + ", " + revision.getRevisionNumber());
		return productCore;
	}

	/**
	 * 
	 * @param productCore
	 * @param revision
	 *            when 0, then the latest revision is returned.
	 * @return
	 */
	public IProduct convertProductToGWT(final Product productCore, int revisionToGet) {
		_log.debug("Convert core -> GWT, id: " + productCore.getId() + ", rev: " + revisionToGet);
		_log.debug("product :"+productCore);
		// these are always existing products!!!
		ProductRevision pr = productCore.getCurrentRevision();
		_log.debug("currentRevision :"+pr);
		if(revisionToGet == 0) {
			productCore.getCurrentRevision();
		} else {
			for(ProductRevision pr_temp: productCore.getRevisions()){
				if(pr_temp.getRevisionNumber() == revisionToGet) {
					pr = pr_temp;
					break;
				}
			}
		}
		_log.debug("end of if/else");

		return convertCoreProductRevisionToGWTProduct(pr);
	}

	public IProduct convertCoreProductRevisionToGWTProduct(ProductRevision coreRevision) {
		IProduct gwtProduct = new org.tagaprice.client.gwt.shared.entities.productmanagement.Product();

		// get the data from the latest revision
		long id = coreRevision.getId();
		int revision = coreRevision.getRevisionNumber();
		String title = coreRevision.getTitle();
		ICategory category = new org.tagaprice.client.gwt.shared.entities.dump.Category(coreRevision.getCategory().getTitle());
		IQuantity quantity = new Quantity(coreRevision.getAmount(), coreRevision.getUnit());

		IRevisionId revisionId = new RevisionId(id, revision);
		IProduct productGWT = new org.tagaprice.client.gwt.shared.entities.productmanagement.Product(revisionId, title,
				category, quantity);
		_log.debug("Converted: " + coreRevision.getTitle() + ", " + coreRevision.getId() + ", " + coreRevision.getRevisionNumber() + " into " + productGWT);
		return productGWT;

	}
}
