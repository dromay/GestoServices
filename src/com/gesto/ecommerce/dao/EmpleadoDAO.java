package com.gesto.ecommerce.dao;

import java.sql.Connection;
import java.util.List;

import com.gesto.ecommerce.exceptions.DataException;
import com.gesto.ecommerce.exceptions.InstanceNotFoundException;
import com.gesto.ecommerce.model.Empleado;
import com.gesto.ecommerce.service.EmpleadoCriteria;

public interface EmpleadoDAO {

	public List<Empleado> findAll(Connection connection, String locale) throws DataException;
	
	public Empleado findByUsuario(Connection connection, String usuario, String locale) throws InstanceNotFoundException, DataException;
	
	public Empleado findSupervisor(Connection connection, Empleado es, String locale)
			throws InstanceNotFoundException, DataException;

	public List<Empleado> findByCriteria(Connection connection, EmpleadoCriteria empleado, String locale, int startIndex, int count)
			throws DataException;

	public void updatePassword(Connection connection, Empleado e) throws InstanceNotFoundException, DataException;

	
}
