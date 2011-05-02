package org.tagaprice.server.dao;

import java.util.List;

import org.tagaprice.shared.entities.productmanagement.Package;

public interface IPackageDAO extends IDaoClass<Package> {
	public List<Package> find(final Package searchPattern);
}
