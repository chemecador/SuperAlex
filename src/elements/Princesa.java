package elements;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.Stage;

import screens.GameScreen;

public class Princesa extends Enemigo {

	private int direccion;
	private int velocidad;
	private Animation quieta;
	private Animation izquierda;
	private Animation derecha;
	private Element pie;
	private boolean pisa;

	public Princesa(float x, float y, Stage s, GameScreen nivel) {
		super(x, y, s, nivel);
		// TODO Auto-generated constructor stub
		this.setEnabled(true);
		velocidad = 100;
		izquierda = loadFullAnimation("enemies/izquierdaPrincesa.png", 2, 1, 0.2f, true);
		derecha = loadFullAnimation("enemies/derechaPrincesa.png", 2, 1, 0.2f, true);
		quieta = loadFullAnimation("enemies/izquierdaQuieta.png", 1, 1, 0.2f, true);
		direccion = -1;
		pie = new Element(0, 0, s, this.getWidth() / 4, this.getHeight() / 4);
		pie.setRectangle();
		ponerPies();

	}

	private void ponerPies() {
		if (direccion == -1) {
			pie.setPosition(this.getX(), this.getY() - this.getHeight() / 8);
		} else {
			pie.setPosition(this.getX() + this.getWidth() * 3 / 4, this.getY() - this.getHeight() / 8);
		}

	}

	@Override
	public void act(float delta) {
		// TODO Auto-generated method stub
		super.act(delta);
		pisa = false;
		/*
		 * for (Solid solido : nivel.suelo) { if (pie.overlaps(solido)) { pisa = true; }
		 * }
		 */

		if (!pisa) {
			direccion *= -1;
		}

		moveBy(direccion * velocidad * delta, 0);
		ponerPies();

	}

}
