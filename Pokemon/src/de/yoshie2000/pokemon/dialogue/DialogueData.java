package de.yoshie2000.pokemon.dialogue;

public class DialogueData {

	private String[] text;
	private int box;

	public DialogueData(String[] text, int box) {
		this.text = text;
		this.box = box;
	}

	public DialogueData(String line) {
		try {
			String[] split = line.split("~");
			text = split[0].split("&");
			box = Integer.valueOf(split[1]);
		} catch (Exception e) {
			if (text[0].equals("TV")) {
				box = 0;
				text = new String[] {
						"There's a movie on TV.|Four boys are walking on railroad tracks.", "...It's time to go now!" };
			}
		}
	}

	public String[] getText() {
		return text;
	}

	public int getBox() {
		return box;
	}

}
