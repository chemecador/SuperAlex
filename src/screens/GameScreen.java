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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

import elements.Barril;
import elements.Blob;
import elements.Element;
import elements.Enemigo;
import elements.Player;
import elements.Solid;
import game.Demo;
import game.Parametros;
import managers.ResourceManager;
import ui.BarraVida;


public class GameScreen extends BScreen{
	
Stage mainStage;
Stage uiStage;
Array<Barril> barriles;
public Array<Solid> suelo;
public Array<Enemigo> enemigos;
Solid end;


OrthographicCamera camara;
private TiledMap map;
private int tileWidth, tileHeight, mapWidthInTiles, mapHeightInTiles,
mapWidthInPixels, mapHeightInPixels;
private BarraVida barra;




private OrthogonalTiledMapRenderer renderer;
	   
private Player player;

	public GameScreen(Demo game) {
	
		super(game);
		
		Parametros.nivel+=1;
		mainStage=new Stage();
		float inicioX;
		float inicioY;
		System.out.println(Parametros.nivel);
		switch(Parametros.nivel) {
		case 1:
		map= ResourceManager.getMap("maps/mapa0.tmx");
		break;
		case 2:
		
		map= ResourceManager.getMap("maps/mapa2.tmx");
		break;
		default: 
			map= ResourceManager.getMap("maps/mapa0.tmx");
			
			
			break;}
		
		
		
		MapProperties properties=map.getProperties();
		
		tileWidth=properties.get("tilewidth", Integer.class);
		tileHeight=properties.get("tileheight", Integer.class);
		mapWidthInTiles=properties.get("width", Integer.class);
		mapHeightInTiles=properties.get("height", Integer.class);
		mapWidthInPixels=tileWidth*mapWidthInTiles;
		mapHeightInPixels=tileHeight*mapHeightInTiles;
		
		
		renderer=new OrthogonalTiledMapRenderer(map,mainStage.getBatch());
		
		
		camara=(OrthographicCamera) mainStage.getCamera();
		camara.setToOrtho(false, Parametros.getAnchoPantalla()*Parametros.zoom, Parametros.getAltoPantalla()*Parametros.zoom);
		
		
		ArrayList<MapObject> elementos=getRectangleList("Inicio");
		MapProperties props;
		props=elementos.get(0).getProperties();
		inicioX=(float)props.get("x");
		inicioY=(float)props.get("y");
		
		
		
		elementos=getRectangleList("Final");
		props=elementos.get(0).getProperties();
		end=new Solid((float)props.get("x"),(float)props.get("y"), mainStage,
				(float)props.get("width"),(float)props.get("height"));
		
		
		elementos=getRectangleList("Solid");
		
		Solid solido;
		suelo=new Array<Solid>();
		for(MapObject solid:elementos) {
			props=solid.getProperties();
			solido=new Solid((float)props.get("x"),(float)props.get("y"), mainStage,
					(float)props.get("width"),(float)props.get("height"));
			
			suelo.add(solido);
			
		}
		
		
		enemigos=new Array<Enemigo>();
		for(MapObject obj:getEnemyList()) {
			props=obj.getProperties();
			switch(props.get("Enemy").toString()) {
			case "Blob":
				Blob blob=new Blob((float)props.get("x"), (float)props.get("y"), mainStage, this);
				enemigos.add(blob);
				break;
			
			
			}
			
			
			
			
			
		}
		
		
		
		player=new Player(inicioX,inicioY,mainStage);
	barriles= new Array<Barril>();
		for(int i=0; i<10; i++) {
			barriles.add(new Barril(i*14,30,mainStage));
		}
		
		uiStage=new Stage();
		barra= new BarraVida(Parametros.getAnchoPantalla()/50,Parametros.getAltoPantalla()*9/10,this.uiStage);
		
		
		
	}
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		super.render(delta);
	     mainStage.act();
	     uiStage.act();
	    colide();
	    
	    Parametros.jugadorx=player.getX();
	    Parametros.jugadory=player.getY();
	    player.colocarPies();
	    
	    centrarCamara();
	    renderer.setView(camara);
	    renderer.render();
	    
	        mainStage.draw();
	        uiStage.draw();
	    

	}
	
	public void colide() {
		player.tocoSuelo=false;
		for(Barril b:barriles) {
			
			
			if(b.getEnabled() && b.overlaps(player)) {
				player.preventOverlap(b);
				
				//b.preventOverlap(player);
				
			}
			
			
			if(player.pies.overlaps(b)) {
				player.tocoSuelo=true;
				
			}
			
			
		
		}
		
for(Solid b:suelo) {
			
			
			if(b.getEnabled() && b.overlaps(player)) {
				player.preventOverlap(b);
				
				//b.preventOverlap(player);
				
			}
			
			
			if(player.pies.overlaps(b)) {
				player.tocoSuelo=true;
			}
			
		}


if(player.overlaps(end) ) {
	

	this.dispose();
	game.setScreen(new GameScreen(game));
	
}
		
	}
	
	public void centrarCamara() {
		this.camara.position.x=player.getX();
		this.camara.position.y=player.getY();
		camara.update();
		
	}
	
	
	public ArrayList<MapObject> getRectangleList(String propertyName){
		ArrayList<MapObject> list =new ArrayList<MapObject>();
		for(MapLayer layer: map.getLayers()) {
			for(MapObject obj: layer.getObjects()) {
				if(!(obj instanceof RectangleMapObject))
					continue;
				MapProperties props= obj.getProperties();
				if(props.containsKey("name") &&  props.get("name").equals(propertyName))
				{
					list.add(obj);
				}
				
			}
			
		}
		
		return list;
	}
	
	public ArrayList<MapObject> getEnemyList(){
		ArrayList<MapObject> list =new ArrayList<MapObject>();
		for(MapLayer layer: map.getLayers()) {
			for(MapObject obj: layer.getObjects()) {
				if(!(obj instanceof TiledMapTileMapObject))
					continue;
				MapProperties props= obj.getProperties();
				
				
				TiledMapTileMapObject tmtmo=(TiledMapTileMapObject) obj;
				TiledMapTile t=tmtmo.getTile();
				MapProperties defaultProps=t.getProperties();
				if(defaultProps.containsKey("Enemy")) {
					list.add(obj);
					
					
				}
				
				
				Iterator<String> propertyKeys=defaultProps.getKeys();
				while(propertyKeys.hasNext()) {
					String key =propertyKeys.next();
					
					if(props.containsKey(key))
						continue;
					else {
						Object value=defaultProps.get(key);
						props.put(key, value);
					}
						
				}
				
			}
			
		}
		
		return list;
	}
	
	
	
	
	
}
