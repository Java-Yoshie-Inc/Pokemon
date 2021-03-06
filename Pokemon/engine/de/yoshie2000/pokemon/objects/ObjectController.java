package de.yoshie2000.pokemon.objects;

import de.yoshie2000.pokemon.Direction;
import de.yoshie2000.pokemon.maths.Vector;
import de.yoshie2000.pokemon.sprite.AnimationIndex;

public abstract class ObjectController {
	
	protected Vector position;
	
	protected AnimationIndex animationIndex;
	
	protected boolean canMove = true, isMoving = false;

	public ObjectController() {
		position = new Vector(0, 0);
	}
	
	public ObjectController(Vector position) {
		this.position = position;
	}
	
	public abstract void update();
	
	public abstract void moveInDirection(Direction direction);
	
	public void moveToTile(Vector tile) {
		
	}

	public Vector getPosition() {
		return position;
	}

	public void setPosition(Vector position) {
		this.position = position;
	}

	public AnimationIndex getAnimationIndex() {
		return animationIndex;
	}

	public void setAnimationIndex(AnimationIndex animationIndex) {
		this.animationIndex = animationIndex;
	}

	public boolean isCanMove() {
		return canMove;
	}

	public void setCanMove(boolean canMove) {
		this.canMove = canMove;
	}

	public boolean isMoving() {
		return isMoving;
	}

	public void setMoving(boolean isMoving) {
		this.isMoving = isMoving;
	}
	
}
