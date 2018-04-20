package com.gesto.ecommerce.dao;

import java.sql.Connection;
import java.util.List;

import com.gesto.ecommerce.exceptions.DataException;
import com.gesto.ecommerce.model.Idioma;

public interface IdiomaDAO {
	
	public List<Idioma> findAll(Connection connection, String locale) throws DataException;
	
	public List<Idioma> findByEmpleado(Connection connection, Long idEmpleado, String locale) throws DataException;

	public List<Idioma> findByCliente(Connection connection, Long idCliente, String locale) throws DataException;
	
	public List<Idioma> findByContacto(Connection connection, Long idContacto, String locale) throws DataException;

}
