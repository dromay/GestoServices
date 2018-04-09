/**
 * 
 */
package com.gesto.ecommerce.service;

import java.util.List;

import com.gesto.ecommerce.exceptions.DataException;
import com.gesto.ecommerce.exceptions.DuplicateInstanceException;
import com.gesto.ecommerce.exceptions.InstanceNotFoundException;
import com.gesto.ecommerce.model.Gestion;
import com.gesto.ecommerce.model.Ticket;

public interface GestionService {
	
	public Gestion findById(Long idGestion) throws InstanceNotFoundException, DataException;

	public Boolean exists(Long idGestion) throws DataException;

	public List<Gestion> findAll(int startIndex, int count) throws DataException;

	public long countAll() throws DataException;

	public List<Gestion> findByCliente(Long clienteId, int startIndex, int pageSize) throws DataException;
	
	public List<Gestion> findByEmpleado(Long id, int startIndex, int pageSize) throws DataException;

	public Gestion create(Gestion g, Ticket t) throws DuplicateInstanceException, DataException;

	public List<Gestion> findByCriteria(GestionCriteria gc, int startIndex, int count)
			throws DataException;

}