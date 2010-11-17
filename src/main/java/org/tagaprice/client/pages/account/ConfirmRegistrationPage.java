package org.tagaprice.client.pages.account;

import org.tagaprice.client.RPCHandlerManager;
import org.tagaprice.client.pages.APage;
import org.tagaprice.client.widgets.TitleWidget;
import org.tagaprice.client.widgets.InfoBoxWidget.BoxType;
import org.tagaprice.client.widgets.TitleWidget.Headline;
import org.tagaprice.shared.Address;
import org.tagaprice.shared.rpc.LocalAccountHandler;
import org.tagaprice.shared.rpc.LocalAccountHandlerAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * Create a confirmation page to check if the user is successfully registered.
 * 
 */
public class ConfirmRegistrationPage extends APage {

	private SimplePanel _siPa1 = new SimplePanel();
	private LocalAccountHandlerAsync _userHandler = RPCHandlerManager
			.getLocalAccountHandler();

	/**
	 * Displays a standard text for the user to check his email account for a
	 * confirmation mail.
	 */
	public ConfirmRegistrationPage() {
		_init();
		// TODO Auto-generated constructor stub
		_siPa1.setWidget(new TitleWidget(
				"Confirm",
				new Label(
						"Please check your email account and follow the confirm link in the next 24h to finish the registration!"),
				Headline.H2));

	}

	/**
	 * Displays a text where the user can find out if the registration was
	 * successful or not.
	 * 
	 * @param confirm
	 */
	public ConfirmRegistrationPage(String confirm) {
		_init();
		_siPa1.setWidget(new Label(
				"Please wait while we check you confirmation!"));
		_userHandler.confirm(confirm, new AsyncCallback<Boolean>() {

			@Override
			public void onFailure(Throwable caught) {
				showInfo("Problem at confirming your email!",
						BoxType.WARNINGBOX);
			}

			@Override
			public void onSuccess(Boolean result) {
				if (result) {
					_siPa1.setWidget(new Label(
							"Thank you for joining TagAPrice"));
				} else {
					_siPa1.setWidget(new Label("Ubs! Problem at registration!"));

				}

			}
		});

	}

	@Override
	public void setAddress(Address address) {
		// TODO Auto-generated method stub

	}

	private void _init() {
		_siPa1.setWidth("100%");
		init(_siPa1);
	}

}