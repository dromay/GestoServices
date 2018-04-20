package com.gesto.ecommerce.dao;

import java.sql.Connection;
import java.util.List;

import com.gesto.ecommerce.exceptions.DataException;
import com.gesto.ecommerce.exceptions.InstanceNotFoundException;
import com.gesto.ecommerce.model.Contacto;
import com.gesto.ecommerce.model.Empleado;

public interface ContactoDAO {

	public Contacto findById(Connection connection, Long contactoCod, String locale) throws InstanceNotFoundException, DataException;
	
	public Contacto findByTelefono(Connection connection, String telefono, String locale)
			throws InstanceNotFoundException, DataException;
	
	public List<Contacto> findByNombre(Connection connection, String nombre, String locale)
			throws DataException;

	public List<Contacto> findByCliente(Connection connection, Long clienteId, String locale) throws DataException;

	public List<Contacto> findByGestion(Connection connection, Long idGestion, String locale)
			throws DataException;

}
