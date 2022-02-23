package game;

public class Parametros {

	//Screen

	private static int anchoPantalla = 1200;
	private static int altoPantalla = 900;

	public static final int ALTURA_BOTON = 55;
	public static final int ESPACIADO = 40;

	public static boolean debug = true;

	// Audio;
	public static boolean musica = false;
	public static float musicVolume = 0.1f;
	public static float soundVolume = 1;

	public static float zoom = 1.3f;

	//Variables de juego
	public static int nivel = 0;
	
	//Variables del jugador
	public static int gravedad = -1000;
	public static float jugadorx = 0;
	public static float jugadory = 0;
	public static int vidas = 3;

	public static int getAnchoPantalla() {
		return anchoPantalla;
	}

	public static void setAnchoPantalla(int anchoPantalla) {
		Parametros.anchoPantalla = anchoPantalla;
	}

	public static int getAltoPantalla() {
		return altoPantalla;
	}

	public static void setAltoPantalla(int altoPantalla) {
		Parametros.altoPantalla = altoPantalla;
	}

}
