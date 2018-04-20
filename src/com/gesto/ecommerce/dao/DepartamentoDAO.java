package com.gesto.ecommerce.dao;

import java.sql.Connection;
import java.util.List;

import com.gesto.ecommerce.exceptions.DataException;
import com.gesto.ecommerce.exceptions.InstanceNotFoundException;
import com.gesto.ecommerce.model.Departamento;

public interface DepartamentoDAO {

	public List<Departamento> findAll(Connection connection, String locale) throws DataException;

	public Departamento findById(Connection connection, Long extDepartamento, String locale)
			throws InstanceNotFoundException, DataException;
}
