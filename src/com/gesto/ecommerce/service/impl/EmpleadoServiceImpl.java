package com.gesto.ecommerce.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gesto.ecommerce.dao.EmpleadoDAO;
import com.gesto.ecommerce.dao.impl.EmpleadoDAOImpl;
import com.gesto.ecommerce.dao.util.ConnectionManager;
import com.gesto.ecommerce.dao.util.JDBCUtils;
import com.gesto.ecommerce.exceptions.DataException;
import com.gesto.ecommerce.exceptions.InstanceNotFoundException;
import com.gesto.ecommerce.model.Empleado;
import com.gesto.ecommerce.service.EmpleadoCriteria;
import com.gesto.ecommerce.service.EmpleadoService;

public class EmpleadoServiceImpl implements EmpleadoService {

	private static Logger logger = LogManager.getLogger(EmpleadoServiceImpl.class.getName());

	private EmpleadoDAO dao = null;

	public EmpleadoServiceImpl() {
		dao = new EmpleadoDAOImpl();
	}
	
	@Override
	public List<Empleado> findAll(String locale) throws DataException {
		Connection connection = null;

		try {

			connection = ConnectionManager.getConnection();
			connection.setAutoCommit(true);

			return dao.findAll(connection, locale);

		} catch (SQLException e) {
			logger.error(e.getMessage(),e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeConnection(connection);
		}
	}

	@Override
	public Empleado findByUsuario(String usuario, String locale) throws InstanceNotFoundException, DataException {
		Connection connection = null;

		try {

			connection = ConnectionManager.getConnection();
			connection.setAutoCommit(true);

			return dao.findByUsuario(connection, usuario, locale);

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeConnection(connection);
		}
	}
	
	@Override
	public Empleado findSupervisor(Empleado es, String locale) throws InstanceNotFoundException, DataException {
		Connection connection = null;

		try {

			connection = ConnectionManager.getConnection();
			connection.setAutoCommit(true);

			return dao.findSupervisor(connection, es, locale);

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeConnection(connection);
		}
	}

	@Override
	public List<Empleado> findByCriteria(EmpleadoCriteria empleado, String locale, int startIndex, int count)
			throws DataException {
		Connection connection = null;

		try {

			connection = ConnectionManager.getConnection();
			connection.setAutoCommit(true);

			return dao.findByCriteria(connection, empleado, locale, startIndex, count);

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeConnection(connection);
		}
	}

	@Override
	public void updatePassword(Empleado e) throws InstanceNotFoundException, DataException {

		Connection connection = null;
		boolean commit = false;

		try {

			connection = ConnectionManager.getConnection();

			connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

			connection.setAutoCommit(false);

			// Execute action
			dao.updatePassword(connection, e);
			commit = true;

		} catch (SQLException em) {
			logger.error(em.getMessage(), em);
			throw new DataException(em);
		} finally {
			JDBCUtils.closeConnection(connection, commit);
		}
	}

}
