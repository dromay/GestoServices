package com.gesto.ecommerce.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gesto.ecommerce.dao.ClienteDAO;
import com.gesto.ecommerce.dao.ContactoDAO;
import com.gesto.ecommerce.dao.EmpresaDAO;
import com.gesto.ecommerce.dao.impl.ClienteDAOImpl;
import com.gesto.ecommerce.dao.impl.ContactoDAOImpl;
import com.gesto.ecommerce.dao.impl.EmpresaDAOImpl;
import com.gesto.ecommerce.dao.util.ConnectionManager;
import com.gesto.ecommerce.dao.util.JDBCUtils;
import com.gesto.ecommerce.exceptions.DataException;
import com.gesto.ecommerce.exceptions.InstanceNotFoundException;
import com.gesto.ecommerce.model.Cliente;
import com.gesto.ecommerce.model.Contacto;
import com.gesto.ecommerce.model.Empleado;
import com.gesto.ecommerce.model.Empresa;
import com.gesto.ecommerce.model.Idioma;
import com.gesto.ecommerce.service.ClienteCriteria;
import com.gesto.ecommerce.service.IncomingCallService;

public class IncomingCallServiceImpl implements IncomingCallService {

	private static Logger logger = LogManager.getLogger(IncomingCallServiceImpl.class.getName());

	private ContactoDAO contactoDAO = null;
	private EmpresaDAO empresaDAO = null;
	private ClienteDAO clienteDAO = null;

	public IncomingCallServiceImpl() {
		contactoDAO = new ContactoDAOImpl();
		empresaDAO = new EmpresaDAOImpl();
		clienteDAO = new ClienteDAOImpl();

	}

	@Override
	public Contacto findCall(Empleado em, String locale) throws InstanceNotFoundException, DataException {
		Connection connection = null;

		try {

			connection = ConnectionManager.getConnection();
			connection.setAutoCommit(true);

			Boolean match = false;
			Contacto contacto = null;
			while (match == false) {
				contacto = contactoDAO.findById(connection, Math.round(Math.random() * 10) + 1, locale);
				for (Idioma i : contacto.getIdiomas()) {
					for (Idioma ie : em.getIdiomas()) {
						if (logger.isDebugEnabled() && match == false) {
							logger.debug("El idioma contacto " + i.getDescripcion().trim() + " idioma empleado "
									+ ie.getDescripcion().trim());
						}
						if (ie.getIdIdioma().compareTo(i.getIdIdioma())==0) {
							match = true;
							if (logger.isDebugEnabled() && match == true) {
								logger.debug("Match " + match);
							}
						}
					}
				}
				if (logger.isDebugEnabled() && match == false) {
					logger.debug("El contacto " + contacto.getContactoNombre() + " no puede ser atendido por "
							+ em.getNombre());
				}
				if (logger.isDebugEnabled() && match == true) {
					logger.debug("El contacto " + contacto.getContactoNombre() + " puede ser atendido por "
							+ em.getNombre());
				}
			} 

			return contacto;

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeConnection(connection);
		}
	}

	@Override
	public Empresa findReceiverCompany(String locale) throws InstanceNotFoundException, DataException {

		Connection connection = null;

		try {

			connection = ConnectionManager.getConnection();
			connection.setAutoCommit(true);

			Contacto contactoEmpresa = contactoDAO.findById(connection, Math.round(Math.random() * 5) + 12, locale);
			if (logger.isDebugEnabled()) {
				logger.debug("Result :" + contactoEmpresa.getIdContacto());
			}

			Empresa empresa = empresaDAO.findByTelefono(connection, contactoEmpresa.getContactoTlf());

			return empresa;

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeConnection(connection);
		}
	}

	@Override
	public Cliente findCallClient(String tlf, String locale) throws InstanceNotFoundException, DataException {

		Connection connection = null;

		try {

			connection = ConnectionManager.getConnection();
			connection.setAutoCommit(true);

			ClienteCriteria criteria = new ClienteCriteria();
			criteria.setTlfCriteria(tlf);

			List<Cliente> clientes = clienteDAO.findByCriteria(connection, criteria, locale, 1, 10);

			Cliente cliente = clientes.get(0);

			return cliente;

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeConnection(connection);
		}

	}

}
