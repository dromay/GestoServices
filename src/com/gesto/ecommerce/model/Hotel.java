package com.gesto.ecommerce.model;


public class Hotel {
	
	private String nombre_hotel = null;
	private String descripcion_hotel = null;
	private String fotos = null;
	private String website = null;
	
	public String getNombre_hotel() {
		return nombre_hotel;
	}
	
	public void setNombre_hotel(String nombre_hotel) {
		this.nombre_hotel = nombre_hotel;
	}
	
	public String getDescripcion_hotel() {
		return descripcion_hotel;
	}
	
	public void setDescripcion_hotel(String descripcion_hotel) {
		this.descripcion_hotel = descripcion_hotel;
	}
	
	public String getFotos() {
		return fotos;
	}
	
	public void setFotos(String fotos) {
		this.fotos = fotos;
	}
	
	public String getWebsite() {
		return website;
	}
	
	public void setWebsite(String website) {
		this.website = website;
	}
	
}
