package cl.bhp.middleware.bean;

import cl.bhp.middleware.dao.DataAccessObject;
import cl.bhp.middleware.exception.ServiceException;
import org.apache.camel.Exchange;
import org.json.JSONObject;

/**
 * Clase que permite la realización de forward de peticiones generadas desde
 * una ruta camel, busca realizar validaciones previas antes de ir por el 
 * negocio en búsqueda de resultados
 * @author Luis Oliveros
 */

public class bean {

	/**
	 * Genera un reclamo de un empleado en el EspoCRM. mediante la busqueda con el parametro: datos,
	 * y devuelve una confirmación de haber creado el reclamo
	 * @param ex
	 * @return JSONObject
	 * @throws ServiceException
	 */
	
	@SuppressWarnings("unused")
	public JSONObject beneficio(Exchange ex)  throws ServiceException {
		JSONObject datos;
		datos = new JSONObject(ex.getIn().getBody(String.class));
		
		if(datos == null) {
			throw new ServiceException("400");
		}	
		
		return new DataAccessObject().solicitarbeneficio(datos);
	}
	
}
