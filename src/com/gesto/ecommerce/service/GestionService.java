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
	
	public Gestion findById(Long idGestion, String locale) throws InstanceNotFoundException, DataException;

	public List<Gestion> findByCliente(Long idCliente, String locale, int startIndex, int pageSize) throws DataException;
	
	public List<Gestion> findByEmpleado(Long idEmpleado, String locale, int startIndex, int pageSize) throws DataException;

	public List<Gestion> findByCriteria(GestionCriteria criteria, String locale, int startIndex, int count)
			throws DataException;
	
	public Gestion create(Gestion g, Ticket t) throws DuplicateInstanceException, DataException;

}