package com.gesto.ecommerce.dao;

import java.sql.Connection;
import java.util.List;

import com.gesto.ecommerce.exceptions.DataException;
import com.gesto.ecommerce.model.Idioma;

public interface IdiomaDAO {

	public List<Idioma> findByCliente(Connection connection, Long cod_cliente) throws DataException;
	
	public List<Idioma> findByEmpleado(Connection connection, Long id) throws DataException;
	
	public List<Idioma> findByContacto(Connection connection, Long codContacto) throws DataException;

	public List<Idioma> findAll(Connection connection, int startIndex, int pageSize) throws DataException;

}
