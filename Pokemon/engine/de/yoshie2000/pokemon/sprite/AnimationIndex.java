package de.yoshie2000.pokemon.sprite;

public class AnimationIndex {

	private int index;
	private boolean reversed;
	private boolean tempReversable = false;

	public AnimationIndex(int index, boolean reversed) {
		this.index = index;
		this.reversed = reversed;
	}
	
	public AnimationIndex(int index, boolean reversed, boolean tempReversable) {
		this.index = index;
		this.tempReversable = tempReversable;
		this.reversed = reversed;
	}
	
	public boolean isTempReversable() {
		return tempReversable;
	}

	public void setTempReversable(boolean tempReversable) {
		this.tempReversable = tempReversable;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public boolean isReversed() {
		return reversed;
	}

	public void setReversed(boolean reversed) {
		this.reversed = reversed;
	}

}
