package elements;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.Stage;

import screens.GameScreen;

public class Spider extends Enemigo {

	private int direccion;
	private int velocidad;
	private Animation spider;
	private Element pie;
	private boolean pisa;
	private GameScreen nivel;

	public Spider(float x, float y, Stage s, GameScreen nivel) {
		super(x, y, s, nivel);
		// TODO Auto-generated constructor stub
		this.setEnabled(true);
		spider = loadFullAnimation("enemies/spider.png", 1, 1, 0.2f, true);
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
		for (Solid solido : nivel.suelo) {
			if (pie.overlaps(solido)) {
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
