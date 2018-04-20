package com.gesto.ecommerce.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.gesto.ecommerce.dao.util.XMLRequestManager;
import com.gesto.ecommerce.exceptions.DataException;
import com.gesto.ecommerce.exceptions.InstanceNotFoundException;
import com.gesto.ecommerce.model.Hotel;
import com.gesto.ecommerce.service.XMLService;


public class XMLServiceImpl implements XMLService{

		public List<Hotel> XMLRequest()  
				throws InstanceNotFoundException, DataException, IOException{
			
			StringBuffer result = new StringBuffer();
			String xml = new String();
			List<Hotel> hoteles = new ArrayList<Hotel>();

			xml = XMLRequestManager.getRuta_archivo();
			String xmlEncoded = URLEncoder.encode(xml);


			URL url = new URL(XMLRequestManager.getUrl());
			URLConnection connection = url.openConnection();
			connection.setDoOutput(true);

			PrintWriter out2 = new PrintWriter(connection.getOutputStream());

			// Todos los parámetros van precedidos de un signo & excepto el primero.

			// Parámetros de identificacion
			out2.print(XMLRequestManager.getCodigo_afiliacion());
			out2.print(XMLRequestManager.getCodigo_seacc());
			out2.print(XMLRequestManager.getUsuario());
			out2.print(XMLRequestManager.getCodigo_usuario());
			out2.print(XMLRequestManager.getCodigo_clausula());

			// sistema datos xml: datos cargado en memoria previamente
			out2.print("&xml=" + xmlEncoded);

			// al acabar la secuencia de parámetros printar &\n
			out2.print("&\n");
			out2.close();

			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null)
				result.append(inputLine);
			in.close();

			out2.write((String) result.toString());

			//Guardamos el xml en un objecto
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder;
			Document document = null;

			try {
				Hotel hotel = new Hotel();
				
				builder = factory.newDocumentBuilder();
				document = builder.parse(new InputSource(new StringReader(result.toString())));
				
				//Accedemos a los resultados en base a su tag (Ver especificacion de requisitos de Hotusa)
                
				hotel.setDescripcion_hotel(document.getElementsByTagName("desc_hotel").item(0).getFirstChild().getNodeValue());
				hotel.setNombre_hotel(document.getElementsByTagName("nombre_h").item(0).getFirstChild().getNodeValue());
				hotel.setFotos(document.getElementsByTagName("foto").item(0).getFirstChild().getNodeValue());
				hotel.setWebsite(document.getElementsByTagName("web").item(0).getFirstChild().getNodeValue());
				
				hoteles.add(hotel);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return hoteles;
		}

}
