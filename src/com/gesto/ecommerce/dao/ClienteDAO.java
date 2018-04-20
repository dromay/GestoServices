package com.gesto.ecommerce.dao;

import java.sql.Connection;
import java.util.List;

import com.gesto.ecommerce.exceptions.DataException;
import com.gesto.ecommerce.exceptions.InstanceNotFoundException;
import com.gesto.ecommerce.model.Cliente;
import com.gesto.ecommerce.service.ClienteCriteria;

public interface ClienteDAO {

	public Cliente findById(Connection connection, Long clienteId, String locale) throws InstanceNotFoundException, DataException;

	public List<Cliente> findByCriteria(Connection connection, ClienteCriteria c, String locale, int startIndex, int count)
			throws DataException;

}