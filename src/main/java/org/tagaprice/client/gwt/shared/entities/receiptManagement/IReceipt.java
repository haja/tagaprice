package org.tagaprice.client.gwt.shared.entities.receiptManagement;

import java.util.ArrayList;
import java.util.Date;
import org.tagaprice.client.gwt.shared.entities.IEntity;
import org.tagaprice.client.gwt.shared.entities.dump.User;
import org.tagaprice.client.gwt.shared.entities.shopmanagement.IAddress;



public interface IReceipt extends IEntity<IReceipt> {

	public Date getDate();

	public void setDate(Date date);

	public IAddress getAddress();

	public void setAddress(IAddress address);

	public User getUser();

	public void setUser(User user);

	public ArrayList<IReceiptEntry> getReceiptEntries();

	public void setReceiptEntries(ArrayList<IReceiptEntry> receiptEntries);

	public void addReceiptEntriy(IReceiptEntry receiptEntry);

}