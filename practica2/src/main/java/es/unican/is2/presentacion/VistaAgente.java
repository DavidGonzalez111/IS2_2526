package es.unican.is2.presentacion;

import es.unican.is2.comun.*;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultListModel;
import javax.swing.JTextField;
import javax.swing.JLabel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JList;
import javax.swing.border.LineBorder;

import java.awt.Color;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class VistaAgente extends JFrame {

	private JPanel contentPane;
	private JTextField txtDniCliente;
	private JTextField txtTotalCliente;
	private JTextField txtNombreCliente;
	private JList<String> listSeguros; 
	private DefaultListModel<String> listModel;
	private JButton btnBuscar;
	
	private IGestionClientes clientes;
	private IGestionSeguros seguros;
	private IInfoSeguros info;

	/**
	 * Create the frame.
	 */
	public VistaAgente(IGestionClientes clientes,
			IGestionSeguros seguros, IInfoSeguros info) {
		
		this.clientes = clientes;
		this.seguros = seguros;
		this.info = info;
		init();
	}
	
	public void init() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 341);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		listModel=  new DefaultListModel<String>();
		
		txtTotalCliente = new JTextField();
		txtTotalCliente.setBounds(230, 251, 180, 20);
		contentPane.add(txtTotalCliente);
		txtTotalCliente.setColumns(10);
		txtTotalCliente.setName("txtTotalCliente");
		
		JLabel lblTotalCliente = new JLabel("Total A Pagar");
		lblTotalCliente.setBounds(137, 254, 180, 14);
		contentPane.add(lblTotalCliente);
		
		listSeguros = new JList<String>();
		listSeguros.setBounds(230, 98, 180, 116);
		contentPane.add(listSeguros);
		listSeguros.setBorder(new LineBorder(new Color(0, 0, 0)));
		listSeguros.setModel(listModel);
		
		JLabel lblSeguros = new JLabel("Seguros");
		lblSeguros.setBounds(149, 93, 65, 14);
		contentPane.add(lblSeguros);
		
		JLabel lblNombreCliente = new JLabel("Nombre");
		lblNombreCliente.setBounds(155, 54, 65, 14);
		contentPane.add(lblNombreCliente);
		
		txtNombreCliente = new JTextField();
		txtNombreCliente.setBounds(230, 51, 180, 20);
		contentPane.add(txtNombreCliente);
		txtNombreCliente.setColumns(10);
		txtNombreCliente.setName("txtNombreCliente");
		
		JLabel lblDatosCliente = new JLabel("Datos Cliente");
		lblDatosCliente.setBounds(230, 11, 149, 14);
		contentPane.add(lblDatosCliente);
		
		txtDniCliente = new JTextField();
		txtDniCliente.setBounds(10, 51, 113, 20);
		contentPane.add(txtDniCliente);
		txtDniCliente.setColumns(10);
		txtDniCliente.setName("txtDNICliente");
		
		JLabel lblDniCliente = new JLabel("DNI Cliente");
		lblDniCliente.setBounds(21, 27, 139, 14);
		contentPane.add(lblDniCliente);
		lblDniCliente.setName("lblDniCliente");
		
		btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				rellenaDatosCliente(txtNombreCliente.getText());
			}
		});
		btnBuscar.setBounds(21, 122, 89, 23);
		contentPane.add(btnBuscar);
		btnBuscar.setName("btnBuscar");
		listSeguros.setVisible(true);
	}

	private void rellenaDatosCliente(String dni) {
		Cliente c;
		try {
			c = info.consultaCliente(dni);
		if (c!=null) {
			txtNombreCliente.setText(c.getNombre());
			txtTotalCliente.setText(Double.toString(c.totalSeguros()));
			listModel.removeAllElements();
			for (Seguro v:c.getSeguros()) {
				listModel.addElement(v.getMatricula() + " "+v.getCobertura());
			}
		} else {
			txtNombreCliente.setText("Error en BBDD");
			txtTotalCliente.setText("");
			listModel.removeAllElements();
		}
		} catch (DataAccessException e) {
			e.printStackTrace();
			txtNombreCliente.setText("Error en BBDD");
			txtTotalCliente.setText("");
			listModel.removeAllElements();
		}
		
	}
}


