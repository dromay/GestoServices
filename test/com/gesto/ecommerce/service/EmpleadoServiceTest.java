package com.gesto.ecommerce.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gesto.ecommerce.model.Empleado;
import com.gesto.ecommerce.service.impl.EmpleadoServiceImpl;
import com.gesto.ecommerce.util.PasswordEncryptionUtil;
import com.gesto.ecommerce.util.ToStringUtil;

class EmpleadoServiceTest {
	private static Logger logger = LogManager.getLogger(EmpleadoServiceTest.class.getName());
	private EmpleadoService empleadoService = null;

	public EmpleadoServiceTest() {
		empleadoService = new EmpleadoServiceImpl();
	}

	protected void testFindById() {
		if (logger.isDebugEnabled())
			logger.debug("Testing findByUsuario ...");

		String usuario = "MGD";
		String locale = "ES";

		try {

			Empleado emp = empleadoService.findByUsuario(usuario, locale);
			if (logger.isDebugEnabled())
				logger.debug("Found: " + ToStringUtil.toString(emp));

		} catch (Throwable e) {
			e.printStackTrace();
		}
		if (logger.isDebugEnabled())
			logger.debug("Test testFindByUsuario finished.\n");
	}

	protected void testFindByCriteria() {
		if (logger.isDebugEnabled())
			logger.debug("Testing FindByCriteria ...");
		int pageSize = 5;

		EmpleadoCriteria criteria = new EmpleadoCriteria();
		criteria.setExt(23L);
		criteria.setNombre("Laura");
		String locale = "ES";

		try {
			
			List<Empleado> results = null;
			int startIndex = 1;
			int total = 0;

			do {
				results = empleadoService.findByCriteria(criteria, locale, startIndex, pageSize);
				if (results.size() > 0) {
					if (logger.isDebugEnabled())
						logger.debug("Page [" + startIndex + " - " + (startIndex + results.size() - 1) + "] : ");
					for (Empleado em : results) {
						total++;
						if (logger.isDebugEnabled())
							logger.debug("Result " + total + ": " + ToStringUtil.toString(em));
					}
					startIndex = startIndex + pageSize;
				}

			} while (results.size() == pageSize);

			if (logger.isDebugEnabled())
				logger.debug("Found " + total + " results.");

		} catch (Throwable t) {
			t.printStackTrace();
		}

		if (logger.isDebugEnabled())
			logger.debug("Test FindByCriteria finished.\n");
	}

	protected void empleadoPasswordCreate() {
		if (logger.isDebugEnabled())
			logger.debug("Testing update ...");
		try {
			int i;
			String locale = "ES";
			// Tomamos como muestra para actualizar el primero que retorne
			// que seria solo uno si el test anterior ha finalizado OK
			List<Empleado> results = empleadoService.findAll(locale);
			if (results.size() < 1) {
				throw new RuntimeException("Unexpected results count from previous tests: " + results.size());
			}
			for (i = 0; i < results.size(); i++) {
				Empleado e = results.get(i);
				if (logger.isDebugEnabled())
					logger.debug("Usuario: " + e.getUsuario());
				String pass = "123abc" + i;
				if (logger.isDebugEnabled())
					logger.debug("Plana: " + pass);
				String resultado = PasswordEncryptionUtil.encryptPassword(pass);
				e.setPassword(resultado);
				empleadoService.updatePassword(e);
				if (logger.isDebugEnabled())
					logger.debug("Encriptada: " + resultado);
				
				e = empleadoService.findByUsuario(e.getUsuario(), locale);

				
				if (logger.isDebugEnabled())
					logger.debug("Updated to: " + e.getPassword());	
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
		if (logger.isDebugEnabled())
			logger.debug("Test update finished.\n");
	}

	public static void main(String args[]) {
		EmpleadoServiceTest test = new EmpleadoServiceTest();
		test.empleadoPasswordCreate();
		//test.testFindByCriteria();

	}
}
