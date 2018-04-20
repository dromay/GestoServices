package com.gesto.ecommerce.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gesto.ecommerce.dao.ContactoDAO;
import com.gesto.ecommerce.dao.GestionDAO;
import com.gesto.ecommerce.dao.TicketDAO;
import com.gesto.ecommerce.dao.util.JDBCUtils;
import com.gesto.ecommerce.exceptions.DataException;
import com.gesto.ecommerce.exceptions.DuplicateInstanceException;
import com.gesto.ecommerce.exceptions.InstanceNotFoundException;
import com.gesto.ecommerce.model.Contacto;
import com.gesto.ecommerce.model.Gestion;
import com.gesto.ecommerce.model.Ticket;
import com.gesto.ecommerce.service.GestionCriteria;

public class GestionDAOImpl implements GestionDAO {

	private static Logger logger = LogManager.getLogger(GestionDAOImpl.class.getName());

	private TicketDAO ticketDAO = null;
	private ContactoDAO contactoDAO = null;

	public GestionDAOImpl() {
		ticketDAO = new TicketDAOImpl();
		contactoDAO = new ContactoDAOImpl();
	}

	@Override
	public Gestion findById(Connection connection, Long idGestion, String locale) throws InstanceNotFoundException, DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			String queryString = "SELECT g.cod_gestion, g.cod_cliente, g.cod_empleado, g.cod_empresa, g.fecha_inicio "
					+ "FROM gestion g " + "WHERE g.cod_gestion = ? ";

			preparedStatement = connection.prepareStatement(queryString, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			int i = 1;
			preparedStatement.setLong(i++, idGestion);

			resultSet = preparedStatement.executeQuery();

			Gestion g = null;

			if (resultSet.next()) {
				g = loadNext(connection, resultSet, locale);
			} else {
				logger.error("Gestion with id " + idGestion + "not found",Gestion.class.getName());
				throw new InstanceNotFoundException("Gestion with id " + idGestion + "not found",Gestion.class.getName());
			}

			return g;

		} catch (SQLException e) {
			logger.error("Gestion ID: " + idGestion, e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}

	public List<Gestion> findByCliente(Connection connection, Long idCliente, String locale, int startIndex, int pageSize)
			throws DataException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			String queryString = "SELECT g.cod_gestion, g.cod_cliente, g.cod_empleado, g.cod_empresa, g.fecha_inicio "
					+ "FROM gestion g " + " INNER JOIN cliente c "
					+ " ON c.cod_cliente = g.cod_cliente AND c.cod_cliente = ? ";

			preparedStatement = connection.prepareStatement(queryString, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			int i = 1;
			preparedStatement.setLong(i++, idCliente);

			resultSet = preparedStatement.executeQuery();

			List<Gestion> results = new ArrayList<Gestion>();

			Gestion g = null;

			int currentCount = 0;

			if ((startIndex >= 1) && resultSet.absolute(startIndex)) {
				do {
					g = loadNext(connection, resultSet, locale);
					results.add(g);
					currentCount++;
				} while ((currentCount < pageSize) && resultSet.next());
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

	public List<Gestion> findByEmpleado(Connection connection, Long idEmpleado, String locale, int startIndex, int pageSize)
			throws DataException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			String queryString = "SELECT g.cod_gestion, g.cod_cliente, g.cod_empleado, g.cod_empresa, g.fecha_incio "
					+ "FROM gestion g " + " INNER JOIN empleado em "
					+ " ON em.cod_empleado = g.cod_empleado AND em.cod_empleado = ? ";

			preparedStatement = connection.prepareStatement(queryString, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			int i = 1;
			preparedStatement.setLong(i++, idEmpleado);

			resultSet = preparedStatement.executeQuery();

			List<Gestion> results = new ArrayList<Gestion>();

			Gestion g = null;

			int currentCount = 0;

			if ((startIndex >= 1) && resultSet.absolute(startIndex)) {
				do {
					g = loadNext(connection, resultSet, locale);
					results.add(g);
					currentCount++;
				} while ((currentCount < pageSize) && resultSet.next());
			}
			return results;

		} catch (SQLException e) {
			logger.error("Employee ID: " + idEmpleado, e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}
	
	@Override
	public List<Gestion> findByCriteria(Connection connection, GestionCriteria criteria, String locale, int startIndex, int count)
			throws DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		StringBuilder queryString = null;

		try {

			queryString = new StringBuilder(
					" SELECT g.cod_gestion, g.cod_cliente, g.cod_empleado, g.cod_empresa, g.fecha_inicio  "
							+ " FROM gestion g " + " INNER JOIN ticket t ON g.cod_gestion = t.cod_gestion ");

			// Marca (flag) de primera clausula, que se desactiva en la primera
			boolean first = true;

			if (criteria.getCriteriaNombreEmpleado() != null || criteria.getCriteriaApellidoEmpleado() != null) {
				addClause2(queryString, first, " INNER JOIN empleado em ON em.cod_empleado = t.cod_empleado ");
				first = false;
			}
			if (criteria.getCriteriaCodIdentidad() != null || criteria.getCriteriaNombreCliente() != null) {
				addClause2(queryString, first, " INNER JOIN cliente c ON c.cod_cliente = g.cod_cliente ");
				first = false;
			}
			if (criteria.getCriteriaTlf() != null || criteria.getCriteriaCorreo() != null) {
				addClause2(queryString, first, " INNER JOIN contacto co ON t.cod_contacto = co.cod_contacto ");
				first = false;
			}
			if (criteria.getIdGestion() != null) {
				addClause(queryString, first, " UPPER(g.cod_gestion) LIKE ? ");
				first = false;
			}

			if (criteria.getIdCliente() != null) {
				addClause(queryString, first, " UPPER(g.cod_cliente) LIKE ? ");
				first = false;
			}

			if (criteria.getIdEmpleado() != null) {
				addClause(queryString, first, " UPPER(g.cod_empleado) LIKE ? ");
				first = false;
			}

			if (criteria.getIdEmpresa() != null) {
				addClause(queryString, first, " UPPER(g.cod_empresa) LIKE ? ");
				first = false;
			}

			if (criteria.getFechaInicio() != null) {
				addClause(queryString, first, " UPPER(g.fecha_inicio) LIKE ? ");
				first = false;
			}

			if (criteria.getCriteriaNombreEmpleado() != null) {
				addClause(queryString, first, " UPPER(em.nombre) LIKE ? ");
				first = false;
			}

			if (criteria.getCriteriaApellidoEmpleado() != null) {
				addClause(queryString, first, " UPPER(em.apellido) LIKE ? ");
				first = false;
			}

			if (criteria.getCriteriaCodIdentidad() != null) {
				addClause(queryString, first, " UPPER(c.doc_identidad) LIKE ? ");
				first = false;
			}

			if (criteria.getCriteriaNombreCliente() != null) {
				addClause(queryString, first, " UPPER(c.nombre_cliente) LIKE ? ");
				first = false;
			}

			if (criteria.getCriteriaTlf() != null) {
				addClause(queryString, first, " UPPER(co.tlf) LIKE ? ");
				first = false;
			}

			if (criteria.getCriteriaCorreo() != null) {
				addClause(queryString, first, " UPPER(co.correo) LIKE ? ");
				first = false;
			}

			addClause2(queryString, first, " GROUP BY g.cod_gestion ");

			if (logger.isDebugEnabled()) {
				logger.debug(queryString);
			}

			preparedStatement = connection.prepareStatement(queryString.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			int i = 1;
			if (criteria.getIdGestion() != null)
				preparedStatement.setLong(i++, criteria.getIdGestion());
			if (criteria.getIdEmpleado() != null)
				preparedStatement.setLong(i++, criteria.getIdEmpleado());
			if (criteria.getIdEmpresa() != null)
				preparedStatement.setLong(i++, criteria.getIdEmpresa());
			if (criteria.getFechaInicio() != null)
				preparedStatement.setDate(i++, new java.sql.Date(criteria.getFechaInicio().getTime()));
			if (criteria.getCriteriaNombreEmpleado() != null)
				preparedStatement.setString(i++, "%" + criteria.getCriteriaNombreEmpleado() + "%");
			if (criteria.getCriteriaApellidoEmpleado() != null)
				preparedStatement.setString(i++, "%" + criteria.getCriteriaApellidoEmpleado() + "%");
			if (criteria.getCriteriaCodIdentidad() != null)
				preparedStatement.setString(i++, "%" + criteria.getCriteriaCodIdentidad() + "%");
			if (criteria.getCriteriaNombreCliente() != null)
				preparedStatement.setString(i++, "%" + criteria.getCriteriaNombreCliente() + "%");
			if (criteria.getCriteriaTlf() != null)
				preparedStatement.setString(i++, "%" + criteria.getCriteriaTlf() + "%");
			if (criteria.getCriteriaCorreo() != null)
				preparedStatement.setString(i++, "%" + criteria.getCriteriaCorreo() + "%");

			resultSet = preparedStatement.executeQuery();

			List<Gestion> results = new ArrayList<Gestion>();
			Gestion g = null;
			int currentCount = 0;

			if ((startIndex >= 1) && resultSet.absolute(startIndex)) {
				do {
					g = loadNext(connection, resultSet, locale);
					results.add(g);
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

	@Override
	public Gestion create(Connection connection, Gestion g, Ticket t) throws DuplicateInstanceException, DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {

			String queryString = "INSERT INTO gestion(cod_cliente, cod_empleado, cod_empresa, fecha_inicio) "
					+ "VALUES (?, ?, ?, ?)";

			preparedStatement = connection.prepareStatement(queryString, Statement.RETURN_GENERATED_KEYS);

			int i = 1;
			preparedStatement.setLong(i++, g.getIdCliente());
			preparedStatement.setLong(i++, g.getIdEmpleado());
			preparedStatement.setLong(i++, g.getIdEmpresa());
			preparedStatement.setDate(i++, new java.sql.Date(g.getFechaInicio().getTime()));

			int insertedRows = preparedStatement.executeUpdate();

			if (insertedRows == 0) {
				logger.error("Can not add row to table 'Gestion'");
				throw new SQLException("Can not add row to table 'Gestion'");
			}

			resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next()) {
				Long pk = resultSet.getLong(1);
				g.setIdGestion(pk);
			} else {
				logger.error("Unable to fetch autogenerated primary key");
				throw new DataException("Unable to fetch autogenerated primary key");
			}

			return g;

		} catch (SQLException e) {
			logger.error(ToStringBuilder.reflectionToString(g), e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}
	

	private void addClause(StringBuilder queryString, boolean first, String clause) {
		queryString.append(first ? " WHERE " : " AND ").append(clause);
	}

	private void addClause2(StringBuilder queryString, boolean first, String clause) {
		queryString.append(first ? "" : "").append(clause);
	}
	
	private Gestion loadNext(Connection connection, ResultSet resultSet, String locale) throws SQLException, DataException {

		int i = 1;
		Long cod_gestion = resultSet.getLong(i++);
		Long cod_cliente = resultSet.getLong(i++);
		Long cod_empleado = resultSet.getLong(i++);
		Long cod_empresa = resultSet.getLong(i++);
		Date fecha_inicio = resultSet.getDate(i++);

		Gestion g = new Gestion();
		g.setIdGestion(cod_gestion);
		g.setIdCliente(cod_cliente);
		g.setIdEmpleado(cod_empleado);
		g.setIdEmpresa(cod_empresa);
		g.setFechaInicio(fecha_inicio);

		List<Ticket> tickets = ticketDAO.findByGestion(connection, cod_gestion);
		g.setTickets(tickets);

		List<Contacto> contactos = contactoDAO.findByGestion(connection, cod_gestion, locale);
		g.setContactos(contactos);

		return g;
	}

}
