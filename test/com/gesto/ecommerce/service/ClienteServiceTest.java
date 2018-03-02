package com.gesto.ecommerce.service;

import java.util.List;

import com.gesto.ecommerce.model.Cliente;
import com.gesto.ecommerce.model.Contacto;
import com.gesto.ecommerce.model.Gestion;
import com.gesto.ecommerce.model.Idioma;
import com.gesto.ecommerce.service.impl.ClienteServiceImpl;
import com.gesto.ecommerce.util.ToStringUtil;

class ClienteServiceTest {
	private ClienteService clienteService = null;
	private List<Contacto> contactos;
	private List<Idioma> idiomas;
	private List<Gestion> gestiones;
	
	public ClienteServiceTest() {
		clienteService = new ClienteServiceImpl();
	}
	
	protected void testFindById() {
		System.out.println("Testing findById ...");
				Long id = 1L;
		
		try {			
			Cliente t = clienteService.findById(id);			
			System.out.println("Found: "+ToStringUtil.toString(t));
			
		} catch (Throwable t) {
			t.printStackTrace();
		}
		
		System.out.println("Test testFindById finished.\n");		
	}
	
	protected void testExists() {
		System.out.println("Testing exists ...");
			Long id = 1L;
		
		try {			
			Boolean exists = clienteService.exists(id);			
			System.out.println("Exists: "+id+" -> "+exists);
			
		} catch (Throwable t) {
			t.printStackTrace();
		}
		
		System.out.println("Test exists finished.\n");		
	}
	
	protected void testFindAll() {
		System.out.println("Testing findAll ...");
		int pageSize = 10; 	
		
		try {

			List<Cliente> results = null;
			int startIndex = 1; 
			int total = 0;
			
			do {
				results = clienteService.findAll(startIndex, pageSize);
				if (results.size()>0) {
					System.out.println("Page ["+startIndex+" - "+(startIndex+results.size()-1)+"] : ");				
					for (Cliente c: results) {
						total++;
						System.out.println("Result "+total+": "+ToStringUtil.toString(c));
					}
					startIndex = startIndex + pageSize;
				}
				
			} while (results.size()==pageSize);
			
			System.out.println("Found "+total+" results.");
						
		} catch (Throwable c) {
			c.printStackTrace();
		}
		System.out.println("Test testFindAll finished.\n");
	}
	
	
	protected void testFindByCriteria() {
		System.out.println("Testing FindByCriteria ...");
		int pageSize = 2;
		
		ClienteCriteria c = new ClienteCriteria();
		c.setTipo("PARTICULAR");
		
		
		
		try {

			List<Cliente> results = null;
			int startIndex = 1; 
			int total = 0;
			
			do {
				results = clienteService.findByCriteria(c, startIndex, pageSize);
				if (results.size()>0) {
					System.out.println("Page ["+startIndex+" - "+(startIndex+results.size()-1)+"] : ");				
					for (Cliente t: results) {
						total++;
						System.out.println("Result "+total+": "+ToStringUtil.toString(t));
					}
					startIndex = startIndex + pageSize;
				}
				
			} while (results.size()==pageSize);
			
			System.out.println("Found "+total+" results.");
						
		} catch (Throwable t) {
			t.printStackTrace();
		}
		System.out.println("Test FindByCriteria finished.\n");
	}
	
	
	public static void main(String args[]) {
		ClienteServiceTest test = new ClienteServiceTest();
		test.testFindById();	
		test.testExists();
		test.testFindAll();
		test.testFindByCriteria();

	}
	
}