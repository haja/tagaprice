package org.tagaprice.server.dao.couchdb;

import org.jcouchdb.db.Options;
import org.jcouchdb.document.ValueRow;
import org.jcouchdb.document.ViewResult;
import org.tagaprice.server.dao.IUserDao;
import org.tagaprice.shared.entities.Document;
import org.tagaprice.shared.entities.accountmanagement.User;
import org.tagaprice.shared.exceptions.dao.DaoException;

public class UserDao extends DaoClass<User> implements IUserDao {
	public UserDao(CouchDbDaoFactory daoFactory) {
		super(daoFactory, User.class, Document.Type.USER, null);
	}

	@Override
	protected void _checkCreatorId(String creatorId) throws DaoException {
		if (creatorId != null) throw new DaoException("The creator ID of User objects has to be null!");
	}

	@Override
	public User getByMail(String mail) {
		ViewResult<User> result = m_db.queryView("user/byMail", User.class, new Options().key(mail), null);
		User rc = null;

		if (result.getRows().size() > 0) {
			ValueRow<User> firstRow = result.getRows().get(0);
			rc = firstRow.getValue();
		}

		return rc;
	}
	
	
}
