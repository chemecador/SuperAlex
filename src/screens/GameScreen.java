package screens;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;

import elements.Bandido;
import elements.BebidaEnergetica;
import elements.Bnet;
import elements.Caracol;
import elements.Enemigo;
import elements.Player;
import elements.PowerUp;
import elements.Princesa;
import elements.Seta;
import elements.Solid;
import elements.Spider;
import game.Demo;
import game.Parametros;
import managers.AudioManager;
import managers.ResourceManager;

public class GameScreen extends BScreen {

	Stage mainStage;
	Stage uiStage;
	public Array<Solid> suelo;
	public Array<Solid> paredes;
	public Array<Solid> trampa;
	public Array<Enemigo> enemigos;
	public Bnet bnet;
	public Array<PowerUp> powerUps;
	public static boolean playerIsAlive; // true si el jugador está vivo, false si no
	Solid end;

	OrthographicCamera camara;
	private TiledMap map;

	private OrthogonalTiledMapRenderer renderer;

	public Player player;
	private float tiempoEtiqueta = 5;
	private float tiempoRestante = tiempoEtiqueta;
	private Label lbl;
	public static Label consejo;
	private int distanciaMetros;
	private int distanciaRango;
	private float inicioX;
	private float inicioY;

	public GameScreen(Demo game) {

		super(game);
		playerIsAlive = true;
		Parametros.acelerado = false;
		Parametros.drogado = false;
		Parametros.nivel += 1;
		mainStage = new Stage();
		uiStage = new Stage();

		switch (Parametros.nivel) {
		case 1:
			map = ResourceManager.getMap("maps/mapa1.tmx");
			break;
		case 2:
			map = ResourceManager.getMap("maps/mapa2.tmx");
			break;
		default:
			map = ResourceManager.getMap("maps/mapa1.tmx");
			break;
		}
		renderer = new OrthogonalTiledMapRenderer(map, mainStage.getBatch());
		camara = (OrthographicCamera) mainStage.getCamera();
		camara.setToOrtho(false, Parametros.getAnchoPantalla() * Parametros.zoom,
				Parametros.getAltoPantalla() * Parametros.zoom);

		cargarElementos();
		cargarEtiquetas();
		if (Parametros.musica) {
			AudioManager.playMusic("audio/music/jk.mp3");
			AudioManager.currentMusic.setVolume(Parametros.volumen);
		}
	}

	private void cargarEtiquetas() {
		lbl = new Label("Vidas: " + Parametros.vidas, ResourceManager.buttonStyle);
		lbl.setPosition(Parametros.getAnchoPantalla() / 20, Parametros.getAltoPantalla() / 20);
		uiStage.addActor(lbl);

		consejo = new Label("", ResourceManager.consejoStyle);
		consejo.setPosition(Parametros.getAnchoPantalla() / 3, Parametros.getAltoPantalla() / 20);
		consejo.setVisible(false);
		uiStage.addActor(consejo);

		if (Parametros.consejos && Parametros.activarConsejo && Parametros.causaMuerte == 1 && Parametros.vidas < 3) {
			consejo.setText("Debo tener cuidado con ese enemigo, ¡es peligroso!");
			consejo.setVisible(true);
		}
		if (Parametros.consejos && Parametros.activarConsejo && Parametros.causaMuerte == 3 && Parametros.vidas < 3) {
			consejo.setText("¡Auch! Me ha dado, pero no va a conseguir conquistarme...");
			consejo.setVisible(true);
		}
	}

	private void cargarElementos() {
		ArrayList<MapObject> elementos = getRectangleList("Inicio");
		MapProperties props;
		props = elementos.get(0).getProperties();
		inicioX = (float) props.get("x");
		inicioY = (float) props.get("y");
		elementos = getRectangleList("Final");
		props = elementos.get(0).getProperties();
		end = new Solid((float) props.get("x"), (float) props.get("y"), mainStage, (float) props.get("width"),
				(float) props.get("height"));

		elementos = getRectangleList("Solid");
		Solid solido;
		suelo = new Array<Solid>();
		for (MapObject solid : elementos) {
			props = solid.getProperties();
			solido = new Solid((float) props.get("x"), (float) props.get("y"), mainStage, (float) props.get("width"),
					(float) props.get("height"));
			suelo.add(solido);
		}

		elementos = getRectangleList("Pared");
		paredes = new Array<Solid>();
		for (MapObject solid : elementos) {
			props = solid.getProperties();
			solido = new Solid((float) props.get("x"), (float) props.get("y"), mainStage, (float) props.get("width"),
					(float) props.get("height"));
			paredes.add(solido);
		}
		elementos = getRectangleList("Trampa");
		
		trampa = new Array<Solid>();
		for (MapObject solid : elementos) {
			props = solid.getProperties();
			solido = new Solid((float) props.get("x"), (float) props.get("y"), mainStage, (float) props.get("width"),
					(float) props.get("height"));
			trampa.add(solido);
		}

		enemigos = new Array<Enemigo>();
		for (MapObject obj : getEnemyList()) {
			props = obj.getProperties();
			switch (props.get("Enemy").toString()) {
			case "Caracol":
				Caracol c = new Caracol((float) props.get("x"), (float) props.get("y"), mainStage, this);
				enemigos.add(c);
				break;
			case "Spider":
				Spider s = new Spider((float) props.get("x"), (float) props.get("y"), mainStage, this);
				enemigos.add(s);
				break;
			case "Princesa":
				Princesa p = new Princesa((float) props.get("x"), (float) props.get("y"), mainStage, this);
				enemigos.add(p);
				break;
			case "Bandido":
				Bandido b = new Bandido((float) props.get("x"), (float) props.get("y"), mainStage, this);
				enemigos.add(b);
				break;
			case "Bnet":
				bnet = new Bnet((float) props.get("x"), (float) props.get("y"), mainStage, this);
				enemigos.add(bnet);
				break;
			}
		}

		powerUps = new Array<PowerUp>();
		for (MapObject obj : getPowerUpList()) {
			props = obj.getProperties();
			switch (props.get("PowerUp").toString()) {
			case "Seta":
				Seta seta = new Seta((float) props.get("x"), (float) props.get("y"), mainStage, this);
				powerUps.add(seta);
				break;

			case "BebidaEnergetica":
				BebidaEnergetica be = new BebidaEnergetica((float) props.get("x"), (float) props.get("y"), mainStage,
						this);
				powerUps.add(be);
				break;
			}
		}
		player = new Player(inicioX, inicioY, mainStage);

	}

	@Override
	public void render(float delta) {
		super.render(delta);
		mainStage.act();
		uiStage.act();
		colide();

		Parametros.jugadorx = player.getX();
		Parametros.jugadory = player.getY();
		player.colocarPies();

		centrarCamara();
		if (bnet != null)
		cantar();
		
		if (!playerIsAlive) {
			AudioManager.playSound("audio/sounds/au.mp3");
			if (Parametros.vidas == 0) {
				dispose();
				game.setScreen(new GameOverScreen(game));
			} else {
				Parametros.nivel = 0;
				game.setScreen(new GameScreen(game));
			}
		}
		if (consejo.isVisible()) {
			if (tiempoRestante <= 0) {
				consejo.setVisible(false);
				tiempoRestante = tiempoEtiqueta;
			} else {
				tiempoRestante = tiempoRestante - delta;
			}
		}
		renderer.setView(camara);
		renderer.render();

		mainStage.draw();
		uiStage.draw();

	}

	private void cantar() {

		distanciaMetros = Math.abs((int)Math.abs(bnet.getY()) - (int)Math.abs(player.getY()));

		
		if (distanciaMetros>1000)
			return;
		
		AudioManager.playMusic("audio/music/bnet.mp3");
		distanciaRango = 0;
		if (distanciaMetros < 20)
			distanciaRango = 0;
		else if (distanciaMetros < 250)
			distanciaRango = 1;
		else if (distanciaMetros < 500)
			distanciaRango = 2;
		else if (distanciaMetros < 750)
			distanciaRango = 3;
		else 
			distanciaRango = 4;

		switch (distanciaRango) {
		case 0:
			Parametros.volumen = 1f;
			break;
		case 1:
			Parametros.volumen = 0.6f;
			break;
		case 2:
			Parametros.volumen = 0.3f;
			break;
		case 3:
			Parametros.volumen = 0.1f;
			break;
		default:
			Parametros.volumen = 0f;
			break;
		}
		AudioManager.currentMusic.setVolume(Parametros.volumen);

	}

	public void colide() {
		player.tocoSuelo = false;
		
		for (Solid t : trampa) {
			if (t.getEnabled() && t.overlaps(player)) {
				player.setPosition(inicioX, inicioY);
				consejo.setText("Casi me caigo, debo tener más cuidado...");
				Parametros.activarConsejo = true;
				consejo.setVisible(true);
			}
				
		}

		for (Solid pared : paredes) {
			if (pared.getEnabled() && pared.overlaps(player)) {
				player.preventOverlap(pared);
			}
		}
		for (Solid b : suelo) {
			if (b.getEnabled() && b.overlaps(player)) {
				player.preventOverlap(b);
			}
			if (player.pies.overlaps(b)) {
				player.tocoSuelo = true;
			}
		}
		if (bnet != null && player.pies.overlaps(bnet.cabeza)) {
			bnet.morir();
			bnet.cabeza.setEnabled(false);
			AudioManager.currentMusic.stop();
			consejo.setText("Así seguro que se calla");
			Parametros.activarConsejo = true;
			consejo.setVisible(true);
		}
		pelear();
		for (PowerUp pu : powerUps) {
			if (player.overlaps(pu)) {
				pu.activo = true;
				pu.setEnabled(false);
			}
		}
		if (player.overlaps(end)) {

			this.dispose();
			if (Parametros.nivel < 2)
				game.setScreen(new GameScreen(game));
			else 
				game.setScreen(new FinalScreen(game));

		}

	}

	private void pelear() {
		// enemigos
		for (Enemigo e : enemigos) {
			// choque con peligroso
			if (e.tieneCabeza() && player.pies.overlaps(e.cabeza) && e.isAlive()) {
				e.morir();
				if (!e.peligroso) {
					consejo.setText("Pobrecillo, no tendría que haber hecho eso. Era inofensivo...");
					Parametros.activarConsejo = true;
					consejo.setVisible(true);
				}
				if (e.peligroso) {
					consejo.setText("He hecho bien. ¡Estaba a punto de atacarme!");
					Parametros.activarConsejo = true;
					consejo.setVisible(true);
				}
			}
			if (e.overlaps(player) && e.isAlive()) {
				player.preventOverlap(e);
			}
			if (e.pies != null && e.pies.overlaps(player) && e.peligroso && e.isAlive()) {
				player.preventOverlap(e);
				AudioManager.playSound("audio/sounds/au.mp3");
				Parametros.vidas--;
				Parametros.nivel--;
				if (Parametros.vidas == 0) {
					this.dispose();
					game.setScreen(new GameOverScreen(game));
				} else {
					Parametros.causaMuerte = 1;
					Parametros.activarConsejo = true;
					game.setScreen(new GameScreen(game));
				}

			}
			/*
			 * if (e.overlaps(player) && !e.peligroso && e.isAlive()) {
			 * player.preventOverlap(e); }
			 */
		}

	}

	public void centrarCamara() {
		this.camara.position.x = Parametros.getAnchoPantalla() * 5 / 8;
		this.camara.position.y = player.getY();
		camara.update();

	}

	public ArrayList<MapObject> getRectangleList(String propertyName) {
		ArrayList<MapObject> list = new ArrayList<MapObject>();
		for (MapLayer layer : map.getLayers()) {
			for (MapObject obj : layer.getObjects()) {
				if (!(obj instanceof RectangleMapObject))
					continue;
				MapProperties props = obj.getProperties();
				if (props.containsKey("name") && props.get("name").equals(propertyName)) {
					list.add(obj);
				}

			}

		}

		return list;
	}

	public ArrayList<MapObject> getEnemyList() {
		ArrayList<MapObject> list = new ArrayList<MapObject>();
		for (MapLayer layer : map.getLayers()) {
			for (MapObject obj : layer.getObjects()) {
				if (!(obj instanceof TiledMapTileMapObject))
					continue;
				MapProperties props = obj.getProperties();

				TiledMapTileMapObject tmtmo = (TiledMapTileMapObject) obj;
				TiledMapTile t = tmtmo.getTile();
				MapProperties defaultProps = t.getProperties();
				if (defaultProps.containsKey("Enemy")) {
					list.add(obj);

				}

				Iterator<String> propertyKeys = defaultProps.getKeys();
				while (propertyKeys.hasNext()) {
					String key = propertyKeys.next();

					if (props.containsKey(key))
						continue;
					else {
						Object value = defaultProps.get(key);
						props.put(key, value);
					}

				}

			}

		}

		return list;
	}

	public ArrayList<MapObject> getPowerUpList() {
		ArrayList<MapObject> list = new ArrayList<MapObject>();
		for (MapLayer layer : map.getLayers()) {
			for (MapObject obj : layer.getObjects()) {
				if (!(obj instanceof TiledMapTileMapObject))
					continue;
				MapProperties props = obj.getProperties();

				TiledMapTileMapObject tmtmo = (TiledMapTileMapObject) obj;
				TiledMapTile t = tmtmo.getTile();
				MapProperties defaultProps = t.getProperties();
				if (defaultProps.containsKey("PowerUp")) {
					list.add(obj);
				}

				Iterator<String> propertyKeys = defaultProps.getKeys();
				while (propertyKeys.hasNext()) {
					String key = propertyKeys.next();

					if (props.containsKey(key))
						continue;
					else {
						Object value = defaultProps.get(key);
						props.put(key, value);
					}

				}

			}

		}

		return list;
	}

}
