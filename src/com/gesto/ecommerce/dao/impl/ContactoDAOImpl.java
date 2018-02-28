package com.gesto.ecommerce.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gesto.ecommerce.dao.ContactoDAO;
import com.gesto.ecommerce.model.Contacto;
import com.gesto.ecommerce.dao.util.JDBCUtils;
import com.gesto.ecommerce.exceptions.DataException;

public class ContactoDAOImpl implements ContactoDAO {

	public ContactoDAOImpl() {
	}

	@Override
	public Boolean exists(Connection connection, Long contactoCod) throws DataException {
		boolean exist = false;

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			String queryString = "SELECT co.contactoCod, co.nombre, co.apellido, co.correo, co.tlf "
					+ "FROM contacto co " + "WHERE co.contactoCod = ? ";

			preparedStatement = connection.prepareStatement(queryString);

			int i = 1;
			preparedStatement.setLong(i++, contactoCod);

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
	public List<Contacto> findAll(Connection connection, int startIndex, int count) throws DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			String queryString = "SELECT co.contactoCod, co.nombre, co.apellido, co.correo, co.tlf  "
					+ "FROM customerdemographics d " + "ORDER BY d.CustomerTypeID ASC";

			preparedStatement = connection.prepareStatement(queryString, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			resultSet = preparedStatement.executeQuery();

			List<Contacto> results = new ArrayList<Contacto>();
			Contacto co = null;
			int currentCount = 0;

			if ((startIndex >= 1) && resultSet.absolute(startIndex)) {
				do {
					co = loadNext(resultSet);
					results.add(co);
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

	public List<Contacto> findByCliente(Connection connection, Long clienteId) throws DataException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			String queryString = "SELECT co.contactoCod, co.nombre, co.apellido, co.correo, co.tlf  "
					+ "FROM contacto co " + " INNER JOIN cliente_contacto cco ON cco.contactoCod = co.contactoCod "
					+ " INNER JOIN cliente c " + " 	ON c.cod_cliente = cco.cod_cliente AND c.cod_cliente = ? ";

			preparedStatement = connection.prepareStatement(queryString, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			int i = 1;
			preparedStatement.setLong(i++, clienteId);

			resultSet = preparedStatement.executeQuery();

			List<Contacto> results = new ArrayList<Contacto>();

			Contacto co = null;

			while (resultSet.next()) {
				co = loadNext(resultSet);
				results.add(co);
			}
			return results;

		} catch (SQLException e) {
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}

	
	public List<Contacto> findByGestion(Connection connection, Long idGestion) throws DataException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			String queryString = "SELECT co.contactoCod, co.nombre, co.apellido, co.correo, co.tlf  "
					+ "FROM contacto co " + " INNER JOIN cliente_contacto cco ON cco.contactoCod = co.contactoCod "
					+ " INNER JOIN cliente c " + " 	ON c.cod_cliente = cco.cod_cliente"
					+ " INNER JOIN gestion g " + " 	ON g.cod_cliente = c.cod_cliente AND g.cod_gestion = ? ";

			preparedStatement = connection.prepareStatement(queryString, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			int i = 1;
			preparedStatement.setLong(i++, idGestion);

			resultSet = preparedStatement.executeQuery();

			List<Contacto> results = new ArrayList<Contacto>();

			Contacto co = null;

			while (resultSet.next()) {
				co = loadNext(resultSet);
				results.add(co);
			}
			return results;

		} catch (SQLException e) {
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}

	@Override
	public List<Contacto> findByNombre(Connection connection, String nombre, int startIndex, int count)
			throws DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			String queryString = "SELECT co.contactoCod, co.nombre, co.apellido, co.correo, co.tlf  "
					+ "FROM contacto co " + "WHERE UPPER(co.nombre) LIKE ? " + "ORDER BY co.nombre ASC ";

			preparedStatement = connection.prepareStatement(queryString, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			int i = 1;
			preparedStatement.setString(i++, "%" + nombre.toUpperCase() + "%");

			resultSet = preparedStatement.executeQuery();

			List<Contacto> results = new ArrayList<Contacto>();
			Contacto co = null;
			int currentCount = 0;

			if ((startIndex >= 1) && resultSet.absolute(startIndex)) {
				do {
					co = loadNext(resultSet);
					results.add(co);
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

	private Contacto loadNext(ResultSet resultSet) throws SQLException {

		int i = 1;
		Long cod_contacto = resultSet.getLong(i++);
		String nombre = resultSet.getString(i++);
		String apellido = resultSet.getString(i++);
		String correo = resultSet.getString(i++);
		String tlf = resultSet.getString(i++);

		Contacto co = new Contacto();
		co.setContactoCod(cod_contacto);
		co.setContactoNombre(nombre);
		co.setContactoApellido(apellido);
		co.setContactoCorreo(correo);
		co.setContactoTlf(tlf);

		return co;
	}

}
