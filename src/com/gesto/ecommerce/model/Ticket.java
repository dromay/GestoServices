package com.gesto.ecommerce.model;

import java.util.Date;

public class Ticket {

	private Long idTicket = null;
	private Long idGestion = null;
	private String tipoTicket = null;
	private Date tiempoTotal = null;
	private String comentario = null;
	private Date fechaInicio = null;
	private Date fechaFin = null;
	private Long idEmpleado = null;
	private Long extDepartamento = null;
	private Long idContacto = null;

	public Long getIdTicket() {
		return idTicket;
	}

	public void setIdTicket(Long idTicket) {
		this.idTicket = idTicket;
	}

	public Long getIdGestion() {
		return idGestion;
	}

	public void setIdGestion(Long idGestion) {
		this.idGestion = idGestion;
	}

	public String getTipoTicket() {
		return tipoTicket;
	}

	public void setTipoTicket(String tipoTicket) {
		this.tipoTicket = tipoTicket;
	}

	public Date getTiempoTotal() {
		return tiempoTotal;
	}

	public void setTiempoTotal(Date tiempoTotal) {
		this.tiempoTotal = tiempoTotal;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Long getIdEmpleado() {
		return idEmpleado;
	}

	public void setIdEmpleado(Long idEmpleado) {
		this.idEmpleado = idEmpleado;
	}

	public Long getExtDepartamento() {
		return extDepartamento;
	}

	public void setExtDepartamento(Long extDepartamento) {
		this.extDepartamento = extDepartamento;
	}

	public Long getIdContacto() {
		return idContacto;
	}

	public void setIdContacto(Long idContacto) {
		this.idContacto = idContacto;
	}

}
