package videogame;

//BorderLayout stuff//
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

//Canvas3D
import javax.media.j3d.Canvas3D;

//The Universe
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;

import character.Boss;
import character.Bot;
import character.Sphere;

import javax.media.j3d.BranchGroup;

import javax.vecmath.*;

import javax.media.j3d.DirectionalLight;
import javax.media.j3d.GraphicsContext3D;
import javax.media.j3d.ImageComponent;
import javax.media.j3d.ImageComponent2D;
import javax.media.j3d.Raster;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.AmbientLight;
import javax.media.j3d.Background;

import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;

import map.MapController;

@SuppressWarnings("serial")
public class Game3D extends Canvas3D implements KeyListener{
	//Dimensión de la pantalla de juego
	private int width, height;
	//Contenedor del jugador
	private Sphere player;
	//Contenedor del jefe
	private Boss boss;
	//Flag que indica si la partida ha acabado
	private boolean end_game;
	//Puntuaciones para mostrar en scoreboard
	private double score_distance;
	private int score_coins;
	//Universo sobre el que se desarrolla la escena
	private SimpleUniverse simpleU;
	//Grupo que contiene todos los objetos de la escena
	private BranchGroup rootBranchGroup;
	//Metodo principal
	private Main main;
	//Mapa del juego
	private MapController map;
	//Contenedor del mapa al que se le pueden aplicar diferentes transformaciones
	private TransformGroup map_cont;
	private OrbitBehavior orbit;

	public Game3D(int width, int height, MapController map, Main main) {
		super(SimpleUniverse.getPreferredConfiguration());
		//Inicializacion de variables
		this.width = width;
		this.height = height;
		this.map = map;
		this.main = main;
		this.end_game = false;
		//Establece las opciones del canvas
		setPreferredSize(new Dimension(width, height));
		setDoubleBufferEnable(true);
		requestFocus();
		//Inicializa la puntuacion
		init_score();
		//Inicia la configuracion del mundo
		initial_setup();
		//Establece la posicion y movimientos de la camara
		camera_setup();
		//Carga el contenedor del mapa y los mapas iniciales
		loadMap();
		//Carga la iluminacion
		addLights();
		//Carga el jugador
		initPlayer();
		//Finaliza la creacion del mundo
		finalise();
		//coloca la camara en su lugar
		orbit.goHome();
		//Agrega el listener del teclado
		addKeyListener(this);

		//Ejemplo de borrar un elemento del tipo tesoro del mapa
		//((Treasure)map.getCurrentMap().getObject(7, 8)).removeObject();
	}

	private void initPlayer() {
		player = new Sphere(2*map.getWidthBlock(), 7*map.getHeightBlock(), map.getWidthBlock(), map.getHeightBlock());
		TransformGroup tg = player.get3DModel();

		//Mueve el mapa en el eje de las Y hacia abajo para llenar la pantalla
//		Transform3D transform_map = new Transform3D();
//		tg.getTransform(transform_map);
//		Vector3f translate = new Vector3f();
//		transform_map.get(translate);
//		translate.y -= 0.1f;
//		transform_map.set(translate);
//		tg.setTransform(transform_map);
		
		rootBranchGroup.addChild(tg);
	}

	private void addLights() {
		//Agrega iluminacion blanca
		addDirectionalLight(new Vector3f(0f, 0f, -1f),
				new Color3f(1f, 1f, 1f));
		//Agrega iluminacion blanca
		addDirectionalLight(new Vector3f(0f, 1f, 0f),
				new Color3f(1f, 1f, 1f));
		//Agrega iluminacion blanca
		addDirectionalLight(new Vector3f(0f, 0f, 1f),
				new Color3f(1f, 1f, 1f));
		//Agrega iluminacion blanca
		addAmbientalLight();
	}

	private void loadMap() {
		//Crea el contenedor del mapa y le establece los permisos
		map_cont = new TransformGroup();
		map_cont.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		map_cont.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		map_cont.setCapability(TransformGroup.ALLOW_CHILDREN_EXTEND);
		map_cont.setCapability(TransformGroup.ALLOW_CHILDREN_WRITE);
		map_cont.setCapability(TransformGroup.ALLOW_CHILDREN_READ);
		//Carga los primeros mapas en el contenedor
		map_cont.addChild(map.get3DFirstMap());
		map_cont.addChild(map.get3DSecondMap());
		//Mueve el mapa en el eje de las Y hacia abajo para llenar la pantalla
//		Transform3D transform_map = new Transform3D();
//		map_cont.getTransform(transform_map);
//		Vector3f translate = new Vector3f();
//		transform_map.get(translate);
//		translate.y -= 0.3f;
//		transform_map.set(translate);
//		map_cont.setTransform(transform_map);
		//Agrega el contenedor de mapa a la raiz
		rootBranchGroup.addChild(map_cont);
	}

	private void init_score() {
		this.score_distance = 0;
		this.score_coins = 0;
	}

	private void camera_setup() {
		//Establece el movimiento de la camara de forma que orbita alrededor del origen
		//permite movmiento de rotacion, traslacion y zoom
		orbit = new OrbitBehavior(this, OrbitBehavior.REVERSE_ALL);
		orbit.setSchedulingBounds(new BoundingSphere());
		Transform3D home=new Transform3D();
		simpleU.getViewingPlatform().getViewPlatformTransform().getTransform(home);
		//System.out.println(home.toString());
		double[] homeM = {1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.4, 0.0, 0.0, 1.0, 2.41421356, 0.0, 0.0, 0.0, 1.0};
		Transform3D homeT = new Transform3D(homeM);
		orbit.setHomeTransform(homeT);		
		ViewingPlatform vp = simpleU.getViewingPlatform();
		vp.setViewPlatformBehavior(orbit);
	}

	/**
	 * Perform the essential setups for the Java3D
	 */
	private void initial_setup() {
		//Establece el tamaño del canvas
		setSize(width, height);
		//Crea el universon en el que se representa la escena
		simpleU = new SimpleUniverse(this);
		//Raiz sobra la que se agregan los elementos visualizar, se le agregan permisos para
		//agregar mas elementos y modificarlos
		rootBranchGroup = new BranchGroup();
		rootBranchGroup.setCapability(TransformGroup.ALLOW_CHILDREN_EXTEND);
		rootBranchGroup.setCapability(TransformGroup.ALLOW_CHILDREN_WRITE);
		rootBranchGroup.setCapability(TransformGroup.ALLOW_CHILDREN_READ);
		//Establece el fondo del universo
		Background background = new Background(new Color3f(0.3476f,0.6484f,0.9257f));
		BoundingSphere sphere = new BoundingSphere(new Point3d(0,0,0), 100000);
		background.setApplicationBounds(sphere);
		rootBranchGroup.addChild(background);
	}

	private void addDirectionalLight(Vector3f direction, Color3f color) {
		//Establece la esfera en la que afecta la luz
		BoundingSphere bounds = new BoundingSphere();
		bounds.setRadius(1000d);
		//Crea la luz y establece el color y la direccion
		DirectionalLight lightD = new DirectionalLight(color, direction);
		lightD.setInfluencingBounds(bounds);
		//Agrega la luz a la raiz de elementos
		rootBranchGroup.addChild(lightD);

	}

	private void addAmbientalLight() {
		//Establece la esfera en la que afecta la luz
		BoundingSphere bounds = new BoundingSphere();
		bounds.setRadius(1000d);
		//Crea la luz y establece el color y la direccion
		Color3f ambientColour = new Color3f(0.5f, 0.5f, 0.5f);
		AmbientLight ambientLight = new AmbientLight(ambientColour);
		ambientLight.setInfluencingBounds(bounds);
		//Agrega la luz a la raiz de elementos
		rootBranchGroup.addChild(ambientLight);
	}

	public void finalise() {
		//Se agrega la raiz de todos los elementos a visualizar al universo
		simpleU.addBranchGraph(rootBranchGroup);
		//Establece la vista de la camara
		simpleU.getViewingPlatform().setNominalViewingTransform();
	}

	public boolean actionGame(int x_ori,int y_ori) {
		if(!end_game){
			//Mueve el mapa
			double dist = map.move();
			//mueve la camara para que siga la esfera
			//orbit.setRotationCenter(new Point3d(player.x,player.y,0));
			//Realiza las transformaciones para mover el mapa
			Transform3D translate = new Transform3D();
			Vector3f translate_vector = new Vector3f();
			map_cont.getTransform(translate);
			translate.get(translate_vector);
			translate_vector.x -= dist*0.085f;
			translate.set(translate_vector);
			map_cont.setTransform(translate);
			//Establece la puntuacion de distancia
			score_distance += dist;
			//Comprueba si hay que cargar un nuevo mapa
			if(map.hasNewMap()){
				//Hay que cargar un mapa nuevo
				TransformGroup nextMap = map.get3DSecondMap();
				BranchGroup bg = new BranchGroup();
				bg.addChild(nextMap);
				map_cont.addChild(bg);
			}
			//Mueve los bot del mapa si los hubiera, solo del mapa actual y el siguiente
			map.moveBot();
			/* Acciones a realizar */
			//TODO velocidad con la velocidad de plataformas
			int block = player.checkCollision(map,x_ori,y_ori);
			switch (block) {
			case Sphere.COLLINF:	//Colision inferior
				player.setVelocity(2, 0);
				break;
			case Sphere.COLLSUP:	//Colision superior
				player.setVelocity(2, 0);
				player.gravity();
				break;
			case Sphere.COLLLAT:	//Colision lateral
				player.setVelocity(0, player.vy);
				player.gravity();
				break;
			case Sphere.COLLINFLAT:
				player.setVelocity(0, 0);
				break;
			case Sphere.COLLSUPLAT:
				player.setVelocity(0, player.vy);
				player.gravity();
				break;
			case Sphere.COLLDEATH:
				end_game = true;
				//restart(map_cont);
				break;
			case Sphere.COLLKILL:
				player.miniJump();
				break;
			case Sphere.COLLGET:
				score_coins += map.removeTresure(player,block,x_ori,y_ori);
				player.gravity();
				break;
			default:
				//System.out.println("Gravedad");
				player.setVelocity(2, player.vy);
				player.gravity();
				break;
			}	
			player.move();
			if(end_game){
				//animacion de muerte
				player.setVelocity(0, -15);
			}
		}
		return end_game;
	}

	public int getCoins(){
		return score_coins;
	}

	public double getDistance(){
		return score_distance;
	}

	public BufferedImage createBufferedImageFromCanvas3D(){
		//Crea el contexto para cargar los graficos del canvas
		GraphicsContext3D  ctx = getGraphicsContext3D();
		int w = getWidth();
		int h = getHeight();
		//Establece la imagen que contiene el canvas del mismo tamaño
		BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		ImageComponent2D im = new ImageComponent2D(ImageComponent.FORMAT_RGB, bi);
		//Prepara la rasterizacion
		Raster ras = new Raster(new Point3f( -1.0f, -1.0f, -1.0f ),
				Raster.RASTER_COLOR, 0, 0, w, h, im, null );
		//Vuelca toda la informacion del canvas y lo rasteriza
		ctx.flush(true);
		ctx.readRaster(ras);
		//Devuelve la imagen del raster
		return ras.getImage().getImage();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		//((Bot)map.getCurrentMap().getObject(3, 5)).death();
		if(e.getKeyChar() == '.'){
			orbit.goHome();
		}else{
			//Propaga el evento de pulsar al main para tratarlo
			main.keyPressed(e);
		}		
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	public void restart() {
		//Deshace la transofrmacion de movmiento del mapa en el eje de las x
		Transform3D translate = new Transform3D();
		Vector3f translate_vector = new Vector3f();
		map_cont.getTransform(translate);
		translate.get(translate_vector);
		translate_vector.x = 0;
		translate.set(translate_vector);
		map_cont.setTransform(translate);
		//Reinicia los marcadores
		init_score();
	}
}