package org.tagaprice.client.gwt.shared.entities;

import java.util.Date;

import org.tagaprice.client.gwt.shared.entities.accountmanagement.IUser;

/**
 * A {@link RevisionId} is used to
 * - FIND an {@link AEntity}, by the ID or ID and Revision
 * - UPDATE an {@link AEntity} by the ID and Revision
 */
public class RevisionId implements IRevisionId {


	private static final long serialVersionUID = 2011503142739304476L;
	private long _id;
	private int _rev;
	private IUser _user;
	private Date _date;

	public RevisionId() {
		this._id = 0L;
		this._rev = 0;
	}

	/**
	 * Initializes Revision id with given productId and Revision 0L.
	 * @param id
	 */
	public RevisionId(long id) {
		setId(id);
		setRevision(0);
	}

	public RevisionId(long id, int revision) {
		this(id);
		setRevision(revision);
	}

	@Override
	public void setId(long id) {
		_id = id;
	}

	@Override
	public long getId() {
		return _id;
	}

	@Override
	public void setRevision(int rev) {
		_rev = rev;
	}

	@Override
	public int getRevision() {
		return _rev;
	}

	@Override
	public void setUser(IUser user) {
		_user = user;
	}

	@Override
	public IUser getUser() {
		return _user;
	}

	@Override
	public void setDate(Date date) {
		_date = date;
	}

	@Override
	public Date getDate() {
		return _date;
	}

	@Override
	public int hashCode() {
		// http://www.javapractices.com/topic/TopicAction.do?Id=28
		int prime = 37;
		// Calculate with ID and REV
		int hash = ((int) (this._id ^ (this._id >>> 32))) * prime + (this._rev ^ (this._rev >>> 32));

		return hash;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof IRevisionId) {
			return this.equals((IRevisionId) o);
		} else {
			return false;
		}
	}

	public boolean equals(IRevisionId other) {
		return this._id == other.getId() && this._rev == other.getRevision();
	}

	@Override
	public String toString() {
		return this._id + "_" + this._rev;
	}

	@Override
	public IRevisionId copy() {
		return new RevisionId(this._id, this._rev);
	}

}
