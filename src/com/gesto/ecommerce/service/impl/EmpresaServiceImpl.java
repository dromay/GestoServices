package com.gesto.ecommerce.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.gesto.ecommerce.dao.EmpresaDAO;
import com.gesto.ecommerce.dao.impl.EmpresaDAOImpl;
import com.gesto.ecommerce.dao.util.ConnectionManager;
import com.gesto.ecommerce.dao.util.JDBCUtils;
import com.gesto.ecommerce.exceptions.DataException;
import com.gesto.ecommerce.exceptions.InstanceNotFoundException;
import com.gesto.ecommerce.model.Empresa;
import com.gesto.ecommerce.service.EmpresaService;

public class EmpresaServiceImpl implements EmpresaService {
	
	private EmpresaDAO dao = null;
		
		public EmpresaServiceImpl() {
			dao = new EmpresaDAOImpl();
		}

		@Override
		public Empresa findById(Long id) throws InstanceNotFoundException, DataException {
			Connection connection = null;

			try {

				connection = ConnectionManager.getConnection();
				connection.setAutoCommit(true);

				return dao.findById(connection, id);

			} catch (SQLException e) {
				throw new DataException(e);
			} finally {
				JDBCUtils.closeConnection(connection);
			}
		}

		@Override
		public Boolean exists(Long id) throws DataException {
			Connection connection = null;

			try {

				connection = ConnectionManager.getConnection();
				connection.setAutoCommit(true);

				return dao.exists(connection, id);

			} catch (SQLException e) {
				throw new DataException(e);
			} finally {
				JDBCUtils.closeConnection(connection);
			}
		}

		@Override
		public List<Empresa> findAll(int startIndex, int count) throws DataException {
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
		
}

