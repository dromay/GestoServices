
package com.gesto.ecommerce.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gesto.ecommerce.dao.ClienteDAO;
import com.gesto.ecommerce.dao.ContactoDAO;
import com.gesto.ecommerce.dao.GestionDAO;
import com.gesto.ecommerce.dao.IdiomaDAO;
import com.gesto.ecommerce.dao.util.JDBCUtils;
import com.gesto.ecommerce.exceptions.DataException;
import com.gesto.ecommerce.exceptions.InstanceNotFoundException;
import com.gesto.ecommerce.model.Cliente;
import com.gesto.ecommerce.model.Contacto;
import com.gesto.ecommerce.model.Gestion;
import com.gesto.ecommerce.model.Idioma;
import com.gesto.ecommerce.service.ClienteCriteria;

public class ClienteDAOImpl implements ClienteDAO {

	private static Logger logger = LogManager.getLogger(ClienteDAOImpl.class.getName());

	private ContactoDAO contactoDAO = null;
	private IdiomaDAO idiomaDAO = null;
	private GestionDAO gestionDAO = null;

	public ClienteDAOImpl() {
		contactoDAO = new ContactoDAOImpl();
		idiomaDAO = new IdiomaDAOImpl();
		gestionDAO = new GestionDAOImpl();
	}

	@Override
	public Cliente findById(Connection connection, Long idCliente, String locale) throws InstanceNotFoundException, DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			String queryString = "SELECT c.cod_cliente, c.tipo_cliente, c.nombre_cliente, c.doc_identidad "
					+ "FROM cliente c  " + "WHERE c.cod_cliente = ? ";

			preparedStatement = connection.prepareStatement(queryString, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			int i = 1;
			preparedStatement.setLong(i++, idCliente);

			resultSet = preparedStatement.executeQuery();

			Cliente c = null;

			if (resultSet.next()) {
				c = loadNext(connection, resultSet, locale);
			} else {
				logger.error("Client with id " + idCliente + " not found",
						Cliente.class.getName());
				throw new InstanceNotFoundException("Client with id " + idCliente + " not found",
						Cliente.class.getName());
			}

			return c;

		} catch (SQLException e) {
			logger.error("Client ID: "+idCliente, e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}

	@Override
	public List<Cliente> findByCriteria(Connection connection, ClienteCriteria criteria, String locale, int startIndex, int count)
			throws DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		StringBuilder queryString = null;

		try {

			queryString = new StringBuilder(
					" SELECT c.cod_cliente , c.tipo_cliente, c.nombre_cliente, c.doc_identidad" + " FROM cliente c ");

			// Marca (flag) de primera clausula, que se desactiva en la primera
			boolean first = true;

			if (criteria.getCorreoCriteria() != null || criteria.getTlfCriteria() != null) {
				addClause2(queryString, first, " INNER JOIN cliente_contacto cc ON c.cod_cliente = cc.cod_cliente ");
				addClause2(queryString, first, " INNER JOIN contacto co ON cc.cod_contacto = co.cod_contacto ");
				first = false;
			}
			if (criteria.getIdGestionCriteria() != null) {
				addClause2(queryString, first, " INNER JOIN gestion g ON g.cod_cliente = c.cod_cliente ");
				first = false;
			}
			if (criteria.getIdIdiomaCriteria() != null) {
				addClause2(queryString, first, " INNER JOIN idioma_cliente ic ON ic.cod_cliente = c.cod_cliente ");
				addClause2(queryString, first, " INNER JOIN idioma i ON i.cod_idioma = ic.cod_idioma ");
				addClause2(queryString, first, " INNER JOIN i_idioma ii ON ii.cod_idioma = i.cod_idioma ");
				first = false;
			}

			if (criteria.getClienteIdCriteria() != null) {
				addClause(queryString, first, " UPPER(c.cod_cliente) = ? ");
				first = false;
			}

			if (criteria.getTipoCriteria() != null) {
				addClause(queryString, first, " UPPER(c.tipo_cliente) LIKE ? ");
				first = false;
			}

			if (criteria.getNombreCriteria() != null) {
				addClause(queryString, first, " UPPER(c.nombre_cliente) LIKE ? ");
				first = false;
			}

			if (criteria.getDocIdentidadCriteria() != null) {
				addClause(queryString, first, " UPPER(c.doc_identidad) LIKE ? ");
				first = false;
			}

			if (criteria.getCorreoCriteria() != null) {
				addClause(queryString, first, " UPPER(co.correo) LIKE ? ");
				first = false;
			}

			if (criteria.getTlfCriteria() != null) {
				addClause(queryString, first, " UPPER(co.tlf) LIKE ? ");
				first = false;
			}

			if (criteria.getIdIdiomaCriteria() != null) {
				addClause(queryString, first, " UPPER(i.cod_idioma) LIKE ? ");
				first = false;
			}

			if (criteria.getIdGestionCriteria() != null) {
				addClause(queryString, first, " UPPER(g.cod_gestion) = ? ");
				first = false;
			}

			addClause2(queryString, first, " GROUP BY c.cod_cliente ");
			
			if (logger.isDebugEnabled()) {
				logger.debug(ToStringBuilder.reflectionToString(criteria));
				logger.debug(queryString.toString());
			}

			preparedStatement = connection.prepareStatement(queryString.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			int i = 1;

			if (criteria.getClienteIdCriteria() != null)
				preparedStatement.setLong(i++, criteria.getClienteIdCriteria());
			if (criteria.getTipoCriteria() != null)
				preparedStatement.setString(i++, "%" + criteria.getTipoCriteria() + "%");
			if (criteria.getNombreCriteria() != null)
				preparedStatement.setString(i++, "%" + criteria.getNombreCriteria() + "%");
			if (criteria.getDocIdentidadCriteria() != null)
				preparedStatement.setString(i++, "%" + criteria.getDocIdentidadCriteria() + "%");
			if (criteria.getCorreoCriteria() != null)
				preparedStatement.setString(i++, "%" + criteria.getCorreoCriteria() + "%");
			if (criteria.getTlfCriteria() != null)
				preparedStatement.setString(i++,criteria.getTlfCriteria());
			if (criteria.getIdIdiomaCriteria() != null)
				preparedStatement.setString(i++, "%" + criteria.getIdIdiomaCriteria() + "%");
			if (criteria.getIdGestionCriteria() != null)
				preparedStatement.setLong(i++, criteria.getIdGestionCriteria());

			resultSet = preparedStatement.executeQuery();

			List<Cliente> results = new ArrayList<Cliente>();
			Cliente e = null;
			int currentCount = 0;

			if ((startIndex >= 1) && resultSet.absolute(startIndex)) {
				do {
					e = loadNext(connection, resultSet, locale);
					results.add(e);
					currentCount++;
				} while ((currentCount < count) && resultSet.next());
			}

			return results;

		} catch (SQLException e) {
			logger.error(ToStringBuilder.reflectionToString(criteria), e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}

	// @Override
	// public List<Cliente> findByCriteria(Connection connection, ClienteCriteria
	// criteria, int startIndex, int count)
	// throws DataException {
	//
	// PreparedStatement preparedStatement = null;
	// ResultSet resultSet = null;
	// StringBuilder queryString = null;
	//
	// try {
	//
	// queryString = new StringBuilder(
	// " SELECT c.cod_cliente , c.tipo_cliente, c.nombre_cliente, c.doc_identidad"
	// + " FROM cliente c ");
	//
	// // Marca (flag) de primera clausula, que se desactiva en la primera
	// boolean first = true;
	//
	// if (!criteria.getContactos().isEmpty()) {
	// addClause(queryString, first, " INNER JOIN cliente_contacto cc ON
	// c.cod_cliente = cc.cod_cliente ");
	// addClause(queryString, first, " INNER JOIN contacto co ON cc.cod_contacto =
	// co.cod_contacto ");
	// first = false;
	// }
	// if (!criteria.getGestiones().isEmpty()) {
	// addClause(queryString, first, " INNER JOIN gestion g ON g.cod_cliente =
	// c.cod_cliente ");
	// first = false;
	// }
	// if (!criteria.getIdiomas().isEmpty()) {
	// addClause(queryString, first, " INNER JOIN idioma_cliente ic ON
	// ic.cod_cliente = c.cod_cliente ");
	// addClause(queryString, first, " INNER JOIN idioma i ON i.cod_idioma =
	// ic.cod_idioma ");
	// first = false;
	// }
	//
	// if (criteria.getClienteId() != null) {
	// addClause(queryString, first, " UPPER(c.cod_cliente) = ? ");
	// first = false;
	// }
	//
	// if (criteria.getTipo() != null) {
	// addClause(queryString, first, " UPPER(c.tipo_cliente) LIKE ? ");
	// first = false;
	// }
	//
	// if (criteria.getNombre() != null) {
	// addClause(queryString, first, " UPPER(c.nombre_cliente) LIKE ? ");
	// first = false;
	// }
	//
	// if (criteria.getDocIdentidad() != null) {
	// addClause(queryString, first, " UPPER(c.doc_identidad) LIKE ? ");
	// first = false;
	// }
	//
	// if (!criteria.getContactos().isEmpty()) {
	// addClause(queryString, first, " UPPER(co.tlf) LIKE ? ");
	// first = false;
	// }
	//
	// if (!criteria.getIdiomas().isEmpty()) {
	// addClause(queryString, first, " UPPER(i.cod_idioma) LIKE ? ");
	// first = false;
	// }
	//
	// if (!criteria.getGestiones().isEmpty()) {
	// addClause(queryString, first, " UPPER(g.cod_gestion) = ? ");
	// first = false;
	// }
	//
	// if (logger.isDebugEnabled()) {
	// logger.debug(ToStringBuilder.reflectionToString(criteria));
	// logger.debug(queryString.toString());
	// }
	//
	// preparedStatement = connection.prepareStatement(queryString.toString(),
	// ResultSet.TYPE_SCROLL_INSENSITIVE,
	// ResultSet.CONCUR_READ_ONLY);
	//
	// int i = 1;
	//
	// if (criteria.getClienteId() != null)
	// preparedStatement.setLong(i++, criteria.getClienteId());
	// if (criteria.getTipo() != null)
	// preparedStatement.setString(i++, "%" + criteria.getTipo() + "%");
	// if (criteria.getNombre() != null)
	// preparedStatement.setString(i++, "%" + criteria.getNombre() + "%");
	// if (criteria.getDocIdentidad() != null)
	// preparedStatement.setString(i++, "%" + criteria.getDocIdentidad() + "%");
	// if (!criteria.getContactos().isEmpty())
	// preparedStatement.setString(i++, "%" + criteria.getContactos() + "%");
	// if (!criteria.getIdiomas().isEmpty())
	// preparedStatement.setString(i++, "%" + criteria.getIdiomas() + "%");
	// if (!criteria.getGestiones().isEmpty())
	// preparedStatement.setString(i++, "%" + criteria.getGestiones() + "%");
	//
	// resultSet = preparedStatement.executeQuery();
	//
	// List<Cliente> results = new ArrayList<Cliente>();
	// Cliente e = null;
	// int currentCount = 0;
	//
	// if ((startIndex >= 1) && resultSet.absolute(startIndex)) {
	// do {
	// e = loadNext(connection, resultSet);
	// results.add(e);
	// currentCount++;
	// } while ((currentCount < count) && resultSet.next());
	// }
	//
	// return results;
	//
	// } catch (SQLException e) {
	// throw new DataException(e);
	// } finally {
	// JDBCUtils.closeResultSet(resultSet);
	// JDBCUtils.closeStatement(preparedStatement);
	// }
	// }

	private void addClause(StringBuilder queryString, boolean first, String clause) {
		queryString.append(first ? " WHERE " : " AND ").append(clause);
	}

	private void addClause2(StringBuilder queryString, boolean first, String clause) {
		queryString.append(first ? "" : "").append(clause);
	}

	private Cliente loadNext(Connection connection, ResultSet resultSet, String locale) throws SQLException, DataException {

		int i = 1;
		Long cod_cliente = resultSet.getLong(i++);
		String tipo_cliente = resultSet.getString(i++);
		String nombre_cliente = resultSet.getString(i++);
		String doc_identidad = resultSet.getString(i++);

		Cliente c = new Cliente();
		c.setIdCliente(cod_cliente);
		c.setTipo(tipo_cliente);
		c.setNombre(nombre_cliente);
		c.setDocIdentidad(doc_identidad);

		List<Contacto> contactos = contactoDAO.findByCliente(connection, cod_cliente, locale);
		c.setContactos(contactos);
		List<Idioma> idiomas = idiomaDAO.findByCliente(connection, cod_cliente, locale);
		c.setIdiomas(idiomas);
		List<Gestion> gestiones = gestionDAO.findByCliente(connection, cod_cliente, locale, 1, Integer.MAX_VALUE);
		c.setGestiones(gestiones);

		return c;
	}
}
