/**
 * 
 */
package com.gesto.ecommerce.service;

import java.util.List;

import com.gesto.ecommerce.exceptions.DataException;
import com.gesto.ecommerce.exceptions.InstanceNotFoundException;
import com.gesto.ecommerce.model.Contacto;

public interface ContactoService {

	public Contacto findById(Long idContacto, String locale) throws InstanceNotFoundException, DataException;

	public List<Contacto> findByCliente(Long idCliente, String locale) throws DataException;
	
	public List<Contacto> findByGestion(Long idGestion, String locale) throws DataException;
	
	public Contacto findByTelefono(String tlf, String locale) throws InstanceNotFoundException, DataException;
	
}