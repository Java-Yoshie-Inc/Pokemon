package de.yoshie2000.pokemon.map;

import org.newdawn.slick.tiled.TiledMap;

import de.yoshie2000.pokemon.Direction;
import de.yoshie2000.pokemon.dialogue.Dialogue;
import de.yoshie2000.pokemon.engine.Game;
import de.yoshie2000.pokemon.maths.Vector;

public class Map {

	private TiledMap map;
	private MapData data;
	private String name;

	@Override
	public String toString() {
		return "Map[" + name + "]";
	}

	public Map(String name) {
		try {
			this.name = name;
			map = new TiledMap("res/" + name + ".tmx");
			data = new MapData(name, map.getWidth(), map.getHeight());
			Game.addMap(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean checkTile(Vector pos, Direction direction, boolean interactable) {
		try {
			if(interactable) {
				interactWithTile(pos, direction);
			}
			
			String colData = getData().getCollisionData()[(int) pos.x / Game.getGridSize()][(int) pos.y
					/ Game.getGridSize()];
			if (!colData.equals("0")) {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public void interactWithTile(Vector pos, Direction direction) {
		try {

			String colData = getData().getCollisionData()[(int) pos.x / Game.getGridSize()][(int) pos.y
					/ Game.getGridSize()];
			
			// DIALOGUE
			if (data.getDialogues().containsKey(colData) && direction.equals(Direction.UP)) {
				Dialogue.startDialogue(data.getDialogues().get(colData));
			}

			// MAP SWITCH
			if (data.getMapSwitches().containsKey(colData)) {
				Game.setMap(data.getMapSwitches().get(colData));
			}

		} catch (Exception e) {

		}
	}

	public TiledMap getMap() {
		return map;
	}

	public MapData getData() {
		return data;
	}

	public String getName() {
		return name;
	}

}
