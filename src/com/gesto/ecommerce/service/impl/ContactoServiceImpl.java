package com.gesto.ecommerce.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.gesto.ecommerce.dao.ContactoDAO;
import com.gesto.ecommerce.dao.impl.ContactoDAOImpl;
import com.gesto.ecommerce.dao.util.ConnectionManager;
import com.gesto.ecommerce.dao.util.JDBCUtils;
import com.gesto.ecommerce.exceptions.DataException;
import com.gesto.ecommerce.model.Contacto;
import com.gesto.ecommerce.service.ContactoService;

public class ContactoServiceImpl implements ContactoService {

	private ContactoDAO dao = null;

	public ContactoServiceImpl() {
		dao = new ContactoDAOImpl();
	}

	@Override
	public Boolean exists(Long contactoCod) throws DataException {
		Connection connection = null;

		try {

			connection = ConnectionManager.getConnection();
			connection.setAutoCommit(true);

			return dao.exists(connection, contactoCod);

		} catch (SQLException e) {
			throw new DataException(e);
		} finally {
			JDBCUtils.closeConnection(connection);
		}
	}

	@Override
	public List<Contacto> findAll(int startIndex, int count) throws DataException {
		Connection connection = null;

		try {

			connection = ConnectionManager.getConnection();
			connection.setAutoCommit(true);

			return dao.findAll(connection, startIndex, count);

		} catch (SQLException e) {
			throw new DataException(e);
		} finally {
			JDBCUtils.closeConnection(connection);
		}
	}

	@Override
	public List<Contacto> findByCliente(Long clienteId) throws DataException {
		Connection connection = null;

		try {

			connection = ConnectionManager.getConnection();
			connection.setAutoCommit(true);

			return dao.findByCliente(connection, clienteId);

		} catch (SQLException e) {
			throw new DataException(e);
		} finally {
			JDBCUtils.closeConnection(connection);
		}
	}

	@Override
	public List<Contacto> findByGestion(Long idGestion) throws DataException {
		Connection connection = null;

		try {

			connection = ConnectionManager.getConnection();
			connection.setAutoCommit(true);

			return dao.findByGestion(connection, idGestion);

		} catch (SQLException e) {
			throw new DataException(e);
		} finally {
			JDBCUtils.closeConnection(connection);
		}
	}

}
