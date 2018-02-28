package com.gesto.ecommerce.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.gesto.ecommerce.dao.ContactoDAO;
import com.gesto.ecommerce.dao.GestionDAO;
import com.gesto.ecommerce.dao.TicketDAO;
import com.gesto.ecommerce.model.Contacto;
import com.gesto.ecommerce.model.Gestion;
import com.gesto.ecommerce.model.Ticket;
import com.gesto.ecommerce.service.GestionCriteria;
import com.gesto.ecommerce.dao.util.JDBCUtils;
import com.gesto.ecommerce.exceptions.DataException;
import com.gesto.ecommerce.exceptions.DuplicateInstanceException;
import com.gesto.ecommerce.exceptions.InstanceNotFoundException;

public class GestionDAOImpl implements GestionDAO {

	private TicketDAO ticketDAO = null;
	private ContactoDAO contactoDAO = null;

	public GestionDAOImpl() {
		ticketDAO = new TicketDAOImpl();
		contactoDAO = new ContactoDAOImpl();
	}

	@Override
	public Gestion findById(Connection connection, Long idGestion) throws InstanceNotFoundException, DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			String queryString = "SELECT g.cod_gestion, g.cod_cliente, g.cod_empleado, g.cod_empresa "
					+ "FROM gestion g " + "WHERE g.cod_gestion = ? ";

			preparedStatement = connection.prepareStatement(queryString, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			int i = 1;
			preparedStatement.setLong(i++, idGestion);

			resultSet = preparedStatement.executeQuery();

			Gestion g = null;

			if (resultSet.next()) {
				g = loadNext(connection, resultSet);
			} else {
				throw new InstanceNotFoundException("Gestion con id " + idGestion + "not found",
						Gestion.class.getName());
			}

			return g;

		} catch (SQLException e) {
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}

	@Override
	public Boolean exists(Connection connection, Long idGestion) throws DataException {
		boolean exist = false;

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			String queryString = "SELECT g.cod_gestion, g.cod_cliente, g.cod_empleado, g.cod_empresa "
					+ "FROM gestion g " + "WHERE g.cod_gestion = ? ";

			preparedStatement = connection.prepareStatement(queryString);

			int i = 1;
			preparedStatement.setLong(i++, idGestion);

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
	public List<Gestion> findAll(Connection connection, int startIndex, int count) throws DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			String queryString = "SELECT g.cod_gestion, g.cod_cliente, g.cod_empleado, g.cod_empresa "
					+ "FROM gestion g " + "ORDER BY g.cod_gestion ASC";

			preparedStatement = connection.prepareStatement(queryString, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			resultSet = preparedStatement.executeQuery();

			List<Gestion> results = new ArrayList<Gestion>();
			Gestion g = null;
			int currentCount = 0;

			if ((startIndex >= 1) && resultSet.absolute(startIndex)) {
				do {
					g = loadNext(connection, resultSet);
					results.add(g);
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

	public List<Gestion> findByCliente(Connection connection, Long clienteID) throws DataException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			String queryString = "SELECT g.cod_gestion, g.cod_cliente, g.cod_empleado, g.cod_empresa "
					+ "FROM gestion g " + " INNER JOIN cliente c "
					+ " ON c.cod_cliente = g.cod_cliente AND c.cod_cliente = ? ";

			preparedStatement = connection.prepareStatement(queryString, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			int i = 1;
			preparedStatement.setLong(i++, clienteID);

			resultSet = preparedStatement.executeQuery();

			List<Gestion> results = new ArrayList<Gestion>();

			Gestion g = null;

			while (resultSet.next()) {
				g = loadNext(connection, resultSet);
				results.add(g);
			}
			return results;

		} catch (SQLException e) {
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}

	public List<Gestion> findByEmpleado(Connection connection, Long id) throws DataException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			String queryString = "SELECT g.cod_gestion, g.cod_cliente, g.cod_empleado, g.cod_empresa "
					+ "FROM gestion g " + " INNER JOIN empleado em "
					+ " ON em.cod_empleado = g.cod_empleado AND em.cod_empleado = ? ";

			preparedStatement = connection.prepareStatement(queryString, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			int i = 1;
			preparedStatement.setLong(i++, id);

			resultSet = preparedStatement.executeQuery();

			List<Gestion> results = new ArrayList<Gestion>();

			Gestion g = null;

			while (resultSet.next()) {
				g = loadNext(connection, resultSet);
				results.add(g);
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

			String queryString = " SELECT count(*) " + " FROM gestion";

			preparedStatement = connection.prepareStatement(queryString);

			resultSet = preparedStatement.executeQuery();

			int i = 1;
			if (resultSet.next()) {
				return resultSet.getLong(i++);
			} else {
				throw new DataException("Error inesperado al intentar devolver los datos");
			}

		} catch (SQLException e) {
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}

	private Gestion loadNext(Connection connection, ResultSet resultSet) throws SQLException, DataException {

		int i = 1;
		long cod_gestion = resultSet.getLong(i++);
		long cod_cliente = resultSet.getLong(i++);
		long cod_empleado = resultSet.getLong(i++);
		long cod_empresa = resultSet.getLong(i++);

		Gestion g = new Gestion();
		g.setIdGestion(cod_gestion);
		g.setIdCliente(cod_cliente);
		g.setIdEmpleado(cod_empleado);
		g.setIdEmpresa(cod_empresa);

		List<Ticket> tickets = ticketDAO.findByGestion(connection, cod_gestion);
		g.setTickets(tickets);
		List<Contacto> contactos = contactoDAO.findByGestion(connection, cod_gestion);
		g.setContactos(contactos);

		return g;
	}

	private void addClause(StringBuilder queryString, boolean first, String clause) {
		queryString.append(first ? " WHERE " : " AND ").append(clause);
	}

	@Override
	public Gestion create(Connection connection, Gestion g, Ticket t) throws DuplicateInstanceException, DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {

			String queryString = "INSERT INTO gestion(cod_cliente, cod_empleado, cod_empresa) " + "VALUES (?, ?, ?)";

			preparedStatement = connection.prepareStatement(queryString, Statement.RETURN_GENERATED_KEYS);

			int i = 1;
			preparedStatement.setLong(i++, g.getIdCliente());
			preparedStatement.setLong(i++, g.getIdEmpleado());
			preparedStatement.setLong(i++, g.getIdEmpresa());

			int insertedRows = preparedStatement.executeUpdate();

			if (insertedRows == 0) {
				throw new SQLException("Can not add row to table 'CustomersDemographics'");
			}

			resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next()) {
				Long pk = resultSet.getLong(1);
				g.setIdGestion(pk);
			} else {
				throw new DataException("Unable to fetch autogenerated primary key");
			}

			return g;

		} catch (SQLException e) {
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}

	@Override
	public List<Gestion> findByCriteria(Connection connection, GestionCriteria gestion, int startIndex, int count)
			throws DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		StringBuilder queryString = null;

		try {

			queryString = new StringBuilder(
					" SELECT g.cod_gestion, g.cod_cliente, g.cod_empleado, g.cod_empresa, co.tlf,  "
							+ " FROM gestion g " + " INNER JOIN cliente c ON c.cod_cliente = g.cod_cliente "
							+ " INNER JOIN cliente_contacto cc ON c.cod_cliente = cc.cod_cliente "
							+ " INNER JOIN contacto co ON cc.cod_contacto = co.cod_contacto "
							+ " INNER JOIN ticket t ON t.cod_gestion = g.cod_gestion "
							+ " INNER JOIN idioma i ON i.cod_idioma = ic.cod_idioma ");

			// Marca (flag) de primera clausula, que se desactiva en la primera
			boolean first = true;

			if (gestion.getIdGestion() != null) {
				addClause(queryString, first, " UPPER(g.cod_gestion) LIKE ? ");
				first = false;
			}

			if (gestion.getIdCliente() != null) {
				addClause(queryString, first, " UPPER(g.cod_cliente) LIKE ? ");
				first = false;
			}

			if (gestion.getIdEmpleado() != null) {
				addClause(queryString, first, " UPPER(g.cod_empleado) LIKE ? ");
				first = false;
			}

			if (gestion.getIdEmpresa() != null) {
				addClause(queryString, first, " UPPER(g.cod_empresa) LIKE ? ");
				first = false;
			}

			if (gestion.getContactos().isEmpty()) {
				addClause(queryString, first, " UPPER(co.tlf) LIKE ? ");
				first = false;
			}

			if (gestion.getTickets().isEmpty()) {
				addClause(queryString, first, " UPPER(t.fecha_desde) LIKE ? ");
				first = false;
			}

			preparedStatement = connection.prepareStatement(queryString.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			int i = 1;

			preparedStatement.setString(i++, "%" + gestion.getIdGestion() + "%");
			preparedStatement.setString(i++, "%" + gestion.getIdCliente() + "%");
			preparedStatement.setString(i++, "%" + gestion.getIdEmpleado() + "%");
			preparedStatement.setString(i++, "%" + gestion.getIdEmpresa() + "%");
			preparedStatement.setString(i++, "%" + gestion.getContactos() + "%");
			preparedStatement.setString(i++, "%" + gestion.getTickets() + "%");

			resultSet = preparedStatement.executeQuery();

			List<Gestion> results = new ArrayList<Gestion>();
			Gestion g = null;
			int currentCount = 0;

			if ((startIndex >= 1) && resultSet.absolute(startIndex)) {
				do {
					g = loadNext(connection, resultSet);
					results.add(g);
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

}
