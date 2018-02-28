package com.gesto.ecommerce.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.gesto.ecommerce.dao.EmpleadoDAO;
import com.gesto.ecommerce.dao.GestionDAO;
import com.gesto.ecommerce.dao.IdiomaDAO;
import com.gesto.ecommerce.dao.TicketDAO;
import com.gesto.ecommerce.model.Empleado;
import com.gesto.ecommerce.model.Gestion;
import com.gesto.ecommerce.model.Idioma;
import com.gesto.ecommerce.model.Ticket;
import com.gesto.ecommerce.service.EmpleadoCriteria;
import com.gesto.ecommerce.dao.util.JDBCUtils;
import com.gesto.ecommerce.exceptions.DataException;
import com.gesto.ecommerce.exceptions.InstanceNotFoundException;

public class EmpleadoDAOImpl implements EmpleadoDAO {
	private TicketDAO ticketDAO = null;
	private IdiomaDAO idiomaDAO = null;
	private GestionDAO gestionDAO = null;

	public EmpleadoDAOImpl() {
		ticketDAO = new TicketDAOImpl();
		idiomaDAO = new IdiomaDAOImpl();
		gestionDAO = new GestionDAOImpl();
	}

	@Override
	public Empleado findById(Connection connection, Long id) throws InstanceNotFoundException, DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			String queryString = "SELECT d.CustomerTypeID, d.CustomerDesc " + "FROM customerdemographics d "
					+ "WHERE d.CustomerTypeID = ? ";

			preparedStatement = connection.prepareStatement(queryString, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			int i = 1;
			preparedStatement.setLong(i++, id);

			resultSet = preparedStatement.executeQuery();

			Empleado em = null;

			if (resultSet.next()) {
				em = loadNext(connection, resultSet);
			} else {
				throw new InstanceNotFoundException("Customer with id " + id + "not found", Empleado.class.getName());
			}

			return em;

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

			String queryString = "SELECT d.CustomerTypeID, d.CustomerDesc " + "FROM customerdemographics d "
					+ "WHERE d.CustomerTypeID = ? ";

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
	public long countAll(Connection connection) throws DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			String queryString = " SELECT count(*) " + " FROM CustomersDemographics";

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
	public List<Empleado> findByCriteria(Connection connection, EmpleadoCriteria empleado, int startIndex, int count)
			throws DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		StringBuilder queryString = null;

		try {

			queryString = new StringBuilder(
					" SELECT em.cod_empleado, em.usuario, em.nombre, em.apellido, em.ext_departamento, em.ext, em.supervisor, em.fecha_baja , i.cod_idioma "
							+ " FROM empleado em " + " INNER JOIN gestion g ON em.cod_empleado = g.cod_empleado "
							+ " INNER JOIN ticket t ON t.cod_empleado = em.cod_empleado "
							+ " INNER JOIN departamento d ON d.ext_departamento = em.ext_departamento "
							+ " INNER JOIN idioma_empleado ie ON ie.cod_empleado = em.cod_empleado "
							+ " INNER JOIN idioma i ON i.cod_idioma = ie.cod_idioma ");

			// Marca (flag) de primera clausula, que se desactiva en la primera
			boolean first = true;

			if (empleado.getId() != null) {
				addClause(queryString, first, " UPPER(em.cod_empleado) LIKE ? ");
				first = false;
			}

			if (empleado.getUsuario() != null) {
				addClause(queryString, first, " UPPER(em.usuario) LIKE ? ");
				first = false;
			}

			if (empleado.getNombre() != null) {
				addClause(queryString, first, " UPPER(g.nombre) LIKE ? ");
				first = false;
			}

			if (empleado.getApellido() != null) {
				addClause(queryString, first, " UPPER(g.apellido) LIKE ? ");
				first = false;
			}

			if (empleado.getExtDepartamento() != null) {
				addClause(queryString, first, " UPPER(g.ext_departamento) LIKE ? ");
				first = false;
			}

			if (empleado.getExt() != null) {
				addClause(queryString, first, " UPPER(g.ext) LIKE ? ");
				first = false;
			}

			if (empleado.getSupervisor() != null) {
				addClause(queryString, first, " UPPER(g.supervisor) LIKE ? ");
				first = false;
			}

			if (empleado.getFechaBaja() != null) {
				addClause(queryString, first, " UPPER(g.cod_empresa) LIKE ? ");
				first = false;
			}

			if (empleado.getGestiones().isEmpty()) {
				addClause(queryString, first, " UPPER(g.cod_empleado) LIKE ? ");
				first = false;
			}

			if (empleado.getTickets().isEmpty()) {
				addClause(queryString, first, " UPPER(t.cod_empleado) LIKE ? ");
				first = false;
			}

			if (empleado.getIdiomas().isEmpty()) {
				addClause(queryString, first, " UPPER(i.cod_idioma) LIKE ? ");
				first = false;
			}

			preparedStatement = connection.prepareStatement(queryString.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			int i = 1;

			preparedStatement.setString(i++, "%" + empleado.getId() + "%");
			preparedStatement.setString(i++, "%" + empleado.getUsuario() + "%");
			preparedStatement.setString(i++, "%" + empleado.getNombre() + "%");
			preparedStatement.setString(i++, "%" + empleado.getApellido() + "%");
			preparedStatement.setString(i++, "%" + empleado.getExtDepartamento() + "%");
			preparedStatement.setString(i++, "%" + empleado.getExt() + "%");
			preparedStatement.setString(i++, "%" + empleado.getSupervisor() + "%");
			preparedStatement.setString(i++, "%" + empleado.getFechaBaja() + "%");
			preparedStatement.setString(i++, "%" + empleado.getGestiones() + "%");
			preparedStatement.setString(i++, "%" + empleado.getTickets() + "%");
			preparedStatement.setString(i++, "%" + empleado.getIdiomas() + "%");

			resultSet = preparedStatement.executeQuery();

			List<Empleado> results = new ArrayList<Empleado>();
			Empleado em = null;
			int currentCount = 0;

			if ((startIndex >= 1) && resultSet.absolute(startIndex)) {
				do {
					em = loadNext(connection, resultSet);
					results.add(em);
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

	private Empleado loadNext(Connection connection, ResultSet resultSet) throws SQLException, DataException {

		int i = 1;
		Long cod_empleado = resultSet.getLong(i++);
		String usuario = resultSet.getString(i++);
		String nombre = resultSet.getString(i++);
		String apellido = resultSet.getString(i++);
		Long ext_departamento = resultSet.getLong(i++);
		Long ext = resultSet.getLong(i++);
		Long supervisor = resultSet.getLong(i++);
		Date fecha_baja = resultSet.getDate(i++);

		Empleado em = new Empleado();
		em.setId(cod_empleado);
		em.setUsuario(usuario);
		em.setNombre(nombre);
		em.setApellido(apellido);
		em.setExtDepartamento(ext_departamento);
		em.setExt(ext);
		em.setSupervisor(supervisor);
		em.setFechaBaja(fecha_baja);

		List<Ticket> tickets = ticketDAO.findByEmpleado(connection, cod_empleado);
		em.setTickets(tickets);
		List<Idioma> idiomas = idiomaDAO.findByEmpleado(connection, cod_empleado);
		em.setIdiomas(idiomas);
		List<Gestion> gestiones = gestionDAO.findByEmpleado(connection, cod_empleado);
		em.setGestiones(gestiones);

		return em;
	}

}
