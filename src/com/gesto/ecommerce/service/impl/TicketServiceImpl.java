package com.gesto.ecommerce.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gesto.ecommerce.dao.TicketDAO;
import com.gesto.ecommerce.dao.impl.TicketDAOImpl;
import com.gesto.ecommerce.dao.util.ConnectionManager;
import com.gesto.ecommerce.dao.util.JDBCUtils;
import com.gesto.ecommerce.exceptions.DataException;
import com.gesto.ecommerce.exceptions.InstanceNotFoundException;
import com.gesto.ecommerce.model.Empresa;
import com.gesto.ecommerce.model.Ticket;
import com.gesto.ecommerce.service.TicketService;

public class TicketServiceImpl implements TicketService {
	
	private static Logger logger = LogManager.getLogger(TicketServiceImpl.class.getName());

	private TicketDAO dao = null;

	public TicketServiceImpl() {
		dao = new TicketDAOImpl();
	}
	
	@Override
	public Ticket findById(Long idTicket) throws InstanceNotFoundException, DataException {
		Connection connection = null;

		try {

			connection = ConnectionManager.getConnection();
			connection.setAutoCommit(true);

			return dao.findById(connection, idTicket);

		} catch (SQLException e) {
			logger.error(connection,e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeConnection(connection);
		}
	}

	@Override
	public long countAll() throws com.gesto.ecommerce.exceptions.DataException {
		Connection connection = null;

		try {

			connection = ConnectionManager.getConnection();
			connection.setAutoCommit(true);

			return dao.countAll(connection);

		} catch (SQLException e) {
			logger.error(connection,e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeConnection(connection);
		}
	}

	@Override
	public Ticket create(Ticket t) throws com.gesto.ecommerce.exceptions.DuplicateInstanceException,
			com.gesto.ecommerce.exceptions.DataException {
		Connection connection = null;
		boolean commit = false;

		try {

			connection = ConnectionManager.getConnection();

			connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

			connection.setAutoCommit(false);

			// Execute action
			Ticket result = dao.create(connection, t);
			commit = true;

			return result;

		} catch (SQLException e) {
			logger.error(connection,e);
			throw new DataException(e);

		} finally {
			JDBCUtils.closeConnection(connection, commit);
		}
	}

	@Override
	public List<Ticket> findByGestion(Long idGestion) throws com.gesto.ecommerce.exceptions.DataException {
		Connection connection = null;

		try {

			connection = ConnectionManager.getConnection();
			connection.setAutoCommit(true);

			return dao.findByGestion(connection, idGestion);

		} catch (SQLException e) {
			logger.error(connection,e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeConnection(connection);
		}
	}

	@Override
	public List<Ticket> findByEmpleado(Long id) throws com.gesto.ecommerce.exceptions.DataException {
		Connection connection = null;

		try {

			connection = ConnectionManager.getConnection();
			connection.setAutoCommit(true);

			return dao.findByEmpleado(connection, id);

		} catch (SQLException e) {
			logger.error(connection,e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeConnection(connection);
		}
	}

}
