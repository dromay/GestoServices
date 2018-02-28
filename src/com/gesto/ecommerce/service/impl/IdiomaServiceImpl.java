package com.gesto.ecommerce.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.gesto.ecommerce.dao.IdiomaDAO;
import com.gesto.ecommerce.dao.impl.IdiomaDAOImpl;
import com.gesto.ecommerce.dao.util.ConnectionManager;
import com.gesto.ecommerce.dao.util.JDBCUtils;
import com.gesto.ecommerce.exceptions.DataException;
import com.gesto.ecommerce.model.Idioma;
import com.gesto.ecommerce.service.IdiomaService;

public class IdiomaServiceImpl implements IdiomaService {
	
	private IdiomaDAO dao = null;
		
		public IdiomaServiceImpl() {
			dao = new IdiomaDAOImpl();
		}

		@Override
		public List<Idioma> findByCliente(Long cod_cliente) throws DataException {
			Connection connection = null;

			try {

				connection = ConnectionManager.getConnection();
				connection.setAutoCommit(true);

				return dao.findByCliente(connection, cod_cliente);

			} catch (SQLException e) {
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

				return dao.findByCliente(connection, id);

			} catch (SQLException e) {
				throw new DataException(e);
			} finally {
				JDBCUtils.closeConnection(connection);
			}
		}
		
}

