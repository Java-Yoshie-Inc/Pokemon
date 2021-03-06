package de.yoshie2000.pokemon.engine;

import java.awt.Font;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.util.Log;

import de.yoshie2000.pokemon.dialogue.Dialogue;
import de.yoshie2000.pokemon.map.Map;
import de.yoshie2000.pokemon.map.MapSwitchData;
import de.yoshie2000.pokemon.maths.Vector;
import de.yoshie2000.pokemon.objects.GameObject;

public class Game extends BasicGame {

	private static long frameCount = 0;

	private static GameLauncher gameLauncher;
	private static AppGameContainer app;
	private static Input input;

	private static List<GameObject> objects;

	private static Color clearColor;

	private static int gridSize = 16;

	private static List<Map> maps;
	private static Map map;
	private static GameObject player;
	
	private static boolean switchingMap = false;
	private static int switchingMapTimer = 0;
	private static MapSwitchData mapSwitchData;

	private static TrueTypeFont font;
	
	private static boolean xPressed = false;

	@Override
	public void init(GameContainer gc) throws SlickException {
		input = gc.getInput();
		clearColor = Color.black;
		try {
			font = new TrueTypeFont(Font.createFont(Font.TRUETYPE_FONT, new File("res/pkmn.ttf")).deriveFont(24f),
					true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		objects = new ArrayList<GameObject>();
		maps = new ArrayList<Map>();

		map = new Map("pallet_town");
		new Map("pallet_town_1");
		new Map("pallet_town_2");
		new Map("pallet_town_3");
		new Map("pallet_town_4");

		Dialogue.start();

		gameLauncher.initialize(gc);
	}

	@Override
	public void update(GameContainer gc, int arg1) throws SlickException {
		input = gc.getInput();
		xPressed = input.isKeyPressed(Input.KEY_X);
		
		Dialogue.update();
		gameLauncher.update(gc);

		for (GameObject object : objects) {
			object.update();
		}

		if (switchingMap) {
			switchingMapTimer += 10;
			if(switchingMapTimer > 255) {
				map = getMapByName(mapSwitchData.getMapName());
				switchingMapTimer = 0;
				switchingMap = false;
				player.getController().setPosition(mapSwitchData.getPosition());
				player.getController().setCanMove(true);
				player.getController().moveInDirection(mapSwitchData.getDirection());
			}
		}
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		input = gc.getInput();

		g.setColor(clearColor);
		g.fillRect(0, 0, app.getWidth(), app.getHeight());

		int xTranslate = app.getWidth() / 2 - gridSize / 2, yTranslate = app.getHeight() / 2 - gridSize / 2;
		g.translate(xTranslate, yTranslate);
		g.scale(4, 4);

		Vector mapRender = new Vector(-player.getController().getPosition().x,
				-player.getController().getPosition().y + 16);

		map.getMap().render((int) mapRender.x, (int) mapRender.y, 0);
		map.getMap().render((int) mapRender.x, (int) mapRender.y, 1);

		for (GameObject object : objects) {
			if (object != player)
				object.render(g);
		}
		
		// Draw player
		player.render(g);

		map.getMap().render((int) mapRender.x, (int) mapRender.y, 2);
		g.resetTransform();

		gameLauncher.render(gc, g);

		Dialogue.render(g);

		if (switchingMap) {
			g.setColor(new Color(0, 0, 0, switchingMapTimer));
			g.fillRect(0, 0, app.getWidth(), app.getHeight());
		}

		frameCount++;
	}

	public static List<Map> getMaps() {
		return maps;
	}

	public static void addMap(Map m) {
		maps.add(m);
	}

	public static Map getMapByName(String name) {
		for (Map m : maps) {
			if (m.getName().equals(name)) {
				return m;
			}
		}
		return null;
	}

	public static void setClearColor(Color color) {
		clearColor = color;
	}

	public static Input getInput() {
		return input;
	}
	
	public static boolean isXPressed() {
		return xPressed;
	}
	
	public static void setXPressed(boolean pressed) {
		xPressed = pressed;
	}

	public static float getFPS() {
		return app.getFPS() == 0 ? 1 : app.getFPS();
	}

	public static long getFrameCount() {
		return frameCount;
	}

	public static AppGameContainer getGameContainer() {
		return app;
	}

	public static List<GameObject> getObjects() {
		return objects;
	}

	public static Map getMap() {
		return map;
	}

	public static void setMap(MapSwitchData data) {
		mapSwitchData = data;
		switchingMap = true;
		player.getController().setCanMove(false);
	}

	public static GameObject getPlayer() {
		return player;
	}

	public static void setPlayer(GameObject _player) {
		player = _player;
	}

	public static int getGridSize() {
		return gridSize;
	}

	public static void setGridSize(int size) {
		gridSize = size;
	}

	public static void addObject(GameObject object) {
		objects.add(object);
	}

	public static void addObjects(List<GameObject> objectList) {
		objects.addAll(objectList);
	}

	public static void destroyObject(GameObject object) {
		objects.remove(object);
		object = null;
	}

	public static void destroyObjects(List<GameObject> objectList) {
		for (GameObject object : objectList) {
			destroyObject(object);
		}
	}

	public static TrueTypeFont getFont() {
		return font;
	}

	public static void create(String title, int width, int height, boolean fullscreen, int maxFPS, boolean vsync,
			GameLauncher launcher) {
		Log.setVerbose(false);
		try {
			gameLauncher = launcher;
			app = new AppGameContainer(new Game(title));
			app.setDisplayMode(width, height, fullscreen);
			app.setTargetFrameRate(maxFPS);
			app.setVSync(vsync);
			app.start();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public Game(String title) {
		super(title);
	}

}
