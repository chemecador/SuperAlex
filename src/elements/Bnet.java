package elements;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.Stage;

import screens.GameScreen;

public class Bnet extends Enemigo {

	private Animation anim;

	public Bnet(float x, float y, Stage s, GameScreen nivel) {
		super(x, y, s, nivel);
		setEnabled(true);
		peligroso = false;
		anim = loadFullAnimation("enemies/bnet.png", 2, 1, 0.2f, true);
		//this.setPolygon(0);

	}
}
