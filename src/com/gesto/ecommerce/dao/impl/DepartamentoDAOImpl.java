package com.gesto.ecommerce.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gesto.ecommerce.dao.DepartamentoDAO;
import com.gesto.ecommerce.model.Departamento;
import com.gesto.ecommerce.dao.util.JDBCUtils;
import com.gesto.ecommerce.exceptions.DataException;
import com.gesto.ecommerce.exceptions.InstanceNotFoundException;

public class DepartamentoDAOImpl implements DepartamentoDAO {

	private static Logger logger = LogManager.getLogger(DepartamentoDAOImpl.class.getName());

	public DepartamentoDAOImpl() {
	}

	@Override
	public List<Departamento> findAll(Connection connection, String locale) throws DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			String queryString = "SELECT dep.ext_departamento, ii.descripcion " + "FROM departamento dep "
					+ " INNER JOIN i_idioma_departamento ii ON ii.ext_departamento = dep.ext_departamento "
					+ " WHERE ii.cod_idioma = ? " + " ORDER BY ii.descripcion ASC ";

			preparedStatement = connection.prepareStatement(queryString, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			int i = 1;
			preparedStatement.setString(i++, locale);

			resultSet = preparedStatement.executeQuery();

			List<Departamento> results = new ArrayList<Departamento>();
			Departamento dep = null;

			while (resultSet.next()) {
				dep = loadNext(resultSet);
				results.add(dep);
			}

			return results;

		} catch (SQLException e) {
			logger.error(e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}

	@Override
	public Departamento findById(Connection connection, Long extDepartamento, String locale)
			throws InstanceNotFoundException, DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			String queryString = "SELECT dep.ext_departamento, ii.descripcion " + "FROM departamento dep "
					+ " INNER JOIN i_idioma_departamento ii ON ii.ext_departamento = dep.ext_departamento "
					+ " WHERE dep.ext_departamento = ? AND ii.cod_idioma = ? ";

			preparedStatement = connection.prepareStatement(queryString, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			int i = 1;
			preparedStatement.setLong(i++, extDepartamento);
			preparedStatement.setString(i++, locale);

			resultSet = preparedStatement.executeQuery();

			Departamento dep = null;

			if (resultSet.next()) {
				dep = loadNext(resultSet);
			} else {
				logger.error("Department with extension " + extDepartamento + " and locale "+locale+" not found",
						Departamento.class.getName());
				throw new InstanceNotFoundException("Department with extension " + extDepartamento +" and locale "+locale+ " not found",
						Departamento.class.getName());
			}

			return dep;

		} catch (SQLException e) {
			logger.error("Department extension: " + extDepartamento, e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}

	private Departamento loadNext(ResultSet resultSet) throws SQLException {

		int i = 1;
		Long ext_departamento = resultSet.getLong(i++);
		String descripcion = resultSet.getString(i++);

		Departamento dep = new Departamento();
		dep.setExtDepartamento(ext_departamento);
		dep.setDescripcion(descripcion);

		return dep;
	}

}
