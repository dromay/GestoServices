package com.gesto.ecommerce.service;

import java.util.List;

import com.gesto.ecommerce.exceptions.DataException;

import com.gesto.ecommerce.exceptions.InstanceNotFoundException;

import com.gesto.ecommerce.model.Empresa;

public interface EmpresaService {

	public Empresa findById(Long id) throws InstanceNotFoundException, DataException;

	public Boolean exists(Long id) throws DataException;

	public List<Empresa> findAll(int startIndex, int count) throws DataException;
}