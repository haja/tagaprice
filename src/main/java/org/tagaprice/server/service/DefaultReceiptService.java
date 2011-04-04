package org.tagaprice.server.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.tagaprice.core.api.IReceiptService;
import org.tagaprice.core.api.ServerException;
import org.tagaprice.core.entities.Receipt;
import org.tagaprice.core.entities.ReceiptEntry;
import org.tagaprice.server.dao.interfaces.IReceiptDAO;

public class DefaultReceiptService implements IReceiptService {
	private IReceiptDAO _receiptDao;

	@Transactional
	@Override
	public Receipt save(Receipt receipt) throws ServerException {
		// TODO replace this by ArgumentUtility
		if(receipt == null)
			throw new IllegalArgumentException("receipt is null");
		return _receiptDao.save(receipt);
	}

	@Transactional(readOnly=true)
	@Override
	public List<ReceiptEntry> getReceiptEntriesByProductIdAndRev(Long productId, Integer productRevision) throws ServerException {
		List<ReceiptEntry> entries = _receiptDao.getReceiptEntriesByProductIdAndRev(productId, productRevision);
		return entries;
	}

	public void setReceiptDAO(IReceiptDAO receiptDao) {
		_receiptDao = receiptDao;
	}
}
