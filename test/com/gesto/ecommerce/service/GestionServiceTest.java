package com.gesto.ecommerce.service;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gesto.ecommerce.model.Gestion;
import com.gesto.ecommerce.model.Ticket;
import com.gesto.ecommerce.service.impl.GestionServiceImpl;
import com.gesto.ecommerce.util.ToStringUtil;

class GestionServiceTest {
	private GestionService gestionService = null;

	public GestionServiceTest() {
		gestionService = new GestionServiceImpl();
	}

	private static Logger logger = LogManager.getLogger(GestionServiceTest.class.getName());

	protected void testFindByCliente() {
		if (logger.isDebugEnabled())
			logger.debug("Testing FindByCliente ...");
		int pageSize = 2;

		Long clienteId = 1L;

		try {

			List<Gestion> results = null;
			int startIndex = 1;
			int total = 0;

			do {
				results = gestionService.findByCliente(clienteId, startIndex, pageSize);
				if (results.size() > 0) {
					if (logger.isDebugEnabled())
						logger.debug("Page [" + startIndex + " - " + (startIndex + results.size() - 1) + "] : ");
					for (Gestion g : results) {
						total++;
						if (logger.isDebugEnabled())
							logger.debug("Result " + total + ": " + ToStringUtil.toString(g));
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
			logger.debug("Test FindByCliente finished.\n");
	}

	protected void testFindByEmpleado() {
		if (logger.isDebugEnabled()) {
			logger.debug("Testing FindByEmpleado ...");
		}
		int pageSize = 2;

		Long id = 1L;

		try {

			List<Gestion> results = null;
			int startIndex = 1;
			int total = 0;

			do {
				results = gestionService.findByEmpleado(id, startIndex, pageSize);
				if (results.size() > 0) {
					if (logger.isDebugEnabled()) {
						logger.debug("Page [" + startIndex + " - " + (startIndex + results.size() - 1) + "] : ");
					}
					for (Gestion g : results) {
						total++;
						if (logger.isDebugEnabled()) {
							logger.debug("Result " + total + ": " + ToStringUtil.toString(g));
						}
					}
					startIndex = startIndex + pageSize;
				}

			} while (results.size() == pageSize);

			if (logger.isDebugEnabled()) {
				logger.debug("Found " + total + " results.");
			}
		} catch (Throwable t) {
			logger.error(t.getMessage(), t);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("Test FindByEmpleado finished.\n");
		}
	}

	protected void testCreate() {
		if (logger.isDebugEnabled())
			logger.debug("Testing create ...");
		try {
			Ticket t = new Ticket();
			Gestion g = new Gestion();

			g.setIdCliente(1L);
			g.setIdEmpleado(1L);
			g.setIdEmpresa(1L);

			t.setTipoTicket("Modificar reserva");
			t.setComentario("Modificamos reservas en paralelo");
			t.setFechaInicio(new Date());
			t.setTiempoTotal(260L);
			t.setIdEmpleado(1L);
			t.setExtDepartamento(45L);
			t.setIdContacto(3L);

			g = gestionService.create(g, t);

			if (logger.isDebugEnabled())
				logger.debug("Created gestion: " + ToStringUtil.toString(g));
			logger.debug("Created ticket: " + ToStringUtil.toString(t));

		} catch (Throwable t) {
			t.printStackTrace();
		}
		if (logger.isDebugEnabled())
			logger.debug("Test created finished.\n");
	}

	public static void main(String args[]) {
		GestionServiceTest test = new GestionServiceTest();
		test.testFindByEmpleado();
		test.testFindByCliente();
		test.testCreate();

	}

}