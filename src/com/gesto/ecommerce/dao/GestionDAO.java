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

	public Gestion findById(Connection connection, Long idGestion, String locale) throws InstanceNotFoundException, DataException;

	public List<Gestion> findByCliente(Connection connection, Long idCliente, String locale, int startIndex, int pageSize) throws DataException;
	
	public List<Gestion> findByEmpleado(Connection connection, Long idEmpleado, String locale, int startIndex, int pageSize) throws DataException;

	public List<Gestion> findByCriteria(Connection connection, GestionCriteria criteria, String locale, int startIndex, int count)
			throws DataException;
	
	public Gestion create(Connection connection, Gestion g, Ticket t) throws DuplicateInstanceException, DataException;

}
