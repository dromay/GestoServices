package com.gesto.ecommerce.model;

public class Contacto {

	private Long contactoCod = null;
	private String contactoNombre = null;
	private String contactoApellido = null;
	private String contactoCorreo = null;
	private String contactoTlf = null;

	public Contacto() {

	}

	public Long getContactoCod() {
		return contactoCod;
	}

	public void setContactoCod(Long contactoCod) {
		this.contactoCod = contactoCod;
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

}
