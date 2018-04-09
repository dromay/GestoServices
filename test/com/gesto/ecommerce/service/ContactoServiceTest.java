package com.gesto.ecommerce.service;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gesto.ecommerce.dao.impl.ClienteDAOImpl;
import com.gesto.ecommerce.model.Contacto;
import com.gesto.ecommerce.service.impl.ContactoServiceImpl;
import com.gesto.ecommerce.util.ToStringUtil;

class ContactoServiceTest {
	private ContactoService contactoService = null;

	public ContactoServiceTest() {
		contactoService = new ContactoServiceImpl();
	}
	
	private static Logger logger = LogManager.getLogger(ContactoServiceTest.class.getName());

	protected void testFindByCliente() {
		if (logger.isDebugEnabled())
			logger.debug("Testing FindByCliente ...");
		int pageSize = 2;

		Long clienteId = 1L;

		try {

			List<Contacto> results = null;
			int startIndex = 1;
			int total = 0;

			do {
				results = contactoService.findByCliente(clienteId);
				if (results.size() > 0) {
					if (logger.isDebugEnabled())
						logger.debug("Page [" + startIndex + " - " + (startIndex + results.size() - 1) + "] : ");
					for (Contacto co : results) {
						total++;
						if (logger.isDebugEnabled())
							logger.debug("Result " + total + ": " + ToStringUtil.toString(co));
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

	protected void testFindByGestion() {
		if (logger.isDebugEnabled()) {
			logger.debug("Testing FindByGestion ...");
		}
		int pageSize = 2;

		Long idGestion = 1L;

		try {

			List<Contacto> results = null;
			int startIndex = 1;
			int total = 0;

			do {
				results = contactoService.findByGestion(idGestion, total, total);
				if (results.size() > 0) {
					if (logger.isDebugEnabled()) {
						logger.debug("Page [" + startIndex + " - " + (startIndex + results.size() - 1) + "] : ");
					}
					for (Contacto co : results) {
						total++;
						if (logger.isDebugEnabled()) {
							logger.debug("Result " + total + ": " + ToStringUtil.toString(co));
						}
					}
					startIndex = startIndex + pageSize;
				}

			} while (results.size() == pageSize);
			
			if (logger.isDebugEnabled()) {
				logger.debug("Found " + total + " results.");
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
		if (logger.isDebugEnabled()) {
			logger.debug("Test FindByGestion finished.\n");
		}
	}

	public static void main(String args[]) {
		ContactoServiceTest test = new ContactoServiceTest();
		//test.testFindByGestion();
		test.testFindByCliente();

	}

}