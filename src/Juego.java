import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.Timer;

public class Juego extends Canvas {
	public static final int FICHA_L=0;
	public static final int FICHA_I=1;
	public static final int FICHA_S=2;
	public static final int FICHA_CUADRADO=3;
	public static final int FICHA_T=4;
	public static final int NO_FICHA=5;
	public static final int TAMAÑO_FICHA=30;
	public static final int FACIL=3;
	public static final int NORMAL=2;
	public static final int DIFICIL=1;

	private Controles controles;
	private Ficha limiteAbajo[];
	private Ficha fichaInmovil[];
	private Ficha ficha;
	private Ficha campoFicha[][];
	private Random r=new Random();
	private Timer reloj;
	int contador=0;
	int fichaPos = 3;
	private Rectangle rCuad[];
	private Rectangle hitboxFicha[];

	/**
	 * Create the panel.
	 */
	public Juego(Controles controles) {
		setBackground(Color.BLACK);
		this.controles=controles;
		ficha=new Ficha();
		registrarEventos();
		crearFicha();
		fichaInmovil=new Ficha[100];
		limiteAbajo=new Ficha[10];
	}

	private void registrarEventos() {
		reloj=new Timer(1000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				requestFocus();//ESTO ES TEMPORAL
				agregarHitbox();
				limiteAbajo();	
				contador++;
				int dificultad = FACIL;
				if (dificultad==FACIL) {
					if (contador==3) {
						for (int i = 0; i < 4; i++) {
							for (int j = 0; j < 4; j++) {
								//if () {
									campoFicha[i][j].setPosY(campoFicha[i][j].getPosY()+30);	
								//}
								
							}
						}
						contador=0;
					}
					repaint();
				}
				if (dificultad==NORMAL) {
					if (contador==2) {
						for (int i = 0; i < 4; i++) {
							for (int j = 0; j < 4; j++) {
								campoFicha[i][j].setPosY(campoFicha[i][j].getPosY()+30);
							}
						}
						contador=0;
					}
					repaint();
				}
				if(dificultad==DIFICIL){
					if (contador==1) {
						for (int i = 0; i < 4; i++) {
							for (int j = 0; j < 4; j++) {
								campoFicha[i][j].setPosY(campoFicha[i][j].getPosY()+30);
							}
						}
						contador=0;
					}
					repaint();
				}
			}
			
		});
		reloj.start();

		addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				if (key==KeyEvent.VK_SPACE) {
					fichaPos++;
					if(fichaPos>=4) fichaPos=0;
				}
				if (key==KeyEvent.VK_DOWN) {
					for (int i = 0; i < 4; i++) {
						for (int j = 0; j < 4; j++) {
							campoFicha[i][j].setPosY(campoFicha[i][j].getPosY()+30);
						}
					}
				}
				if (key==KeyEvent.VK_RIGHT) {
					for (int i = 0; i < 4; i++) {
						for (int j = 0; j < 4; j++) {
							campoFicha[i][j].setPosX(campoFicha[i][j].getPosX()+30);
						}
					}
				}
				if (key==KeyEvent.VK_LEFT) {
					for (int i = 0; i < 4; i++) {
						for (int j = 0; j < 4; j++) {
							campoFicha[i][j].setPosX(campoFicha[i][j].getPosX()-30);
						}
					}
				}
				repaint();				
			}
		});

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
		//ESTO QUE SETA AQUI ABAJO ES PARA HACER EL HITBOX DEL FONDO
		limiteAbajo();
		g.setColor(limiteAbajo[0].getColor());
		for (int i = 0; i < 10; i++) {
			g.fillRect(limiteAbajo[i].getPosX(), limiteAbajo[i].getPosY(), TAMAÑO_FICHA, TAMAÑO_FICHA);
		}
		moverFicha(campoFicha[0][0].getFormaFicha(), fichaPos);
		mostrarFicha(g);
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 10; j++) {
				if (rCuad[i].intersects(hitboxFicha[j])) {
					System.out.println("HOLAAAAAA");
				}
			}
		}
	}

	private void limiteAbajo() {
		rCuad=new Rectangle[10];
		ficha=new Ficha(0, 570, Color.WHITE, NO_FICHA, false);
		limiteAbajo[0]=ficha;
		for (int i = 1; i < 10; i++) {
			ficha=new Ficha(i*30, 570, Color.WHITE, NO_FICHA, false);
			limiteAbajo[i]=ficha;
			rCuad[i]=new Rectangle(limiteAbajo[i].getPosX(), limiteAbajo[i].getPosY(), TAMAÑO_FICHA, TAMAÑO_FICHA);
		}
		
	}

	private void mostrarFicha(Graphics g) {
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

	private void moverFicha(int tipoFicha, int posFicha){
		switch (tipoFicha) {
		//MOVIMIENTOS PARA LA FICHA DE TIPO L
		case FICHA_L:
			if (posFicha==0) {
				eliminarHitboxTodo();
				campoFicha[0][2].setHitbox(true);
				campoFicha[1][2].setHitbox(true);
				campoFicha[2][2].setHitbox(true);
				campoFicha[2][1].setHitbox(true);
			} else if(posFicha==1){
				eliminarHitboxTodo();
				campoFicha[1][1].setHitbox(true);
				campoFicha[2][1].setHitbox(true);
				campoFicha[2][2].setHitbox(true);
				campoFicha[2][3].setHitbox(true);
			} else if(posFicha==2){
				eliminarHitboxTodo();
				campoFicha[1][1].setHitbox(true);
				campoFicha[1][2].setHitbox(true);
				campoFicha[2][1].setHitbox(true);
				campoFicha[3][1].setHitbox(true);
			} else if(posFicha==3){
				eliminarHitboxTodo();
				campoFicha[1][0].setHitbox(true);
				campoFicha[1][1].setHitbox(true);
				campoFicha[1][2].setHitbox(true);
				campoFicha[2][2].setHitbox(true);
			}
			break;
			//MOVIMIENTOS PARA LA FICHA DE TIPO I
		case FICHA_I:
			if (posFicha==0) {
				eliminarHitboxTodo();
				campoFicha[0][0].setHitbox(true);
				campoFicha[1][0].setHitbox(true);
				campoFicha[2][0].setHitbox(true);
				campoFicha[3][0].setHitbox(true);			
			} else if(posFicha==1){
				eliminarHitboxTodo();
				campoFicha[0][0].setHitbox(true);
				campoFicha[0][1].setHitbox(true);
				campoFicha[0][2].setHitbox(true);
				campoFicha[0][3].setHitbox(true);
			} else if (posFicha==2) {
				eliminarHitboxTodo();
				campoFicha[0][0].setHitbox(true);
				campoFicha[1][0].setHitbox(true);
				campoFicha[2][0].setHitbox(true);
				campoFicha[3][0].setHitbox(true);			
			} else if(posFicha==3){
				eliminarHitboxTodo();
				campoFicha[0][0].setHitbox(true);
				campoFicha[0][1].setHitbox(true);
				campoFicha[0][2].setHitbox(true);
				campoFicha[0][3].setHitbox(true);
			}
			break;
			//MOVIMIENTOS PARA LA FICHA DE TIPO S
		case FICHA_S:
			if (posFicha==0) {
				eliminarHitboxTodo();
				campoFicha[2][0].setHitbox(true);
				campoFicha[2][1].setHitbox(true);
				campoFicha[1][1].setHitbox(true);
				campoFicha[1][2].setHitbox(true);
			}else if(posFicha==1){
				eliminarHitboxTodo();
				campoFicha[0][1].setHitbox(true);
				campoFicha[1][1].setHitbox(true);
				campoFicha[1][2].setHitbox(true);
				campoFicha[2][2].setHitbox(true);
			}else if (posFicha==2) {
				eliminarHitboxTodo();
				campoFicha[2][0].setHitbox(true);
				campoFicha[2][1].setHitbox(true);
				campoFicha[1][1].setHitbox(true);
				campoFicha[1][2].setHitbox(true);
			}else if(posFicha==3){
				eliminarHitboxTodo();
				campoFicha[0][1].setHitbox(true);
				campoFicha[1][1].setHitbox(true);
				campoFicha[1][2].setHitbox(true);
				campoFicha[2][2].setHitbox(true);
			}
			break;
			//MOVIMIENTOS PARA LA FICHA DE TIPO CUADRADO, EL CUAL NO HAY
		case FICHA_CUADRADO:
			//System.out.println("Estas intentando mover un cuadrado LOL");
			break;
			//MOVIMIENTOS PARA LA FICHA DE TIPO T
		case FICHA_T:
			if (posFicha==0) {
				eliminarHitboxTodo();
				campoFicha[0][2].setHitbox(true);
				campoFicha[1][1].setHitbox(true);
				campoFicha[1][2].setHitbox(true);
				campoFicha[1][3].setHitbox(true);
			}else if(posFicha==1){
				eliminarHitboxTodo();
				campoFicha[0][2].setHitbox(true);
				campoFicha[1][2].setHitbox(true);
				campoFicha[2][2].setHitbox(true);
				campoFicha[1][3].setHitbox(true);
			}else if(posFicha==2){
				eliminarHitboxTodo();
				campoFicha[1][1].setHitbox(true);
				campoFicha[1][2].setHitbox(true);
				campoFicha[1][3].setHitbox(true);
				campoFicha[2][2].setHitbox(true);
			}else if(posFicha==3){
				eliminarHitboxTodo();
				campoFicha[1][1].setHitbox(true);
				campoFicha[0][2].setHitbox(true);
				campoFicha[1][2].setHitbox(true);
				campoFicha[2][2].setHitbox(true);
			}
			break;
		default:
			System.out.println("Problema con el tipo(forma) de ficha");
			break;
		}
	}

	private void eliminarHitboxTodo() {
		//ELIMINA EL HITBOX DE TODO EL CAMPO DE LA FICHA, SOLO USADO PARA HACER MOVIMIENTOS DE LA FICHA
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				campoFicha[i][j].setHitbox(false);
			}
		}

	}
	
	private void agregarHitbox(){
		hitboxFicha=new Rectangle[4];
		int contador=0;
		for (int i = 1; i < 5; i++) {
			int aux=0;
			int aux2=30;
			aux2=aux2*i;
			for (int j = 1; j < 5; j++) {
				if(campoFicha[i-1][j-1].isHitbox()){
					hitboxFicha[0]=new Rectangle(campoFicha[i-1][j-1].getPosX(), campoFicha[i-1][j-1].getPosY(), TAMAÑO_FICHA, TAMAÑO_FICHA);
					contador++;
				}
				aux=aux+30;
			}
		}
	}
	
	private void crearFicha() {
		// TODO Auto-generated method stub
		int numeroFicha=r.nextInt(4);
		//numeroFicha=2; //Numero provisional para probar la creacion de fichas
		campoFicha=new Ficha[4][4];
		switch (numeroFicha) {
		case 0:
			for (int i = 1; i < 5; i++) {
				int aux=0;
				int aux2=30;
				aux2=aux2*i;
				for (int j = 1; j < 5; j++) {
					ficha=new Ficha(60+aux2, 0+aux, Color.GRAY, FICHA_L, false);
					campoFicha[i-1][j-1]=ficha;
					aux=aux+30;
				}
			}
			campoFicha[1][0].setHitbox(true);
			campoFicha[1][1].setHitbox(true);
			campoFicha[1][2].setHitbox(true);
			campoFicha[2][2].setHitbox(true);
			break;
		case 1:
			for (int i = 1; i < 5; i++) {
				int aux=0;
				int aux2=30;
				aux2=aux2*i;
				for (int j = 1; j < 5; j++) {
					ficha=new Ficha(60+aux2, 0+aux, Color.GRAY, FICHA_I, false);
					campoFicha[i-1][j-1]=ficha;
					aux=aux+30;
				}
			}
			campoFicha[0][0].setHitbox(true);
			campoFicha[1][0].setHitbox(true);
			campoFicha[2][0].setHitbox(true);
			campoFicha[3][0].setHitbox(true);
			break;
		case 2:
			for (int i = 1; i < 5; i++) {
				int aux=0;
				int aux2=30;
				aux2=aux2*i;
				for (int j = 1; j < 5; j++) {
					ficha=new Ficha(60+aux2, 0+aux, Color.GRAY, FICHA_S, false);
					campoFicha[i-1][j-1]=ficha;
					aux=aux+30;
				}
			}
			campoFicha[0][1].setHitbox(true);
			campoFicha[1][1].setHitbox(true);
			campoFicha[1][2].setHitbox(true);
			campoFicha[2][2].setHitbox(true);
			break;
		case 3:
			for (int i = 1; i < 5; i++) {
				int aux=0;
				int aux2=30;
				aux2=aux2*i;
				for (int j = 1; j < 5; j++) {
					ficha=new Ficha(60+aux2, 0+aux, Color.GRAY, FICHA_CUADRADO, false);
					campoFicha[i-1][j-1]=ficha;
					aux=aux+30;
				}
			}
			campoFicha[1][1].setHitbox(true);
			campoFicha[2][1].setHitbox(true);
			campoFicha[1][2].setHitbox(true);
			campoFicha[2][2].setHitbox(true);
			break;
		case 4:
			for (int i = 1; i < 5; i++) {
				int aux=0;
				int aux2=30;
				aux2=aux2*i;
				for (int j = 1; j < 5; j++) {
					ficha=new Ficha(60+aux2, 0+aux, Color.GRAY, FICHA_T, false);
					campoFicha[i-1][j-1]=ficha;
					aux=aux+30;
				}
			}
			campoFicha[1][1].setHitbox(true);
			campoFicha[0][2].setHitbox(true);
			campoFicha[1][2].setHitbox(true);
			campoFicha[2][2].setHitbox(true);
			break;
		default:
			for (int i = 1; i < 5; i++) {
				int aux=0;
				int aux2=30;
				aux2=aux2*i;
				for (int j = 1; j < 5; j++) {
					ficha=new Ficha(60+aux2, 0+aux, Color.GRAY, FICHA_CUADRADO, false);
					campoFicha[i-1][j-1]=ficha;
					aux=aux+30;
				}
			}
			campoFicha[0][0].setHitbox(true);
			campoFicha[1][0].setHitbox(true);
			campoFicha[2][0].setHitbox(true);
			campoFicha[3][0].setHitbox(true);
			break;
		}


	}

}
