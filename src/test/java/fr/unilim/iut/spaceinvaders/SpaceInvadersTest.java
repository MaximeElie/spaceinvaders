package fr.unilim.iut.spaceinvaders;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import fr.unilim.iut.spaceinvaders.model.Dimension;
import fr.unilim.iut.spaceinvaders.model.Position;
import fr.unilim.iut.spaceinvaders.model.SpaceInvaders;
import fr.unilim.iut.spaceinvaders.utils.DebordementEspaceJeuException;
import fr.unilim.iut.spaceinvaders.utils.HorsEspaceJeuException;
import fr.unilim.iut.spaceinvaders.utils.MissileException;

public class SpaceInvadersTest {
	
	private SpaceInvaders spaceinvaders;

    @Before
    public void initialisation() {
	    spaceinvaders = new SpaceInvaders(15, 10);
    }
	
	@Test
	public void test_AuDebut_JeuSpaceInvaderEstVide() {
		assertEquals("" + 
		"...............\n" + 
		"...............\n" +
		"...............\n" + 
		"...............\n" + 
		"...............\n" + 
		"...............\n" + 
		"...............\n" + 
		"...............\n" + 
		"...............\n" + 
		"...............\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
	}
	
	@Test
	public void test_unNouveauVaisseauEstCorrectementPositionneDansEspaceJeu() {
		spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(1,1), new Position(7,9), 1);
		assertEquals("" + 
		"...............\n" + 
		"...............\n" +
		"...............\n" + 
		"...............\n" + 
		"...............\n" + 
		"...............\n" + 
		"...............\n" + 
		"...............\n" + 
		"...............\n" + 
		".......V.......\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
	}
	
	@Test
	public void test_UnNouveauVaisseauPositionneHorsEspaceJeu_DoitLeverUneException() {
		
		try {
			spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(1,1), new Position(15,9), 1);
			fail("Position trop à droite : devrait déclencher une exception HorsEspaceJeuException");
		} catch (final HorsEspaceJeuException e) {
		}
		
		
		try {
			spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(1,1), new Position(-1,9), 1);
			fail("Position trop à gauche : devrait déclencher une exception HorsEspaceJeuException");
		} catch (final HorsEspaceJeuException e) {
		}
		
		
		try {
			spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(1,1), new Position(14,10), 1);
			fail("Position trop en bas : devrait déclencher une exception HorsEspaceJeuException");
		} catch (final HorsEspaceJeuException e) {
		}
		
		
		try {
			spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(1,1), new Position(14,-1), 1);
			fail("Position trop à haut : devrait déclencher une exception HorsEspaceJeuException");
		} catch (final HorsEspaceJeuException e) {
		}
			
	}
	
	@Test
	public void test_unNouveauVaisseauAvecDimensionEstCorrectementPositionneDansEspaceJeu() {
		spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(3,2), new Position(7,9), 1);
		assertEquals("" + 
		"...............\n" + 
		"...............\n" +
		"...............\n" + 
		"...............\n" + 
		"...............\n" + 
		"...............\n" + 
		"...............\n" + 
		"...............\n" + 
		".......VVV.....\n" + 
		".......VVV.....\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
	}
	
	@Test
	public void test_UnNouveauVaisseauPositionneDansEspaceJeuMaisAvecDimensionTropGrande_DoitLeverUneExceptionDeDebordement() {
		
		try {
			spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(9,2), new Position(7,9), 1);
			fail("Dépassement du vaisseau à droite en raison de sa longueur trop importante : devrait déclencher une exception DebordementEspaceJeuException");
		} catch (final DebordementEspaceJeuException e) {
		}
		
		
		try {
			spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(3,4), new Position(7,1), 1);
			fail("Dépassement du vaisseau vers le haut en raison de sa hauteur trop importante : devrait déclencher une exception DebordementEspaceJeuException");
		} catch (final DebordementEspaceJeuException e) {
		}
			
	}
	
	@Test
	public void test_VaisseauImmobile_DeplacerVaisseauVersLaDroite() {
		
		spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(3,2), new Position(12,9), 3);
		spaceinvaders.deplacerVaisseauVersLaDroite();
		assertEquals("" + 
		"...............\n" + 
		"...............\n" +
		"...............\n" + 
		"...............\n" + 
		"...............\n" + 
		"...............\n" + 
		"...............\n" + 
		"...............\n" + 
		"............VVV\n" + 
		"............VVV\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
	}
	
	@Test
    public void test_VaisseauAvance_DeplacerVaisseauVersLaGauche() {
		
	   spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(3,2),new Position(7,9), 3);
	   spaceinvaders.deplacerVaisseauVersLaGauche();
	
	   assertEquals("" + 
	   "...............\n" + 
	   "...............\n" +
	   "...............\n" + 
	   "...............\n" + 
	   "...............\n" + 
	   "...............\n" + 
	   "...............\n" + 
	   "...............\n" + 
	   "....VVV........\n" + 
	   "....VVV........\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
	}
    
    @Test
	public void test_VaisseauImmobile_DeplacerVaisseauVersLaGauche() {
		
		spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(3,2), new Position(0,9), 3);
		spaceinvaders.deplacerVaisseauVersLaGauche();
		
		assertEquals("" + 
		"...............\n" + 
		"...............\n" +
		"...............\n" + 
		"...............\n" + 
		"...............\n" + 
		"...............\n" + 
		"...............\n" + 
		"...............\n" + 
		"VVV............\n" + 
		"VVV............\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
	}
    
    @Test
    public void test_VaisseauAvance_DeplacerVaisseauVersLaDroite() {

        spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(3,2),new Position(7,9), 3);
        spaceinvaders.deplacerVaisseauVersLaDroite();
        assertEquals("" + 
        "...............\n" + 
        "...............\n" +
        "...............\n" + 
        "...............\n" + 
        "...............\n" + 
        "...............\n" + 
        "...............\n" + 
        "...............\n" + 
        "..........VVV..\n" + 
        "..........VVV..\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
    }
    
    @Test
    public void test_VaisseauAvancePartiellement_DeplacerVaisseauVersLaDroite() {

       spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(3,2),new Position(10,9),3);
       spaceinvaders.deplacerVaisseauVersLaDroite();
       assertEquals("" + 
       "...............\n" + 
       "...............\n" +
       "...............\n" + 
       "...............\n" + 
       "...............\n" + 
       "...............\n" + 
       "...............\n" + 
       "...............\n" + 
       "............VVV\n" + 
       "............VVV\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
    }
    
    @Test
    public void test_VaisseauAvancePartiellement_DeplacerVaisseauVersLaGauche() {

       spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(3,2),new Position(1,9), 3);
       spaceinvaders.deplacerVaisseauVersLaGauche();

       assertEquals("" + 
       "...............\n" + 
       "...............\n" +
       "...............\n" + 
       "...............\n" + 
       "...............\n" + 
       "...............\n" + 
       "...............\n" + 
       "...............\n" + 
       "VVV............\n" + 
       "VVV............\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
     }
    
    @Test
    public void test_MissileBienTireDepuisVaisseau_VaisseauLongueurImpaireMissileLongueurImpaire() {

	   spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(7,2),new Position(5,9), 2);
	   spaceinvaders.tirerUnMissile(new Dimension(3,2),2);

      assertEquals("" + 
      "...............\n" + 
      "...............\n" +
      "...............\n" + 
      "...............\n" + 
      "...............\n" + 
      "...............\n" + 
      ".......MMM.....\n" + 
      ".......MMM.....\n" + 
      ".....VVVVVVV...\n" + 
      ".....VVVVVVV...\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
   }
    
    @Test(expected = MissileException.class)
	public void test_PasAssezDePlacePourTirerUnMissile_UneExceptionEstLevee() throws Exception { 
	   spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(7,2),new Position(5,9), 1);
	   spaceinvaders.tirerUnMissile(new Dimension(7,9),1);
	}
    
    @Test
    public void test_MissileAvanceAutomatiquement_ApresTirDepuisLeVaisseau() {

	   spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(7,2),new Position(5,9), 2);
	   spaceinvaders.tirerUnMissile(new Dimension(3,2),2);

	   spaceinvaders.deplacerMissiles();
	   
       assertEquals("" + 
       "...............\n" + 
       "...............\n" +
       "...............\n" + 
       "...............\n" + 
       ".......MMM.....\n" + 
       ".......MMM.....\n" + 
       "...............\n" + 
       "...............\n" + 
       ".....VVVVVVV...\n" + 
       ".....VVVVVVV...\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
   }
    
    @Test
    public void test_MissileDisparait_QuandIlCommenceASortirDeEspaceJeu() {

 	   spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(7,2),new Position(5,9), 1);
 	   spaceinvaders.tirerUnMissile(new Dimension(3,2),1);
 	   for (int i = 1; i <=6 ; i++) {
 		   spaceinvaders.deplacerMissiles();
 	   }
 	   
 	  spaceinvaders.deplacerMissiles();
 	   
        assertEquals("" +
        "...............\n" + 
        "...............\n" +
        "...............\n" + 
        "...............\n" +
        "...............\n" +
        "...............\n" + 
        "...............\n" +
        "...............\n" + 
        ".....VVVVVVV...\n" + 
        ".....VVVVVVV...\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
    }
    
    @Test
    public void test_EnvahisseurCorrectementPositionne() {
    	spaceinvaders.positionnerUnNouvelEnvahisseur(new Dimension(3, 3), new Position(7,2), 2, 0);
    	
    	assertEquals("" + 
		".......EEE.....\n" + 
		".......EEE.....\n" +
		".......EEE.....\n" + 
		"...............\n" + 
		"...............\n" + 
		"...............\n" + 
		"...............\n" + 
		"...............\n" + 
		"...............\n" + 
		"...............\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());    	
    }
    
    @Test
    public void test_EnvahisseurSeDeplaceVersLaDroiteParDefault() {
    	
    	spaceinvaders.positionnerUnNouvelEnvahisseur(new Dimension(3, 3), new Position(7,2), 2, 0);
    	
    	spaceinvaders.deplacerEnvahisseurs();
    	
    	assertEquals("" + 
    			".........EEE...\n" + 
    			".........EEE...\n" +
    			".........EEE...\n" + 
    			"...............\n" + 
    			"...............\n" + 
    			"...............\n" + 
    			"...............\n" + 
    			"...............\n" + 
    			"...............\n" + 
    			"...............\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
    }
    
    @Test
    public void test_EnvahisseurChangeDeDirectionAuBordDeEspaceDeJeu() {
    	spaceinvaders.positionnerUnNouvelEnvahisseur(new Dimension(3, 3), new Position(11,2), 2, 0);
    	
    	spaceinvaders.deplacerEnvahisseurs();
    	spaceinvaders.deplacerEnvahisseurs();
    	
    	assertEquals("" + 
    			"..........EEE..\n" + 
    			"..........EEE..\n" +
    			"..........EEE..\n" + 
    			"...............\n" + 
    			"...............\n" + 
    			"...............\n" + 
    			"...............\n" + 
    			"...............\n" + 
    			"...............\n" + 
    			"...............\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
    }
    
    /*
     * 
     * Ce test était utilisé lorsque nous n'avions qu'un envahisseur
		
	    @Test
	    public void test_PartieFinieQuandMissileToucheEnvahisseur() {
	    	spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(7,2),new Position(5,9), 2);
	    	spaceinvaders.positionnerUnNouvelEnvahisseur(new Dimension(3, 3), new Position(7,2), 0);
	    	spaceinvaders.tirerUnMissile(new Dimension(3,2),2);
	    	
	    	spaceinvaders.deplacerMissiles();
	    	spaceinvaders.deplacerMissiles();
	
	    	if(!spaceinvaders.isFinPartie())
	    		fail("La partie devrait être terminée lors de la collision entre le missile et l'envahisseur");
	    }
	    
    */
    
    @Test
    public void test_plusieursMissilesPossibles() {
    	spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(3, 2), new Position(0, 9), 1);
    	
    	spaceinvaders.tirerUnMissile(new Dimension(1, 2), 3);
    	spaceinvaders.deplacerMissiles();
    	
    	spaceinvaders.tirerUnMissile(new Dimension(1, 2), 3);
    	
    	assertEquals("" + 
    			"...............\n" + 
    			"...............\n" +
    			"...............\n" + 
    			".M.............\n" + 
    			".M.............\n" + 
    			"...............\n" + 
    			".M.............\n" + 
    			".M.............\n" + 
    			"VVV............\n" + 
    			"VVV............\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
    }
    
    @Test
    public void test_LesMissilesNePeuventPasSeSuperposer() {
    	spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(3, 2), new Position(0, 9), 1);
    	spaceinvaders.tirerUnMissile(new Dimension(1, 2), 1);
    	spaceinvaders.deplacerMissiles();
    	
    	spaceinvaders.tirerUnMissile(new Dimension(1, 2), 1);
    	
    	assertEquals("" + 
    			"...............\n" + 
    			"...............\n" +
    			"...............\n" + 
    			"...............\n" + 
    			"...............\n" + 
    			".M.............\n" + 
    			".M.............\n" + 
    			"...............\n" + 
    			"VVV............\n" + 
    			"VVV............\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
    }
    
    @Test
    public void test_LaLigneEnvahisseursSeDeplaceCorrectement() {
    	for(int i = 0 ; i < 3 ; i++) {
    		spaceinvaders.positionnerUnNouvelEnvahisseur(new Dimension(3, 2), new Position(i*4,1), 2, i);
    	}
    	
    	spaceinvaders.deplacerEnvahisseurs();
    	spaceinvaders.deplacerEnvahisseurs();
    	
    	assertEquals("" + 
    			"....EEE.EEE.EEE\n" + 
    			"....EEE.EEE.EEE\n" +
    			"...............\n" + 
    			"...............\n" + 
    			"...............\n" + 
    			"...............\n" + 
    			"...............\n" + 
    			"...............\n" + 
    			"...............\n" + 
    			"...............\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
    	
    	
    	spaceinvaders.deplacerEnvahisseurs();
    	
    	assertEquals("" + 
    			"..EEE.EEE.EEE..\n" + 
    			"..EEE.EEE.EEE..\n" +
    			"...............\n" + 
    			"...............\n" + 
    			"...............\n" + 
    			"...............\n" + 
    			"...............\n" + 
    			"...............\n" + 
    			"...............\n" + 
    			"...............\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
    	
    }
    
    @Test
    public void test_DetruireUnEnvahisseurAugmenteLeScore() {
    	spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(3, 2), new Position(0, 9), 1);
    	spaceinvaders.positionnerUnNouvelEnvahisseur(new Dimension(3, 2), new Position(0, 1), 0, 0);
    	
    	spaceinvaders.tirerUnMissile(new Dimension(1, 2), 2);
    	
    	spaceinvaders.deplacerMissiles();
    	spaceinvaders.deplacerMissiles();
    	spaceinvaders.deplacerMissiles();
    	
    	if(spaceinvaders.getScore() != 1)
    		fail("Le score devrait être égale a 1 si on détruit un envahisseur");
    }
    
    @Test
    public void test_EnvahisseurPeutTirerMissile() {
    	spaceinvaders.positionnerUnNouvelEnvahisseur(new Dimension(3, 2), new Position(0, 1), 0, 0);
    	spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(3, 2), new Position(0, 9), 1);
    	spaceinvaders.tirerMissileEnvahisseur(new Dimension(1, 2), 2, 0);
    	
    	assertEquals("" + 
    			"EEE............\n" + 
    			"EEE............\n" +
    			".M.............\n" + 
    			".M.............\n" + 
    			"...............\n" + 
    			"...............\n" + 
    			"...............\n" + 
    			"...............\n" + 
    			"VVV............\n" + 
    			"VVV............\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
    }
    
    @Test
    public void test_MissileEnvahisseurSeDeplaceVersLeBas() {
    	spaceinvaders.positionnerUnNouvelEnvahisseur(new Dimension(3, 2), new Position(0, 1), 0, 0);
    	spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(3, 2), new Position(0, 9), 1);
    	spaceinvaders.tirerMissileEnvahisseur(new Dimension(1, 2), 2, 0);
    	
    	spaceinvaders.deplacerMissilesEnvahisseurs();
    	
    	assertEquals("" + 
    			"EEE............\n" + 
    			"EEE............\n" +
    			"...............\n" + 
    			"...............\n" + 
    			".M.............\n" + 
    			".M.............\n" + 
    			"...............\n" + 
    			"...............\n" + 
    			"VVV............\n" + 
    			"VVV............\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
    }
    
    @Test
    public void test_siMissileEnvahisseurToucheVaisseau_finPartie() {
    	spaceinvaders.positionnerUnNouvelEnvahisseur(new Dimension(3, 2), new Position(0, 1), 0, 0);
    	spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(3, 2), new Position(0, 9), 1);
    	spaceinvaders.tirerMissileEnvahisseur(new Dimension(1, 2), 3, 0);
    	
    	spaceinvaders.deplacerMissilesEnvahisseurs();
    	spaceinvaders.deplacerMissilesEnvahisseurs();
    	
    	if(!spaceinvaders.isFinPartie())
    		fail("La partie devrait se finir lorsque le vaisseau est touche");
    }
}