package fr.unilim.iut.spaceinvaders.model;

public class Envahisseur extends Sprite {
	
	private Direction direction;

	public Envahisseur(Dimension dimension, Position position, int vitesse) {
		super(dimension, position, vitesse);
		direction = Direction.DROITE;
	}

	public void deplacerHorizontalement() {
		deplacerHorizontalementVers(direction);
	}

	public void inverserDirection() {
		if(direction == Direction.DROITE)
			direction = Direction.GAUCHE;
		else if(direction == Direction.GAUCHE)
			direction = Direction.DROITE;
	}
	
	public Direction getDirection() {
		return direction;
	}
}
