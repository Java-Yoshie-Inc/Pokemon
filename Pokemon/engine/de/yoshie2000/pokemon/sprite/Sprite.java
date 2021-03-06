package de.yoshie2000.pokemon.sprite;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import de.yoshie2000.pokemon.engine.Game;
import de.yoshie2000.pokemon.maths.Vector;

public class Sprite {

	private Image texture;
	private Image[] sprites;

	private int spriteCountX, spriteCountY;
	private AnimationIndex animationIndex;

	private boolean hasAnimation = false, animationRunning = false;
	private int frameDelay = 0, tempDelay = 0;

	public Sprite(String file, int spriteCount) {
		try {
			this.spriteCountX = spriteCount;
			this.spriteCountY = spriteCount;

			texture = new Image(file);
			sprites = new Image[spriteCount * spriteCount];

			int spriteWidth = texture.getWidth() / spriteCount;
			int spriteHeight = texture.getHeight() / spriteCount;

			int counter = 0;
			for (int y = 0; y < spriteCountY; y++) {
				for (int x = 0; x < spriteCountX; x++) {
					sprites[counter++] = texture.getSubImage(spriteWidth * x, spriteHeight * y, spriteWidth,
							spriteHeight);
					sprites[counter].setFilter(Image.FILTER_NEAREST);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public Sprite(String file, int spriteCountX, int spriteCountY) {
		try {
			this.spriteCountX = spriteCountX;
			this.spriteCountY = spriteCountY;

			texture = new Image(file);
			sprites = new Image[spriteCountX * spriteCountY];

			int spriteWidth = texture.getWidth() / spriteCountX;
			int spriteHeight = texture.getHeight() / spriteCountY;

			int counter = 0;
			for (int y = 0; y < spriteCountY; y++) {
				for (int x = 0; x < spriteCountX; x++) {
					sprites[counter] = texture.getSubImage(spriteWidth * x, spriteHeight * y, spriteWidth,
							spriteHeight);
					sprites[counter++].setFilter(Image.FILTER_NEAREST);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	private Sprite(Image[] images) {
		sprites = new Image[images.length];
		spriteCountX = images.length;
		spriteCountY = 1;

		for (int i = 0; i < images.length; i++) {
			sprites[i] = images[i];
			sprites[i].setFilter(Image.FILTER_NEAREST);
		}
	}

	public Sprite getSubsprite(int first, int last) {
		Image[] images = new Image[last - first];
		for (int i = first; i < last; i++) {
			images[i - first] = sprites[i];
		}
		return new Sprite(images);
	}

	public void render(Graphics g, Vector position, boolean gameobject) {
		if (animationIndex == null)
			animationIndex = new AnimationIndex(0, false);
		
		if (gameobject) {
			Vector pos = Game.getPlayer().getController().getPosition().getAdded(position.getMultiplied(-1));
			if (!animationIndex.isReversed()) {
				sprites[animationIndex.getIndex()].draw(pos.x, pos.y);
			} else {
				sprites[animationIndex.getIndex()].draw(getWidth() - pos.x, pos.y, -getWidth(), getHeight());
			}
		} else {
			if (!animationIndex.isReversed()) {
				sprites[animationIndex.getIndex()].draw(position.x, position.y);
			} else {
				sprites[animationIndex.getIndex()].draw(position.x, position.y);
			}
		}
	}

	public void update() {
		if (animationIndex == null)
			animationIndex = new AnimationIndex(0, false);

		if (animationRunning) {
			tempDelay++;
			if (tempDelay >= frameDelay) {
				if (next()) {
					animationRunning = false;
				}
				tempDelay = 0;
			}
		}
	}

	public boolean next() {
		animationIndex.setIndex(animationIndex.getIndex() + 1);
		if (animationIndex.getIndex() >= sprites.length) {
			animationIndex.setIndex(0);
			return true;
		}
		return false;
	}

	public void startAnimation() {
		this.hasAnimation = true;
		this.animationRunning = true;
	}

	public void endAnimation() {
		this.animationRunning = false;
	}

	public Image getImage(int index) {
		return sprites[index];
	}

	public Image getImage(int x, int y) {
		return sprites[x + y * spriteCountY];
	}

	public void setAnimationIndex(AnimationIndex index) {
		this.animationIndex = index;
	}

	public AnimationIndex getAnimationIndex() {
		return animationIndex;
	}

	public Image getCurrentImage() {
		return sprites[animationIndex.getIndex()];
	}

	public int getWidth() {
		return sprites[0].getWidth();
	}

	public int getHeight() {
		return sprites[0].getHeight();
	}

	public int getFrameDelay() {
		return frameDelay;
	}

	public void setFrameDelay(int frameDelay) {
		this.frameDelay = frameDelay;
	}

	public boolean hasAnimation() {
		return hasAnimation;
	}

	public Vector getSpriteCount() {
		return new Vector(spriteCountX, spriteCountY);
	}

}
