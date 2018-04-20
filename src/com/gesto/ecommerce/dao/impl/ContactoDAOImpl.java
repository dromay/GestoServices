package com.gesto.ecommerce.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gesto.ecommerce.dao.ContactoDAO;
import com.gesto.ecommerce.dao.IdiomaDAO;
import com.gesto.ecommerce.dao.util.JDBCUtils;
import com.gesto.ecommerce.exceptions.DataException;
import com.gesto.ecommerce.exceptions.InstanceNotFoundException;
import com.gesto.ecommerce.model.Cliente;
import com.gesto.ecommerce.model.Contacto;
import com.gesto.ecommerce.model.Empresa;
import com.gesto.ecommerce.model.Idioma;

public class ContactoDAOImpl implements ContactoDAO {

	private static Logger logger = LogManager.getLogger(ContactoDAOImpl.class.getName());

	private IdiomaDAO idiomaDAO = null;

	public ContactoDAOImpl() {
		idiomaDAO = new IdiomaDAOImpl();
	}

	@Override
	public Contacto findById(Connection connection, Long idContacto, String locale) throws InstanceNotFoundException, DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			String queryString = " SELECT co.cod_contacto, co.nombre, co.apellido, co.correo, co.tlf  "
					+ " FROM contacto co " + " WHERE co.cod_contacto  = ? ";

			preparedStatement = connection.prepareStatement(queryString, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			int i = 1;
			preparedStatement.setLong(i++, idContacto);

			resultSet = preparedStatement.executeQuery();

			Contacto co = null;

			if (resultSet.next()) {
				co = loadNext(connection, resultSet, locale);
			} else {
				logger.error("Contact with id " + idContacto + "not found", Empresa.class.getName());
				throw new InstanceNotFoundException("Contact with id " + idContacto + "not found",
						Empresa.class.getName());
			}

			return co;

		} catch (SQLException e) {
			logger.error("Contact code: " + idContacto, e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}

	@Override
	public Contacto findByTelefono(Connection connection, String tlf, String locale) throws InstanceNotFoundException, DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			String queryString = "SELECT co.cod_contacto, co.nombre, co.apellido, co.correo, co.tlf  "
					+ "FROM contacto co  " + "WHERE co.tlf = ? ";

			preparedStatement = connection.prepareStatement(queryString, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			int i = 1;
			preparedStatement.setString(i++, tlf);

			resultSet = preparedStatement.executeQuery();

			Contacto co = null;

			if (resultSet.next()) {
				co = loadNext(connection, resultSet, locale);
			} else {
				logger.error("Contact with tlf " + tlf + " not found", Cliente.class.getName());
				throw new InstanceNotFoundException("Contact with tlf " + tlf + " not found", Cliente.class.getName());
			}

			return co;

		} catch (SQLException e) {
			logger.error("Telephone: " + tlf, e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}

	@Override
	public List<Contacto> findByNombre(Connection connection, String nombre, String locale) throws DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			String queryString = "SELECT co.cod_contacto, co.nombre, co.apellido, co.correo, co.tlf  "
					+ "FROM contacto co " + "WHERE UPPER(co.nombre) LIKE ? " + "ORDER BY co.nombre ASC ";

			preparedStatement = connection.prepareStatement(queryString, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			int i = 1;
			preparedStatement.setString(i++, "%" + nombre.toUpperCase() + "%");

			resultSet = preparedStatement.executeQuery();

			List<Contacto> results = new ArrayList<Contacto>();
			Contacto co = null;

			while (resultSet.next()) {
				co = loadNext(connection, resultSet, locale);
				results.add(co);
			}

			return results;

		} catch (SQLException e) {
			logger.error("Name: " + nombre, e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}

	public List<Contacto> findByCliente(Connection connection, Long idCliente, String locale) throws DataException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			String queryString = "SELECT co.cod_contacto, co.nombre, co.apellido, co.correo, co.tlf  "
					+ "FROM contacto co " + " INNER JOIN cliente_contacto cco ON cco.cod_contacto = co.cod_contacto "
					+ " INNER JOIN cliente c " + " 	ON c.cod_cliente = cco.cod_cliente AND c.cod_cliente = ? ";

			preparedStatement = connection.prepareStatement(queryString, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			int i = 1;
			preparedStatement.setLong(i++, idCliente);

			if (logger.isDebugEnabled())
				logger.debug(queryString.toString());

			resultSet = preparedStatement.executeQuery();

			List<Contacto> results = new ArrayList<Contacto>();

			Contacto co = null;

			while (resultSet.next()) {
				co = loadNext(connection, resultSet, locale);
				results.add(co);
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

	public List<Contacto> findByGestion(Connection connection, Long idGestion, String locale) throws DataException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			String queryString = "SELECT co.cod_contacto, co.nombre, co.apellido, co.correo, co.tlf  "
					+ "FROM contacto co " + " INNER JOIN cliente_contacto cco ON cco.cod_contacto = co.cod_contacto "
					+ " INNER JOIN cliente c " + " 	ON c.cod_cliente = cco.cod_cliente" + " INNER JOIN gestion g "
					+ " ON g.cod_cliente = c.cod_cliente AND g.cod_gestion = ? ";

			preparedStatement = connection.prepareStatement(queryString, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			int i = 1;

			preparedStatement.setLong(i++, idGestion);

			if (logger.isDebugEnabled())
				logger.debug(queryString.toString());

			resultSet = preparedStatement.executeQuery();

			List<Contacto> results = new ArrayList<Contacto>();

			Contacto co = null;

			while (resultSet.next()) {
				co = loadNext(connection, resultSet, locale);
				results.add(co);
			}

			return results;

		} catch (SQLException e) {
			logger.error("Gestion ID: " + idGestion, e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}

	private Contacto loadNext(Connection connection, ResultSet resultSet, String locale) throws SQLException, DataException {

		int i = 1;
		Long cod_contacto = resultSet.getLong(i++);
		String nombre = resultSet.getString(i++);
		String apellido = resultSet.getString(i++);
		String correo = resultSet.getString(i++);
		String tlf = resultSet.getString(i++);

		Contacto co = new Contacto();
		co.setIdContacto(cod_contacto);
		co.setContactoNombre(nombre);
		co.setContactoApellido(apellido);
		co.setContactoCorreo(correo);
		co.setContactoTlf(tlf);

		List<Idioma> idiomas = idiomaDAO.findByContacto(connection, cod_contacto, locale);
		co.setIdiomas(idiomas);

		return co;
	}

}
