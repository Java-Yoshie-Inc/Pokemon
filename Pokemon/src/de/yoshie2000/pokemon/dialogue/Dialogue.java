package de.yoshie2000.pokemon.dialogue;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;

import de.yoshie2000.pokemon.engine.Game;

public class Dialogue {

	private static String[] text;
	private static String currentText = "";

	private static int textIndex = 0, currentTextIndex = 0, boxIndex = 0;

	private static boolean isRunning = false, isStopped = false;

	private static Image[] boxes;

	public static void start() {
		try {
			Image sprite = new Image("res/ui/dialogue_boxes.png");
			boxes = new Image[4];

			boxes[0] = sprite.getSubImage(0, 0, 238, 42);
			boxes[1] = sprite.getSubImage(0, 42, 238, 44);
			boxes[2] = sprite.getSubImage(0, 86, 238, 44);
			boxes[3] = sprite.getSubImage(0, 130, 238, 46);
			boxes[0].setFilter(Image.FILTER_NEAREST);
			boxes[1].setFilter(Image.FILTER_NEAREST);
			boxes[2].setFilter(Image.FILTER_NEAREST);
			boxes[3].setFilter(Image.FILTER_NEAREST);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void startDialogue(DialogueData data) {
		text = data.getText();
		boxIndex = data.getBox();

		Game.getPlayer().getController().setCanMove(false);
		isRunning = true;
	}

	public static void update() {
		boolean xDown = Game.getInput().isKeyDown(Input.KEY_X);
		
		if (isRunning && !isStopped && (Game.getFrameCount() % 5 == 0
				|| (xDown && Game.getFrameCount() % 2 == 0))) {
			currentTextIndex++;

			if (currentTextIndex > text[textIndex].length()) {
				textIndex++;
				currentTextIndex = 0;
				isStopped = true;
				return;
			}

			if (textIndex >= text.length) {
				stopDialogue();
			}

			currentText = text[textIndex].substring(0, currentTextIndex);
		} else if (isStopped && Game.isXPressed()) {
			isStopped = false;
			Game.setXPressed(false);
			if (textIndex >= text.length) {
				stopDialogue();
			}
		}
	}

	public static void render(Graphics g) {
		if (isRunning) {
			float scale = (float) Game.getGameContainer().getWidth() / (float) boxes[boxIndex].getWidth();

			boxes[boxIndex].draw(0, Game.getGameContainer().getHeight() - (boxes[boxIndex].getHeight() * scale), scale);

			g.setFont(Game.getFont());
			g.setColor(Color.gray);

			String[] lines = currentText.split("\\|");
			g.drawString(lines[0], 60,
					Game.getGameContainer().getHeight() - (boxes[boxIndex].getHeight() * scale) + 30);
			if (lines.length > 1)
				g.drawString(lines[1], 60,
						Game.getGameContainer().getHeight() - (boxes[boxIndex].getHeight() * scale) + 90);
		}
	}

	public static void stopDialogue() {
		isRunning = false;
		isStopped = false;
		textIndex = 0;
		currentTextIndex = 0;
		currentText = "";
		Game.getPlayer().getController().setCanMove(true);
	}

}
