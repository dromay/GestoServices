package com.gesto.ecommerce.model;

import java.util.ArrayList;
import java.util.List;

public class Contacto {

	private Long idContacto = null;
	private String contactoNombre = null;
	private String contactoApellido = null;
	private String contactoCorreo = null;
	private String contactoTlf = null;

	private List<Idioma> idiomas = null;

	public Contacto() {
		idiomas = new ArrayList<Idioma>();
	}

	public Long getIdContacto() {
		return idContacto;
	}

	public void setIdContacto(Long idContacto) {
		this.idContacto = idContacto;
	}

	public String getClienteDescripcion() {
		return contactoNombre;
	}

	public void setClienteDescripcion(String contactoNombre) {
		this.contactoNombre = contactoNombre;
	}

	public String getContactoNombre() {
		return contactoNombre;
	}

	public void setContactoNombre(String contactoNombre) {
		this.contactoNombre = contactoNombre;
	}

	public String getContactoApellido() {
		return contactoApellido;
	}

	public void setContactoApellido(String contactoApellido) {
		this.contactoApellido = contactoApellido;
	}

	public String getContactoCorreo() {
		return contactoCorreo;
	}

	public void setContactoCorreo(String contactoCorreo) {
		this.contactoCorreo = contactoCorreo;
	}

	public String getContactoTlf() {
		return contactoTlf;
	}

	public void setContactoTlf(String contactoTlf) {
		this.contactoTlf = contactoTlf;
	}

	public List<Idioma> getIdiomas() {
		return idiomas;
	}

	public void setIdiomas(List<Idioma> idiomas) {
		this.idiomas = idiomas;
	}

}
