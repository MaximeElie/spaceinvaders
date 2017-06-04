package fr.unilim.iut.spaceinvaders.model;

import java.util.ArrayList;
import java.util.List;

import fr.unilim.iut.spaceinvaders.moteurjeu.Commande;
import fr.unilim.iut.spaceinvaders.moteurjeu.Jeu;
import fr.unilim.iut.spaceinvaders.utils.DebordementEspaceJeuException;
import fr.unilim.iut.spaceinvaders.utils.HorsEspaceJeuException;
import fr.unilim.iut.spaceinvaders.utils.MissileException;

public class SpaceInvaders implements Jeu {

	private int longueur;
	private int hauteur;
	private Vaisseau vaisseau;
	private List<Missile> missiles;
	private List<Envahisseur> envahisseurs;
	private boolean finPartie;

	public boolean isFinPartie() {
		return finPartie;
	}

	public SpaceInvaders(int longueur, int hauteur) {
		this.longueur = longueur;
		this.hauteur = hauteur;
		missiles = new ArrayList<Missile>();
		envahisseurs = new ArrayList<Envahisseur>();
	}

	public void initialiserJeu() {
		Position positionVaisseau = new Position(this.longueur / 2, this.hauteur - 1);
		Dimension dimensionVaisseau = new Dimension(Constante.VAISSEAU_LONGUEUR, Constante.VAISSEAU_HAUTEUR);
		positionnerUnNouveauVaisseau(dimensionVaisseau, positionVaisseau, Constante.VAISSEAU_VITESSE);
		
		positionnerLigneEnvahisseur();
		
		finPartie = false;
	}

	private void positionnerLigneEnvahisseur() {
		int nbEnvahisseur = calculerNbEnvahisseur();
		
		for(int i = 0 ; i < nbEnvahisseur ; i++) {
			positionnerUnNouvelEnvahisseur(	new Dimension(Constante.ENVAHISSEUR_LONGUEUR, Constante.ENVAHISSEUR_HAUTEUR),
					new Position(calculerAbscisseEnvahisseur(i), Constante.ENVAHISSEUR_HAUTEUR*2),
					Constante.ENVAHISSEUR_VITESSE);
		}
	}

	private int calculerAbscisseEnvahisseur(int i) {
		return (int) ( (float)i * ( ((float)Constante.ENVAHISSEUR_LONGUEUR) * 1.5 ) );
	}

	private int calculerNbEnvahisseur() {
		return (int) ( ( (float) Constante.ESPACEJEU_LONGUEUR ) / ( ((float)Constante.ENVAHISSEUR_LONGUEUR) * 1.5 ) ) - 1;
	}

	public String recupererEspaceJeuDansChaineASCII() {
		StringBuilder espaceDeJeu = new StringBuilder();
		for (int y = 0; y < hauteur; y++) {
			for (int x = 0; x < longueur; x++) {
				espaceDeJeu.append(recupererMarqueDeLaPosition(x, y));
			}
			espaceDeJeu.append(Constante.MARQUE_FIN_LIGNE);
		}
		return espaceDeJeu.toString();
	}

	private char recupererMarqueDeLaPosition(int x, int y) {
		char marque;
		if (this.aUnVaisseauQuiOccupeLaPosition(x, y))
			marque = Constante.MARQUE_VAISSEAU;
		else if (this.aUnMissileQuiOccupeLaPosition(x, y))
			marque = Constante.MARQUE_MISSILE;
		else if(this.aUnEnvahisseurQuiOccupeLaPosition(x, y))
			marque = Constante.MARQUE_ENVAHISSEUR;
		else
			marque = Constante.MARQUE_VIDE;
		return marque;
	}

	private boolean aUnEnvahisseurQuiOccupeLaPosition(int x, int y) {
		for(Envahisseur envahisseur : envahisseurs) {
			if(envahisseur.occupeLaPosition(x, y))
				return true;
		}
		
		return false;
	}

	private boolean aUnMissileQuiOccupeLaPosition(int x, int y) {
		for(Missile missile : missiles) {
			if(missile.occupeLaPosition(x, y))
				return true;
		}
		
		return false;
	}

	private boolean aUnVaisseauQuiOccupeLaPosition(int x, int y) {
		return this.aUnVaisseau() && vaisseau.occupeLaPosition(x, y);
	}

	public boolean aUnVaisseau() {
		return vaisseau != null;
	}

	public void positionnerUnNouveauVaisseau(Dimension dimension, Position position, int vitesse) {

		int x = position.abscisse();
		int y = position.ordonnee();

		if (!estDansEspaceJeu(x, y))
			throw new HorsEspaceJeuException("La position du vaisseau est en dehors de l'espace jeu");

		int longueurVaisseau = dimension.longueur();
		int hauteurVaisseau = dimension.hauteur();

		if (!estDansEspaceJeu(x + longueurVaisseau - 1, y))
			throw new DebordementEspaceJeuException(
					"Le vaisseau déborde de l'espace jeu vers la droite à cause de sa longueur");
		if (!estDansEspaceJeu(x, y - hauteurVaisseau + 1))
			throw new DebordementEspaceJeuException(
					"Le vaisseau déborde de l'espace jeu vers le bas à cause de sa hauteur");

		vaisseau = new Vaisseau(dimension, position, vitesse);
	}

	private boolean estDansEspaceJeu(int x, int y) {
		return ((x >= 0) && (x < longueur)) && ((y >= 0) && (y < hauteur));
	}

	public void deplacerVaisseauVersLaDroite() {
		if (vaisseau.abscisseLaPlusADroite() < (longueur - 1)) {
			vaisseau.deplacerHorizontalementVers(Direction.DROITE);
			if (!estDansEspaceJeu(vaisseau.abscisseLaPlusADroite(), vaisseau.ordonneeLaPlusBasse())) {
				vaisseau.positionner(longueur - vaisseau.longueur(), vaisseau.ordonneeLaPlusBasse());
			}
		}
	}

	public void deplacerVaisseauVersLaGauche() {
		if (0 < vaisseau.abscisseLaPlusAGauche()) {
			vaisseau.deplacerHorizontalementVers(Direction.GAUCHE);
			if (!estDansEspaceJeu(vaisseau.abscisseLaPlusAGauche(), vaisseau.ordonneeLaPlusBasse())) {
				vaisseau.positionner(0, vaisseau.ordonneeLaPlusBasse());
			}
		}
	}

	public Vaisseau recupererVaisseau() {
		return this.vaisseau;
	}

	public void evoluer(Commande commandeUser) {

		if (commandeUser.gauche) {
			deplacerVaisseauVersLaGauche();
		}

		if (commandeUser.droite) {
			deplacerVaisseauVersLaDroite();
		}

		if (commandeUser.tir) {
			tirerUnMissile(new Dimension(Constante.MISSILE_LONGUEUR, Constante.MISSILE_HAUTEUR),
					Constante.MISSILE_VITESSE);
		}
		
		deplacerEnvahisseurs();
		
		deplacerMissiles();
	}

	private void finPartie() {
		finPartie = true;
	}

	public boolean etreFini() {
		return false;
	}

	public void tirerUnMissile(Dimension dimensionMissile, int vitesseMissile) {

		if ((vaisseau.hauteur() + dimensionMissile.hauteur()) > this.hauteur)
			throw new MissileException(
					"Pas assez de hauteur libre entre le vaisseau et le haut de l'espace jeu pour tirer le missile");
		
		Missile nouveauMissile = this.vaisseau.tirerUnMissile(dimensionMissile, vitesseMissile);
		
		boolean collision = false;
		for(Missile missile : missiles) {
			if(Collision.detecterCollision(missile, nouveauMissile))
				collision = true;
		}
		
		if(!collision)
			this.missiles.add(nouveauMissile);
	}

	public List<Missile> recupererMissiles() {
		return this.missiles;
	}

	public void deplacerMissiles() {
		
		for(int i = 0 ; i < missiles.size() ; i++) {
			missiles.get(i).deplacerVerticalementVers(Direction.HAUT_ECRAN);
			
			for(int j = 0 ; j < envahisseurs.size() ; j++) {
				if(Collision.detecterCollision(envahisseurs.get(j), missiles.get(i)))
					envahisseurs.remove(j);
			}
			
			if(envahisseurs.size() == 0) {
				finPartie();
			}
			
			if(!estDansEspaceJeu(missiles.get(i).abscisseLaPlusAGauche(), missiles.get(i).ordonneeLaPlusHaute()))
				missiles.remove(i);
		}
	}

	public void positionnerUnNouvelEnvahisseur(Dimension dimension, Position position, int vitesse) {
		int x = position.abscisse();
		int y = position.ordonnee();

		if (!estDansEspaceJeu(x, y))
			throw new HorsEspaceJeuException("La position de l'envahisseur est en dehors de l'espace jeu");

		int longueurEnvahisseur = dimension.longueur();
		int hauteurEnvahisseur = dimension.hauteur();

		if (!estDansEspaceJeu(x + longueurEnvahisseur - 1, y))
			throw new DebordementEspaceJeuException(
					"L'envahisseur déborde de l'espace jeu vers la droite à cause de sa longueur");
		if (!estDansEspaceJeu(x, y - hauteurEnvahisseur + 1))
			throw new DebordementEspaceJeuException(
					"L'envahisseur déborde de l'espace jeu vers le bas à cause de sa hauteur");

		envahisseurs.add(new Envahisseur(dimension, position, vitesse));
	}

	public List<Envahisseur> recupererEnvahisseurs() {
		return envahisseurs;
	}

	public void deplacerEnvahisseurs() {
		
		for(int i = 0 ; i < envahisseurs.size() ; i++) {
		
			if( (envahisseurs.get(i).abscisseLaPlusAGauche() == 0 && envahisseurs.get(i).getDirection() == Direction.GAUCHE)
			||  (envahisseurs.get(i).abscisseLaPlusADroite() == (this.longueur - 1) && envahisseurs.get(i).getDirection() == Direction.DROITE) ) {
				
				for(int j = 0 ; j < envahisseurs.size() ; j++) {
					envahisseurs.get(j).inverserDirection();
				}
				
			}
		}
		
		for(int i = 0 ; i < envahisseurs.size() ; i++) {
			envahisseurs.get(i).deplacerHorizontalement();
			
			if (!estDansEspaceJeu(envahisseurs.get(i).abscisseLaPlusAGauche(), envahisseurs.get(i).ordonneeLaPlusBasse())) {
				envahisseurs.get(i).positionner(0, envahisseurs.get(i).ordonneeLaPlusBasse());
			}
			else if (!estDansEspaceJeu(envahisseurs.get(i).abscisseLaPlusADroite(), envahisseurs.get(i).ordonneeLaPlusBasse())) {
				envahisseurs.get(i).positionner(longueur - envahisseurs.get(i).longueur(), envahisseurs.get(i).ordonneeLaPlusBasse());
			}
		}
	}

}