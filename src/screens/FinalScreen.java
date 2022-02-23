package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputEvent.Type;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;

import game.Demo;
import game.Parametros;
import managers.ResourceManager;

public class FinalScreen extends BScreen {
	private Table tabla;
	private Texture background;

	public FinalScreen(Demo game) {
		super(game);
		this.background = new Texture("ui/fondofinal.png");
		tabla = new Table();
		tabla.setFillParent(true);

		this.uiStage.addActor(tabla);
		TextButton volver = new TextButton("Volver a jugar",ResourceManager.textButtonStyle /*ResourceManager.getBoton(Color.GRAY)*/);
		volver.addListener((Event e) -> {
			if (!(e instanceof InputEvent) || !((InputEvent) e).getType().equals(Type.touchDown))
				return false;
			this.dispose();
			game.setScreen(new TitleScreen(game));
			return false;
		});
		tabla.add(volver);
	}

	@Override
	public void render(float delta) {
		super.render(delta);		
		uiStage.act();
        uiStage.getBatch().begin();
        uiStage.getBatch().draw(background, 0, 0, Parametros.getAnchoPantalla(), Parametros.getAltoPantalla());
        uiStage.getBatch().end();
        uiStage.draw();

	}

}
