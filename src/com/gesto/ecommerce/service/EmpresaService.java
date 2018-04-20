package com.gesto.ecommerce.service;

import com.gesto.ecommerce.exceptions.DataException;
import com.gesto.ecommerce.exceptions.InstanceNotFoundException;
import com.gesto.ecommerce.model.Empresa;

public interface EmpresaService {
	
	public Empresa findById(Long idEmpresa) throws InstanceNotFoundException, DataException;

	public Empresa findByTelefono(String tlf) throws InstanceNotFoundException, DataException;
}