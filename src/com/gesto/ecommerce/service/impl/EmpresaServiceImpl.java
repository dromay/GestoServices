package com.gesto.ecommerce.service.impl;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gesto.ecommerce.dao.EmpresaDAO;
import com.gesto.ecommerce.dao.impl.EmpresaDAOImpl;
import com.gesto.ecommerce.dao.util.ConnectionManager;
import com.gesto.ecommerce.dao.util.JDBCUtils;
import com.gesto.ecommerce.exceptions.DataException;
import com.gesto.ecommerce.exceptions.InstanceNotFoundException;
import com.gesto.ecommerce.model.Empresa;
import com.gesto.ecommerce.service.EmpresaService;

public class EmpresaServiceImpl implements EmpresaService {

	private static Logger logger = LogManager.getLogger(EmpresaServiceImpl.class.getName());

	private EmpresaDAO dao = null;

	public EmpresaServiceImpl() {
		dao = new EmpresaDAOImpl();
	}

	@Override
	public Empresa findById(Long idEmpresa) throws InstanceNotFoundException, DataException {
		Connection connection = null;

		try {

			connection = ConnectionManager.getConnection();
			connection.setAutoCommit(true);

			return dao.findById(connection, idEmpresa);

		} catch (SQLException e) {
			logger.error(connection, e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeConnection(connection);
		}
	}

	@Override
	public Empresa findByTelefono(String tlf) throws InstanceNotFoundException, DataException {
		Connection connection = null;
		try {

			connection = ConnectionManager.getConnection();
			connection.setAutoCommit(true);

			return dao.findByTelefono(connection, tlf);

		} catch (SQLException e) {
			logger.error(connection, e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeConnection(connection);
		}
	}

}
