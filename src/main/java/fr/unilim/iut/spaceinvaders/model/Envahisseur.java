package fr.unilim.iut.spaceinvaders.model;

import fr.unilim.iut.spaceinvaders.utils.MissileException;

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

	public Missile tirerUnMissile(Dimension dimensionMissile, int vitesseMissile) {
		if(dimensionMissile.longueur > this.longueur())
			throw new MissileException("la longueur du missile est supérieure à celle du vaisseau");
		
		Position positionOrigineMissile = calculerLaPositionDeTirDuMissile(dimensionMissile);
		return new Missile(dimensionMissile, positionOrigineMissile, vitesseMissile, Direction.BAS_ECRAN);
	}
	
	public Position calculerLaPositionDeTirDuMissile(Dimension dimensionMissile) {
		int abscisseMilieuVaisseau = this.abscisseLaPlusAGauche() + (this.longueur() / 2);
		int abscisseOrigineMissile = abscisseMilieuVaisseau - (dimensionMissile.longueur() / 2);

		int ordonneeeOrigineMissile = this.ordonneeLaPlusBasse() + dimensionMissile.hauteur;
		
		return new Position(abscisseOrigineMissile, ordonneeeOrigineMissile);		
	}
}
