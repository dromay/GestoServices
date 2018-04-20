package com.gesto.ecommerce.service;

import java.io.IOException;
import java.util.List;

import com.gesto.ecommerce.exceptions.DataException;
import com.gesto.ecommerce.exceptions.InstanceNotFoundException;
import com.gesto.ecommerce.model.Hotel;


public interface XMLService {
	
	public List<Hotel> XMLRequest() 
    		throws InstanceNotFoundException, DataException, IOException;

}