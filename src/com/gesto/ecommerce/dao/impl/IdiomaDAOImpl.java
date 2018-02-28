package com.gesto.ecommerce.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gesto.ecommerce.dao.IdiomaDAO;
import com.gesto.ecommerce.dao.util.JDBCUtils;
import com.gesto.ecommerce.exceptions.DataException;
import com.gesto.ecommerce.model.Idioma;

public class IdiomaDAOImpl implements IdiomaDAO {

	public IdiomaDAOImpl() {
	}

	private Idioma loadNext(ResultSet resultSet) throws SQLException {

		int i = 1;
		String cod_idioma = resultSet.getString(i++);
		String descripcion = resultSet.getString(i++);

		Idioma idm = new Idioma();
		idm.setId(cod_idioma);
		idm.setDescripcion(descripcion);

		return idm;
	}

	public List<Idioma> findByEmpleado(Connection connection, Long id) throws DataException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			String queryString = "SELECT idm.cod_idioma, idm.descripcion " + "FROM idioma idm "
					+ " INNER JOIN empleado em ON idm.cod_idioma = em.cod_idioma AND em.cod_empleado = ? ";

			preparedStatement = connection.prepareStatement(queryString, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			int i = 1;
			preparedStatement.setLong(i++, id);

			resultSet = preparedStatement.executeQuery();

			List<Idioma> results = new ArrayList<Idioma>();

			Idioma idm = null;

			while (resultSet.next()) {
				idm = loadNext(resultSet);
				results.add(idm);
			}
			return results;

		} catch (SQLException e) {
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}

	public List<Idioma> findByCliente(Connection connection, Long clienteId) throws DataException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			String queryString = "SELECT idm.cod_idioma, idm.descripcion " + "FROM idioma idm "
					+ " INNER JOIN cliente c ON c.cod_idioma = em.cod_idioma AND em.cod_cliente = ? ";

			preparedStatement = connection.prepareStatement(queryString, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			int i = 1;
			preparedStatement.setLong(i++, clienteId);

			resultSet = preparedStatement.executeQuery();

			List<Idioma> results = new ArrayList<Idioma>();

			Idioma idm = null;

			while (resultSet.next()) {
				idm = loadNext(resultSet);
				results.add(idm);
			}
			return results;

		} catch (SQLException e) {
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}
}
