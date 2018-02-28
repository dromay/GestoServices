/**
 * 
 */
package com.gesto.ecommerce.service;

import java.util.List;

import com.gesto.ecommerce.exceptions.DataException;
import com.gesto.ecommerce.model.Contacto;

public interface ContactoService {
	
	public Boolean exists(Long contactoCod) throws DataException;

	public List<Contacto> findAll(int startIndex, int count) throws DataException;

	public List<Contacto> findByCliente(Long clienteId) throws DataException;
	
	public List<Contacto> findByGestion(Long idGestion) throws DataException;

}