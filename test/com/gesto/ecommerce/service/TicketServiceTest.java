package com.gesto.ecommerce.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gesto.ecommerce.model.Ticket;
import com.gesto.ecommerce.service.impl.TicketServiceImpl;
import com.gesto.ecommerce.util.ToStringUtil;

public class TicketServiceTest {
	private static Logger logger = LogManager.getLogger(TicketServiceTest.class.getName());
	private TicketService ticketService = null;

	public TicketServiceTest() {
		ticketService = new TicketServiceImpl();
	}

	protected void testFindByEmpleado() {

		if (logger.isDebugEnabled())
			logger.debug("Testing FindByEmpleado ...");
		int pageSize = 2;

		Long idEmpleado = 1L;

		try {

			List<Ticket> results = null;
			int startIndex = 1;
			int total = 0;

			do {
				results = ticketService.findByEmpleado(idEmpleado);
				if (results.size() > 0) {
					if (logger.isDebugEnabled())
						logger.debug("Page [" + startIndex + " - " + (startIndex + results.size() - 1) + "] : ");
					for (Ticket t : results) {
						total++;
						if (logger.isDebugEnabled())
							logger.debug("Result " + total + ": " + ToStringUtil.toString(t));
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
			logger.debug("Test FindByEmpleado finished.\n");
	}

	protected void testFindByGestion() {
		if (logger.isDebugEnabled())
			logger.debug("Testing FindByGestion ...");
		int pageSize = 2;

		Long idGestion = 1L;

		try {

			List<Ticket> results = null;
			int startIndex = 1;
			int total = 0;

			do {
				results = ticketService.findByGestion(idGestion);
				if (results.size() > 0) {
					if (logger.isDebugEnabled())
						logger.debug("Page [" + startIndex + " - " + (startIndex + results.size() - 1) + "] : ");
					for (Ticket t : results) {
						total++;
						if (logger.isDebugEnabled())
							logger.debug("Result " + total + ": " + ToStringUtil.toString(t));
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
		if (logger.isDebugEnabled())
			logger.debug("Test FindByGestion finished.\n");
	}

	protected void testCreate() {
		if (logger.isDebugEnabled())
			logger.debug("Testing create ...");
		try {
			Ticket t = new Ticket();
			t.setIdGestion(1L);
			t.setTipoTicket("Modificar reserva");
			t.setComentario("Modificamos reservas en paralelo");
			t.setFechaInicio(new Date());
			t.setTiempoTotal(260L);
			t.setIdEmpleado(1L);
			t.setExtDepartamento(45L);
			t.setIdContacto(3L);

			t = ticketService.create(t);

			if (logger.isDebugEnabled())
				logger.debug("Created: " + ToStringUtil.toString(t));

		} catch (Throwable t) {
			t.printStackTrace();
		}
		if (logger.isDebugEnabled())
			logger.debug("Test created finished.\n");
	}

	public static void main(String args[]) {
		TicketServiceTest test = new TicketServiceTest();
		//test.testFindByEmpleado();
		//test.testFindByGestion();
		test.testCreate();
	}

}
