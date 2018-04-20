package com.gesto.ecommerce.service;

import java.util.List;

import com.gesto.ecommerce.exceptions.DataException;
import com.gesto.ecommerce.exceptions.InstanceNotFoundException;
import com.gesto.ecommerce.model.Empleado;

public interface EmpleadoService {
	
	public List<Empleado> findAll(String locale) throws DataException;

	public Empleado findByUsuario(String usuario, String locale) throws InstanceNotFoundException, DataException;
	
	public Empleado findSupervisor(Empleado es, String locale) throws InstanceNotFoundException, DataException;

	public List<Empleado> findByCriteria(EmpleadoCriteria empleado, String locale, int startIndex, int count)
			throws DataException;

	public void updatePassword(Empleado e) throws InstanceNotFoundException, DataException;

	
}