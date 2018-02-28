
package com.gesto.ecommerce.model;

import java.util.ArrayList;
import java.util.List;

public class Gestion {

	private Long idGestion = null;
	private Long idCliente = null;
	private Long idEmpleado = null;
	private Long idEmpresa = null;

	private List<Ticket> tickets = null;
	private List<Contacto> contactos = null;

	public Gestion() {
		tickets = new ArrayList<Ticket>();
		contactos=new ArrayList<Contacto>();
	}

	public Long getIdGestion() {
		return idGestion;
	}

	public void setIdGestion(Long idGestion) {
		this.idGestion = idGestion;
	}

	public Long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}

	public Long getIdEmpleado() {
		return idEmpleado;
	}

	public void setIdEmpleado(Long idEmpleado) {
		this.idEmpleado = idEmpleado;
	}

	public Long getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Long idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public List<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}

	public List<Contacto> getContactos() {
		return contactos;
	}

	public void setContactos(List<Contacto> contactos) {
		this.contactos = contactos;
	}

}
