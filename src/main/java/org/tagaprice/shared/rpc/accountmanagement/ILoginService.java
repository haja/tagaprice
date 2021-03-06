package org.tagaprice.shared.rpc.accountmanagement;

import org.tagaprice.shared.entities.accountmanagement.User;
import org.tagaprice.shared.exceptions.InvitationKeyUsedOrInvalidException;
import org.tagaprice.shared.exceptions.UserAlreadyLoggedInException;
import org.tagaprice.shared.exceptions.UserNotConfirmedException;
import org.tagaprice.shared.exceptions.UserNotLoggedInException;
import org.tagaprice.shared.exceptions.WrongEmailOrPasswordException;
import org.tagaprice.shared.exceptions.dao.DaoException;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("loginservice")
public interface ILoginService extends RemoteService {

	/**
	 * Try to login using a unique mail address and a password
	 * @param email User's mail address
	 * @param password User's password
	 * @return Session-ID
	 * @throws WrongEmailOrPasswordException if the login failed
	 * @throws DaoException if something went wrong at the database backend
	 */
	public User setLogin(String email, String password) throws WrongEmailOrPasswordException,
	UserAlreadyLoggedInException, DaoException, UserNotConfirmedException;


	/**
	 * clear sessionId on server
	 */
	public void setLogout() throws UserNotLoggedInException;


	/**
	 * Ask server if user is loggedIn. If so the server will return a new sessionId.
	 * 
	 * @return Returns a new or old sessionId if user is logged in. If user is not logged in, return value is NULL.
	 */
	public User isLoggedIn();

	
	/**
	 * Add this email address to invite queue
	 * @param email email of the person who wants to get an invitation
	 * @return true if everything was ok
	 */
	public boolean addEmailToInviteQueue(String email) throws DaoException;
	

	/**
	 * Returns true if email is not used yet.
	 * 
	 * @param email
	 *            to lookup.
	 * @return true if email is not used yet.
	 */
	public boolean isEmailAvailable(String email);

	/**
	 * 
	 * @param displayName The users display name. Don't have to be unique
	 * @param email email that should be registered
	 * @param password password
	 * @param invitationKey 
	 * @return true if everything was ok.
	 * @throws DaoException
	 */
	public boolean registerUser(String displayName, String email, String password, String invitationKey) 
	throws InvitationKeyUsedOrInvalidException, DaoException, UserAlreadyLoggedInException;

	/**
	 * Set new password.
	 * 
	 * @param newPassword
	 *            new password
	 * @param confirmPassword
	 *            second time the new password
	 * @return True if password has being changed.
	 */
	public boolean setNewPassword(String newPassword, String confirmPassword)
	throws UserNotLoggedInException, DaoException;
	
	/**
	 * Returns true/false if this email has been confirmed. But false if not, or not available. 
	 * @param email email to check
	 * @return true/false if this email has been confirmed. But false if not, or not available. 
	 */
	public boolean isEmailConfirmed(String email);


	/**
	 * Send invite key to this email and returns current count
	 * @param email email of a friend
	 * @return current invite count
	 * @throws DaoException
	 */
	long sendInviteToFriend(String email) throws DaoException, UserNotLoggedInException;
}
