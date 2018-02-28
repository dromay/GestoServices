package com.gesto.ecommerce.service;


import java.util.List;

import com.gesto.ecommerce.exceptions.DataException;
import com.gesto.ecommerce.exceptions.InstanceNotFoundException;
import com.gesto.ecommerce.model.Cliente;

public interface ClienteService {
     
 	public Cliente findById(Long clienteId) throws InstanceNotFoundException, DataException;

 	public Boolean exists(Long clienteId) throws DataException;

 	public List<Cliente> findAll(int startIndex, int count) throws DataException;

 	public long countAll() throws DataException;

 	public List<Cliente> findByCriteria(ClienteCriteria c, int startIndex, int count)
 			throws DataException;

}