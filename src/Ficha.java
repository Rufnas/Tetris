import java.awt.Color;
import java.lang.reflect.Array;

public class Ficha {
	//CONSTANTES
	public static final int FICHA_L=0;
	public static final int FICHA_I=1;
	public static final int FICHA_S=2;
	public static final int FICHA_CUADRADO=3;
	public static final int FICHA_T=4;
	public static final int TAMAÑO_FICHA=30;

	//DATOS
	private Color color;
	private int alto, ancho;
	private int posX, posY;
	private boolean hitbox;
	private int formaFicha,fichaPos;


	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public int getAlto() {
		return alto;
	}

	public void setAlto(int alto) {
		this.alto = alto;
	}

	public int getAncho() {
		return ancho;
	}

	public void setAncho(int ancho) {
		this.ancho = ancho;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public static int getTamañoFicha() {
		return TAMAÑO_FICHA;
	}

	public int getFormaFicha() {
		return formaFicha;
	}

	public void setFormaFicha(int formaFicha) {
		this.formaFicha = formaFicha;
	}

	public boolean isHitbox() {
		return hitbox;
	}

	public void setHitbox(boolean hitbox) {
		this.hitbox = hitbox;
	}

	public Ficha(){
		this.posX=0;
		this.posY=120;
		this.ancho=TAMAÑO_FICHA;
		this.alto=TAMAÑO_FICHA;
		this.color=Color.white;
		this.formaFicha=FICHA_I;
		this.hitbox=false;
	}

	public Ficha(int posX, int posY, Color color, int formaFicha, boolean hitbox){
		this.posX=posX;
		this.posY=posY;
		this.ancho=TAMAÑO_FICHA;
		this.alto=TAMAÑO_FICHA;
		this.color=color;
		this.formaFicha=formaFicha;
		this.hitbox=hitbox;
	}

}
