package com.gesto.ecommerce.model;

import java.util.ArrayList;
import java.util.List;

public class Idioma {

	private String idIdioma;
	private String descripcion;

	private List<Cliente> clientes = null;
	private List<Empleado> empleados = null;
	private List<Contacto> contactos = null;

	public Idioma() {
		empleados = new ArrayList<Empleado>();
		clientes = new ArrayList<Cliente>();
		contactos = new ArrayList<Contacto>();
	}

	public String getIdIdioma() {
		return idIdioma;
	}

	public void setIdIdioma(String idIdioma) {
		this.idIdioma = idIdioma;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public List<Empleado> getEmpleados() {
		return empleados;
	}

	public void setEmpleados(List<Empleado> empleados) {
		this.empleados = empleados;
	}

	public List<Contacto> getContactos() {
		return contactos;
	}

	public void setContactos(List<Contacto> contactos) {
		this.contactos = contactos;
	}

}
