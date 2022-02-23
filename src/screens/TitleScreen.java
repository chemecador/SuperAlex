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

public class TitleScreen extends BScreen {
	private static final int ALTURA_BOTON = 55;
	private static final int ESPACIADO = 40;
	private Table tabla;
	private Texture background;

	public TitleScreen(Demo game) {
		super(game);
		// TODO Auto-generated constructor stub
		this.background = new Texture("ui/fondoinicio.png");
		tabla = new Table();
		tabla.setFillParent(true);

		this.uiStage.addActor(tabla);
		
		Label.LabelStyle titulo = new Label.LabelStyle();
		titulo.font = ResourceManager.fuentePropia;
		TextButton tituloBtn = new TextButton("SUPERALEX", ResourceManager.textButtonStyle);
		tabla.add(tituloBtn).fill().height(60).spaceBottom(100);
		tabla.row();

		TextButton boton = new TextButton("Jugar", ResourceManager.textButtonStyle);
		boton.addListener((Event e) -> {
			if (!(e instanceof InputEvent) || !((InputEvent) e).getType().equals(Type.touchDown))
				return false;
			this.dispose();
			game.setScreen(new GameScreen(game));
			return false;
		});
		tabla.add(boton).fill().height(ALTURA_BOTON).spaceBottom(ESPACIADO);
		tabla.row();

		TextButton botonOpciones = new TextButton("Opciones", ResourceManager.textButtonStyle);
		botonOpciones.addListener((Event e) -> {
			if (!(e instanceof InputEvent) || !((InputEvent) e).getType().equals(Type.touchDown))
				return false;
			this.dispose();
			game.setScreen(new SettingsScreen(game));
			return false;
		});
		tabla.add(botonOpciones).fill().height(ALTURA_BOTON).spaceBottom(ESPACIADO);
		tabla.row();
		TextButton botonSalir = new TextButton("Salir", ResourceManager.textButtonStyle);
		botonSalir.addListener((Event e) -> {
			if (!(e instanceof InputEvent) || !((InputEvent) e).getType().equals(Type.touchDown))
				return false;
			this.dispose();
			Gdx.app.exit();
			return false;
		});
		tabla.add(botonSalir).fill().height(ALTURA_BOTON).spaceBottom(ESPACIADO);
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
