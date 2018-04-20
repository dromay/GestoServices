package com.gesto.ecommerce.dao.util;

import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class XMLRequestManager {

	private static Logger logger = LogManager.getLogger(XMLRequestManager.class.getName());

	private static ResourceBundle dbConfiguration = ResourceBundle.getBundle("XMLRequestConfiguration");

	private static final String CODIGO_AFILIACION = "codigo.afiliacion";
	private static final String CODIGO_SEACC = "codigo.seacc";
	private static final String USUARIO = "usuario";
	private static final String CODIGO_USUARIO = "codigo.usuario";
	private static final String CODIGO_CLAUSULA = "codigo.clausula";
	private static final String URL = "hotusa.url";
	private static final String RUTA_ARCHIVO = "xml.file";


	private static String codigo_afiliacion;
	private static String codigo_seacc;
	private static String usuario;
	private static String codigo_usuario;
	private static String codigo_clausula;
	private static String url;
	private static String ruta_archivo;

	static {

		try {

			codigo_afiliacion = dbConfiguration.getString(CODIGO_AFILIACION);
			codigo_seacc = dbConfiguration.getString(CODIGO_SEACC);
			usuario = dbConfiguration.getString(USUARIO);
			codigo_usuario = dbConfiguration.getString(CODIGO_USUARIO);
			codigo_clausula = dbConfiguration.getString(CODIGO_CLAUSULA);
			url = dbConfiguration.getString(URL);
			ruta_archivo = dbConfiguration.getString(RUTA_ARCHIVO);
			
		} catch (Exception e) {
			logger.fatal(e.getMessage(), e); 
		}

	}

	private XMLRequestManager() {}

	public static String getCodigo_afiliacion() {
		return codigo_afiliacion;
	}

	public static String getCodigo_seacc() {
		return codigo_seacc;
	}

	public static String getUsuario() {
		return usuario;
	}

	public static String getCodigo_usuario() {
		return codigo_usuario;
	}

	public static String getCodigo_clausula() {
		return codigo_clausula;
	}

	public static String getUrl() {
		return url;
	}

	public static String getRuta_archivo() {
		return ruta_archivo;
	}
	
}
