package com.gesto.ecommerce.dao;

import java.sql.Connection;
import java.util.List;

import com.gesto.ecommerce.exceptions.DataException;
import com.gesto.ecommerce.exceptions.InstanceNotFoundException;
import com.gesto.ecommerce.model.Cliente;
import com.gesto.ecommerce.service.ClienteCriteria;

public interface ClienteDAO {

	public Cliente findById(Connection connection, Long clienteId) throws InstanceNotFoundException, DataException;

	public Boolean exists(Connection connection, Long clienteId) throws DataException;

	public List<Cliente> findAll(Connection connection, int startIndex, int count) throws DataException;

	public long countAll(Connection connection) throws DataException;

	public List<Cliente> findByCriteria(Connection connection, ClienteCriteria c, int startIndex, int count)
			throws DataException;

}