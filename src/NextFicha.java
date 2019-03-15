import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

public class NextFicha extends Canvas {
	public static final int FICHA_L=0;
	public static final int FICHA_I=1;
	public static final int FICHA_S=2;
	public static final int FICHA_CUADRADO=3;
	public static final int FICHA_T=4;
	public static final int NO_FICHA=5;
	public static final int TAMAÑO_FICHA=30;
	
	private Controles controles;
	private Juego juego;
	private Ficha ficha[][];
	
	public NextFicha(Controles controles) {
		setBackground(Color.BLACK);
		this.controles=controles;		
	}

	public Ficha[][] getFicha() {
		return ficha;
	}

	public void setFicha(Ficha[][] campoFicha2) {
		this.ficha = campoFicha2;
	}
	
	public void paint(Graphics g){
		//System.out.println("hola");
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (ficha[i][j].isHitbox()) {
					g.setColor(ficha[i][j].getColor());
					g.fillRect(ficha[i][j].getPosX()-90, ficha[i][j].getPosY(), TAMAÑO_FICHA, TAMAÑO_FICHA);
				} else {
					/*g.setColor(Color.GRAY);
					g.fillRect(campoFicha1[i][j].getPosX(), campoFicha1[i][j].getPosY(), TAMAÑO_FICHA, TAMAÑO_FICHA);*/
				}
			}
		}
	}
	

}
