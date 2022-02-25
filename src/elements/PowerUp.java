package elements;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.scenes.scene2d.Stage;

import screens.GameScreen;

public class PowerUp extends Element {

	public float tiempoTotal;
	public float tiempoRestante;
	public GameScreen nivel;
	public boolean activo;
	public Animation anim;
	
	public PowerUp(float x, float y, Stage s, GameScreen nivel) {
		super(x, y, s);
		this.nivel = nivel;
		this.activo = false;
		this.setEnabled(true);
	}
	
}
