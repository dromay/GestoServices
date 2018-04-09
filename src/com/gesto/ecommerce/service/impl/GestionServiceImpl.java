package com.gesto.ecommerce.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gesto.ecommerce.dao.GestionDAO;
import com.gesto.ecommerce.dao.TicketDAO;
import com.gesto.ecommerce.dao.impl.GestionDAOImpl;
import com.gesto.ecommerce.dao.impl.TicketDAOImpl;
import com.gesto.ecommerce.dao.util.ConnectionManager;
import com.gesto.ecommerce.dao.util.JDBCUtils;
import com.gesto.ecommerce.exceptions.DataException;
import com.gesto.ecommerce.exceptions.DuplicateInstanceException;
import com.gesto.ecommerce.exceptions.InstanceNotFoundException;
import com.gesto.ecommerce.model.Gestion;
import com.gesto.ecommerce.model.Ticket;
import com.gesto.ecommerce.service.GestionCriteria;
import com.gesto.ecommerce.service.GestionService;

public class GestionServiceImpl implements GestionService {
	
	private static Logger logger = LogManager.getLogger(GestionServiceImpl.class.getName());

	private GestionDAO dao = null;
	private TicketDAO ticketDao= null;

	public GestionServiceImpl() {
		dao = new GestionDAOImpl();
		ticketDao = new TicketDAOImpl();
	}
	


	@Override
	public long countAll() throws DataException {

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
	public Gestion findById(Long idGestion) throws InstanceNotFoundException, DataException {

		Connection connection = null;

		try {

			connection = ConnectionManager.getConnection();
			connection.setAutoCommit(true);

			return dao.findById(connection, idGestion);

		} catch (SQLException e) {
			logger.error(connection,e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeConnection(connection);
		}

	}

	@Override
	public Boolean exists(Long idGestion) throws DataException {

		Connection connection = null;

		try {

			connection = ConnectionManager.getConnection();
			connection.setAutoCommit(true);

			return dao.exists(connection, idGestion);

		} catch (SQLException e) {
			logger.error(connection,e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeConnection(connection);
		}
	}

	@Override
	public List<Gestion> findByCliente(Long clienteId, int startIndex, int pageSize) throws DataException {

		Connection connection = null;

		try {

			connection = ConnectionManager.getConnection();
			connection.setAutoCommit(true);

			return dao.findByCliente(connection, clienteId, startIndex, pageSize);

		} catch (SQLException e) {
			logger.error(connection,e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeConnection(connection);
		}
	}

	@Override
	public List<Gestion> findByEmpleado(Long id, int startIndex, int pageSize) throws DataException {
		Connection connection = null;

		try {

			connection = ConnectionManager.getConnection();
			connection.setAutoCommit(true);

			return dao.findByEmpleado(connection, id, startIndex, pageSize);

		} catch (SQLException e) {
			logger.error(connection,e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeConnection(connection);
		}
	}

	@Override
	public Gestion create(Gestion g, Ticket t) throws DuplicateInstanceException, DataException {

		Connection connection = null;
		boolean commit = false;

		try {

			connection = ConnectionManager.getConnection();

			connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

			connection.setAutoCommit(false);

			// Execute action
			Gestion result = dao.create(connection, g, t);
			t.setIdGestion(g.getIdGestion());
			t.setIdEmpleado(g.getIdEmpleado());
			ticketDao.create(connection, t);
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
	public List<Gestion> findByCriteria(GestionCriteria gc, int startIndex, int count) throws DataException {
		Connection connection = null;

		try {

			connection = ConnectionManager.getConnection();
			connection.setAutoCommit(true);

			return dao.findByCriteria(connection, gc, startIndex, count);

		} catch (SQLException e) {
			logger.error(connection,e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeConnection(connection);
		}
	}

	@Override
	public List<Gestion> findAll(int startIndex, int count) throws DataException {
		Connection connection = null;

		try {

			connection = ConnectionManager.getConnection();
			connection.setAutoCommit(true);

			return dao.findAll(connection, startIndex, count);

		} catch (SQLException e) {
			logger.error(connection,e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeConnection(connection);
		}
	}
}
