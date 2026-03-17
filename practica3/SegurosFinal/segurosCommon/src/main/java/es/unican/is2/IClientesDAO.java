package es.unican.is2;

import java.util.List;



/**
 * Interfaz DAO para vehiculos
 */
public interface IClientesDAO  {
	
	/** 
	 * Persite un nuevo Cliente
	 * @param c Cliente a persistir
	 * @return El cliente una vez persistido
	 *         null si no se puede persistir porque ya existe
	 * @throws DataAccessException si se produce un error 
	 * en el acceso a la base de datos
	 */
	public Cliente creaCliente(Cliente c) throws DataAccessException;
	
	/**
	 * Retorna el cliente con el dni indicado
	 * @param dni 
	 * @return El cliente
	 * 		   null si no se encuentra
	 * @throws DataAccessException si se produce un error 
	 * en el acceso a la base de datos
	 */
	public Cliente cliente(String dni) throws DataAccessException;
	
	/**
	 * Actualiza el estado del cliente
	 * @param nuevo Nuevo estado del cliente
	 * @return El cliente actualizado
	 * @throws DataAccessException si se produce un error 
	 * en el acceso a la base de datos
	 */
	public Cliente actualizaCliente(Cliente nuevo) throws DataAccessException;
	
	/**
	 * Elimina el cliente con el dni indicado
	 * @param dni
	 * @return El cliente eliminado
	 *         null si no se encuentra el cliente
	 * @throws DataAccessException si se produce un error 
	 * en el acceso a la base de datos
	 */
	public Cliente eliminaCliente(String dni) throws DataAccessException;
	
	/**
	 * Retorna la lista completa de clientes
	 * @return Lista de clientes
	 *         Lista vacia si no hay ninguno
	* @throws DataAccessException si se produce un error 
	 * en el acceso a la base de datos
	 */
	public List<Cliente> clientes() throws DataAccessException;
	
}

