package com.gesto.ecommerce.model;

import java.util.ArrayList;
import java.util.List;

public class Empresa {

	private Long idEmpresa = null;
	private String descripcion = null;

	private List<Gestion> gestiones = null;
	private List<Contacto> contactos = null;

	public Empresa() {
		gestiones = new ArrayList<Gestion>();
		contactos = new ArrayList<Contacto>();
	}

	public Long getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Long idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<Gestion> getGestiones() {
		return gestiones;
	}

	public void setGestiones(List<Gestion> gestiones) {
		this.gestiones = gestiones;
	}

	public List<Contacto> getContactos() {
		return contactos;
	}

	public void setContactos(List<Contacto> contactos) {
		this.contactos = contactos;
	}

}