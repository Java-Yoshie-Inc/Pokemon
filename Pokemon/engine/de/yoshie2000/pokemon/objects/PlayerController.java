package de.yoshie2000.pokemon.objects;

import java.util.ArrayList;

import org.newdawn.slick.Input;

import de.yoshie2000.pokemon.Direction;
import de.yoshie2000.pokemon.engine.Game;
import de.yoshie2000.pokemon.maths.Vector;
import de.yoshie2000.pokemon.sprite.AnimationIndex;

public class PlayerController extends ObjectController {

	private boolean sprinting = false, tempAnimation = false;

	private Vector direction = new Vector(0, 0);

	private int speed = 1, pixelsMoved = 0;

	private int animationCounter;

	private ArrayList<AnimationIndex> animationIndecies;

	private ArrayList<AnimationIndex> walkingUpIndecies, walkingDownIndecies, walkingLeftIndecies, walkingRightIndecies;

	public PlayerController() {
		walkingUpIndecies = new ArrayList<AnimationIndex>();
		walkingUpIndecies.add(new AnimationIndex(6, false, true));
		walkingUpIndecies.add(new AnimationIndex(5, false, true));
		walkingUpIndecies.add(new AnimationIndex(6, true, true));
		walkingUpIndecies.add(new AnimationIndex(5, true, true));

		walkingDownIndecies = new ArrayList<AnimationIndex>();
		walkingDownIndecies.add(new AnimationIndex(0, false, true));
		walkingDownIndecies.add(new AnimationIndex(1, false, true));
		walkingDownIndecies.add(new AnimationIndex(0, true, true));
		walkingDownIndecies.add(new AnimationIndex(1, true, true));

		walkingLeftIndecies = new ArrayList<AnimationIndex>();
		walkingLeftIndecies.add(new AnimationIndex(2, false));
		walkingLeftIndecies.add(new AnimationIndex(3, false));
		walkingLeftIndecies.add(new AnimationIndex(2, false));
		walkingLeftIndecies.add(new AnimationIndex(4, false));

		walkingRightIndecies = new ArrayList<AnimationIndex>();
		walkingRightIndecies.add(new AnimationIndex(2, true));
		walkingRightIndecies.add(new AnimationIndex(3, true));
		walkingRightIndecies.add(new AnimationIndex(2, true));
		walkingRightIndecies.add(new AnimationIndex(4, true));
	}

	@Override
	public void update() {
		if (canMove) {

			sprinting = Game.getInput().isKeyDown(Input.KEY_LCONTROL);

			if (isMoving) {

				if (pixelsMoved % (Game.getGridSize() / 2) == 0) {
					animationCounter++;
					if (animationCounter >= animationIndecies.size()) {
						animationCounter = 0;
					}
				}

				Vector moveVector = sprinting ? direction.getMultiplied(2) : direction;
				position.add(moveVector);
				pixelsMoved += moveVector.getMagnitude();

				if (pixelsMoved >= Game.getGridSize()) {
					stopWalking();
				}

				animationIndex = animationIndecies.get(animationCounter);

				return;
			} else if (Game.isXPressed()) {
				Game.getMap().interactWithTile(getLookingTile(), getLookingDirection());
			}

			if (position.x % Game.getGridSize() != 0) {
				int multiplier = (int) position.x / (int) Game.getGridSize();
				position.x = Game.getGridSize() * multiplier;
			}
			if (position.y % Game.getGridSize() != 0) {
				int multiplier = (int) position.y / Game.getGridSize();
				position.y = Game.getGridSize() * multiplier;
			}

			Vector newPosition = new Vector(position.x, position.y);

			if (Game.getInput().isKeyDown(Input.KEY_W)) {
				direction = new Vector(0, -speed);
				newPosition.add(new Vector(0, -Game.getGridSize()));
				isMoving = true;
				animationIndecies = walkingUpIndecies;
			}
			if (Game.getInput().isKeyDown(Input.KEY_S) && !isMoving) {
				direction = new Vector(0, speed);
				newPosition.add(new Vector(0, Game.getGridSize()));
				isMoving = true;
				animationIndecies = walkingDownIndecies;
			}
			if (Game.getInput().isKeyDown(Input.KEY_A) && !isMoving) {
				direction = new Vector(-speed, 0);
				newPosition.add(new Vector(-Game.getGridSize(), 0));
				isMoving = true;
				animationIndecies = walkingLeftIndecies;
			}
			if (Game.getInput().isKeyDown(Input.KEY_D) && !isMoving) {
				direction = new Vector(speed, 0);
				newPosition.add(new Vector(Game.getGridSize(), 0));
				isMoving = true;
				animationIndecies = walkingRightIndecies;
			}

			if (isMoving) {
				animationIndex = animationIndecies.get(animationCounter);
				if (!Game.getMap().checkTile(newPosition, getLookingDirection(), true)) {
					stopWalking();
				}
			}
		}
	}

	@Override
	public void moveInDirection(Direction direction) {
		Vector newPosition = new Vector(position.x, position.y);

		switch (direction) {
		case UP:
			this.direction = new Vector(0, -speed);
			newPosition.add(new Vector(0, -Game.getGridSize()));
			isMoving = true;
			animationIndecies = walkingUpIndecies;
			break;
		case DOWN:
			this.direction = new Vector(0, speed);
			newPosition.add(new Vector(0, Game.getGridSize()));
			isMoving = true;
			animationIndecies = walkingDownIndecies;
			break;
		case LEFT:
			this.direction = new Vector(-speed, 0);
			newPosition.add(new Vector(-Game.getGridSize(), 0));
			isMoving = true;
			animationIndecies = walkingLeftIndecies;
			break;
		case RIGHT:
			this.direction = new Vector(speed, 0);
			newPosition.add(new Vector(Game.getGridSize(), 0));
			isMoving = true;
			animationIndecies = walkingRightIndecies;
			break;
		}

		if (isMoving) {
			animationIndex = animationIndecies.get(animationCounter);
			if (!Game.getMap().checkTile(newPosition, getLookingDirection(), true)) {
				stopWalking();
			}
		}
	}

	private Vector getLookingTile() {
		Vector d = direction.getNormalized().getMultiplied(Game.getGridSize());
		return position.getAdded(d);
	}

	private Direction getLookingDirection() {
		Vector d = direction.getNormalized();
		
		if (d.equals(new Vector(0, -1)))
			return Direction.UP;
		if (d.equals(new Vector(0, 1)))
			return Direction.DOWN;
		if (d.equals(new Vector(-1, 0)))
			return Direction.LEFT;
		if (d.equals(new Vector(1, 0)))
			return Direction.RIGHT;
		return null;
	}

	private void stopWalking() {
		isMoving = false;
		pixelsMoved = 0;
		tempAnimation = !tempAnimation;
		animationCounter = 0;
	}

}
