package es.unican.is2;




/**
 * Interfaz de negocio para gestionar los 
 * clientes de la empresa de seguros
 */
public interface IGestionClientes {
	
	/**
	 * Persiste un nuevo cliente
	 * @param c Cliente que desea persistir
	 * @return El cliente persitido
	 * 		   null si no se persiste porque ya existe
     *  @throws OperacionNoValida si ya existe un cliente con el mismo DNI 
	  * @throws DataAccessException si se produce un error 
	 * en el acceso a la base de datos
	 */
	public Cliente nuevoCliente(Cliente c) throws DataAccessException,OperacionNoValida;
	
	/**
	 * Elimina el cliente cuyo dni se indica
	 * @param dni DNI del cliente que se quiere eliminar
	 * @return El cliente eliminado
	 * 		   null si no se elimina porque no se encuentra 
	 * @throws OperacionNoValida si el cliente existe 
	 *         pero tiene seguros a su nombre o si no existe el cliente
	 * @throws DataAccessException si se produce un error 
	 * en el acceso a la base de datos
	 */
	public Cliente bajaCliente(String dni) throws OperacionNoValida,DataAccessException;
		
	
}

