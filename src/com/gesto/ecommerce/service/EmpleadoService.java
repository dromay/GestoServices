package com.gesto.ecommerce.service;

import java.util.List;

import com.gesto.ecommerce.exceptions.DataException;
import com.gesto.ecommerce.exceptions.InstanceNotFoundException;
import com.gesto.ecommerce.model.Empleado;
import com.gesto.ecommerce.model.Gestion;

public interface EmpleadoService {
	
	public List<Empleado> findAll(int startIndex, int count) throws DataException;

	public Empleado findByUsuario(String usuario) throws InstanceNotFoundException, DataException;

	public Boolean exists(Long id) throws DataException;

	public long countAll() throws DataException;

	public List<Empleado> findByCriteria(EmpleadoCriteria empleado, int startIndex, int count)
			throws DataException;

	public void update(Empleado e) throws InstanceNotFoundException, DataException;
}