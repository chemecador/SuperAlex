package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputEvent.Type;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import game.Demo;
import game.Parametros;
import managers.ResourceManager;

public class TitleScreen extends BScreen {
	private Table tabla;

	public TitleScreen(Demo game) {
		super(game);
		// TODO Auto-generated constructor stub

		tabla = new Table();
		tabla.setFillParent(true);

		this.uiStage.addActor(tabla);
		
		Label.LabelStyle titulo = new Label.LabelStyle();
		titulo.font = ResourceManager.fuentePropia;
		TextButton tituloBtn = new TextButton("SUPERALEX", ResourceManager.textButtonStyle);
		tabla.add(tituloBtn);
		tabla.row();

		TextButton boton = new TextButton("Jugar", ResourceManager.textButtonStyle);
		boton.addListener((Event e) -> {
			if (!(e instanceof InputEvent) || !((InputEvent) e).getType().equals(Type.touchDown))
				return false;
			this.dispose();
			game.setScreen(new GameScreen(game));
			return false;
		});
		tabla.add(boton);
		tabla.row();

		TextButton botonOpciones = new TextButton("Opciones", ResourceManager.textButtonStyle);
		tabla.add(botonOpciones);
		tabla.row();
		TextButton botonSalir = new TextButton("Salir", ResourceManager.textButtonStyle);
		botonSalir.addListener((Event e) -> {
			if (!(e instanceof InputEvent) || !((InputEvent) e).getType().equals(Type.touchDown))
				return false;
			this.dispose();
			Gdx.app.exit();
			return false;
		});
		tabla.add(botonSalir);
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		super.render(delta);

		uiStage.act();
		uiStage.draw();

	}

}
