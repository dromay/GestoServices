package com.gesto.ecommerce.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gesto.ecommerce.dao.EmpresaDAO;
import com.gesto.ecommerce.model.Empresa;
import com.gesto.ecommerce.dao.util.JDBCUtils;
import com.gesto.ecommerce.exceptions.DataException;
import com.gesto.ecommerce.exceptions.InstanceNotFoundException;

public class EmpresaDAOImpl implements EmpresaDAO {

	public EmpresaDAOImpl() {
	}

	@Override
	public Empresa findById(Connection connection, Long id) throws InstanceNotFoundException, DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			String queryString = "SELECT empr.cod_empresa, empr.descripcion " + "FROM empresa empr "
					+ "WHERE empr.cod_empresa = ? ";

			preparedStatement = connection.prepareStatement(queryString, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			int i = 1;
			preparedStatement.setLong(i++, id);

			resultSet = preparedStatement.executeQuery();

			Empresa empr = null;

			if (resultSet.next()) {
				empr = loadNext(resultSet);
			} else {
				throw new InstanceNotFoundException("Customer with id " + id + "not found", Empresa.class.getName());
			}

			return empr;

		} catch (SQLException e) {
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}

	@Override
	public Boolean exists(Connection connection, Long id) throws DataException {
		boolean exist = false;

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			String queryString = "SELECT empr.cod_empresa, empr.descripcion " + "FROM empresa empr "
					+ "WHERE empr.cod_empresa = ? ";

			preparedStatement = connection.prepareStatement(queryString);

			int i = 1;
			preparedStatement.setLong(i++, id);

			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				exist = true;
			}

		} catch (SQLException e) {
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}

		return exist;
	}

	@Override
	public List<Empresa> findAll(Connection connection, int startIndex, int count) throws DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			String queryString = "SELECT empr.cod_empresa, empr.descripcion " + "FROM empresa empr "
					+ "ORDER BY empr.descripcion ASC";

			preparedStatement = connection.prepareStatement(queryString, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			resultSet = preparedStatement.executeQuery();

			List<Empresa> results = new ArrayList<Empresa>();
			Empresa empr = null;
			int currentCount = 0;

			if ((startIndex >= 1) && resultSet.absolute(startIndex)) {
				do {
					empr = loadNext(resultSet);
					results.add(empr);
					currentCount++;
				} while ((currentCount < count) && resultSet.next());
			}

			return results;

		} catch (SQLException e) {
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
		empr.setId(cod_empresa);
		empr.setDescripcion(descripcion);

		return empr;
	}

}
