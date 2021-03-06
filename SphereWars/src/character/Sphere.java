package character;

import java.awt.Graphics2D;
import java.awt.Image;

import audio.Audio;
import audio.AudioClip;
import java.awt.Rectangle;
import java.awt.dnd.DragGestureEvent;
import java.awt.image.BufferedImage;

import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Material;
import javax.media.j3d.Texture;
import javax.media.j3d.TextureAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;

import com.sun.corba.se.impl.orbutil.closure.Constant;
import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.image.TextureLoader;

import graphic.Sprite;
import map.MapController;
import map.MapObject;
import obstacle.Liquid;
import utils.Constants;
import videogame.Game;
import videogame.GameObject;

/**
 * Autores: Victor Adrian Milla Español - 557022,
 * 			Juan Luis Burillo Ortín - 542083,
 * 			Sandra Malpica Mallo - 670607,
 * 			Richard Elvira López-Echazarreta - 666800
 * 	
 * Clase: Sphere.java
 * 
 * Comentarios: Objeto que representa al jugador en la pantalla
 * 
 */
public class Sphere extends GameObject implements Sprite{
	//Tipo de plataforma, depende del tipo usa un sprite u otro
	public static final int NORMAL = 0;
	public static final int JUMP = 1;
	public static final int SPEED_UP = 2;
	public static final int SPEED_DOWN = 3;
	//Resultados colisiones
	public static final int NOCOLLISION = -1;
	public static final int COLLINF = 0;
	public static final int COLLSUP = 1;
	public static final int COLLLAT = 2;
	public static final int COLLINFLAT = 3;
	public static final int COLLSUPLAT = 4;
	public static final int COLLDEATH = 5;
	public static final int COLLKILL = 6;
	public static final int COLLGET = 7;

	//Posicion de la imagen a representar
	private static int[] x_imgs={2};
	private static int[] y_imgs={0};
	//Tamaño de la imagen
	private static int[] width_imgs={30};
	private static int[] height_imgs={30};
	//Estado del jugador
	private int type;
	//Logica
	private int counter = Constants.speedActions;
	private boolean jump = false;
	private int canJump = 0;	
	//private final int jumpVelocity = -18;
	private int jumpVelocity = -block_width/3;
	private final int miniJumpVelocity = -12;
	private int jumpSpeed = jumpVelocity;
	private int maxGravity = 10;
	private int maxX = block_width*6;  
	private int totalX = 0;
	boolean nextMap = false;
	private AudioClip soundJump;
	private AudioClip deathLiquid;
	private AudioClip takeSound;
	private int x_ori;
	private int y_ori;
	private int tick_action;

	/**
	 * Constructor del jugador
	 * @param x
	 * @param y
	 * @param block_width
	 * @param block_height
	 * @param num_players
	 */
	public Sphere(int x, int y, int block_width,int block_height, int num_players) {
		super(x,y,x_imgs[0],y_imgs[0], width_imgs[0], height_imgs[0], block_width, block_height);
		this.type = NORMAL;
		this.kills = false;
		tick_action = 0;
		soundJump = Audio.Load("audioEffects/boing_x.wav");
		deathLiquid = Audio.Load("audioEffects/splash.wav");
		takeSound = Audio.Load("audioEffects/coin.wav");
		takeSound.setRepeat(true);
		selectImage();
		resize();
		if(Constants.visualMode == Game.MODE_3D){
			loadModel3D();
		}
		if(Constants.scale==2){
			jumpVelocity = -13/num_players;
		}else if(Constants.scale==4){
			jumpVelocity = -16/num_players;			
		}
		this.x_ori=Constants.xOri;
		this.y_ori=Constants.yOri;
	}

	/**
	 * Carga el modelo 3D del jugador
	 */
	private void loadModel3D(){
		//Apariencia de la esfera
		Appearance app = new Appearance();
		//Material de la esfera
		Material mat = new Material();
		mat.setAmbientColor(new Color3f(0.015f,0.03f,0.2f));
		mat.setDiffuseColor(new Color3f(0.0784f,0.1254f,0.8078f));
		app.setMaterial(mat);	    
		//Creacion de la esfera
		object_primitive = new com.sun.j3d.utils.geometry.Sphere(block_width*0.0007f,app);
		tg_model3D = new TransformGroup();
		tg_model3D.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		tg_model3D.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		tg_model3D.setCapability(TransformGroup.ALLOW_CHILDREN_EXTEND);
		tg_model3D.setCapability(TransformGroup.ALLOW_CHILDREN_WRITE);
		tg_model3D.setCapability(TransformGroup.ALLOW_CHILDREN_READ);
		branch_group = new BranchGroup();
		branch_group.addChild(object_primitive);
		branch_group.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		branch_group.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		branch_group.setCapability(TransformGroup.ALLOW_CHILDREN_EXTEND);
		branch_group.setCapability(TransformGroup.ALLOW_CHILDREN_WRITE);
		branch_group.setCapability(TransformGroup.ALLOW_CHILDREN_READ);
		tg_model3D.addChild(branch_group);
		//Establece la posicion en 3D
		setPosition3D();
	}

	/**
	 * Actualiza la posicion 3D objeto
	 */
	private void setPosition3D() {
		Transform3D transform = new Transform3D();
		Vector3f translate_vector = new Vector3f();
		//Otiene la transformacion actual
		tg_model3D.getTransform(transform);
		transform.get(translate_vector);
		//Mueve la esfera
		translate_vector.x = x*0.002f;
		translate_vector.y = -y*0.002f;
		//Establece la nueva posición
		transform.set(translate_vector);
		tg_model3D.setTransform(transform);
	}

	/**
	 * Selecciona la imagen que ha de ser escogida en el spritesheet
	 */
	private void selectImage() {
		image = Constants.img_handler.getImagePlayer(x_img, y_img, width, height);
	}

	/**
	 * Añade la velocidad del salto cuando el jugador pulsa la tecla
	 */
	public void jump(){
		if(soundJump == null){
			System.err.println("soundnull");
			System.exit(1);
		}
		if(canJump <= 0){
			soundJump.start();
			jumpSpeed = jumpVelocity;
			jump = true;
			canJump = 2;
		}		
	}

	/**
	 * Añade una fuerza de salto inferior a la de la funcion jump
	 */
	public void miniJump(){
		if(soundJump == null){
			System.err.println("soundnull");
			System.exit(1);
		}
		soundJump.start();
		jumpSpeed = miniJumpVelocity;
		jump = true;
	}

	/**
	 * Aplica la gravedad
	 */
	public void gravity(){		
		int aum = 1;
		if(Constants.scale == 2){
			//aum = 7;
		}else if(Constants.scale == 4){
			aum=8;
		}
		if(vy+aum >= maxGravity){
			this.setVelocity(vx, maxGravity);
		}
		else{
			tick_action++;
			if(tick_action >= Constants.speedActions){
				this.setVelocity(vx, vy+1);
				tick_action = 0;
			}
		}		
	}

	/**
	 * Comprueba las colisiones de la esfera con las casillas adyacentes
	 * @param mc
	 * @param x_ori
	 * @param y_ori
	 * @return
	 */
	public int checkCollision(MapController mc, int x_ori, int y_ori){
		boolean print = false;	//False para no ver mensajes
		int result = -1;
		int collisionInf,collisionLat,collisionSup,collisionCen;
		//Comprueba si toca fin de mapa
		if(x + width < 0){
			return COLLDEATH;
		}
		//Control de mapa
		MapObject map;
		//Mapa actual			
		if(nextMap && mc.getPos()>0){
			map = mc.getNextMap();
		}
		else{
			nextMap = false;
			map = mc.getCurrentMap();
		}	
		//}

		//Calculo coordenadas respecto mapa
		//int xMap = (this.x + (this.block_width/2)) / mc.getWidthBlock() + mc.getPos();
		int xMap = (totalX + (block_width/2)) / mc.getWidthBlock();
		int yMap = Math.abs((y + (block_height/2)) / mc.getHeightBlock() - mc.getMaxHeight());
		//Comprueba si esta en el siguiente mapa
		if(xMap >= map.getWidthBlocks()){
			//xMap = xMap - map.getWidthBlocks();
			nextMap = true;
			totalX = totalX - map.getWidthBlocks()*mc.getWidthBlock();
			xMap = (totalX + (block_width/2)) / mc.getWidthBlock();
			map = mc.getNextMap();	
		}		
		if(print){
			//system.out.println("-----------------------");
			mc.getCurrentMap().print();
			//system.out.printf("esfera x: %d, y: %d, w: %d, h: %d tx:%d\n", x, y, xMap, yMap,totalX);
		}
		//Colision inferior	
		collisionInf = map.collision(xMap, yMap-1,x_ori,y_ori, this);
		if(collisionInf != MapObject.NOCOLLISION && print){
			//system.out.printf("colision inferior x: %d, y: %d\n", xMap, yMap-1);
			map.infoOject(xMap, yMap-1);	
		}
		//Colision lateral
		collisionLat = map.collision(xMap+1, yMap,x_ori,y_ori, this);
		if(collisionLat != MapObject.NOCOLLISION && print){
			//system.out.printf("colision lateral x: %d, y: %d\n", xMap+1, yMap);
			map.infoOject(xMap+1, yMap);
		}		
		//Colision superior
		collisionSup = map.collision(xMap, yMap+1,x_ori,y_ori, this);
		if(collisionSup != MapObject.NOCOLLISION && print){
			//system.out.printf("colision superior x: %d, y: %d\n", xMap, yMap+1);
			map.infoOject(xMap, yMap+1);	
		}
		//Colision central
		collisionCen = map.collision(xMap, yMap,x_ori,y_ori, this);
		if(collisionCen != MapObject.NOCOLLISION && print){
			//system.out.printf("colision central x: %d, y: %d\n", xMap, yMap);
			map.infoOject(xMap, yMap);
		}

		//Actualiza la xtotal
		totalX += mc.getVelocity() + vx;

		//Prioridad de colisiones	
		if(collisionInf == MapObject.KILLS || collisionLat == MapObject.KILLS || collisionSup == MapObject.KILLS || collisionCen == MapObject.KILLS){
			GameObject obj = null;
			if(collisionInf == MapObject.KILLS){
				//Colision inferior
				obj = map.getObject(xMap, yMap-1);
			}else if(collisionCen == MapObject.KILLS){
				//Colision central
				obj = map.getObject(xMap, yMap);
			}
			if(obj != null){
				if(obj instanceof Liquid){
					deathLiquid.start();
				}
			}			
			result = COLLDEATH;
		}
		else if(collisionInf == MapObject.DEATH || collisionCen == MapObject.DEATH){
			result = COLLKILL;
		}
		else if(collisionInf == MapObject.GET || collisionLat == MapObject.GET || collisionSup == MapObject.GET || collisionCen == MapObject.GET){
			takeSound.start();
			result = COLLGET;
		}
		else if(collisionInf >= MapObject.COLLISION && collisionLat >= MapObject.COLLISION){
			result = COLLINFLAT;
			totalX = (xMap+1)*mc.getWidthBlock() - this.getWidthScreen() - mc.getVelocity()+1;
			x = map.getObject(xMap+1, yMap).getPositionX() - this.getWidthScreen() - mc.getVelocity()+1;			
			y = map.getObject(xMap, yMap-1).getPositionY() - this.getHeightScreen() + 1;
		}
		else if(collisionInf >= MapObject.COLLISION && collisionCen >= MapObject.COLLISION){
			result = COLLINFLAT;
			totalX = (xMap)*mc.getWidthBlock() - this.getWidthScreen() - mc.getVelocity()+1;
			x = map.getObject(xMap, yMap).getPositionX() - this.getWidthScreen() - mc.getVelocity()+1;			
			y = map.getObject(xMap, yMap-1).getPositionY() - this.getHeightScreen() + 1;
		}
		else if(collisionSup >= MapObject.COLLISION && collisionLat >= MapObject.COLLISION){
			result = COLLSUPLAT;
			totalX = (xMap+1)*mc.getWidthBlock() - this.getWidthScreen() - mc.getVelocity()+1;
			x = map.getObject(xMap+1, yMap).getPositionX() - this.getWidthScreen() - mc.getVelocity()+1;
			y = map.getObject(xMap, yMap+1).getPositionY() + map.getObject(xMap, yMap+1).getHeightScreen();
		}	
		else if(collisionSup >= MapObject.COLLISION && collisionCen >= MapObject.COLLISION){
			result = COLLSUPLAT;
			totalX = (xMap)*mc.getWidthBlock() - this.getWidthScreen() - mc.getVelocity()+1;
			x = map.getObject(xMap, yMap).getPositionX() - this.getWidthScreen() - mc.getVelocity()+1;
			y = map.getObject(xMap, yMap+1).getPositionY() + map.getObject(xMap, yMap+1).getHeightScreen();
		}
		else if(collisionLat >= MapObject.COLLISION){
			result = COLLLAT;
			totalX = (xMap+1)*mc.getWidthBlock() - this.getWidthScreen() - mc.getVelocity()+1;
			x = map.getObject(xMap+1, yMap).getPositionX() - this.getWidthScreen() - mc.getVelocity()+1;
		}
		else if(collisionCen >= MapObject.COLLISION){
			result = COLLLAT;
			totalX = (xMap)*mc.getWidthBlock() - this.getWidthScreen() - mc.getVelocity()+1;
			x = map.getObject(xMap, yMap).getPositionX() - this.getWidthScreen() - mc.getVelocity()+1;
		}
		else if(collisionInf >= MapObject.COLLISION){
			result = COLLINF;
			//this.y = (this.y / mc.getHeightBlock() + 1)*mc.getHeightBlock()-this.height+1;
			y = map.getObject(xMap, yMap-1).getPositionY() - this.getHeightScreen() + 1;
		}
		else if(collisionSup >= MapObject.COLLISION){
			result = COLLSUP;
			//this.y = (this.y / mc.getHeightBlock() + 1)*mc.getHeightBlock();
			y = map.getObject(xMap, yMap+1).getPositionY() + map.getObject(xMap, yMap+1).getHeightScreen();
		}		
		else{
			result = NOCOLLISION;
		}
		if(collisionInf == MapObject.COLLISION && canJump > 0){
			canJump--;
		}
		return result;
	}

	/**
	 * Aplica la velocidad actual moviendo al personaje
	 */
	@Override
	public void move(){
		if(x>=maxX){
			this.setVelocity(0, vy);
		}
		if(jump){
			jump = false;
			this.setVelocity(vx, jumpSpeed);
		}
		setPosition((x+vx), (y+vy));
		if(Constants.visualMode == Game.MODE_3D){
			setPosition3D();
		}
	}

	/**
	 * Dibuja la esfera en 3D
	 */
	@Override
	public void draw2D(Graphics2D g2d,int x_ori, int y_ori) {
		g2d.drawImage(image, x_ori+x, y_ori+y, null);
		//Dibujo cada de colisiones
		this.x_ori=x_ori;
		this.y_ori=y_ori;
	}

	/**
	 * Devuelve el ancho de la imagen
	 * @return
	 */
	public int getWidthImage(){
		return width_imgs[type];
	}

	/**
	 * Devuelve el alto de la imagen
	 * @return
	 */
	public int getHeightImage(){
		return height_imgs[type];
	}

	/**
	 * Devuelve si el rectangulo del boss colisiona con la esfera
	 * @param bossBox
	 * @return
	 */
	public boolean bossCollision(Rectangle bossBox) {
		Rectangle playerBox = this.getBox(x_ori, y_ori);
		if(bossBox.intersects(playerBox) && playerBox.y>bossBox.y &&
				x+width > bossBox.x && x < bossBox.x+bossBox.width && vy > 0){
			return false;
		}else{
			return true;
		}
	}

}
