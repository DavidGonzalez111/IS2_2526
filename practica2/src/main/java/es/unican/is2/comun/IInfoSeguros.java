package es.unican.is2.comun;

//he cambiado los nomrbes a consultaCliente y consultaSeguro para que sea igual al anexo
//y añadido OperacionNoValida tambien

/**
 * Interfaz con metodos de consulta de informacion
 * de la empresa de seguros
 */
public interface IInfoSeguros {
	
	/**
	 * Retorna el cliente cuyo dni se indica
	 * @param dni DNI del cliente buscado
	 * @return El cliente cuyo dni coincide
	 * 		   null en caso de que no exista
     * @throws OperacionNoValida si el cliente no existe
	 * @throws DataAccessException si se produce un error 
	 * en el acceso a la base de datos
	 */
	public Cliente consultaCliente(String dni) throws DataAccessException, OperacionNoValida; 
	
	/**
	 * Retorna el seguro cuya matricula se indica
	 * @param matricula Identificador del seguro
	 * @return El seguro indicado
	 * 	       null si no existe
     * @throws OperacionNoValida si el seguro no existe
	* @throws DataAccessException si se produce un error 
	 * en el acceso a la base de datos
	 */
	public Seguro consultaSeguro(String matricula) throws DataAccessException, OperacionNoValida;

}

