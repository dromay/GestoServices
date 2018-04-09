/**
 * 
 */
package com.gesto.ecommerce.service;

import java.sql.Connection;
import java.util.List;

import com.gesto.ecommerce.exceptions.DataException;
import com.gesto.ecommerce.exceptions.InstanceNotFoundException;
import com.gesto.ecommerce.model.Contacto;
import com.gesto.ecommerce.model.Empresa;

public interface ContactoService {
	
	public Boolean exists(Long contactoCod) throws DataException;

	public Contacto findById(Long contactoCod) throws InstanceNotFoundException, DataException;
	
	public List<Contacto> findAll(int startIndex, int pageSize) throws DataException;

	public List<Contacto> findByCliente(Long clienteId) throws DataException;
	
	public List<Contacto> findByGestion(Long idGestion, int startIndex, int pageSize) throws DataException;
	
	public Contacto findByTelefono(String telefono) throws InstanceNotFoundException, DataException;

}