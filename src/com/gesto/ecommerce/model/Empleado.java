package com.gesto.ecommerce.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class Empleado {
	private Long id = null;
	private String usuario = null;
	private String nombre = null;
	private String apellido = null;
	private Long extDepartamento = null;
	private Long ext = null;
	private Long supervisor = null;
	private Date fechaBaja = null;

	private List<Ticket> tickets = null;
	private List<Gestion> gestiones = null;
	private List<Idioma> idiomas = null;

	public Empleado() {
		gestiones = new ArrayList<Gestion>();
		tickets = new ArrayList<Ticket>();
		idiomas=new ArrayList<Idioma>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public Long getExtDepartamento() {
		return extDepartamento;
	}

	public void setExtDepartamento(Long extDepartamento) {
		this.extDepartamento = extDepartamento;
	}

	public Long getExt() {
		return ext;
	}

	public void setExt(Long ext) {
		this.ext = ext;
	}

	public Long getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(Long supervisor) {
		this.supervisor = supervisor;
	}

	public Date getFechaBaja() {
		return fechaBaja;
	}

	public void setFechaBaja(Date fecha_baja) {
		this.fechaBaja = fecha_baja;
	}

	public List<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}

	public List<Gestion> getGestiones() {
		return gestiones;
	}

	public void setGestiones(List<Gestion> gestiones) {
		this.gestiones = gestiones;
	}

	public List<Idioma> getIdiomas() {
		return idiomas;
	}

	public void setIdiomas(List<Idioma> idiomas) {
		this.idiomas = idiomas;
	}
	
	

}
