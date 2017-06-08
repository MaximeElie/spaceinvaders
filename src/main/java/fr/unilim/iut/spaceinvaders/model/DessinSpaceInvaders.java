package fr.unilim.iut.spaceinvaders.model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import java.util.List;

import fr.unilim.iut.spaceinvaders.moteurjeu.DessinJeu;

public class DessinSpaceInvaders implements DessinJeu {

	private SpaceInvaders jeu;

	public DessinSpaceInvaders(SpaceInvaders spaceInvaders) {
		this.jeu = spaceInvaders;
	}

	public void dessiner(BufferedImage im) {
		
		dessinBackground(im);
		
		if(jeu.isFinPartie()) {
			this.dessinerMessageFinPartie(im);
			this.dessinerScore(im);
			return;
		}
		
		if (this.jeu.aUnVaisseau()) {
			Vaisseau vaisseau = this.jeu.recupererVaisseau();
			this.dessinerUnVaisseau(vaisseau, im);
		}
		
		for(Missile missile : jeu.recupererMissiles()) {
			this.dessinerUnMissile(missile, im);
		}
		
		for(List<Envahisseur> envs : jeu.recupererEnvahisseurs()) {
			for(Envahisseur env : envs) {
				this.dessinerUnEnvahisseur(env, im);
			}
		}
		
		for(Missile missile : jeu.recupererMissilesEnvahisseurs()) {
			this.dessinerUnMissileEnvahisseur(missile, im);
		}
		
		this.dessinerScore(im);
	}

	private void dessinBackground(BufferedImage im) {
		Graphics2D crayon = (Graphics2D) im.getGraphics();

		crayon.setColor(Color.black);
		crayon.fillRect(0, 0, Constante.ESPACEJEU_LONGUEUR + Constante.ZONESCORE_LONGUEUR, Constante.ESPACEJEU_HAUTEUR);			
	}

	private void dessinerUnMissileEnvahisseur(Missile missile, BufferedImage im) {
		Graphics2D crayon = (Graphics2D) im.getGraphics();

		crayon.setColor(Color.green);
		crayon.fillRect(missile.abscisseLaPlusAGauche(), missile.ordonneeLaPlusHaute(), missile.longueur(),
				missile.hauteur());	
		
	}

	private void dessinerScore(BufferedImage im) {
		Graphics2D crayon = (Graphics2D) im.getGraphics();

		crayon.setColor(Color.white);
		crayon.setFont(new Font("Verdana", Font.BOLD, 30));
		crayon.drawString("Score : " + jeu.getScore(), Constante.ESPACEJEU_LONGUEUR, Constante.ESPACEJEU_HAUTEUR/2);		
	}

	private void dessinerMessageFinPartie(BufferedImage im) {
		Graphics2D crayon = (Graphics2D) im.getGraphics();

		crayon.setColor(Color.white);
		crayon.setFont(new Font("Verdana", Font.BOLD, 50));
		crayon.drawString("FIN DE LA PARTIE", 50, Constante.ESPACEJEU_HAUTEUR/2);
	}

	private void dessinerUnMissile(Missile missile, BufferedImage im) {
		Graphics2D crayon = (Graphics2D) im.getGraphics();

		crayon.setColor(Color.blue);
		crayon.fillRect(missile.abscisseLaPlusAGauche(), missile.ordonneeLaPlusHaute(), missile.longueur(),
				missile.hauteur());		
	}

	private void dessinerUnVaisseau(Vaisseau vaisseau, BufferedImage im) {
		Graphics2D crayon = (Graphics2D) im.getGraphics();

		crayon.setColor(Color.gray);
		crayon.fillRect(vaisseau.abscisseLaPlusAGauche(), vaisseau.ordonneeLaPlusHaute(), vaisseau.longueur(),
				vaisseau.hauteur());

	}
	
	private void dessinerUnEnvahisseur(Envahisseur envahisseur, BufferedImage im) {
		Graphics2D crayon = (Graphics2D) im.getGraphics();
		
		crayon.setColor(Color.yellow);
		crayon.fillRect(envahisseur.abscisseLaPlusAGauche(), envahisseur.ordonneeLaPlusHaute(), envahisseur.longueur(), envahisseur.hauteur());
	}

}