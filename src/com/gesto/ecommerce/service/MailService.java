package com.gesto.ecommerce.service;

import com.gesto.ecommerce.exceptions.MailException;

public interface MailService {
	
	public void sendMail(String subject, String message, String... to)
		throws MailException;

}
