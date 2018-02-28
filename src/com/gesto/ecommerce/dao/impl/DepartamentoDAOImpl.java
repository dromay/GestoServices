package com.gesto.ecommerce.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gesto.ecommerce.dao.DepartamentoDAO;
import com.gesto.ecommerce.model.Departamento;
import com.gesto.ecommerce.dao.util.JDBCUtils;
import com.gesto.ecommerce.exceptions.DataException;
import com.gesto.ecommerce.exceptions.InstanceNotFoundException;

public class DepartamentoDAOImpl implements DepartamentoDAO {

	public DepartamentoDAOImpl() {
	}

	@Override
	public Departamento findById(Connection connection, Long extDepartamento)
			throws InstanceNotFoundException, DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			String queryString = "SELECT dep.ext_departamento, dep.descripcion " + "FROM departamento dep "
					+ "WHERE dep.ext_departamento = ? ";

			preparedStatement = connection.prepareStatement(queryString, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			int i = 1;
			preparedStatement.setLong(i++, extDepartamento);

			resultSet = preparedStatement.executeQuery();

			Departamento dep = null;

			if (resultSet.next()) {
				dep = loadNext(resultSet);
			} else {
				throw new InstanceNotFoundException("Customer with id " + extDepartamento + "not found",
						Departamento.class.getName());
			}

			return dep;

		} catch (SQLException e) {
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}

	@Override
	public Boolean exists(Connection connection, Long extDepartamento) throws DataException {
		boolean exist = false;

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			String queryString = "SELECT dep.ext_departamento, dep.descripcion " + "FROM departamento dep "
					+ "WHERE dep.ext_departamento = ? ";

			preparedStatement = connection.prepareStatement(queryString);

			int i = 1;
			preparedStatement.setLong(i++, extDepartamento);

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
	public List<Departamento> findAll(Connection connection, int startIndex, int count) throws DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			String queryString = "SELECT dep.ext_departamento, dep.descripcion " + "FROM departamento dep "
					+ "ORDER BY dep.descripcion ASC";

			preparedStatement = connection.prepareStatement(queryString, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			resultSet = preparedStatement.executeQuery();

			List<Departamento> results = new ArrayList<Departamento>();
			Departamento dep = null;
			int currentCount = 0;

			if ((startIndex >= 1) && resultSet.absolute(startIndex)) {
				do {
					dep = loadNext(resultSet);
					results.add(dep);
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
