package com.gesto.ecommerce.dao;

import java.sql.Connection;
import java.util.List;

import com.gesto.ecommerce.exceptions.DataException;
import com.gesto.ecommerce.exceptions.InstanceNotFoundException;
import com.gesto.ecommerce.model.Empresa;

public interface EmpresaDAO {

	public Empresa findById(Connection connection, Long id) throws InstanceNotFoundException, DataException;

	public Boolean exists(Connection connection, Long id) throws DataException;

	public List<Empresa> findAll(Connection connection, int startIndex, int count) throws DataException;

	public Empresa findByTelefono(Connection connection, String telefono) throws InstanceNotFoundException, DataException;
}
