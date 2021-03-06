package com.gesto.ecommerce.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gesto.ecommerce.dao.EmpresaDAO;
import com.gesto.ecommerce.model.Empresa;
import com.gesto.ecommerce.dao.util.JDBCUtils;
import com.gesto.ecommerce.exceptions.DataException;
import com.gesto.ecommerce.exceptions.InstanceNotFoundException;

public class EmpresaDAOImpl implements EmpresaDAO {
	
	private static Logger logger = LogManager.getLogger(EmpresaDAOImpl.class.getName());
	
	public EmpresaDAOImpl() {
	}

	@Override
	public Empresa findById(Connection connection, Long idEmpresa) throws InstanceNotFoundException, DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			String queryString = "SELECT empr.cod_empresa, empr.descripcion " + "FROM empresa_receptor empr "
					+ "WHERE empr.cod_empresa = ? ";

			preparedStatement = connection.prepareStatement(queryString, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			int i = 1;
			preparedStatement.setLong(i++, idEmpresa);

			resultSet = preparedStatement.executeQuery();

			Empresa empr = null;

			if (resultSet.next()) {
				empr = loadNext(resultSet);
			} else {
				logger.error("Company with id " + idEmpresa + "not found", Empresa.class.getName());
				throw new InstanceNotFoundException("Company with id " + idEmpresa + "not found", Empresa.class.getName());
			}

			return empr;

		} catch (SQLException e) {
			logger.error("Company ID: " + idEmpresa, e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}
	@Override
	public Empresa findByTelefono(Connection connection, String tlf) throws InstanceNotFoundException, DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			String queryString = "SELECT empr.cod_empresa, empr.descripcion " + "FROM empresa_receptor empr "
					+" INNER JOIN empresa_contacto ec ON empr.cod_empresa = ec.cod_empresa " 
					+" INNER JOIN contacto co ON ec.cod_contacto = co.cod_contacto "
					+ "WHERE co.tlf = ? ";
			

			preparedStatement = connection.prepareStatement(queryString, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			int i = 1;
			preparedStatement.setString(i++, tlf);

			resultSet = preparedStatement.executeQuery();

			Empresa empr = null;

			if (resultSet.next()) {
				empr = loadNext(resultSet);
			} else {
				logger.error("Company with tlf " + tlf + "not found", Empresa.class.getName());
				throw new InstanceNotFoundException("Company with tlf " + tlf + "not found", Empresa.class.getName());
			}

			return empr;

		} catch (SQLException e) {
			logger.error("Telephone number: " + tlf, e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}

	private Empresa loadNext(ResultSet resultSet) throws SQLException {

		int i = 1;
		Long cod_empresa = resultSet.getLong(i++);
		String descripcion = resultSet.getString(i++);

		Empresa empr = new Empresa();
		empr.setIdEmpresa(cod_empresa);
		empr.setDescripcion(descripcion);

		return empr;
	}

}
