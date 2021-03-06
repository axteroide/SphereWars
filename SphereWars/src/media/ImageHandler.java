package media;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Autores: Victor Adrian Milla Español - 557022,
 * 			Juan Luis Burillo Ortín - 542083,
 * 			Sandra Malpica Mallo - 670607,
 * 			Richard Elvira López-Echazarreta - 666800
 * 	
 * Clase: ImageHandler.java
 * 
 * Comentarios: Manejador de las imagenes para la representación del juego en 2D
 * 
 */
public class ImageHandler {
	//Contenedores de imagenes de los elementos que estan en pantalla
	private BufferedImage tiles_image;
	private BufferedImage player_image;
	private BufferedImage items_image;
	private BufferedImage enemies_image;
	private BufferedImage hud_image;
	private BufferedImage spike_image;
	private BufferedImage topField_image;
	private BufferedImage topDessert_image;
	private BufferedImage topCastle_image;
	private BufferedImage topSnow_image;
	private BufferedImage topGhost_image;
	private BufferedImage slimeTexture_image;
	private BufferedImage gemTexture_image;
	//Ruta de aceso a las imagenes
	private String path_tiles = "images/platforms.png";
	private String path_player = "images/ball.gif";
	private String path_items = "images/items_spritesheet.png";
	private String path_enemies = "images/enemies_spritesheet.png";
	private String path_hud = "images/hud_spritesheet.png";
	private String path_spike = "images/spike.png";
	private String path_topField = "images/surface_field.png";
	private String path_topDessert = "images/surface_dessert.png";
	private String path_topCastle = "images/surface_castle.png";
	private String path_topSnow = "images/surface_snow.png";
	private String path_topGhost = "images/surface_ghost.png";
	private String path_slimeTexture = "images/slime_texture.jpg";
	private String path_gemTexture = "images/gem_texture.png";
	
	public ImageHandler(){
		initHandler();
	}
	
	private void initHandler() {
		try {
			tiles_image = ImageIO.read(new File(path_tiles));
			player_image = ImageIO.read(new File(path_player));
			items_image = ImageIO.read(new File(path_items));
			enemies_image = ImageIO.read(new File(path_enemies));
			hud_image = ImageIO.read(new File(path_hud));
			spike_image = ImageIO.read(new File(path_spike));
			topField_image = ImageIO.read(new File(path_topField));
			topDessert_image = ImageIO.read(new File(path_topDessert));
			topCastle_image = ImageIO.read(new File(path_topCastle));
			topSnow_image = ImageIO.read(new File(path_topSnow));
			topGhost_image = ImageIO.read(new File(path_topGhost));
			slimeTexture_image = ImageIO.read(new File(path_slimeTexture));
			gemTexture_image = ImageIO.read(new File(path_gemTexture));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public BufferedImage getImageTile(int x, int y, int width, int height){
		BufferedImage image = tiles_image.getSubimage(x, y, width, height);
		return image;
	}
	
	public BufferedImage getImagePlayer(int x, int y, int width, int height){
		BufferedImage image = player_image.getSubimage(x, y, width, height);
		return image;
	}
	
	public BufferedImage getImageItem(int x, int y, int width, int height){
		BufferedImage image = items_image.getSubimage(x, y, width, height);
		return image;
	}
	
	public BufferedImage getImageEnemie(int x, int y, int width, int height){
		BufferedImage image = enemies_image.getSubimage(x, y, width, height);
		return image;
	}
	
	public BufferedImage getImageHud(int x, int y, int width, int height){
		BufferedImage image = hud_image.getSubimage(x, y, width, height);
		return image;
	}
	
	public BufferedImage getImageSpike(int x, int y, int width, int height){
		BufferedImage image = spike_image.getSubimage(x, y, width, height);
		return image;
	}
	
	public BufferedImage getImageTopField(){
		return topField_image;
	}
	
	public BufferedImage getImageTopDessert(){
		return topDessert_image;
	}
	
	public BufferedImage getImageTopCastle(){
		return topCastle_image;
	}
	
	public BufferedImage getImageTopSnow(){
		return topSnow_image;
	}
	
	public BufferedImage getImageTopGhost(){
		return topGhost_image;
	}
	
	public BufferedImage getImageSlimeTexture(){
		return slimeTexture_image;
	}
	
	public BufferedImage getGemTexture(){
		return gemTexture_image;
	}
}
