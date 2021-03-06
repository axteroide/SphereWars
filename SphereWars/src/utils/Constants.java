package utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.vecmath.Color3f;

import media.ImageHandler;
import scoreboard.Ranking;
import videogame.Game;

/**
 * Autores: Victor Adrian Milla Español - 557022,
 * 			Juan Luis Burillo Ortín - 542083,
 * 			Sandra Malpica Mallo - 670607,
 * 			Richard Elvira López-Echazarreta - 666800
 * 	
 * Clase: Constants.java
 * 
 * Comentarios: Clase con todos las variables globales
 * 
 */
public class Constants {
	public static final String[] list_menu = { "Start", "versus","Help", "options", "credits", "Exit" };
	public static final String[] list_options = { "sound", "resolution", "controller", "back" };
	public static final String[] list_controller = {"controller", "device", "pause", "jump", "run"};
	public static final String[] list_modes = {"maraton", "treasure", "back"};
	
	//dependen de la escala que se este utilizando
	public static int ax=0;
	public static int ay=0;
	
	//rutas a las imagenes del menu de titulos
	public static final String starBackName = "images/base_background.png";
	public static final String mountainBackName = "images/mountain_edited.png";
	public static final String titleName = "fonts/title.png";
	public static final String jugarName = "fonts/menu_jugar.png";
	public static final String helpName = "fonts/menu_ayuda.png";
	public static final String salirName = "fonts/menu_salir.png";
	public static final String opcionesName = "fonts/menu_opciones.png";
	public static final String creditosName = "fonts/menu_creditos.png";
	public static final String versusName = "fonts/menu_versus.png";
	public static final String dosdName = "fonts/menu_2D.png";
	public static final String tresdName = "fonts/menu_3D.png";
	//posiciones (iniciales) de los elementos del menu de titulo
	static int xPos = 125;
	public static final Position titlePos = new Position(60+ax,20+ay);	//posicion del titulo
	public static Position jugarPos = new Position(xPos,110+ay);		//posicion de "jugar"
	public static Position versusPos = new Position(xPos, 170+ay);
	public static Position dosDjPos = new Position(xPos+300,110+ay);
	public static Position tresDjPos = new Position(xPos+380,110+ay);
	public static Position helpPos = new Position(xPos,230+ay);		
	public static Position opcionesPos = new Position(xPos,290+ay);	
	public static Position creditPos = new Position(xPos,350+ay);
	public static Position salirPos = new Position(xPos,410+ay);	
	//Opciones del menu principal
	public static int numJugadores = 1;	//numero jugadores
	public static int visualMode = Game.MODE_2D;
	public static boolean cursorDesplazado = false;
	public static int xOri = 0;
	public static int yOri = 50 * Constants.scale;
	
	//rutas a las imagenes del menu de opciones
	public static final String soundName = "fonts/opciones_sonido.png";
	public static final String yesName = "fonts/opciones_si.png";
	public static final String noName = "fonts/opciones_no.png";
	public static final String resName = "fonts/opciones_resolucion.png";
	public static final String res480Name = "fonts/opciones_res480.png";
	public static final String res960Name = "fonts/opciones_res960.png";
	public static final String controllerName = "fonts/opciones_controles.png";
	public static final String keyboardName = "fonts/opciones_teclado.png";
	public static final String kinnectName = "fonts/opciones_kinnect.png";
	public static final String keyName = "fonts/opciones_teclas.png";
	public static final String jumpName = "fonts/opciones_saltar.png";
	public static final String runName = "fonts/opciones_correr.png";
	public static final String backName = "fonts/opciones_volver.png";
	public static final String controller1Name = "fonts/opciones_control1.png";
	public static final String controller2Name = "fonts/opciones_controles2.png";
	public static final String okName = "fonts/opciones_ok.png";
	public static final String pauseName = "fonts/opciones_pausa.png";
	//posiciones (iniciales) de los elementos del menu de opciones
	public static int optX = 40;
	public static final Position soundPos = new Position(optX,110);
	public static final Position yesPos = new Position(optX+270,110);
	public static final Position noPos = new Position(optX+330,110);
	public static final Position resPos = new Position(optX,170);
	public static final Position res480Pos = new Position(optX+330,170);
	public static final Position res960Pos = new Position(optX+330,170);
	public static final Position backPos = new Position(optX,290);
	public static final Position controllerPos= new Position(optX,230);
	public static final Position keyboardPos = new Position(optX,170);
	public static final Position kinnectPos = new Position(optX,170);
	public static final Position keyPos = new Position(optX,230);
	public static final Position jumpPos = new Position(optX,290);
	public static final Position runPos = new Position(optX,350);
	public static final Position controller1Pos = new Position(optX,110);
	public static final Position controller2Pos = new Position(optX,110);
	//opciones del menu de opciones
	public static boolean sound = true;
	public static boolean conTeclado = true;	//solo para jugador uno
	public static boolean zurdo = false;
	public static int scale = 2;
	public static int speedActions = 2;
	public static int teclaSaltop1 = KeyEvent.VK_UP;
	public static int teclaPausap1 = KeyEvent.VK_SPACE;
	public static int teclaSprintp1 = KeyEvent.VK_ENTER;
	public static int teclaSaltop2 = KeyEvent.VK_W;
	public static int teclaPausap2 = KeyEvent.VK_SPACE;
	public static int teclaSprintp2 = KeyEvent.VK_Z;
	public static boolean elegidoJugador = false;
	public static int jugador = 1;
	
	//rutas a las imagenes del menu de modos de juego
	public static final String maratonName = "fonts/modo_maraton.png";
	public static final String tesorosName = "fonts/modo_cazatesoros.png";
	//posiciones de las opciones del menu de modos de juego
	public static final Position maratonPos = new Position(xPos,110+ay);
	public static final Position tesorosPos = new Position(xPos,170+ay);
	public static final Position backModePos = new Position(xPos,230+ay);
	
	public static boolean sprintp1 = false;
	public static boolean sprintp2 = false;
	
	public static final int guion = '-';
	public static  float alphaComp = 1f;	
	
	//colores
	public static final Color3f white = new Color3f(Color.white);
	public static final Color3f black = new Color3f(Color.black);
	
	
	//posiciones del cursor	
	public static final Position titleIniPos = new Position(xPos-25,110);	//posicion inicial del cursor en menu inicio
	public static final int titleMaxPos = list_menu.length;	//posiciones cursor en menu de titulo
	public static final int titleGap = 60;	//espacio entre posiciones en menu de titulo
	public static final int desplazamiento = 40;
	
	public static final Position optionIniPos = new Position(optX-25,110);	//posicion inicial del cursor en menu opciones
	public static final int optionMaxPos = list_options.length;	//posiciones cursor en menu de opciones
	public static final int controlMaxPos = list_controller.length;
	
	public static final Position modeIniPos = new Position(xPos-25,110);	//posicion inicial del cursor en menu inicio
	public static final int modeMaxPos = list_modes.length;
	
	//booleanos menus
	public static boolean enMenu = true;
	public static final String titMenu = "titleMenu";
	public static final String optMenu = "optionsMenu";
	public static final String conMenu = "controllerMenu";
	public static final String modMenu = "gameModeMenu";
	public static final String helMenu = "helpMenu";
	public static final String creMenu = "creditsMenu";
	public static String tipoMenu = titMenu;
	//Constantes de estado
	public static final int MENU = 0;
	public static final int PAUSE = 1;
	public static final int GAME = 2;
	
	//esperando a que el jugador escriba una tecla
	public static boolean esperandoTecla = false;
	
	//Manejador de imagenes para las pantallas
	public static ImageHandler img_handler;
	//Interacciona con los ranking
	public static Ranking ranking;
	//TODO: aplicar escala para constantes de tama�o en los menus
	
	//Array de indices de mapas generados, se comparte entre los dos jugadores y permite repetir pantalla
	public static ArrayList<Integer> map_index;
	
	//Fuente y ruta de las fuente
	private static String path_font = "fonts/M04.TTF";
	private static String path_font_bold = "fonts/M04B.TTF";
	private static String path_font_humanoid = "fonts/HUMANOID.TTF";
	public static Font font = crearFuente();
	public static Font font_bold = crearFuenteBold();
	public static Font humanoid = crearFuenteHumanoid();
	
	//Estado del juego
	public static int gameState;
	//Opcion seleccionada en menu de pausa
	public static int optionSelect;
	
	private static Font crearFuente() {
		try {
			return Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(path_font));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static Font crearFuenteHumanoid() {
		try {
			return Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(path_font_humanoid));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static Font crearFuenteBold() {
		try {
			return Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(path_font_bold));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
