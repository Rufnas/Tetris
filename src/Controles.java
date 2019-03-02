import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JLabel;

public class Controles extends JFrame {

	private JPanel contentPane;
	
	private Juego juego;
	
	private ButtonGroup grpNiveles;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Controles frame = new Controles();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Controles() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(500, 50, 480, 680);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		juego=new Juego(Controles.this);
		juego.setBounds(136, 21, 300, 600);
		contentPane.add(juego);
		
		grpNiveles=new ButtonGroup();
		
		JButton btnJugar = new JButton("Jugar");
		btnJugar.setBounds(22, 131, 89, 23);
		contentPane.add(btnJugar);
		
		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.setBounds(22, 185, 89, 23);
		contentPane.add(btnCerrar);
		
		JRadioButton rdbtnFacil = new JRadioButton("Facil");
		rdbtnFacil.setSelected(true);
		rdbtnFacil.setBounds(30, 49, 63, 23);
		contentPane.add(rdbtnFacil);
		
		JRadioButton rdbtnMedio = new JRadioButton("Medio");
		rdbtnMedio.setBounds(30, 75, 63, 23);
		contentPane.add(rdbtnMedio);
		
		JRadioButton rdbtnDificil = new JRadioButton("Dificil");
		rdbtnDificil.setBounds(30, 101, 63, 23);
		contentPane.add(rdbtnDificil);
		
		grpNiveles.add(rdbtnFacil);
		grpNiveles.add(rdbtnMedio);
		grpNiveles.add(rdbtnDificil);
		
		JLabel lblEligeNivel = new JLabel("Elige nivel");
		lblEligeNivel.setBounds(30, 21, 63, 14);
		contentPane.add(lblEligeNivel);
	}
}
