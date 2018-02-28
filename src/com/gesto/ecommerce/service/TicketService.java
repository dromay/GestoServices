/**
 * 
 */
package com.gesto.ecommerce.service;


import java.util.List;

import com.gesto.ecommerce.exceptions.DataException;
import com.gesto.ecommerce.exceptions.DuplicateInstanceException;
import com.gesto.ecommerce.exceptions.InstanceNotFoundException;
import com.gesto.ecommerce.model.Ticket;

public interface TicketService {
	
	public Ticket findById(Long idTicket) throws InstanceNotFoundException, DataException;

	public long countAll() throws DataException;

	public Ticket create(Ticket t) throws DuplicateInstanceException, DataException;

	public List<Ticket> findByGestion(Long idGestion) throws DataException;
	
	public List<Ticket> findByEmpleado(Long id) throws DataException;

}