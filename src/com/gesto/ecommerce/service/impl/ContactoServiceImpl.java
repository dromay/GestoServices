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
import com.gesto.ecommerce.model.Cliente;
import com.gesto.ecommerce.model.Contacto;
import com.gesto.ecommerce.model.Empresa;
import com.gesto.ecommerce.service.ContactoService;

public class ContactoServiceImpl implements ContactoService {
	
	private static Logger logger = LogManager.getLogger(ContactoServiceImpl.class.getName());

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
			logger.error(connection,e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeConnection(connection);
		}
	}
	
	@Override
	public Contacto findById(Long contactoCod) throws InstanceNotFoundException, DataException {
		Connection connection = null;

		try {

			connection = ConnectionManager.getConnection();
			connection.setAutoCommit(true);

			return dao.findById(connection, contactoCod);

		} catch (SQLException e) {
			logger.error(connection,e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeConnection(connection);
		}
	}
	
	
	@Override
	public Contacto findByTelefono(String telefono) throws InstanceNotFoundException, DataException {

		Connection connection = null;

		try {

			connection = ConnectionManager.getConnection();
			connection.setAutoCommit(true);

			return dao.findByTelefono(connection, telefono);

		} catch (SQLException e) {
			logger.error(connection,e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeConnection(connection);
		}
	}

	@Override
	public List<Contacto> findAll(int startIndex, int pageSize) throws DataException {
		Connection connection = null;

		try {

			connection = ConnectionManager.getConnection();
			connection.setAutoCommit(true);

			return dao.findAll(connection, startIndex, pageSize);

		} catch (SQLException e) {
			logger.error(connection,e);
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
			logger.error(connection,e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeConnection(connection);
		}
	}

	@Override
	public List<Contacto> findByGestion(Long idGestion, int startIndex, int pageSize) throws DataException {
		Connection connection = null;

		try {

			connection = ConnectionManager.getConnection();
			connection.setAutoCommit(true);

			return dao.findByGestion(connection, idGestion, startIndex, pageSize);

		} catch (SQLException e) {
			logger.error(connection,e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeConnection(connection);
		}
	}

}
