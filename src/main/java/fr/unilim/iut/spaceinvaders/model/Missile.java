package fr.unilim.iut.spaceinvaders.model;

public class Missile extends Sprite {
	
	private Direction direction;

	public Missile(Dimension dimension, Position positionOrigine, int vitesse, Direction direction) {
		super(dimension, positionOrigine, vitesse);
		this.direction = direction;
	}
	
	public Direction getDirection() {
		return direction;
	}

	public void deplacerVerticalement() {
		deplacerVerticalementVers(direction);		
	}
}
