package org.tagaprice.server.dao.mock;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import org.tagaprice.server.dao.IReceiptDao;
import org.tagaprice.shared.entities.receiptManagement.Receipt;
import org.tagaprice.shared.entities.receiptManagement.ReceiptEntry;
import org.tagaprice.shared.exceptions.dao.DaoException;

public class ReceiptDao extends DaoClass<Receipt> implements IReceiptDao {
	@Override
	public List<Receipt> list() {
		ArrayList<Receipt> rc = new ArrayList<Receipt>();

		for (Deque<Receipt> deque: m_data.values()) {
			rc.add(deque.peek());
		}

		return rc;
	}

	@Override
	public List<Receipt> listByPackage(String packageId) throws DaoException {
		List<Receipt> rc = new ArrayList<Receipt>();

		for (Receipt receipt: list()) {
			for (ReceiptEntry entry: receipt.getReceiptEntries()) {
				if (packageId.equals(entry.getPackageId())) {
					rc.add(receipt);
				}
			}
		}

		return rc;
	}

	@Override
	public List<Receipt> listByUser(String userId) throws DaoException {
		List<Receipt> rc = new ArrayList<Receipt>();

		for (Receipt receipt: list()) {
			if (userId.equals(receipt.getCreatorId())) {
				rc.add(receipt);
			}
		}

		return rc;
	}

	@Override
	public List<String> listPackageIDsByShop(String shopId) throws DaoException {
		List<String> rc = new ArrayList<String>();
		
		for (Receipt receipt: list()) {
			if (shopId.equals(receipt.getShopId())) {
				for (ReceiptEntry entry: receipt.getReceiptEntries()) {
					rc.add(entry.getPackageId());
				}
			}
		}
		
		return rc;
	}

}
