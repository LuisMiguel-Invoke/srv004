package cl.bhp.middleware.dao;

import cl.bhp.middleware.exception.ServiceException;
import cl.bhp.middleware.util.PropertiesUtil;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Clase que permite crear un reclamo en EspoCRM por la API Expuesta
 * @author Luis Oliveros
 */

public class DataAccessObject {
	static PropertiesUtil prop = new PropertiesUtil();
	
	private static final Logger LOGGER = Logger.getLogger(DataAccessObject.class);
	
	/**
	 * Genera reclamo de un empleado
	 * recibe los datos por parametro
	 * @param datos
	 * @return  
	 * @throws ServiceException
	 */
	
	public JSONObject solicitarbeneficio (JSONObject datos) throws ServiceException {
		long init = System.currentTimeMillis();
		JSONObject Json = new JSONObject();
		JSONObject beneficiosId = new JSONObject();
		String URI = prop.getLocalProperties().getProperty("api.espocrm.uri");
		String auth = prop.getLocalProperties().getProperty("api.espocrm.auth");
		try {
			
			HttpResponse<String> responseUser = Unirest.post(URI)
					  .header("Authorization", "Basic "+auth)
					  .header("cache-control", "no-cache")
					  .header("Content-Type", "application/json")
					  .body(datos)
					  .asString();
					 		
			System.out.println(responseUser.getStatus());
			System.out.println(responseUser.getStatusText());
			System.out.println(responseUser.getBody());
			Json = new JSONObject(responseUser.getBody());
			System.out.println(Json.toString());
			beneficiosId.put("beneficiosId", Json.get("beneficiosId"));

			} catch (UnirestException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new ServiceException("400");
			}
		
		LOGGER.info("Tiempo en consulta EspoCRM "+(System.currentTimeMillis() - init)+" ms.");
		return beneficiosId;	
	}
}
