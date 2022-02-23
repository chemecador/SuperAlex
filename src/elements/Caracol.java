package elements;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.Stage;

import screens.GameScreen;

public class Caracol extends Enemigo {

	private int direccion;
	private int velocidad;
	private Animation izq;
	private Animation der;
	private Element pies;
	private boolean pisa;

	public Caracol(float x, float y, Stage s, GameScreen nivel) {
		super(x, y, s, nivel);
		this.setEnabled(true);
		velocidad = 5;
		izq = loadFullAnimation("enemies/caracolIzquierda.png", 1, 1, 0.2f, true);
		der = loadFullAnimation("enemies/caracolDerecha.png", 1, 1, 0.2f, true);
		direccion = -1;
		pies = new Element(0, 0, s, this.getWidth() / 4, this.getHeight() / 4);
		pies.setRectangle();
		ponerPies();

	}
	private void ponerPies() {
		if (direccion == -1) {
			pies.setPosition(this.getX(), this.getY() - this.getHeight() / 8);
			this.setAnimation(izq);
		} else {
			pies.setPosition(this.getX() + this.getWidth() * 3 / 4, this.getY() - this.getHeight() / 8);
			this.setAnimation(der);
		}

	}
	@Override
	public void act(float delta) {
		super.act(delta);
		pisa = false;
		for (Solid solidoo : nivel.suelo) {
			if (pies.overlaps(solidoo)) {
				pisa = true;
			}

		}

		if (!pisa) {
			direccion *= -1;
		}

		moveBy(direccion * velocidad * delta, 0);
		ponerPies();

	}

}
