package com.gesto.ecommerce.service;

import java.sql.Connection;
import java.util.List;

import com.gesto.ecommerce.exceptions.DataException;
import com.gesto.ecommerce.exceptions.DuplicateInstanceException;
import com.gesto.ecommerce.exceptions.InstanceNotFoundException;
import com.gesto.ecommerce.model.Cliente;
import com.gesto.ecommerce.model.Empleado;

public interface EmpleadoService {

	public Empleado findById(Long id) throws InstanceNotFoundException, DataException;

	public Boolean exists(Long id) throws DataException;

	public long countAll() throws DataException;

	public List<Empleado> findByCriteria(EmpleadoCriteria empleado, int startIndex, int count)
			throws DataException;
}