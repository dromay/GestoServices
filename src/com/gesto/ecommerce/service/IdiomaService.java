/**
 * 
 */
package com.gesto.ecommerce.service;

import java.util.List;

import com.gesto.ecommerce.exceptions.DataException;
import com.gesto.ecommerce.model.Idioma;

public interface IdiomaService {

	public List<Idioma> findByCliente(Long clienteId) throws DataException;
	
	public List<Idioma> findByEmpleado(Long id) throws DataException;
	
	public List<Idioma> findByContacto(Long codContacto) throws DataException;

	public List<Idioma> findAll(int startIndex, int pageSize) throws DataException;


}