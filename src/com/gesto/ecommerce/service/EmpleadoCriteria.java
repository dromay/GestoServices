package com.gesto.ecommerce.service;

import com.gesto.ecommerce.model.Empleado;

public class EmpleadoCriteria extends Empleado {
	private Long idTicketCriteria = null;
	private String idIdiomaCriteria = null;
	private Long idGestionCriteria = null;

	public EmpleadoCriteria() {

	}

	public Long getIdTicketCriteria() {
		return idTicketCriteria;
	}

	public void setIdTicketCriteria(Long idTicketCriteria) {
		this.idTicketCriteria = idTicketCriteria;
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
