package org.tagaprice.shared.entities;

import java.io.Serializable;

public interface IEntity<T> extends Serializable  {


	/**
	 * Returns the RevisionId of the {@link AEntity} or null, if IRevisionId is not set.
	 * @return Returns the RevisionId of the {@link AEntity or null, if IRevisionId is not set.
	 */
	public IRevisionId getRevisionId() ;

	public void setRevisionId(IRevisionId revisionId);

	/**
	 * Returns the Title of the {@link AEntity}, if Title is NULL you get NULL back
	 * @return Returns the Title of the {@link AEntity} or null.
	 */
	public String getTitle();

	/**
	 * This method can be used by the client and the server.
	 * @param title Sets the entity title
	 */
	public void setTitle(String title);


}
