package com.gesto.ecommerce.dao;

import java.sql.Connection;
import java.util.List;

import com.gesto.ecommerce.exceptions.DataException;
import com.gesto.ecommerce.exceptions.InstanceNotFoundException;
import com.gesto.ecommerce.model.Contacto;
import com.gesto.ecommerce.model.Empleado;

public interface ContactoDAO {

	public Boolean exists(Connection connection, Long contactoCod) throws DataException;

	public List<Contacto> findAll(Connection connection, int startIndex, int pageSize) throws DataException;

	public Contacto findById(Connection connection, Long contactoCod) throws InstanceNotFoundException, DataException;

	public List<Contacto> findByNombre(Connection connection, String nombre, int startIndex, int pageSize)
			throws DataException;

	public List<Contacto> findByCliente(Connection connection, Long clienteId) throws DataException;

	public List<Contacto> findByGestion(Connection connection, Long idGestion, int startIndex, int pageSize)
			throws DataException;

	public Contacto findByTelefono(Connection connection, String telefono)
			throws InstanceNotFoundException, DataException;
}
