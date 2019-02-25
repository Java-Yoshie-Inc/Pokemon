package de.yoshie2000.pokemon.engine;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public interface GameLauncher {
	
	/**
	 * 
	 * This method will be called <b>after</b> <i>Game.create(...)</i> has been called.
	 *
	 */
	public void initialize(GameContainer container);
	
	public void update(GameContainer container);
	
	public void render(GameContainer container, Graphics graphics);
	
}
