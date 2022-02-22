package managers;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public final class ResourceManager {
	private ResourceManager() {}
	public static AssetManager assets=new AssetManager();
	public static LabelStyle buttonStyle;
	public static TextButtonStyle textButtonStyle;
	public static BitmapFont fuentePropia;
	
	public static void loadAllResources(){

		//mapas
		assets.setLoader(TiledMap.class, new TmxMapLoader());
		assets.load("maps/mapa1.tmx", TiledMap.class);
		assets.load("maps/tiles.png", TiledMap.class);
		assets.load("maps/mapa2.tmx", TiledMap.class);
		
        //elementos de mapa
		
        //enemigos
		assets.load("enemies/spider.png", Texture.class);
		assets.load("enemies/bandidoDerecha.png", Texture.class);
		assets.load("enemies/bandidoIzquierda.png", Texture.class);
		assets.load("enemies/caracolDerecha.png", Texture.class);
		assets.load("enemies/caracolIzquierda.png", Texture.class);
        assets.load("enemies/princesaIzquierda.png",Texture.class);
        assets.load("enemies/princesaQuieta.png",Texture.class);
        assets.load("enemies/princesaDerecha.png",Texture.class);
        assets.load("enemies/corazon.png",Texture.class);
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
 
	//añadir más elementos
	
	}
	

	public static void botones() {
		
		//estilo para botones
        FreeTypeFontGenerator ftfg= new FreeTypeFontGenerator(Gdx.files.internal("sans.ttf"));
		FreeTypeFontParameter ftfp= new FreeTypeFontParameter();
		
		ftfp.size=36;
		ftfp.color=Color.WHITE;
		ftfp.borderColor=Color.BLACK;
		ftfp.borderWidth=2;
		
		fuentePropia=ftfg.generateFont(ftfp);
		buttonStyle=new LabelStyle();
		buttonStyle.font=fuentePropia;
		textButtonStyle=new TextButtonStyle();
		Texture buttonText = ResourceManager.getTexture("enemies/corazon.png");
		NinePatch buttonPatch = new NinePatch(buttonText);
		textButtonStyle.up=new NinePatchDrawable(buttonPatch);
		textButtonStyle.font=fuentePropia;
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
