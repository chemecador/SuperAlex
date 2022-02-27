package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputEvent.Type;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import elements.Consejos;
import game.Demo;
import game.Parametros;
import managers.ResourceManager;

public class TitleScreen extends BScreen {
	private Table tabla;
	private Texture background;

	public TitleScreen(Demo game) {
		super(game);
		uiStage = new Stage();
		Parametros.vidas = 3;
		this.background = new Texture("ui/fondoInicio.png");
		tabla = new Table();
		tabla.setFillParent(true);
		this.uiStage.addActor(tabla);

		cargarEtiquetas();
		cargarBotones();
	}

	private void cargarBotones() {
		cargarBotonJugar();
		cargarBotonControles();
		cargarBotonOpciones();
		cargarBotonSalir();
	}

	private void cargarBotonJugar() {

		TextButton boton = new TextButton("Jugar", ResourceManager.textButtonStyle);
		boton.addListener((Event e) -> {
			if (!(e instanceof InputEvent) || !((InputEvent) e).getType().equals(Type.touchDown))
				return false;
			this.dispose();
			game.setScreen(new GameScreen(game));
			return false;
		});
		tabla.add(boton).fill().height(Parametros.ALTURA_BOTON).spaceBottom(Parametros.ESPACIADO);
		tabla.row();

	}

	private void cargarBotonControles() {
		TextButton controles = new TextButton("Controles", ResourceManager.textButtonStyle);
		controles.addListener((Event e) -> {
			if (!(e instanceof InputEvent) || !((InputEvent) e).getType().equals(Type.touchDown))
				return false;
			this.dispose();
			game.setScreen(new ControlesScreen(game));
			return false;
		});
		tabla.add(controles).fill().height(Parametros.ALTURA_BOTON).spaceBottom(Parametros.ESPACIADO);
		tabla.row();

	}

	private void cargarBotonOpciones() {
		TextButton botonOpciones = new TextButton("Opciones", ResourceManager.textButtonStyle);
		botonOpciones.addListener((Event e) -> {
			if (!(e instanceof InputEvent) || !((InputEvent) e).getType().equals(Type.touchDown))
				return false;
			this.dispose();
			game.setScreen(new SettingsScreen(game));
			return false;
		});
		tabla.add(botonOpciones).fill().height(Parametros.ALTURA_BOTON).spaceBottom(Parametros.ESPACIADO);
		tabla.row();

	}

	private void cargarBotonSalir() {
		TextButton botonSalir = new TextButton("Salir", ResourceManager.textButtonStyle);
		botonSalir.addListener((Event e) -> {
			if (!(e instanceof InputEvent) || !((InputEvent) e).getType().equals(Type.touchDown))
				return false;
			this.dispose();
			Gdx.app.exit();
			return false;
		});
		tabla.add(botonSalir).fill().height(Parametros.ALTURA_BOTON).spaceBottom(Parametros.ESPACIADO);
	}

	private void cargarEtiquetas() {

		TextButton tituloBtn = new TextButton("SUPERALEX", ResourceManager.textButtonStyle);
		tabla.add(tituloBtn).fill().height(60).spaceBottom(100);
		tabla.row();

		if (Parametros.consejos) {
			Label consejo = new Label("Consejo: " + Consejos.consejoRandom(), ResourceManager.consejoStyle);
			consejo.setPosition(Parametros.getAnchoPantalla() / 5, Parametros.getAltoPantalla() / 20);
			consejo.setVisible(true);
			uiStage.addActor(consejo);
		}
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
