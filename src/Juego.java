import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

public class Juego extends Canvas {
	public static final int TAMAÑO_FICHA=30;

	private Controles controles;
	private Ficha ficha;
	private Ficha campoFicha[][];

	/**
	 * Create the panel.
	 */
	public Juego(Controles controles) {
		setBackground(Color.BLACK);
		this.controles=controles;
		ficha=new Ficha();
	}

	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
		g.setColor(Color.GRAY);
		for (int i = 0; i < 10; i++) {
			g.drawLine(TAMAÑO_FICHA*i, 0, this.getHeight(), this.getHeight()*10000);
		}
		for (int i = 0; i < 20; i++) {
			g.drawLine(0, TAMAÑO_FICHA*i, this.getWidth()*10000, this.getWidth());
		}
		crearFichaI();
		mostrarFichaI(g);

	}
	
	private void mostrarFichaI(Graphics g) {
		// TODO Auto-generated method stub
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (campoFicha[i][j].isHitbox()) {
					g.setColor(Color.BLUE);
					g.fillRect(campoFicha[i][j].getPosX(), campoFicha[i][j].getPosY(), TAMAÑO_FICHA, TAMAÑO_FICHA);
				} else {
					g.setColor(Color.GRAY);
					g.fillRect(campoFicha[i][j].getPosX(), campoFicha[i][j].getPosY(), TAMAÑO_FICHA, TAMAÑO_FICHA);
				}
			}
		}
	}

	private void moverFichaI(){
		if (campoFicha[0][3].isHitbox()) {
			campoFicha[0][0].setHitbox(true);
			campoFicha[1][0].setHitbox(true);
			campoFicha[2][0].setHitbox(true);
			campoFicha[3][0].setHitbox(true);
			campoFicha[0][1].setHitbox(false);
			campoFicha[0][2].setHitbox(false);
			campoFicha[0][3].setHitbox(false);
			
		} else{
			campoFicha[0][0].setHitbox(true);
			campoFicha[0][1].setHitbox(true);
			campoFicha[0][2].setHitbox(true);
			campoFicha[0][3].setHitbox(true);
			campoFicha[1][0].setHitbox(false);
			campoFicha[2][0].setHitbox(false);
			campoFicha[3][0].setHitbox(false);
			
		}
		
	}

	private void crearFichaI() {
		// TODO Auto-generated method stub
		campoFicha=new Ficha[4][4];

		for (int i = 1; i < 5; i++) {
			int aux=0;
			int aux2=30;
			aux2=aux2*i;
			for (int j = 1; j < 5; j++) {
				ficha=new Ficha(60+aux2, 0+aux, Color.GRAY, 1, false);
				campoFicha[i-1][j-1]=ficha;
				aux=aux+30;
			}
		}
		campoFicha[0][0].setHitbox(true);
		campoFicha[1][0].setHitbox(true);
		campoFicha[2][0].setHitbox(true);
		campoFicha[3][0].setHitbox(true);

		
	}

}
