package elements;

import com.badlogic.gdx.scenes.scene2d.Stage;

import screens.GameScreen;

public class Enemigo extends Element {

	public int vida;
	public int velocidad;

	public Enemigo(float x, float y, Stage s, GameScreen nivel) {
		super(x, y, s);
		this.setEnabled(true);
	}
}
