package game;

public class Parametros {

	//Screen

	private static int ANCHO_PANTALLA = 1200;
	private static int ALTO_PANTALLA = 900;

	public static final int ALTURA_BOTON = 55;
	public static final int ESPACIADO = 40;

	public static boolean debug = true;

	// Audio;
	public static boolean musica = false;
	public static boolean consejos = true;
	public static float volumen = 0.5f;

	public static float zoom = 1.3f;

	//Variables de juego
	public static int nivel = 0;
	
	//Variables del jugador
	public static int gravedad = -1000;
	public static float jugadorx = 0;
	public static float jugadory = 0;
	public static int vidas = 3;
	
	/***	 
	 * causaMuerte = 0 -> No ha muerto
	 * causaMuerte = 1 -> Bandido	 
	 * causaMuerte = 2 -> Princesa	 
	 * causaMuerte = 3 -> Arañusco
	 */
	public static int causaMuerte = 0;
	
	public static boolean activarConsejo = false;
	


	public static int getAnchoPantalla() {
		return ANCHO_PANTALLA;
	}

	public static void setAnchoPantalla(int anchoPantalla) {
		Parametros.ANCHO_PANTALLA = anchoPantalla;
	}

	public static int getAltoPantalla() {
		return ALTO_PANTALLA;
	}

	public static void setAltoPantalla(int altoPantalla) {
		Parametros.ALTO_PANTALLA = altoPantalla;
	}

}
