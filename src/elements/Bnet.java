package elements;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.Stage;

import screens.GameScreen;

public class Bnet extends Enemigo {

	private Animation anim;
	public Element cabeza;

	public Bnet(float x, float y, Stage s, GameScreen nivel) {
		super(x, y, s, nivel);
		setEnabled(true);
		direccion = 0;
		velocidad = 0;
		peligroso = false;
		anim = loadFullAnimation("enemies/bnet.png", 2, 1, 0.2f, true);
		cabeza = new Element(0, 0, s, this.getWidth() / 3, this.getHeight() / 8);
		cabeza.setRectangle();
		cabeza.setPosition(this.getX() + this.getWidth() /3, this.getY() + this.getHeight() * 7 / 8);
	}
	@Override
	public void morir() {
		super.morir();
		cabeza.setEnabled(false);
	}
}
