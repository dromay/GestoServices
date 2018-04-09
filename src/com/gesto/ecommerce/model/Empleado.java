package com.gesto.ecommerce.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Empleado {
	private Long id = null;
	private String usuario = null;
	private String password = null;
	private String nombre = null;
	private String apellido = null;
	private Long extDepartamento = null;
	private Long ext = null;
	private Long supervisor = null;
	private Date fechaBaja = null;

	private List<Idioma> idiomas = null;

	public Empleado() {
		idiomas = new ArrayList<Idioma>();
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public List<Idioma> getIdiomas() {
		return idiomas;
	}

	public void setIdiomas(List<Idioma> idiomas) {
		this.idiomas = idiomas;
	}

}
