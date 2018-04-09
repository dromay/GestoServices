package com.gesto.ecommerce.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gesto.ecommerce.dao.TicketDAO;
import com.gesto.ecommerce.dao.util.JDBCUtils;
import com.gesto.ecommerce.exceptions.DataException;
import com.gesto.ecommerce.exceptions.DuplicateInstanceException;
import com.gesto.ecommerce.exceptions.InstanceNotFoundException;
import com.gesto.ecommerce.model.Empresa;
import com.gesto.ecommerce.model.Ticket;
import com.gesto.ecommerce.util.DateTimeUtils;

public class TicketDAOImpl implements TicketDAO {
	private static Logger logger = LogManager.getLogger(TicketDAOImpl.class.getName());

	public TicketDAOImpl() {
	}

	@Override
	public Ticket findById(Connection connection, Long idTicket) throws InstanceNotFoundException, DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			String queryString = "SELECT t.cod_ticket, t.cod_gestion, t.tipo_ticket, t.tiempo_total, t.comentario, t.fecha_inicio, t.cod_empleado, t.ext_departamento, t.cod_contacto "
					+ "FROM ticket t " + " WHERE t.cod_ticket = ? ";

			preparedStatement = connection.prepareStatement(queryString, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			int i = 1;
			preparedStatement.setLong(i++, idTicket);

			resultSet = preparedStatement.executeQuery();

			Ticket t = null;

			if (resultSet.next()) {
				t = loadNext(resultSet);
			} else {
				throw new InstanceNotFoundException("Ticket with id " + idTicket + "not found", Ticket.class.getName());
			}

			return t;

		} catch (SQLException e) {
			logger.error("Ticket ID: " + idTicket, e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}

	public List<Ticket> findByEmpleado(Connection connection, Long id) throws DataException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			String queryString = "SELECT t.cod_ticket, t.cod_gestion, t.tipo_ticket, t.tiempo_total, t.comentario, t.fecha_inicio, t.cod_empleado, t.ext_departamento, t.cod_contacto "
					+ "FROM ticket t " + " WHERE t.cod_empleado = ? ";

			preparedStatement = connection.prepareStatement(queryString, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			int i = 1;
			preparedStatement.setLong(i++, id);

			resultSet = preparedStatement.executeQuery();

			List<Ticket> results = new ArrayList<Ticket>();

			Ticket t = null;

			while (resultSet.next()) {
				t = loadNext(resultSet);
				results.add(t);
			}
			return results;

		} catch (SQLException e) {
			logger.error("Employee ID: " + id, e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}

	public List<Ticket> findByGestion(Connection connection, Long idGestion) throws DataException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			String queryString = "SELECT t.cod_ticket, t.cod_gestion, t.tipo_ticket, t.tiempo_total, t.comentario, t.fecha_inicio, t.cod_empleado, t.ext_departamento, t.cod_contacto "
					+ "FROM ticket t "
					+ " INNER JOIN gestion g ON g.cod_gestion = t.cod_gestion AND t.cod_gestion = ? ";

			preparedStatement = connection.prepareStatement(queryString, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			int i = 1;
			preparedStatement.setLong(i++, idGestion);

			resultSet = preparedStatement.executeQuery();

			List<Ticket> results = new ArrayList<Ticket>();

			Ticket t = null;

			while (resultSet.next()) {
				t = loadNext(resultSet);
				results.add(t);
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

	@Override
	public long countAll(Connection connection) throws DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			String queryString = " SELECT count(*) " + " FROM ticket";

			preparedStatement = connection.prepareStatement(queryString);

			resultSet = preparedStatement.executeQuery();

			int i = 1;
			if (resultSet.next()) {
				return resultSet.getLong(i++);
			} else {
				logger.error("Unexpected condition trying to retrieve count value");
				throw new DataException("Unexpected condition trying to retrieve count value");
			}

		} catch (SQLException e) {
			logger.error(e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}

	@Override
	public Ticket create(Connection connection, Ticket t) throws DuplicateInstanceException, DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {

			if (!StringUtils.isEmpty(t.getExtDepartamento().toString())) {
				String queryString = "INSERT INTO ticket(cod_gestion, cod_empleado, tipo_ticket, tiempo_total, comentario, fecha_inicio, cod_contacto) "
						+ "VALUES (?, ?, ?, ?, ?, ?, ? )";
				preparedStatement = connection.prepareStatement(queryString, Statement.RETURN_GENERATED_KEYS);

				int i = 1;
				preparedStatement.setLong(i++, t.getIdGestion());
				preparedStatement.setLong(i++, t.getIdEmpleado());
				preparedStatement.setString(i++, t.getTipoTicket());
				preparedStatement.setLong(i++, t.getTiempoTotal());
				preparedStatement.setString(i++, t.getComentario());
				preparedStatement.setDate(i++, new java.sql.Date(t.getFechaInicio().getTime()));
				preparedStatement.setLong(i++, t.getIdContacto());

			} else {
				String queryString = "INSERT INTO ticket(cod_gestion, cod_empleado, tipo_ticket, tiempo_total, comentario, fecha_inicio, ext_departamento, cod_contacto) "
						+ "VALUES (?, ?, ?, ?, ?, ?, ?, ? )";
				preparedStatement = connection.prepareStatement(queryString, Statement.RETURN_GENERATED_KEYS);

				int i = 1;
				preparedStatement.setLong(i++, t.getIdGestion());
				preparedStatement.setLong(i++, t.getIdEmpleado());
				preparedStatement.setString(i++, t.getTipoTicket());
				preparedStatement.setLong(i++, t.getTiempoTotal());
				preparedStatement.setString(i++, t.getComentario());
				preparedStatement.setDate(i++, new java.sql.Date(t.getFechaInicio().getTime()));
				preparedStatement.setLong(i++, t.getExtDepartamento());
				preparedStatement.setLong(i++, t.getIdContacto());
			}

			int insertedRows = preparedStatement.executeUpdate();

			if (insertedRows == 0) {
				logger.error("Can not add row to table 'ticket'");
				throw new SQLException("Can not add row to table 'ticket'");
			}

			resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next()) {
				Long pk = resultSet.getLong(1);
				t.setIdTicket(pk);
			} else {
				logger.error("Unable to fetch autogenerated primary key");
				throw new DataException("Unable to fetch autogenerated primary key");
			}

			return t;

		} catch (SQLException e) {
			logger.error(ToStringBuilder.reflectionToString(t), e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}

	private Ticket loadNext(ResultSet resultSet) throws SQLException {

		int i = 1;
		Long cod_ticket = resultSet.getLong(i++);
		Long cod_gestion = resultSet.getLong(i++);
		String tipo_ticket = resultSet.getString(i++);
		Long tiempo_total = resultSet.getLong(i++);
		String comentario = resultSet.getString(i++);
		Date fecha_inicio = resultSet.getDate(i++);
		Long cod_empleado = resultSet.getLong(i++);
		Long ext_departamento = resultSet.getLong(i++);
		Long cod_contacto = resultSet.getLong(i++);

		Ticket t = new Ticket();
		t.setIdTicket(cod_ticket);
		t.setIdGestion(cod_gestion);
		t.setTipoTicket(tipo_ticket);
		t.setTiempoTotal(tiempo_total);
		t.setComentario(comentario);
		t.setFechaInicio(fecha_inicio);
		t.setIdEmpleado(cod_empleado);
		t.setExtDepartamento(ext_departamento);
		t.setIdContacto(cod_contacto);

		return t;
	}

}
