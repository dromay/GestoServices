package com.gesto.ecommerce.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gesto.ecommerce.dao.ContactoDAO;
import com.gesto.ecommerce.dao.impl.ContactoDAOImpl;
import com.gesto.ecommerce.dao.util.ConnectionManager;
import com.gesto.ecommerce.dao.util.JDBCUtils;
import com.gesto.ecommerce.exceptions.DataException;
import com.gesto.ecommerce.exceptions.InstanceNotFoundException;
import com.gesto.ecommerce.model.Contacto;
import com.gesto.ecommerce.service.ContactoService;

public class ContactoServiceImpl implements ContactoService {

	private static Logger logger = LogManager.getLogger(ContactoServiceImpl.class.getName());

	private ContactoDAO dao = null;

	public ContactoServiceImpl() {
		dao = new ContactoDAOImpl();
	}

	@Override
	public Contacto findById(Long idContacto, String locale) throws InstanceNotFoundException, DataException {
		Connection connection = null;

		try {

			connection = ConnectionManager.getConnection();
			connection.setAutoCommit(true);

			return dao.findById(connection, idContacto, locale);

		} catch (SQLException e) {
			logger.error(e.getMessage(),e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeConnection(connection);
		}
	}

	@Override
	public Contacto findByTelefono(String tlf, String locale) throws InstanceNotFoundException, DataException {

		Connection connection = null;

		try {

			connection = ConnectionManager.getConnection();
			connection.setAutoCommit(true);

			return dao.findByTelefono(connection, tlf, locale);

		} catch (SQLException e) {
			logger.error(e.getMessage(),e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeConnection(connection);
		}
	}

	@Override
	public List<Contacto> findByCliente(Long idCliente, String locale) throws DataException {
		Connection connection = null;

		try {

			connection = ConnectionManager.getConnection();
			connection.setAutoCommit(true);

			return dao.findByCliente(connection, idCliente, locale);

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeConnection(connection);
		}
	}

	@Override
	public List<Contacto> findByGestion(Long idGestion, String locale) throws DataException {
		Connection connection = null;

		try {

			connection = ConnectionManager.getConnection();
			connection.setAutoCommit(true);

			return dao.findByGestion(connection, idGestion, locale);

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeConnection(connection);
		}
	}

}
