package elements;

import com.badlogic.gdx.scenes.scene2d.Stage;

import game.Parametros;

public class Corazon extends Element {
	private float duracionBala = 1.5f;
	private float tiempoBala = 2 * duracionBala;
	private float velocidad;

	public Corazon(float x, float y, Stage s, float velocidad) {

		super(x, y, s);
		this.loadFullAnimation("enemies/corazon.png", 1, 1, 209, true);
		this.setEnabled(false);
		this.velocidad = velocidad;
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		if (tiempoBala >= duracionBala) {
			setEnabled(false);
		} else if (getEnabled()) {
			tiempoBala += delta;
		}
		if (getEnabled()) {
			this.moveBy(this.velocity.x * delta * velocidad, this.velocity.y * delta * velocidad);

		}
	}

	public void disparar(float velX, float velY, float x, float y) {
		this.setEnabled(true);
		this.setPosition(x, y);
		this.getVelocity().set(velX, velY);
		tiempoBala = 0;
	}
}
