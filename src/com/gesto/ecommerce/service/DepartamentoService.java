package com.gesto.ecommerce.service;

import java.util.List;

import com.gesto.ecommerce.exceptions.DataException;
import com.gesto.ecommerce.exceptions.InstanceNotFoundException;
import com.gesto.ecommerce.model.Departamento;

public interface DepartamentoService {

	public Departamento findById(Long extDepartamento)
			throws InstanceNotFoundException, DataException;

	public Boolean exists(Long extDepartamento) throws DataException;

	public List<Departamento> findAll(int startIndex, int count) throws DataException;
}