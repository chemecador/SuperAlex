package elements;

import com.badlogic.gdx.scenes.scene2d.Stage;

import game.Parametros;
import screens.GameScreen;

public class BebidaEnergetica extends PowerUp {

	public BebidaEnergetica(float x, float y, Stage s, GameScreen nivel) {
		super(x, y, s, nivel);
		tiempoTotal = 10;
		tiempoRestante = tiempoTotal;
		anim = loadFullAnimation("ui/monster.png", 1, 1, 0.2f, true);
		setRectangle();
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		if (activo) {
			if (tiempoRestante <= 0) {
				Parametros.acelerado = false;
			} else {
				tiempoRestante = tiempoRestante - delta;
				Parametros.acelerado = true;
			}
		}
	}
}
