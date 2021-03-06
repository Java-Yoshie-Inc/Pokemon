package de.yoshie2000.pokemon.map;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

import de.yoshie2000.pokemon.dialogue.DialogueData;

public class MapData {

	private String[][] collisionData;
	private HashMap<String, DialogueData> dialogues;
	private HashMap<String, MapSwitchData> mapSwitches;

	public MapData(String name, int width, int height) throws Exception {
		BufferedReader reader = new BufferedReader(new FileReader("res/" + name + ".col"));
		String line;
		collisionData = new String[width][height];

		int y = 0;
		while ((line = reader.readLine()) != null) {
			String[] rowData = line.split(",");
			for (int x = 0; x < width; x++) {
				collisionData[x][y] = rowData[x];
			}
			y++;
		}

		reader.close();

		dialogues = new HashMap<String, DialogueData>();
		mapSwitches = new HashMap<String, MapSwitchData>();
		BufferedReader datReader = new BufferedReader(new FileReader("res/" + name + ".dat"));
		String datLine;

		while ((datLine = datReader.readLine()) != null) {
			String[] splitLine = datLine.split("=");

			if (splitLine[0].contains("*")) {
				mapSwitches.put(splitLine[0].substring(0, 1), new MapSwitchData(splitLine[1]));
			} else {
				dialogues.put(splitLine[0], new DialogueData(splitLine[1]));
			}
		}

		datReader.close();
	}

	public HashMap<String, MapSwitchData> getMapSwitches() {
		return mapSwitches;
	}

	public HashMap<String, DialogueData> getDialogues() {
		return dialogues;
	}

	public String[][] getCollisionData() {
		return collisionData;
	}

}
