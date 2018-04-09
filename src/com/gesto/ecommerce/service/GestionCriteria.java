package com.gesto.ecommerce.service;

import com.gesto.ecommerce.model.Gestion;

public class GestionCriteria extends Gestion {
	
	private String criteriaTlf = null;
	private String criteriaNombreCliente = null;
	private String criteriaCodIdentidad = null;
	private String criteriaCorreo = null;
	private String criteriaNombreEmpleado = null;
	private String criteriaApellidoEmpleado = null;
	
	
	public GestionCriteria() {

	}


	public String getCriteriaTlf() {
		return criteriaTlf;
	}


	public void setCriteriaTlf(String criteriaTlf) {
		this.criteriaTlf = criteriaTlf;
	}


	public String getCriteriaNombreCliente() {
		return criteriaNombreCliente;
	}


	public void setCriteriaNombreCliente(String criteriaNombreCliente) {
		this.criteriaNombreCliente = criteriaNombreCliente;
	}


	public String getCriteriaCodIdentidad() {
		return criteriaCodIdentidad;
	}


	public void setCriteriaCodIdentidad(String criteriaCodIdentidad) {
		this.criteriaCodIdentidad = criteriaCodIdentidad;
	}


	public String getCriteriaCorreo() {
		return criteriaCorreo;
	}


	public void setCriteriaCorreo(String criteriaCorreo) {
		this.criteriaCorreo = criteriaCorreo;
	}


	public String getCriteriaNombreEmpleado() {
		return criteriaNombreEmpleado;
	}


	public void setCriteriaNombreEmpleado(String criteriaNombreEmpleado) {
		this.criteriaNombreEmpleado = criteriaNombreEmpleado;
	}


	public String getCriteriaApellidoEmpleado() {
		return criteriaApellidoEmpleado;
	}


	public void setCriteriaApellidoEmpleado(String criteriaApellidoEmpleado) {
		this.criteriaApellidoEmpleado = criteriaApellidoEmpleado;
	}
	
}
