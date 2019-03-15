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
	private NextFicha nextFicha;
	
	private ButtonGroup grpNiveles;
	private JRadioButton rdbtnFacil;
	private JRadioButton rdbtnMedio;
	private JRadioButton rdbtnDificil;
	private JButton btnJugar;

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
		setBounds(500, 50, 570, 680);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		nextFicha=new NextFicha(Controles.this);
		nextFicha.setBounds(34, 276, 150, 150);
		contentPane.add(nextFicha);
		
		juego=new Juego(Controles.this, nextFicha);
		juego.setBounds(214, 21, 300, 600);
		contentPane.add(juego);
		
		
		
		grpNiveles=new ButtonGroup();
		
		btnJugar = new JButton("Jugar");
		btnJugar.setBounds(58, 131, 89, 23);
		contentPane.add(btnJugar);
		
		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.setBounds(58, 185, 89, 23);
		contentPane.add(btnCerrar);
		
		rdbtnFacil = new JRadioButton("Facil");
		rdbtnFacil.setSelected(true);
		rdbtnFacil.setBounds(66, 49, 63, 23);
		contentPane.add(rdbtnFacil);
		
		rdbtnMedio = new JRadioButton("Medio");
		rdbtnMedio.setBounds(66, 75, 63, 23);
		contentPane.add(rdbtnMedio);
		
		rdbtnDificil = new JRadioButton("Dificil");
		rdbtnDificil.setBounds(66, 101, 63, 23);
		contentPane.add(rdbtnDificil);
		
		grpNiveles.add(rdbtnFacil);
		grpNiveles.add(rdbtnMedio);
		grpNiveles.add(rdbtnDificil);
		
		JLabel lblEligeNivel = new JLabel("Elige nivel");
		lblEligeNivel.setBounds(66, 21, 63, 14);
		contentPane.add(lblEligeNivel);
	}
}
