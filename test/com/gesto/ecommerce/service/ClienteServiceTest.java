package com.gesto.ecommerce.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gesto.ecommerce.model.Cliente;
import com.gesto.ecommerce.service.impl.ClienteServiceImpl;
import com.gesto.ecommerce.util.ToStringUtil;

class ClienteServiceTest {
	private ClienteService clienteService = null;
	private static Logger logger = LogManager.getLogger(ClienteServiceTest.class.getName());

	public ClienteServiceTest() {
		clienteService = new ClienteServiceImpl();
	}

	protected void testFindById() {
		if (logger.isDebugEnabled())
			logger.debug("Testing findById ...");
		Long id = 1L;

		try {
			Cliente t = clienteService.findById(id);
			if (logger.isDebugEnabled())
				logger.debug("Found: " + ToStringUtil.toString(t));

		} catch (Throwable t) {
			t.printStackTrace();
		}
		if (logger.isDebugEnabled())
			logger.debug("Test testFindById finished.\n");
	}

	protected void testExists() {
		if (logger.isDebugEnabled())
			logger.debug("Testing exists ...");
		Long id = 1L;

		try {
			Boolean exists = clienteService.exists(id);
			if (logger.isDebugEnabled())
				logger.debug("Exists: " + id + " -> " + exists);

		} catch (Throwable t) {
			t.printStackTrace();
		}
		if (logger.isDebugEnabled())
			logger.debug("Test exists finished.\n");
	}

	protected void testFindByCriteria() {
		if (logger.isDebugEnabled())
			logger.debug("Testing FindByCriteria ...");
		int pageSize = 2;

		ClienteCriteria c = new ClienteCriteria();
		c.setIdIdiomaCriteria("Es");

		try {

			List<Cliente> results = null;
			int startIndex = 1;
			int total = 0;

			do {
				results = clienteService.findByCriteria(c, startIndex, pageSize);
				if (results.size() > 0) {
					logger.debug("Page [" + startIndex + " - " + (startIndex + results.size() - 1) + "] : ");
					for (Cliente t : results) {
						total++;
						logger.debug("Result " + total + ": " + ToStringUtil.toString(t));
					}
					startIndex = startIndex + pageSize;
				}

			} while (results.size() == pageSize);

			logger.debug("Found " + total + " results.");

		} catch (Throwable t) {
			t.printStackTrace();
		}
		logger.debug("Test FindByCriteria finished.\n");
	}

	public static void main(String args[]) {
		ClienteServiceTest test = new ClienteServiceTest();
		test.testFindById();
		test.testExists();
		test.testFindByCriteria();

	}

}