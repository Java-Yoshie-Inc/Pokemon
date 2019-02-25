package de.yoshie2000.pokemon.objects;

import org.newdawn.slick.Graphics;

import de.yoshie2000.pokemon.engine.Game;
import de.yoshie2000.pokemon.maths.Vector;
import de.yoshie2000.pokemon.sprite.Sprite;

public class GameObject {

	private Vector bounds;

	private Sprite sprite;

	private float speedLimit = Float.MAX_VALUE;

	private ObjectController controller;

	public GameObject(Sprite sprite, ObjectController controller) {
		this.controller = controller;
		this.bounds = new Vector(sprite.getWidth(), sprite.getHeight());
		this.sprite = sprite;
		Game.addObject(this);
	}

	public GameObject(Sprite sprite, Vector position, ObjectController controller) {
		this.controller = controller;
		this.bounds = new Vector(sprite.getWidth(), sprite.getHeight());
		this.sprite = sprite;
		Game.addObject(this);
	}

	public void update() {
		sprite.setAnimationIndex(controller.getAnimationIndex());
		sprite.update();
		
		controller.update();
	}

	public void render(Graphics graphics) {
		sprite.render(graphics, controller.getPosition(), true);
	}

	public ObjectController getController() {
		return controller;
	}

	public Sprite getSprite() {
		return sprite;
	}

	public void setSprite(Sprite state) {
		this.sprite = state;
	}

	public Vector getSize() {
		return bounds;
	}

	public void setSize(Vector size) {
		this.bounds = size;
	}

	public float getSpeedLimit() {
		return speedLimit;
	}

	public void setSpeedLimit(float limit) {
		speedLimit = limit;
	}

}
