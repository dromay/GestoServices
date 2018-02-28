package com.gesto.ecommerce.dao;

import java.sql.Connection;
import java.util.List;

import com.gesto.ecommerce.exceptions.DataException;
import com.gesto.ecommerce.exceptions.DuplicateInstanceException;
import com.gesto.ecommerce.exceptions.InstanceNotFoundException;
import com.gesto.ecommerce.model.Ticket;

public interface TicketDAO {

	public Ticket findById(Connection connection, Long idTicket) throws InstanceNotFoundException, DataException;

	public long countAll(Connection connection) throws DataException;

	public Ticket create(Connection connection, Ticket t) throws DuplicateInstanceException, DataException;

	public List<Ticket> findByGestion(Connection connection, Long idGestion) throws DataException;
	
	public List<Ticket> findByEmpleado(Connection connection, Long id) throws DataException;
}
