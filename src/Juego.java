import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

public class Juego extends Canvas {
	
	private Controles controles;

	/**
	 * Create the panel.
	 */
	public Juego(Controles controles) {
		setBackground(Color.BLACK);
		this.controles=controles;
	}
	
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
	}

}
