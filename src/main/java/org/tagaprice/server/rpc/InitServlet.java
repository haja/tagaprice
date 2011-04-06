package org.tagaprice.server.rpc;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.tagaprice.server.dao.IDaoFactory;

public class InitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	IDaoFactory m_daoFactory = null;
	
	public void init() throws ServletException {
		try {
			ServletConfig config = getServletConfig();
			String daoFactoryClassName = config.getInitParameter("daoFactory");
			Object daoFactoryObject = Thread.currentThread().getContextClassLoader().loadClass(daoFactoryClassName).newInstance(); 
			if (daoFactoryObject instanceof IDaoFactory) {
				m_daoFactory = (IDaoFactory) daoFactoryObject;
			}
		}
		catch (ClassNotFoundException e) {
			throw new ServletException("Couldn't load DAO Factory", e);
		}
		catch (IllegalAccessException e) {
			throw new ServletException("Couldn't access DAO factory", e);
		}
		catch (InstantiationException e) {
			throw new ServletException("Failed to load DAO Factory", e);
		}
	}
	
	public IDaoFactory getDaoFactory() {
		return m_daoFactory;
	}
}