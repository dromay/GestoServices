package com.gesto.ecommerce.dao;

import java.sql.Connection;
import java.util.List;

import com.gesto.ecommerce.exceptions.DataException;
import com.gesto.ecommerce.exceptions.DuplicateInstanceException;
import com.gesto.ecommerce.exceptions.InstanceNotFoundException;
import com.gesto.ecommerce.model.Gestion;
import com.gesto.ecommerce.model.Ticket;
import com.gesto.ecommerce.service.GestionCriteria;

public interface GestionDAO {

	public Gestion findById(Connection connection, Long idGestion) throws InstanceNotFoundException, DataException;

	public Boolean exists(Connection connection, Long idGestion) throws DataException;

	public List<Gestion> findAll(Connection connection, int startIndex, int count) throws DataException;

	public long countAll(Connection connection) throws DataException;

	public List<Gestion> findByCliente(Connection connection, Long clienteId) throws DataException;
	
	public List<Gestion> findByEmpleado(Connection connection, Long id) throws DataException;

	public Gestion create(Connection connection, Gestion g, Ticket t) throws DuplicateInstanceException, DataException;

	public List<Gestion> findByCriteria(Connection connection, GestionCriteria gc, int startIndex, int count)
			throws DataException;

}
