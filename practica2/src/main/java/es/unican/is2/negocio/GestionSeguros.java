package es.unican.is2.negocio;

import es.unican.is2.comun.*;
import es.unican.is2.persistencia.*;

public class GestionSeguros implements IGestionSeguros, IGestionClientes, IInfoSeguros {

    private IClientesDAO clientesDAO;
    private ISegurosDAO segurosDAO;

    public GestionSeguros(IClientesDAO clientesDAO, ISegurosDAO segurosDAO) {
        this.clientesDAO = clientesDAO;
        this.segurosDAO = segurosDAO;
    }


    //GESTION SEGUROS

    @Override
    public Seguro nuevoSeguro(Seguro s, String dni) 
            throws OperacionNoValida, DataAccessException {

        // 1 comprobar que existe el cliente
        Cliente cliente = clientesDAO.cliente(dni);

        if (cliente == null) {
            throw new OperacionNoValida("No existe un cliente con el DNI indicado");
        }

        // 2 comprobar que no existe ya un seguro con esa matrícula
        Seguro seguroExistente = segurosDAO.seguroPorMatricula(s.getMatricula());

        if (seguroExistente != null) {
            throw new OperacionNoValida("Ya existe un seguro para esa matrícula");
        }

        // 3 guardar el seguro
        segurosDAO.creaSeguro(s);

        return s;
    }

    @Override
    public Seguro bajaSeguro(String matricula, String dni)
            throws OperacionNoValida, DataAccessException {

        // 1 comprobar cliente
        Cliente cliente = clientesDAO.cliente(dni);

        if (cliente == null) {
            throw new OperacionNoValida("No existe un cliente con ese DNI");
        }

        // 2 comprobar seguro
        Seguro seguro = segurosDAO.seguroPorMatricula(matricula);

        if (seguro == null) {
            throw new OperacionNoValida("No existe seguro para esa matrícula");
        }

        // 3 comprobar que el seguro pertenece al cliente
        boolean pertenece = false;

        for (Seguro s : cliente.getSeguros()) {
            if (s.getMatricula().equals(matricula)) {
                pertenece = true;
                break;
            }
        }

        if (!pertenece) {
            throw new OperacionNoValida("El seguro no pertenece al cliente indicado");
        }

        // 4 eliminar seguro
        return segurosDAO.eliminaSeguro(seguro.getId());
    }

    
    @Override
    public Seguro anhadeConductorAdicional(String matricula, String conductor)
            throws DataAccessException {

        // 1. Include Consulta Seguro y si no existe, comunicar error (según caso de uso)
        Seguro seguro = consultaSeguro(matricula);

        // 3 modificar conductor adicional
        seguro.setConductorAdicional(conductor);

        // 4 actualizar en la base de datos
        return segurosDAO.actualizaSeguro(seguro);
    }


    //GESTION CLIENTES

    @Override
    public Cliente nuevoCliente(Cliente c) throws DataAccessException, OperacionNoValida  {

        // comprobar si ya existe un cliente con ese DNI
        Cliente existente = clientesDAO.cliente(c.getDni());

        if (existente != null) {
            throw new OperacionNoValida("Ya existe un cliente con ese DNI");
        }

        // persistir cliente
        return clientesDAO.creaCliente(c);
    }

    @Override
    public Cliente bajaCliente(String dni)
            throws OperacionNoValida, DataAccessException {

        // 1 comprobar si existe el cliente
        Cliente cliente = clientesDAO.cliente(dni);

        if (cliente == null) {
            throw new OperacionNoValida("El cliente no existe");
        }

        // 2 comprobar si tiene seguros
        if (!cliente.getSeguros().isEmpty()) {
            throw new OperacionNoValida("El cliente tiene seguros a su nombre");
        }

        // 3 eliminar cliente
        return clientesDAO.eliminaCliente(dni);
    }

    //CONSULTAS

    @Override
    public Cliente consultaCliente(String dni) throws DataAccessException, OperacionNoValida {
        Cliente cliente = clientesDAO.cliente(dni);

        if (cliente == null) {
            throw new OperacionNoValida("No existe un cliente con el DNI indicado");
        }

        return cliente;
    }

    @Override
    public Seguro consultaSeguro(String matricula) throws DataAccessException, OperacionNoValida {
        Seguro seguro = segurosDAO.seguroPorMatricula(matricula);

        if (seguro == null) {
            throw new OperacionNoValida("No existe seguro para la matrícula indicada");
        }

        return seguro;
    }
}
