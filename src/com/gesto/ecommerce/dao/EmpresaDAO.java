package com.gesto.ecommerce.dao;

import java.sql.Connection;
import java.util.List;

import com.gesto.ecommerce.exceptions.DataException;
import com.gesto.ecommerce.exceptions.InstanceNotFoundException;
import com.gesto.ecommerce.model.Empresa;

public interface EmpresaDAO {

	public Empresa findById(Connection connection, Long idEmpresa) throws InstanceNotFoundException, DataException;

	public Empresa findByTelefono(Connection connection, String tlf) throws InstanceNotFoundException, DataException;
}
