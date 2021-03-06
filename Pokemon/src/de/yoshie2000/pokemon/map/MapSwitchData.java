package de.yoshie2000.pokemon.map;

import de.yoshie2000.pokemon.Direction;
import de.yoshie2000.pokemon.maths.Vector;

public class MapSwitchData {
	
	private String mapName;
	private Vector position;
	private Direction direction;
	
	public MapSwitchData(String line) {
		String[] split = line.split(",");
		mapName = split[0];
		int x = Integer.valueOf(split[1]);
		int y = Integer.valueOf(split[2]);
		position = new Vector(x, y);
		direction = Direction.valueOf(split[3]);
	}

	public String getMapName() {
		return mapName;
	}

	public Vector getPosition() {
		return position;
	}
	
	public Direction getDirection() {
		return direction;
	}

}
