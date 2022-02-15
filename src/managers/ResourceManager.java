package managers;


import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public final class ResourceManager {
	private ResourceManager() {}
	public static AssetManager assets=new AssetManager();
	
	public static void loadAllResources(){

		//mapas
		assets.setLoader(TiledMap.class, new TmxMapLoader());
		assets.load("maps/mapa1.tmx", TiledMap.class);
		//assets.load("maps/mapa2.tmx", TiledMap.class);
        //elementos de mapa
		
        //enemigos
        //jugador
        assets.load("player/frente.png",Texture.class);
        assets.load("player/depie.png",Texture.class);
        assets.load("player/agachado.png",Texture.class);
        assets.load("player/izquierda.png",Texture.class);
        assets.load("player/derecha.png",Texture.class);
        
        //objetos
        //assets.load("objects/bomb.png",Texture.class);
        //assets.load("objects/hookl.png",Texture.class);
        //assets.load("objects/sword.png",Texture.class);
        //assets.load("objects/swordA.png",Texture.class);
        
        
        //Audio
        assets.load("audio/music/jk.mp3", Music.class);
        assets.load("audio/sounds/salto.mp3", Sound.class);
        
        //UI
        assets.load("ui/rojo.jpg", Texture.class);
        assets.load("ui/morado.jpg", Texture.class);
 
	//a�adir m�s elementos
	
	}
	
	public static boolean update(){
		return assets.update();
	}
	
	/*public static TextureAtlas getAtlas(String path){
		return assets.get(path, TextureAtlas.class);
		
	}*/
	
	public static Texture getTexture(String path) {
		return assets.get(path, Texture.class);
	}
	
	public static Music getMusic(String path){
		return assets.get(path, Music.class);
	}
	
	public static Sound getSound(String path)
	{
		return assets.get(path, Sound.class);
	}
	
	public static TiledMap getMap(String path){
		return assets.get(path, TiledMap.class);
	}

	public static void dispose(){
		assets.dispose();
	}
}
