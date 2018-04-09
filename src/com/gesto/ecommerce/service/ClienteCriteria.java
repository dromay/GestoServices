package com.gesto.ecommerce.service;

import com.gesto.ecommerce.model.Cliente;

public class ClienteCriteria extends Cliente {
	private Long clienteIdCriteria = null;
	private String tipoCriteria = null;
	private String nombreCriteria = null;
	private String docIdentidadCriteria = null;
	private String tlfCriteria = null;
	private String correoCriteria = null;
	private String idIdiomaCriteria = null;
	private Long idGestionCriteria = null;

	public ClienteCriteria() {

	}

	public Long getClienteIdCriteria() {
		return clienteIdCriteria;
	}

	public void setClienteIdCriteria(Long clienteIdCriteria) {
		this.clienteIdCriteria = clienteIdCriteria;
	}

	public String getTipoCriteria() {
		return tipoCriteria;
	}

	public void setTipoCriteria(String tipoCriteria) {
		this.tipoCriteria = tipoCriteria;
	}

	public String getNombreCriteria() {
		return nombreCriteria;
	}

	public void setNombreCriteria(String nombreCriteria) {
		this.nombreCriteria = nombreCriteria;
	}

	public String getDocIdentidadCriteria() {
		return docIdentidadCriteria;
	}

	public void setDocIdentidadCriteria(String docIdentidadCriteria) {
		this.docIdentidadCriteria = docIdentidadCriteria;
	}

	public String getTlfCriteria() {
		return tlfCriteria;
	}

	public void setTlfCriteria(String tlfCriteria) {
		this.tlfCriteria = tlfCriteria;
	}

	public String getCorreoCriteria() {
		return correoCriteria;
	}

	public void setCorreoCriteria(String correoCriteria) {
		this.correoCriteria = correoCriteria;
	}

	public String getIdIdiomaCriteria() {
		return idIdiomaCriteria;
	}

	public void setIdIdiomaCriteria(String idIdiomaCriteria) {
		this.idIdiomaCriteria = idIdiomaCriteria;
	}

	public Long getIdGestionCriteria() {
		return idGestionCriteria;
	}

	public void setIdGestionCriteria(Long idGestionCriteria) {
		this.idGestionCriteria = idGestionCriteria;
	}

}
