package com.gesto.ecommerce.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gesto.ecommerce.dao.DepartamentoDAO;
import com.gesto.ecommerce.dao.impl.DepartamentoDAOImpl;
import com.gesto.ecommerce.dao.util.ConnectionManager;
import com.gesto.ecommerce.dao.util.JDBCUtils;
import com.gesto.ecommerce.exceptions.DataException;
import com.gesto.ecommerce.exceptions.InstanceNotFoundException;
import com.gesto.ecommerce.model.Departamento;
import com.gesto.ecommerce.service.DepartamentoService;

public class DepartamentoServiceImpl implements DepartamentoService {
	
	private static Logger logger = LogManager.getLogger(DepartamentoServiceImpl.class.getName());

	private DepartamentoDAO dao = null;

	public DepartamentoServiceImpl() {
		dao = new DepartamentoDAOImpl();
	}

	
	@Override
	public List<Departamento> findAll(String locale) throws DataException {
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
	public Departamento findById(Long extDepartamento, String locale) throws InstanceNotFoundException, DataException {
		Connection connection = null;

		try {

			connection = ConnectionManager.getConnection();
			connection.setAutoCommit(true);

			return dao.findById(connection, extDepartamento, locale);

		} catch (SQLException e) {
			logger.error(e.getMessage(),e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeConnection(connection);
		}
	}

	



}
