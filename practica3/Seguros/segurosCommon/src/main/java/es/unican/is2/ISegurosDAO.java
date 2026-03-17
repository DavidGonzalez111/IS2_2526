package es.unican.is2;

import java.util.List;

/**
 * Interfaz DAO para los seguros
 */
public interface ISegurosDAO  {
	
	/** 
	 * Persite un nuevo seguro
	 * @param v Seguro a persistir
	 * @return El seguro una vez persistido
	 *         null si no se persiste el seguro porque ya existe
	 * @throws DataAccessException si se produce un error 
	 * en el acceso a la base de datos
	 */
	public Seguro creaSeguro(Seguro v) throws DataAccessException;
	
	/**
	 * Elimina el seguro correspondiente al identificador indicado
	 * @param id
	 * @return El seguro eliminado
	 *         null si no se encuentra el seguro
	 * @throws DataAccessException si se produce un error 
	 * en el acceso a la base de datos
	 */
	public Seguro eliminaSeguro(long id) throws DataAccessException;
	
	/**
	 * Actualiza el estado del seguro
	 * @param nuevo Nuevo estado del seguro
	 * @return El seguro actualizado
	 * @throws DataAccessException si se produce un error 
	 * en el acceso a la base de datos
	 */
	public Seguro actualizaSeguro(Seguro nuevo)throws DataAccessException;
	
	/**
	 * Retorna el seguro correspondiente al identificador indicado
	 * @param id
	 * @return El seguro
	 * 		   null si no se encuentra
	 * @throws DataAccessException si se produce un error 
	 * en el acceso a la base de datos
	 */
	public Seguro seguro(long id) throws DataAccessException;


	/**
	 * Retorna el seguro correspondiente a la matricula indicada
	 * @param matricula
	 * @return El seguro
	 * 		   null si no se encuentra
	 * @throws DataAccessException si se produce un error 
	 * en el acceso a la base de datos
	 */
	public Seguro seguroPorMatricula(String matricula) throws DataAccessException;
	
	/**
	 * Retorna la lista completa de seguros
	 * @return La lista de seguros
	 *         Lista vacia si no hay ninguno
	 * @throws DataAccessException si se produce un error 
	 * en el acceso a la base de datos
	 */
	public List<Seguro> seguros() throws DataAccessException;
}

