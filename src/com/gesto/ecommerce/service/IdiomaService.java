/**
 * 
 */
package com.gesto.ecommerce.service;

import java.util.List;

import com.gesto.ecommerce.exceptions.DataException;
import com.gesto.ecommerce.model.Idioma;

public interface IdiomaService {

	public List<Idioma> findAll(String locale) throws DataException;
	
	public List<Idioma> findByEmpleado(Long idEmpleado, String locale) throws DataException;

	public List<Idioma> findByCliente(Long idCliente, String locale) throws DataException;
	
	public List<Idioma> findByContacto(Long idContacto, String locale) throws DataException;


}