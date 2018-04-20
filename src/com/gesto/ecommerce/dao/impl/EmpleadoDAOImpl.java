package com.gesto.ecommerce.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gesto.ecommerce.dao.EmpleadoDAO;
import com.gesto.ecommerce.dao.IdiomaDAO;
import com.gesto.ecommerce.dao.util.JDBCUtils;
import com.gesto.ecommerce.exceptions.DataException;
import com.gesto.ecommerce.exceptions.InstanceNotFoundException;
import com.gesto.ecommerce.model.Empleado;
import com.gesto.ecommerce.model.Idioma;
import com.gesto.ecommerce.service.EmpleadoCriteria;


public class EmpleadoDAOImpl implements EmpleadoDAO {

	private static Logger logger = LogManager.getLogger(EmpleadoDAOImpl.class.getName());
	
	private IdiomaDAO idiomaDAO = null;

	public EmpleadoDAOImpl() {
		idiomaDAO = new IdiomaDAOImpl();
	}
	
	@Override
	public List<Empleado> findAll(Connection connection, String locale) throws DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			String queryString = " SELECT em.cod_empleado, em.usuario, em.password, em.nombre, em.apellido, em.ext_departamento, em.ext, em.supervisor, em.fecha_baja "
					+ " FROM empleado em "
					+ " INNER JOIN idioma_empleado ie ON ie.cod_empleado = em.cod_empleado "
					+ " INNER JOIN idioma i ON i.cod_idioma = ie.cod_idioma "
					+ " INNER JOIN i_idioma ii ON i.cod_idioma = ii.cod_idioma "
					+ " WHERE ii.cod_idioma = ? "
					+ " GROUP BY em.cod_empleado "
					+ " ORDER BY em.cod_empleado ASC ";

			preparedStatement = connection.prepareStatement(queryString, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			int i = 1;
			preparedStatement.setString(i++, locale);

			resultSet = preparedStatement.executeQuery();

			List<Empleado> results = new ArrayList<Empleado>();
			Empleado em = null;

			while (resultSet.next()) {
				em = loadNext(connection, resultSet,locale);
				results.add(em);
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

	@Override
	public Empleado findByUsuario(Connection connection, String usuario, String locale)
			throws InstanceNotFoundException, DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			String queryString = " SELECT em.cod_empleado, em.usuario, em.password, em.nombre, em.apellido, em.ext_departamento, em.ext, em.supervisor, em.fecha_baja "
					+ " FROM empleado em " + "WHERE em.usuario = ? ";

			preparedStatement = connection.prepareStatement(queryString, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			int i = 1;
			preparedStatement.setString(i++, usuario);

			resultSet = preparedStatement.executeQuery();
			
			Empleado em = null;

			if (resultSet.next()) {
				em = loadNext(connection, resultSet, locale);
			} else {
				logger.error("Employee with user " + usuario + "not found ",
						Empleado.class.getName());
				throw new InstanceNotFoundException("Employee with user " + usuario + "not found ",
						Empleado.class.getName());
			}

			return em;

		} catch (SQLException e) {
			logger.error("User: " + usuario, e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}
	
	@Override
	public Empleado findSupervisor(Connection connection, Empleado es, String locale)
			throws InstanceNotFoundException, DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			String queryString = " SELECT em.cod_empleado, em.usuario, em.password, em.nombre, em.apellido, em.ext_departamento, em.ext, em.supervisor, em.fecha_baja "
					+ " FROM empleado em " + "WHERE em.cod_empleado = ? ";

			preparedStatement = connection.prepareStatement(queryString, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			
			Long idSupervisor = es.getSupervisor();

			int i = 1;
			preparedStatement.setLong(i++, idSupervisor );

			resultSet = preparedStatement.executeQuery();
			
			Empleado em = null;

			if (resultSet.next()) {
				em = loadNext(connection, resultSet, locale);
			} else {
				logger.error("Sypervisor with id " + idSupervisor + "not found ",
						Empleado.class.getName());
				throw new InstanceNotFoundException("Supervisor with id " + idSupervisor + "not found ",
						Empleado.class.getName());
			}

			return em;

		} catch (SQLException e) {
			logger.error("User: "+es.getSupervisor(), e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}

	@Override
	public List<Empleado> findByCriteria(Connection connection, EmpleadoCriteria criteria, String locale, int startIndex, int count)
			throws DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		StringBuilder queryString = null;

		try {

			queryString = new StringBuilder(
					" SELECT em.cod_empleado, em.usuario, em.password, em.nombre, em.apellido, em.ext_departamento, em.ext, em.supervisor, em.fecha_baja"
							+ " FROM empleado em ");

			// Marca (flag) de primera clausula, que se desactiva en la primera
			boolean first = true;

			if (criteria.getIdTicketCriteria() != null) {
				addClause2(queryString, first, " INNER JOIN ticket t ON t.cod_empleado = em.cod_empleado ");
				first = false;
			}
			if (criteria.getIdGestionCriteria() != null) {
				addClause2(queryString, first, " INNER JOIN gestion g ON em.cod_empleado = g.cod_empleado ");
				first = false;
			}
			if (criteria.getIdIdiomaCriteria() != null) {
				addClause2(queryString, first, " INNER JOIN idioma_empleado ie ON ie.cod_empleado = em.cod_empleado ");
				addClause2(queryString, first, " INNER JOIN idioma i ON i.cod_idioma = ie.cod_idioma ");
				first = false;
			}

			if (criteria.getIdEmpleado() != null) {
				addClause(queryString, first, " UPPER(em.cod_empleado) LIKE ? ");
				first = false;
			}

			if (criteria.getUsuario() != null) {
				addClause(queryString, first, " UPPER(em.usuario) LIKE ? ");
				first = false;
			}

			if (criteria.getNombre() != null) {
				addClause(queryString, first, " UPPER(em.nombre) LIKE ? ");
				first = false;
			}

			if (criteria.getApellido() != null) {
				addClause(queryString, first, " UPPER(em.apellido) LIKE ? ");
				first = false;
			}

			if (criteria.getExtDepartamento() != null) {
				addClause(queryString, first, " UPPER(em.ext_departamento) LIKE ? ");
				first = false;
			}

			if (criteria.getExt() != null) {
				addClause(queryString, first, " UPPER(em.ext) LIKE ? ");
				first = false;
			}

			if (criteria.getSupervisor() != null) {
				addClause(queryString, first, " UPPER(em.supervisor) LIKE ? ");
				first = false;
			}

			if (criteria.getIdGestionCriteria() != null) {
				addClause(queryString, first, " UPPER(g.cod_gestion) LIKE ? ");
				first = false;
			}

			if (criteria.getIdTicketCriteria() != null) {
				addClause(queryString, first, " UPPER(t.cod_ticket) LIKE ? ");
				first = false;
			}

			if (criteria.getIdIdiomaCriteria() != null) {
				addClause(queryString, first, " UPPER(i.cod_idioma) LIKE ? ");
				first = false;
			}

			addClause2(queryString, first, " GROUP BY em.cod_empleado ");
			
			if (logger.isDebugEnabled()) {
				logger.debug(queryString);
			}
			
			preparedStatement = connection.prepareStatement(queryString.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			int i = 1;

			if (criteria.getIdEmpleado() != null)
				preparedStatement.setLong(i++, criteria.getIdEmpleado());
			if (criteria.getUsuario() != null)
				preparedStatement.setString(i++, "%" + criteria.getUsuario() + "%");
			if (criteria.getNombre() != null)
				preparedStatement.setString(i++, "%" + criteria.getNombre() + "%");
			if (criteria.getApellido() != null)
				preparedStatement.setString(i++, "%" + criteria.getApellido() + "%");
			if (criteria.getExtDepartamento() != null)
				preparedStatement.setLong(i++, criteria.getExtDepartamento());
			if (criteria.getExt() != null)
				preparedStatement.setLong(i++, criteria.getExt());
			if (criteria.getSupervisor() != null)
				preparedStatement.setLong(i++, criteria.getSupervisor());
			if (criteria.getIdGestionCriteria() != null)
				preparedStatement.setLong(i++, criteria.getIdGestionCriteria());
			if (criteria.getIdTicketCriteria() != null)
				preparedStatement.setLong(i++, criteria.getIdTicketCriteria());
			if (criteria.getIdIdiomaCriteria() != null)
				preparedStatement.setString(i++, criteria.getIdIdiomaCriteria());

			resultSet = preparedStatement.executeQuery();

			List<Empleado> results = new ArrayList<Empleado>();
			Empleado em = null;
			int currentCount = 0;

			if ((startIndex >= 1) && resultSet.absolute(startIndex)) {
				do {
					em = loadNext(connection, resultSet, locale);
					results.add(em);
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
	public void updatePassword(Connection connection, Empleado e) 
			throws InstanceNotFoundException, DataException {
		PreparedStatement preparedStatement = null;
		try {

			// Create "preparedStatement"
			String queryString = 
					"UPDATE empleado " +
					"SET password = ? " +
					"WHERE cod_empleado = ? ";

			preparedStatement = connection.prepareStatement(queryString);

			// Fill "preparedStatement"
			int i = 1;
			preparedStatement.setString(i++, e.getPassword());
			preparedStatement.setLong(i++, e.getIdEmpleado());

			/* Execute update. */
			int updatedRows = preparedStatement.executeUpdate();

			if (updatedRows == 0) {
				throw new InstanceNotFoundException(e.getIdEmpleado(), Empleado.class.getName());
			}

			if (updatedRows > 1) {
				logger.error("Duplicate row for id = '" + 
						e.getIdEmpleado() + "' in table 'Empleado'");
				throw new SQLException("Duplicate row for id = '" + 
						e.getIdEmpleado() + "' in table 'Empleado'");
			}                          

		} catch (SQLException ex) {
			logger.error("Employee: "+ToStringBuilder.reflectionToString(e), ex);			
			throw new DataException(ex);    
		} finally {
			JDBCUtils.closeStatement(preparedStatement);
		}              		
	}

	private void addClause(StringBuilder queryString, boolean first, String clause) {
		queryString.append(first ? " WHERE " : " AND ").append(clause);
	}

	private void addClause2(StringBuilder queryString, boolean first, String clause) {
		queryString.append(first ? "" : "").append(clause);
	}

	private Empleado loadNext(Connection connection, ResultSet resultSet, String locale) throws SQLException, DataException {
		
		int i = 1;
		Long cod_empleado = resultSet.getLong(i++);
		String usuario = resultSet.getString(i++);
		String password =resultSet.getString(i++);
		String nombre = resultSet.getString(i++);
		String apellido = resultSet.getString(i++);
		Long ext_departamento = resultSet.getLong(i++);
		Long ext = resultSet.getLong(i++);
		Long supervisor = resultSet.getLong(i++);
		Date fecha_baja = resultSet.getDate(i++);

		Empleado em = new Empleado();
		em.setIdEmpleado(cod_empleado);
		em.setUsuario(usuario);
		em.setPassword(password);
		em.setNombre(nombre);
		em.setApellido(apellido);
		em.setExtDepartamento(ext_departamento);
		em.setExt(ext);
		em.setSupervisor(supervisor);
		em.setFechaBaja(fecha_baja);

		List<Idioma> idiomas = idiomaDAO.findByEmpleado(connection, em.getIdEmpleado(), locale);
		em.setIdiomas(idiomas);

		return em;
	}
	
	

}
