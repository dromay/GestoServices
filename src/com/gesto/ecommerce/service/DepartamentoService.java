package com.gesto.ecommerce.service;

import java.util.List;

import com.gesto.ecommerce.exceptions.DataException;
import com.gesto.ecommerce.exceptions.InstanceNotFoundException;
import com.gesto.ecommerce.model.Departamento;

public interface DepartamentoService {
	
	public List<Departamento> findAll(String locale) throws DataException;

	public Departamento findById(Long extDepartamento, String locale)
			throws InstanceNotFoundException, DataException;
}