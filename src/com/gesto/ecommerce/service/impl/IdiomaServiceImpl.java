package com.gesto.ecommerce.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gesto.ecommerce.dao.IdiomaDAO;
import com.gesto.ecommerce.dao.impl.IdiomaDAOImpl;
import com.gesto.ecommerce.dao.util.ConnectionManager;
import com.gesto.ecommerce.dao.util.JDBCUtils;
import com.gesto.ecommerce.exceptions.DataException;
import com.gesto.ecommerce.model.Contacto;
import com.gesto.ecommerce.model.Idioma;
import com.gesto.ecommerce.service.IdiomaService;

public class IdiomaServiceImpl implements IdiomaService {
	
	private static Logger logger = LogManager.getLogger(IdiomaServiceImpl.class.getName());
	
	private IdiomaDAO dao = null;
		
		public IdiomaServiceImpl() {
			dao = new IdiomaDAOImpl();
		}
		
		@Override
		public List<Idioma> findAll(int startIndex, int pageSize) throws DataException {
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
		public List<Idioma> findByCliente(Long clienteId) throws DataException {
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
		public List<Idioma> findByEmpleado(Long id) throws DataException {
			Connection connection = null;

			try {

				connection = ConnectionManager.getConnection();
				connection.setAutoCommit(true);

				return dao.findByEmpleado(connection, id);

			} catch (SQLException e) {
				logger.error(connection,e);
				throw new DataException(e);
			} finally {
				JDBCUtils.closeConnection(connection);
			}
		}
		
		@Override
		public List<Idioma> findByContacto(Long codContacto) throws DataException {
			Connection connection = null;

			try {

				connection = ConnectionManager.getConnection();
				connection.setAutoCommit(true);

				return dao.findByContacto(connection, codContacto);

			} catch (SQLException e) {
				logger.error(connection,e);
				throw new DataException(e);
			} finally {
				JDBCUtils.closeConnection(connection);
			}
		}
		
}

