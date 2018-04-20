package com.gesto.ecommerce.service;

import com.gesto.ecommerce.exceptions.DataException;
import com.gesto.ecommerce.exceptions.InstanceNotFoundException;
import com.gesto.ecommerce.model.Cliente;
import com.gesto.ecommerce.model.Contacto;
import com.gesto.ecommerce.model.Empleado;
import com.gesto.ecommerce.model.Empresa;

public interface IncomingCallService {
	
	public Contacto findCall(Empleado e, String locale)
			 throws InstanceNotFoundException, DataException;
	
	public Empresa findReceiverCompany(String locale)
			 throws InstanceNotFoundException, DataException;
	
	public Cliente findCallClient(String tlf, String locale)
			 throws InstanceNotFoundException, DataException;

}
