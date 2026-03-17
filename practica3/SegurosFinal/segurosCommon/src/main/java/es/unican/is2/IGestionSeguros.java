package es.unican.is2;


/**
 * Interfaz de negocio para gestionar los
 * seguros de la empresa de seguros
 */
public interface IGestionSeguros {
	
	/**
	 * Agrega un nuevo seguro al cliente cuyo dni se indica.
	 * @param s Seguro que desea agregar
	 * @param dni DNI del cliente
	 * @return El seguro agregado
	 * 		   null si no se agrega porque no se encuentra el cliente
	 * @throws OperacionNoValida si el seguro ya existe
	 * @throws DataAccessException si se produce un error 
	 * en el acceso a la base de datos
	 */
	public Seguro nuevoSeguro(Seguro s, String dni) throws OperacionNoValida, DataAccessException;
	
	/**
	 * Elimina el seguro cuya matricula se indica y 
	 * que pertenece al cliente cuyo dni se indica
	 * @param matricula Identificador del seguro a eliminar
	 * @param dni DNI del propietario del seguro
 	 * @return El seguro eliminado
 	 *         null si el seguro o el cliente no existen
 	 * @throws OperacionNoValida si el seguro no pertenece al dni indicado
	 * @throws DataAccessException si se produce un error 
	 * en el acceso a la base de datos
	 */
	public Seguro bajaSeguro(String matricula, String dni) throws OperacionNoValida, DataAccessException;

	/**
	 * Agrega o modifica el conductor adicional al seguro cuya matricula se indica
	 * @param matricula Identificador del seguro
	 * @param conductor Nombre del conductor adicional a agregar
 	 * @return El seguro modificado
 	 *         null si el seguro no existe
	 * @throws DataAccessException si se produce un error 
	 * en el acceso a la base de datos
	 */
	public Seguro anhadeConductorAdicional(String matricula, String conductor) throws DataAccessException;

}

