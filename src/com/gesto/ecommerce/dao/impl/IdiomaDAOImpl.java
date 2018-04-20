package com.gesto.ecommerce.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gesto.ecommerce.dao.IdiomaDAO;
import com.gesto.ecommerce.dao.util.JDBCUtils;
import com.gesto.ecommerce.exceptions.DataException;
import com.gesto.ecommerce.model.Contacto;
import com.gesto.ecommerce.model.Idioma;

public class IdiomaDAOImpl implements IdiomaDAO {

	private static Logger logger = LogManager.getLogger(IdiomaDAOImpl.class.getName());

	public IdiomaDAOImpl() {
	}

	@Override
	public List<Idioma> findAll(Connection connection, String locale) throws DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			String queryString = "SELECT idm.cod_idioma, ii.descripcion " + "FROM idioma idm "
					+ " INNER JOIN i_idioma ii ON ii.cod_idioma = idm.cod_idioma " + " WHERE ii.i_cod_idioma ? "
					+ " ORDER BY idm.cod_idioma ASC ";

			preparedStatement = connection.prepareStatement(queryString, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			int i = 1;
			preparedStatement.setString(i++, locale);

			resultSet = preparedStatement.executeQuery();

			List<Idioma> results = new ArrayList<Idioma>();
			Idioma idm = null;

			while (resultSet.next()) {
				idm = loadNext(resultSet);
				results.add(idm);
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

	public List<Idioma> findByEmpleado(Connection connection, Long idEmpleado, String locale) throws DataException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			String queryString = "SELECT idm.cod_idioma, ii.descripcion " + "FROM idioma idm "
					+ " INNER JOIN idioma_empleado ie ON ie.cod_idioma = idm.cod_idioma "
					+ " INNER JOIN i_idioma ii ON ii.cod_idioma = ie.cod_idioma "
					+ " INNER JOIN empleado em ON ie.cod_empleado = em.cod_empleado "
					+ " WHERE em.cod_empleado = ?  AND ii.i_cod_idioma = ? ";
			preparedStatement = connection.prepareStatement(queryString, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			int i = 1;
			preparedStatement.setLong(i++, idEmpleado);
			preparedStatement.setString(i++, locale);

			resultSet = preparedStatement.executeQuery();

			List<Idioma> results = new ArrayList<Idioma>();

			Idioma idm = null;

			while (resultSet.next()) {
				idm = loadNext(resultSet);
				results.add(idm);
			}
			return results;

		} catch (SQLException e) {
			logger.error("Employee ID: " +idEmpleado, e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}

	public List<Idioma> findByCliente(Connection connection, Long idCliente, String locale) throws DataException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			String queryString = "SELECT idm.cod_idioma, ii.descripcion " + "FROM idioma idm "
					+ " INNER JOIN idioma_cliente idc ON idc.cod_idioma = idm.cod_idioma "
					+ " INNER JOIN i_idioma ii ON ii.cod_idioma = idc.cod_idioma "
					+ " INNER JOIN cliente c ON idc.cod_cliente = c.cod_cliente "
					+ " WHERE c.cod_cliente = ? AND ii.i_cod_idioma = ? ";

			preparedStatement = connection.prepareStatement(queryString, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			int i = 1;
			preparedStatement.setLong(i++, idCliente);
			preparedStatement.setString(i++, locale);

			resultSet = preparedStatement.executeQuery();

			List<Idioma> results = new ArrayList<Idioma>();

			Idioma idm = null;

			while (resultSet.next()) {
				idm = loadNext(resultSet);
				results.add(idm);
			}
			return results;

		} catch (SQLException e) {
			logger.error("Client ID: " + idCliente, e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}

	public List<Idioma> findByContacto(Connection connection, Long idContacto, String locale) throws DataException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			String queryString = "SELECT idm.cod_idioma, idm.descripcion " + "FROM idioma idm "
					+ " INNER JOIN idioma_contactar idco ON idco.cod_idioma = idm.cod_idioma "
					+ " INNER JOIN i_idioma ii ON ii.cod_idioma = idco.cod_idioma "
					+ " INNER JOIN contacto co ON idco.cod_contacto = co.cod_contacto "
					+ " WHERE co.cod_contacto = ? AND ii.i_cod_idioma = ? ";

			preparedStatement = connection.prepareStatement(queryString, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			int i = 1;
			preparedStatement.setLong(i++, idContacto);
			preparedStatement.setString(i++, locale);

			resultSet = preparedStatement.executeQuery();

			List<Idioma> results = new ArrayList<Idioma>();

			Idioma idm = null;

			while (resultSet.next()) {
				idm = loadNext(resultSet);
				results.add(idm);
			}
			return results;

		} catch (SQLException e) {
			logger.error("Contact code: " + idContacto, e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}

	private Idioma loadNext(ResultSet resultSet) throws SQLException {

		int i = 1;
		String cod_idioma = resultSet.getString(i++);
		String descripcion = resultSet.getString(i++);

		Idioma idm = new Idioma();
		idm.setIdIdioma(cod_idioma);
		idm.setDescripcion(descripcion);

		return idm;
	}
}
