package com.gesto.ecommerce.dao;

import java.sql.Connection;
import java.util.List;

import com.gesto.ecommerce.exceptions.DataException;
import com.gesto.ecommerce.exceptions.InstanceNotFoundException;
import com.gesto.ecommerce.model.Empleado;
import com.gesto.ecommerce.service.EmpleadoCriteria;

public interface EmpleadoDAO {
	
	public List<Empleado> findAll(Connection connection, int startIndex, int count) throws DataException;

	public Empleado findByUsuario(Connection connection, String usuario) throws InstanceNotFoundException, DataException;

	public Boolean exists(Connection connection, Long id) throws DataException;

	public long countAll(Connection connection) throws DataException;

	public List<Empleado> findByCriteria(Connection connection, EmpleadoCriteria empleado, int startIndex, int count)
			throws DataException;

	public void update(Connection connection, Empleado e) throws InstanceNotFoundException, DataException;
}
