import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
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
	public static final int LIBRE=0;
	public static final int EMPAREJADO=1;

	private Controles controles;
	private NextFicha nextficha;
	private Ficha limiteAbajo[];
	private Ficha[][] piezasInmoviles = new Ficha[10][20];
	private Ficha ficha;
	private Ficha[][] campoFicha1;
	private Ficha[][] campoFicha2;
	private Random r=new Random();
	private Timer reloj;
	int contador=0;
	int fichaPos = 3;
	int casillaX=0, casillaY=0;
	private int numeroPiezas=1;
	private Rectangle hitboxFicha[];
	//Para el doble buffer (para eliminar el parpadeo al dibujar)
	private Graphics pantVirtual;
	private Image buffer;

	/**
	 * Create the panel.
	 */
	public Juego(Controles controles, NextFicha nextficha) {
		setBackground(Color.BLACK);
		this.controles=controles;
		this.nextficha=nextficha;
		ficha=new Ficha();
		registrarEventos();
		crearFondo();
		crearPrimeraFicha();
		limiteAbajo=new Ficha[10];
		crearFichaSiguiente();
		nextficha.setFicha(campoFicha2);
	}

	private void registrarEventos() {
		reloj=new Timer(1000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				requestFocus();//ESTO ES TEMPORAL
				agregarHitbox();
				limiteAbajo();
				comprobarLinea();
				nextficha.repaint();
				contador++;
				int dificultad = NORMAL;
				if (dificultad==FACIL) {
					if (contador==3) {
						comprobarMovimiento();
						for (int i = 0; i < 4; i++) {
							for (int j = 0; j < 4; j++) {
								campoFicha1[i][j].setPosY(campoFicha1[i][j].getPosY()+30);
							}
						}
						contador=0;
					}
					repaint();
				}
				if (dificultad==NORMAL) {
					if (contador==2) {
						comprobarMovimiento();
						for (int i = 0; i < 4; i++) {
							for (int j = 0; j < 4; j++) {
								campoFicha1[i][j].setPosY(campoFicha1[i][j].getPosY()+30);
							}
						}
						contador=0;
					}
					repaint();
				}
				if(dificultad==DIFICIL){
					if (contador==1) {
						comprobarMovimiento();
						for (int i = 0; i < 4; i++) {
							for (int j = 0; j < 4; j++) {
								campoFicha1[i][j].setPosY(campoFicha1[i][j].getPosY()+30);
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
				if (reloj.isRunning()) {
					if (key==KeyEvent.VK_SPACE) {
						fichaPos++;
						if(fichaPos>=4) fichaPos=0;
					}
					if (key==KeyEvent.VK_DOWN) {
						comprobarMovimiento();
						for (int i = 0; i < 4; i++) {
							for (int j = 0; j < 4; j++) {
								campoFicha1[i][j].setPosY(campoFicha1[i][j].getPosY()+30);
							}
						}
					}
					if (key==KeyEvent.VK_RIGHT) {
						int comprobR=0;
						for (int i = 3; i >= 0; i--) {
							for (int j = 3; j >= 0; j--) {
								if (campoFicha1[i][j].isHitbox() && campoFicha1[i][j].getPosX()>240) {
									comprobR=1;
									break;
								}
							}
						}
						if (comprobR==0) {
							for (int i = 0; i < 4; i++) {
								for (int j = 0; j < 4; j++) {
									campoFicha1[i][j].setPosX(campoFicha1[i][j].getPosX()+30);
								}
							}
						}

					}
					if (key==KeyEvent.VK_LEFT) {
						int comprobI=0;
						for (int i = 0; i < 4; i++) {
							for (int j = 0; j < 4; j++) {
								if (campoFicha1[i][j].isHitbox() && campoFicha1[i][j].getPosX()<30) {
									comprobI=1;
									break;
								}
							}
						}
						if (comprobI==0) {
							for (int i = 0; i < 4; i++) {
								for (int j = 0; j < 4; j++) {
									campoFicha1[i][j].setPosX(campoFicha1[i][j].getPosX()-30);
								}
							}
						}
					}

				}
				repaint();				
			}
		});

	}
	//AUN FALTA POR HACER, HAY QUE IR COMPROBANDO MULTIPLES X CADA 30~ Y -------------------------------------------
	protected void comprobarLinea() {
		// TODO Auto-generated method stub
		for (int i = 0; i < 20; i++) {

		}
	}

	protected boolean comprobarMovimiento() {
		//cont 1 = si puedes moverla, 0 = no puedes moverla, fijar ficha
		int cont=1;
		int campo[] = new int[4];
		int fondo[] = new int[10];
		//HAY QUE REVISAR TODO ESTO, SOBRETODO LOS DOS FOR DIRECTOS DE ABAJO...
		//QUE SON PARA SABER CUAL ES LA FICHA MAS ABAJO Y CUAL ES LA BLOQUEADA MAS ARRIBA
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (campoFicha1[j][i].isHitbox()) {
					campo[i]=j;
				}
			}
		}
		for (int i = 0; i < 10; i++) {
			for (int j = 18; j >= 0; j--) {
				if (piezasInmoviles[i][j].isHitbox()) {
					fondo[i]=j;
				}
			}
		}
		for (int i = 0; i < 4; i++) {
			for (int k = 0; k < 20; k++) {
				for (int j = 0; j < 10; j++) {
					if (campoFicha1[campo[i]][i].getPosY()==piezasInmoviles[fondo[j]][k].getPosY()){
						cont=0;
					}
				}
			}

		}
		if (campoFicha1[1][2].getPosY()+60==piezasInmoviles[1][19].getPosY() && campoFicha1[1][2].getPosX()==piezasInmoviles[1][19].getPosX()) {

		}
		if(cont==1){
			System.out.println("Puede moverse");
			return true;
		} else {
			System.out.println("NO Puede moverse");
			return false;
		}

	}

	protected void bloquearFichas(){

	}


	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
		g.setColor(Color.GRAY);
		//CREAMOS Y MOSTRAMOS EL FONDO, QUE ES UN ARRAY DE FICHAS DONDE SOLO DIBUJAMOS LAS LINEAS
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 20; j++) {
				g.drawRect(piezasInmoviles[i][j].getPosX(), piezasInmoviles[i][j].getPosY(), TAMAÑO_FICHA, TAMAÑO_FICHA);
			}
		}
		//ESTO QUE ESTA AQUI ABAJO ES PARA HACER EL HITBOX DEL FONDO
		limiteAbajo();
		g.setColor(piezasInmoviles[0][19].getColor());
		for (int i = 0; i < 10; i++) {
			g.fillRect(piezasInmoviles[i][19].getPosX(), piezasInmoviles[i][19].getPosY(), TAMAÑO_FICHA, TAMAÑO_FICHA);
		}
		//ROTACION DE LA FICHA
		moverFicha(campoFicha1[0][0].getFormaFicha(), fichaPos);
		//MOSTRAMOS LA FICHA DE ULTIMO, POR ENCIMA DE TODO
		mostrarFicha(g);
		//DIBUJAMOS LA FICHA UNA VEZ EMPAREJADA ----------------------------------------------------------------------------------
		dibujarFichaEmparejada(g);
	}

	private void crearFondo() {
		// TODO Auto-generated method stub
		for (int i = 0; i < 10; i++) {
			int aux=0;
			int aux2=30;
			aux2=aux2*i;
			for (int j = 0; j < 20; j++) {
				ficha=new Ficha(0+aux2, 0+aux, Color.BLUE, FICHA_L, false);
				piezasInmoviles[i][j]=ficha;
				aux=aux+30;
			}
		}
	}

	@Override
	public void update(Graphics g) {
		// TODO Auto-generated method stub
		//super.update(g);
		buffer=createImage(300, 600);
		pantVirtual=buffer.getGraphics();
		paint(pantVirtual);
		//volcar la imagen de la pantVirtual sobre la pantalla real
		g.drawImage(buffer, 0, 0, 300, 600, this);
	}

	private void dibujarFichaEmparejada(Graphics g) {
		// TODO Auto-generated method stub

	}

	private void limiteAbajo() {
		ficha=new Ficha(0, 570, Color.WHITE, NO_FICHA, true);
		piezasInmoviles[0][19]=ficha;
		for (int i = 1; i < 10; i++) {
			ficha=new Ficha(i*30, 570, Color.WHITE, NO_FICHA, true);
			piezasInmoviles[i][19]=ficha;
		}

	}

	private void mostrarFicha(Graphics g) {
		// TODO Auto-generated method stub
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (campoFicha1[i][j].isHitbox()) {
					g.setColor(campoFicha1[i][j].getColor());
					g.fillRect(campoFicha1[i][j].getPosX(), campoFicha1[i][j].getPosY(), TAMAÑO_FICHA, TAMAÑO_FICHA);
				} else {
					/*g.setColor(Color.GRAY);
					g.fillRect(campoFicha1[i][j].getPosX(), campoFicha1[i][j].getPosY(), TAMAÑO_FICHA, TAMAÑO_FICHA);*/
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
				campoFicha1[0][2].setHitbox(true);
				campoFicha1[1][2].setHitbox(true);
				campoFicha1[2][2].setHitbox(true);
				campoFicha1[2][1].setHitbox(true);
			} else if(posFicha==1){
				eliminarHitboxTodo();
				campoFicha1[1][1].setHitbox(true);
				campoFicha1[2][1].setHitbox(true);
				campoFicha1[2][2].setHitbox(true);
				campoFicha1[2][3].setHitbox(true);
			} else if(posFicha==2){
				eliminarHitboxTodo();
				campoFicha1[1][1].setHitbox(true);
				campoFicha1[1][2].setHitbox(true);
				campoFicha1[2][1].setHitbox(true);
				campoFicha1[3][1].setHitbox(true);
			} else if(posFicha==3){
				eliminarHitboxTodo();
				campoFicha1[1][0].setHitbox(true);
				campoFicha1[1][1].setHitbox(true);
				campoFicha1[1][2].setHitbox(true);
				campoFicha1[2][2].setHitbox(true);
			}
			break;
			//MOVIMIENTOS PARA LA FICHA DE TIPO I
		case FICHA_I:
			if (posFicha==0) {
				eliminarHitboxTodo();
				campoFicha1[0][0].setHitbox(true);
				campoFicha1[1][0].setHitbox(true);
				campoFicha1[2][0].setHitbox(true);
				campoFicha1[3][0].setHitbox(true);			
			} else if(posFicha==1){
				eliminarHitboxTodo();
				campoFicha1[0][0].setHitbox(true);
				campoFicha1[0][1].setHitbox(true);
				campoFicha1[0][2].setHitbox(true);
				campoFicha1[0][3].setHitbox(true);
			} else if (posFicha==2) {
				eliminarHitboxTodo();
				campoFicha1[0][0].setHitbox(true);
				campoFicha1[1][0].setHitbox(true);
				campoFicha1[2][0].setHitbox(true);
				campoFicha1[3][0].setHitbox(true);			
			} else if(posFicha==3){
				eliminarHitboxTodo();
				campoFicha1[0][0].setHitbox(true);
				campoFicha1[0][1].setHitbox(true);
				campoFicha1[0][2].setHitbox(true);
				campoFicha1[0][3].setHitbox(true);
			}
			break;
			//MOVIMIENTOS PARA LA FICHA DE TIPO S
		case FICHA_S:
			if (posFicha==0) {
				eliminarHitboxTodo();
				campoFicha1[2][0].setHitbox(true);
				campoFicha1[2][1].setHitbox(true);
				campoFicha1[1][1].setHitbox(true);
				campoFicha1[1][2].setHitbox(true);
			}else if(posFicha==1){
				eliminarHitboxTodo();
				campoFicha1[0][1].setHitbox(true);
				campoFicha1[1][1].setHitbox(true);
				campoFicha1[1][2].setHitbox(true);
				campoFicha1[2][2].setHitbox(true);
			}else if (posFicha==2) {
				eliminarHitboxTodo();
				campoFicha1[2][0].setHitbox(true);
				campoFicha1[2][1].setHitbox(true);
				campoFicha1[1][1].setHitbox(true);
				campoFicha1[1][2].setHitbox(true);
			}else if(posFicha==3){
				eliminarHitboxTodo();
				campoFicha1[0][1].setHitbox(true);
				campoFicha1[1][1].setHitbox(true);
				campoFicha1[1][2].setHitbox(true);
				campoFicha1[2][2].setHitbox(true);
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
				campoFicha1[0][2].setHitbox(true);
				campoFicha1[1][1].setHitbox(true);
				campoFicha1[1][2].setHitbox(true);
				campoFicha1[1][3].setHitbox(true);
			}else if(posFicha==1){
				eliminarHitboxTodo();
				campoFicha1[0][2].setHitbox(true);
				campoFicha1[1][2].setHitbox(true);
				campoFicha1[2][2].setHitbox(true);
				campoFicha1[1][3].setHitbox(true);
			}else if(posFicha==2){
				eliminarHitboxTodo();
				campoFicha1[1][1].setHitbox(true);
				campoFicha1[1][2].setHitbox(true);
				campoFicha1[1][3].setHitbox(true);
				campoFicha1[2][2].setHitbox(true);
			}else if(posFicha==3){
				eliminarHitboxTodo();
				campoFicha1[1][1].setHitbox(true);
				campoFicha1[0][2].setHitbox(true);
				campoFicha1[1][2].setHitbox(true);
				campoFicha1[2][2].setHitbox(true);
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
				campoFicha1[i][j].setHitbox(false);
			}
		}

	}
	//POR AHORA ES INUTIL-----------------------------------------------------------------------------------------
	private void agregarHitbox(){
		hitboxFicha=new Rectangle[4];
		for (int i = 1; i < 5; i++) {
			int aux=0;
			int aux2=30;
			aux2=aux2*i;
			for (int j = 1; j < 5; j++) {
				if(campoFicha1[i-1][j-1].isHitbox()){
					hitboxFicha[0]=new Rectangle(campoFicha1[i-1][j-1].getPosX(), campoFicha1[i-1][j-1].getPosY(), TAMAÑO_FICHA, TAMAÑO_FICHA);
				}
				aux=aux+30;
			}
		}
	}

	private void crearPrimeraFicha() {
		// TODO Auto-generated method stub
		int numeroFicha=r.nextInt(4);
		//numeroFicha=0; //Numero provisional para probar la creacion de fichas
		campoFicha1=new Ficha[4][4];
		switch (numeroFicha) {
		case 0:
			for (int i = 1; i < 5; i++) {
				int aux=0;
				int aux2=30;
				aux2=aux2*i;
				for (int j = 1; j < 5; j++) {
					ficha=new Ficha(60+aux2, 0+aux, Color.BLUE, FICHA_L, false);
					campoFicha1[i-1][j-1]=ficha;
					aux=aux+30;
				}
			}
			campoFicha1[1][0].setHitbox(true);
			campoFicha1[1][1].setHitbox(true);
			campoFicha1[1][2].setHitbox(true);
			campoFicha1[2][2].setHitbox(true);
			break;
		case 1:
			for (int i = 1; i < 5; i++) {
				int aux=0;
				int aux2=30;
				aux2=aux2*i;
				for (int j = 1; j < 5; j++) {
					ficha=new Ficha(60+aux2, 0+aux, Color.GRAY, FICHA_I, false);
					campoFicha1[i-1][j-1]=ficha;
					aux=aux+30;
				}
			}
			campoFicha1[0][0].setHitbox(true);
			campoFicha1[1][0].setHitbox(true);
			campoFicha1[2][0].setHitbox(true);
			campoFicha1[3][0].setHitbox(true);
			break;
		case 2:
			for (int i = 1; i < 5; i++) {
				int aux=0;
				int aux2=30;
				aux2=aux2*i;
				for (int j = 1; j < 5; j++) {
					ficha=new Ficha(60+aux2, 0+aux, Color.RED, FICHA_S, false);
					campoFicha1[i-1][j-1]=ficha;
					aux=aux+30;
				}
			}
			campoFicha1[0][1].setHitbox(true);
			campoFicha1[1][1].setHitbox(true);
			campoFicha1[1][2].setHitbox(true);
			campoFicha1[2][2].setHitbox(true);
			break;
		case 3:
			for (int i = 1; i < 5; i++) {
				int aux=0;
				int aux2=30;
				aux2=aux2*i;
				for (int j = 1; j < 5; j++) {
					ficha=new Ficha(60+aux2, 0+aux, Color.MAGENTA, FICHA_CUADRADO, false);
					campoFicha1[i-1][j-1]=ficha;
					aux=aux+30;
				}
			}
			campoFicha1[1][1].setHitbox(true);
			campoFicha1[2][1].setHitbox(true);
			campoFicha1[1][2].setHitbox(true);
			campoFicha1[2][2].setHitbox(true);
			break;
		case 4:
			for (int i = 1; i < 5; i++) {
				int aux=0;
				int aux2=30;
				aux2=aux2*i;
				for (int j = 1; j < 5; j++) {
					ficha=new Ficha(60+aux2, 0+aux, Color.ORANGE, FICHA_T, false);
					campoFicha1[i-1][j-1]=ficha;
					aux=aux+30;
				}
			}
			campoFicha1[1][1].setHitbox(true);
			campoFicha1[0][2].setHitbox(true);
			campoFicha1[1][2].setHitbox(true);
			campoFicha1[2][2].setHitbox(true);
			break;
		default:
			for (int i = 1; i < 5; i++) {
				int aux=0;
				int aux2=30;
				aux2=aux2*i;
				for (int j = 1; j < 5; j++) {
					ficha=new Ficha(60+aux2, 0+aux, Color.GRAY, FICHA_CUADRADO, false);
					campoFicha1[i-1][j-1]=ficha;
					aux=aux+30;
				}
			}
			campoFicha1[0][0].setHitbox(true);
			campoFicha1[1][0].setHitbox(true);
			campoFicha1[2][0].setHitbox(true);
			campoFicha1[3][0].setHitbox(true);
			break;
		}
	}

	private void crearFichaSiguiente() {
		// TODO Auto-generated method stub
		int numeroFicha=r.nextInt(4);
		//numeroFicha=2; //Numero provisional para probar la creacion de fichas
		campoFicha2=new Ficha[4][4];
		switch (numeroFicha) {
		case 0:
			for (int i = 1; i < 5; i++) {
				int aux=0;
				int aux2=30;
				aux2=aux2*i;
				for (int j = 1; j < 5; j++) {
					ficha=new Ficha(60+aux2, 0+aux, Color.BLUE, FICHA_L, false);
					campoFicha2[i-1][j-1]=ficha;
					aux=aux+30;
				}
			}
			campoFicha2[1][0].setHitbox(true);
			campoFicha2[1][1].setHitbox(true);
			campoFicha2[1][2].setHitbox(true);
			campoFicha2[2][2].setHitbox(true);
			break;
		case 1:
			for (int i = 1; i < 5; i++) {
				int aux=0;
				int aux2=30;
				aux2=aux2*i;
				for (int j = 1; j < 5; j++) {
					ficha=new Ficha(60+aux2, 0+aux, Color.GRAY, FICHA_I, false);
					campoFicha2[i-1][j-1]=ficha;
					aux=aux+30;
				}
			}
			campoFicha2[0][0].setHitbox(true);
			campoFicha2[1][0].setHitbox(true);
			campoFicha2[2][0].setHitbox(true);
			campoFicha2[3][0].setHitbox(true);
			break;
		case 2:
			for (int i = 1; i < 5; i++) {
				int aux=0;
				int aux2=30;
				aux2=aux2*i;
				for (int j = 1; j < 5; j++) {
					ficha=new Ficha(60+aux2, 0+aux, Color.RED, FICHA_S, false);
					campoFicha2[i-1][j-1]=ficha;
					aux=aux+30;
				}
			}
			campoFicha2[0][1].setHitbox(true);
			campoFicha2[1][1].setHitbox(true);
			campoFicha2[1][2].setHitbox(true);
			campoFicha2[2][2].setHitbox(true);
			break;
		case 3:
			for (int i = 1; i < 5; i++) {
				int aux=0;
				int aux2=30;
				aux2=aux2*i;
				for (int j = 1; j < 5; j++) {
					ficha=new Ficha(60+aux2, 0+aux, Color.MAGENTA, FICHA_CUADRADO, false);
					campoFicha2[i-1][j-1]=ficha;
					aux=aux+30;
				}
			}
			campoFicha2[1][1].setHitbox(true);
			campoFicha2[2][1].setHitbox(true);
			campoFicha2[1][2].setHitbox(true);
			campoFicha2[2][2].setHitbox(true);
			break;
		case 4:
			for (int i = 1; i < 5; i++) {
				int aux=0;
				int aux2=30;
				aux2=aux2*i;
				for (int j = 1; j < 5; j++) {
					ficha=new Ficha(60+aux2, 0+aux, Color.ORANGE, FICHA_T, false);
					campoFicha2[i-1][j-1]=ficha;
					aux=aux+30;
				}
			}
			campoFicha2[1][1].setHitbox(true);
			campoFicha2[0][2].setHitbox(true);
			campoFicha2[1][2].setHitbox(true);
			campoFicha2[2][2].setHitbox(true);
			break;
		default:
			for (int i = 1; i < 5; i++) {
				int aux=0;
				int aux2=30;
				aux2=aux2*i;
				for (int j = 1; j < 5; j++) {
					ficha=new Ficha(60+aux2, 0+aux, Color.GRAY, FICHA_CUADRADO, false);
					campoFicha2[i-1][j-1]=ficha;
					aux=aux+30;
				}
			}
			campoFicha2[0][0].setHitbox(true);
			campoFicha2[1][0].setHitbox(true);
			campoFicha2[2][0].setHitbox(true);
			campoFicha2[3][0].setHitbox(true);
			break;
		}
	}

	private void obtenerSiguienteFicha(Ficha[][] campoficha2) {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				campoFicha1[i][j]=campoficha2[i][j];
			}
		}
	}
}
