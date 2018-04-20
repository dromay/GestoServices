package com.gesto.ecommerce.service;


import java.util.List;

import com.gesto.ecommerce.exceptions.DataException;
import com.gesto.ecommerce.exceptions.InstanceNotFoundException;
import com.gesto.ecommerce.model.Cliente;

public interface ClienteService {
     
 	public Cliente findById(Long idCliente, String locale) throws InstanceNotFoundException, DataException;

 	public List<Cliente> findByCriteria(ClienteCriteria criteria, String locale, int startIndex, int count)
 			throws DataException;
}