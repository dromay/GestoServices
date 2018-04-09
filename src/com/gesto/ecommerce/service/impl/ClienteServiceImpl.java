package com.gesto.ecommerce.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gesto.ecommerce.dao.ClienteDAO;
import com.gesto.ecommerce.dao.impl.ClienteDAOImpl;
import com.gesto.ecommerce.dao.util.ConnectionManager;
import com.gesto.ecommerce.dao.util.JDBCUtils;
import com.gesto.ecommerce.exceptions.DataException;
import com.gesto.ecommerce.exceptions.InstanceNotFoundException;
import com.gesto.ecommerce.model.Cliente;
import com.gesto.ecommerce.service.ClienteCriteria;
import com.gesto.ecommerce.service.ClienteService;

public class ClienteServiceImpl implements ClienteService {
	
	private static Logger logger = LogManager.getLogger(ClienteServiceImpl.class.getName());

	private ClienteDAO dao = null;

	public ClienteServiceImpl() {
		dao = new ClienteDAOImpl();
	}
	
		@Override
		public Cliente findById(Long clienteId) throws InstanceNotFoundException, DataException {

			Connection connection = null;

			try {

				connection = ConnectionManager.getConnection();
				connection.setAutoCommit(true);

				return dao.findById(connection, clienteId);

			} catch (SQLException e) {
				logger.error(connection,e);
				throw new DataException(e);
			} finally {
				JDBCUtils.closeConnection(connection);
			}
		}

		@Override
		public Boolean exists(Long clienteId) throws DataException {
			Connection connection = null;

			try {

				connection = ConnectionManager.getConnection();
				connection.setAutoCommit(true);

				return dao.exists(connection, clienteId);

			} catch (SQLException e) {
				logger.error(connection,e);
				throw new DataException(e);
			} finally {
				JDBCUtils.closeConnection(connection);
			}
		}

		@Override
		public List<Cliente> findAll(int startIndex, int count) throws DataException {
			Connection connection = null;

			try {

				connection = ConnectionManager.getConnection();
				connection.setAutoCommit(true);

				return dao.findAll(connection, startIndex, count);

			} catch (SQLException e) {
				logger.error(connection,e);
				throw new DataException(e);
			} finally {
				JDBCUtils.closeConnection(connection);
			}
		}

		@Override
		public long countAll() throws DataException {
			Connection connection = null;

			try {

				connection = ConnectionManager.getConnection();
				connection.setAutoCommit(true);

				return dao.countAll(connection);

			} catch (SQLException e) {
				logger.error(connection,e);
				throw new DataException(e);
			} finally {
				JDBCUtils.closeConnection(connection);
			}
		}

		@Override
		public List<Cliente> findByCriteria(ClienteCriteria c, int startIndex, int count) throws DataException {
			Connection connection = null;

			try {

				connection = ConnectionManager.getConnection();
				connection.setAutoCommit(true);

				return dao.findByCriteria(connection, c, startIndex, count);

			} catch (SQLException e) {
				logger.error(connection,e);
				throw new DataException(e);
			} finally {
				JDBCUtils.closeConnection(connection);
			}
		}
}
