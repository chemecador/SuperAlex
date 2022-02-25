package elements;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

import game.Parametros;
import managers.AudioManager;
import screens.GameScreen;

public class Princesa extends Enemigo {
	
	private float aceleracion;
	private float tiempoDisparo;
	private float cuentaDisparo;
	private Array<Corazon> corazones;
	boolean persiguiendo;
	private int numCorazones;
	private int corazonActual;
	private int numero;
	private Animation quieta;
	private Animation izquierda;
	private Animation derecha;
	private boolean pisa;

	public Princesa(float x, float y, Stage s, GameScreen nivel) {
		super(x, y, s, nivel);
		this.setEnabled(true);
		tieneCabeza = true;
		peligroso = true;
		persiguiendo = false;
		numCorazones = 10;
		corazonActual = 0;
		corazones = new Array<Corazon>();
		tiempoDisparo = 3;
		cuentaDisparo = 0;
		velocidad = 30;
		izquierda = loadFullAnimation("enemies/princesaIzquierda.png", 2, 1, 0.2f, true);
		derecha = loadFullAnimation("enemies/princesaDerecha.png", 2, 1, 0.2f, true);
		quieta = loadFullAnimation("enemies/princesaQuieta.png", 1, 1, 0.2f, true);
		direccion = -1;
		pies = new Element(0, 0, s, this.getWidth() / 4, this.getHeight() / 4);
		pies.setRectangle();

		for (int i = 0; i < numCorazones; i++) {
			corazones.add(new Corazon(0, 0, s, 100));

		}

		cabeza = new Element(0, 0, s, this.getWidth() / 3, this.getHeight() / 8);
		cabeza.setRectangle();
		ponerCabeza();
		ponerPies();

	}

	private void ponerCabeza() {
		cabeza.setPosition(this.getX() + this.getWidth() * 4 / 12, this.getY() + this.getHeight() * 7 / 8);
	}

	private void ponerPies() {
		if (direccion == -1) {
			this.setAnimation(izquierda);
			pies.setPosition(this.getX(), this.getY() - this.getHeight() / 8);
		} else {
			this.setAnimation(derecha);
			pies.setPosition(this.getX() + this.getWidth() * 3 / 4, this.getY() - this.getHeight() / 8);
		}

	}
	@Override
	public void morir() {
		super.morir();
		pies.setEnabled(false);
		cabeza.setEnabled(false);
	}
	@Override
	public void act(float delta) {
		super.act(delta);
		pisa = false;

		for (Solid pared : nivel.paredes) {
			if (pies.overlaps(pared)) {
				direccion *= -1;
				break;
			}
		}

		for (Solid solido : nivel.suelo) {
			if (pies.overlaps(solido)) {
				pisa = true;
			}
		}

		if (!pisa) {
			direccion *= -1;
		}

		moveBy(direccion * velocidad * delta, 0);

		if (this.getEnabled()) {
			perseguir();
			if (cuentaDisparo <= 0) {
				disparar();
			} else {
				cuentaDisparo -= delta;
			}
			this.applyPhysics(delta);
		}
		for (Corazon c : corazones) {
			if (c.getEnabled() && c.overlaps(nivel.player)) {
				Parametros.vidas--;
				GameScreen.playerIsAlive = false;
				AudioManager.playSound("audio/sounds/au.mp3");
				Parametros.causaMuerte = 3;
				Parametros.activarConsejo = true;
				GameScreen.consejo.setVisible(true);
				c.setEnabled(false);

			}

		}
		ponerPies();
		ponerCabeza();

	}

	public void perseguir() {
		if (Parametros.jugadorx < this.getX()) {
			acceleration.add(-aceleracion, 0);
		} else if (Parametros.jugadorx > this.getX()) {
			acceleration.add(aceleracion, 0);
		}
		if (Parametros.jugadory < this.getY()) {
			acceleration.add(0, -aceleracion);
		} else if (Parametros.jugadory > this.getY()) {
			acceleration.add(0, aceleracion);
		}
	}

	public void disparar() {
		float x = Parametros.jugadorx - this.getX();
		float y = Parametros.jugadory - this.getY();
		Vector2 vector = new Vector2(x, y).nor();
		cuentaDisparo = tiempoDisparo;
		corazones.get(this.corazonActual).disparar(vector.x, vector.y, this.getX(), this.getY());
		corazonActual++;
		corazonActual = corazonActual % numCorazones;

	}

}