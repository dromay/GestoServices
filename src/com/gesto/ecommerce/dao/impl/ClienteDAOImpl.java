
package com.gesto.ecommerce.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

	private ContactoDAO contactoDAO = null;
	private IdiomaDAO idiomaDAO = null;
	private GestionDAO gestionDAO = null;

	public ClienteDAOImpl() {
		contactoDAO = new ContactoDAOImpl();
		idiomaDAO = new IdiomaDAOImpl();
		gestionDAO = new GestionDAOImpl();
	}

	@Override
	public Cliente findById(Connection connection, Long clienteId) throws InstanceNotFoundException, DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			String queryString = "SELECT c.cod_cliente, c.tipo_cliente, c.nombre_cliente, c.doc_identidad "
					+ "FROM cliente c  " + "WHERE c.cod_cliente = ? ";

			preparedStatement = connection.prepareStatement(queryString, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			int i = 1;
			preparedStatement.setLong(i++, clienteId);

			resultSet = preparedStatement.executeQuery();

			Cliente e = null;

			if (resultSet.next()) {
				e = loadNext(connection, resultSet);
			} else {
				throw new InstanceNotFoundException("Cliente con id " + clienteId + " no encontrado",
						Cliente.class.getName());
			}

			return e;

		} catch (SQLException e) {
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}

	@Override
	public Boolean exists(Connection connection, Long clienteId) throws DataException {
		boolean exist = false;

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			String queryString = "SELECT c.cod_cliente, c.tipo_cliente, c.nombre_cliente, c.doc_identidad "
					+ "FROM cliente c  " + "WHERE c.cod_cliente = ? ";

			preparedStatement = connection.prepareStatement(queryString);

			int i = 1;
			preparedStatement.setLong(i++, clienteId);

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
	public List<Cliente> findAll(Connection connection, int startIndex, int count) throws DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			String queryString = "SELECT c.cod_cliente, c.tipo_cliente, c.nombre_cliente, c.doc_identidad "
					+ "FROM cliente c  " + "ORDER BY c.cod_cliente ASC";

			preparedStatement = connection.prepareStatement(queryString, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			resultSet = preparedStatement.executeQuery();

			List<Cliente> results = new ArrayList<Cliente>();
			Cliente c = null;
			int currentCount = 0;

			if ((startIndex >= 1) && resultSet.absolute(startIndex)) {
				do {
					c = loadNext(connection, resultSet);
					results.add(c);
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

	@Override
	public long countAll(Connection connection) throws DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			String queryString = " SELECT count(*) " + " FROM cliente";

			preparedStatement = connection.prepareStatement(queryString);

			resultSet = preparedStatement.executeQuery();

			int i = 1;
			if (resultSet.next()) {
				return resultSet.getLong(i++);
			} else {
				throw new DataException("Unexpected condition trying to retrieve count value");
			}

		} catch (SQLException e) {
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}

	@Override
	public List<Cliente> findByCriteria(Connection connection, ClienteCriteria cliente, int startIndex, int count)
			throws DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		StringBuilder queryString = null;

		try {

			queryString = new StringBuilder(
					" SELECT c.cod_cliente, c.tipo_cliente, c.nombre_cliente, c.doc_identidad, i.cod_idioma, g.cod_gestion, co.tlf, co.nombre, co.apellido, co.correo"
							+ " FROM cliente c " + " INNER JOIN cliente_contacto cc ON c.cod_cliente = cc.cod_cliente "
							+ " INNER JOIN contacto co ON cc.cod_contacto = co.cod_contacto "
							+ " INNER JOIN gestion g ON g.cod_cliente = c.cod_cliente "
							+ " INNER JOIN idioma_cliente ic ON ic.cod_cliente = c.cod_cliente "
							+ " INNER JOIN idioma i ON i.cod_idioma = ic.cod_idioma ");

			// Marca (flag) de primera clausula, que se desactiva en la primera
			boolean first = true;

			if (cliente.getClienteId() != null) {
				addClause(queryString, first, " UPPER(c.cod_cliente) LIKE ? ");
				first = false;
			}

			if (cliente.getTipo() != null) {
				addClause(queryString, first, " UPPER(c.tipo_cliente) LIKE ? ");
				first = false;
			}

			if (cliente.getNombre() != null) {
				addClause(queryString, first, " UPPER(c.nombre_cliente) LIKE ? ");
				first = false;
			}

			if (cliente.getDocIdentidad() != null) {
				addClause(queryString, first, " UPPER(c.doc_identidad) LIKE ? ");
				first = false;
			}

			if (cliente.getContactos().isEmpty()) {
				addClause(queryString, first, " UPPER(co.tlf) LIKE ? ");
				first = false;
			}

			if (cliente.getIdiomas().isEmpty()) {
				addClause(queryString, first, " UPPER(i.cod_idioma) LIKE ? ");
				first = false;
			}

			if (cliente.getGestiones().isEmpty()) {
				addClause(queryString, first, " UPPER(g.cod_gestion) LIKE ? ");
				first = false;
			}

			preparedStatement = connection.prepareStatement(queryString.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			int i = 1;
			
			if (cliente.getClienteId() != null)
			preparedStatement.setString(i++, "%" + cliente.getClienteId() + "%");
			if (cliente.getTipo() != null)
			preparedStatement.setString(i++, "%" + cliente.getTipo() + "%");
			if (cliente.getNombre() != null)
			preparedStatement.setString(i++, "%" + cliente.getNombre() + "%");
			if (cliente.getDocIdentidad() != null)
			preparedStatement.setString(i++, "%" + cliente.getDocIdentidad() + "%");
			if (cliente.getContactos() != null)
			preparedStatement.setString(i++, "%" + cliente.getContactos() + "%");
			if (cliente.getIdiomas() != null)
			preparedStatement.setString(i++, "%" + cliente.getIdiomas() + "%");
			if (cliente.getGestiones() != null)
			preparedStatement.setString(i++, "%" + cliente.getGestiones() + "%");

			resultSet = preparedStatement.executeQuery();

			List<Cliente> results = new ArrayList<Cliente>();
			Cliente e = null;
			int currentCount = 0;

			if ((startIndex >= 1) && resultSet.absolute(startIndex)) {
				do {
					e = loadNext(connection, resultSet);
					results.add(e);
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

	private void addClause(StringBuilder queryString, boolean first, String clause) {
		queryString.append(first ? " WHERE " : " AND ").append(clause);
	}

	private Cliente loadNext(Connection connection, ResultSet resultSet) throws SQLException, DataException {

		int i = 1;
		Long cod_cliente = resultSet.getLong(i++);
		String tipo_cliente = resultSet.getString(i++);
		String nombre_cliente = resultSet.getString(i++);
		String doc_identidad = resultSet.getString(i++);

		Cliente c = new Cliente();
		c.setClienteId(cod_cliente);
		c.setTipo(tipo_cliente);
		c.setNombre(nombre_cliente);
		c.setDocIdentidad(doc_identidad);

		List<Contacto> contactos = contactoDAO.findByCliente(connection, cod_cliente);
		c.setContactos(contactos);
		List<Idioma> idiomas = idiomaDAO.findByCliente(connection, cod_cliente);
		c.setIdiomas(idiomas);
		List<Gestion> gestiones = gestionDAO.findByCliente(connection, cod_cliente);
		c.setGestiones(gestiones);

		return c;
	}
}
