package com.gesto.ecommerce.dao.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ConnectionManager	 {

	private static ResourceBundle dbConfiguration = ResourceBundle.getBundle("DBConfiguration");

	private static final String DRIVER_CLASS_NAME_PARAMETER = "jdbc.driver.classname";
	private static final String URL_PARAMETER = "jdbc.url";
	private static final String USER_PARAMETER = "jdbc.user";
	private static final String PASSWORD_PARAMETER = "jdbc.password";

	private static String url;
	private static String user;
	private static String password;

	static {

		try {

			String driverClassName = dbConfiguration.getString(DRIVER_CLASS_NAME_PARAMETER);
			url = dbConfiguration.getString(URL_PARAMETER);
			user = dbConfiguration.getString(USER_PARAMETER);
			password = dbConfiguration.getString(PASSWORD_PARAMETER);

			/* Load driver. */
			Class.forName(driverClassName);

		} catch (Exception e) {
			// JAL: TODO Logger
			e.printStackTrace(); 
		}

	}

	private ConnectionManager() {}

	public final static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, user, password);
	}
	
}