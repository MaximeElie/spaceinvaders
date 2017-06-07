package fr.unilim.iut.spaceinvaders;

import static org.junit.Assert.fail;

import org.junit.Test;

import fr.unilim.iut.spaceinvaders.model.Sprite;
import fr.unilim.iut.spaceinvaders.model.Missile;
import fr.unilim.iut.spaceinvaders.model.Envahisseur;
import fr.unilim.iut.spaceinvaders.model.Collision;
import fr.unilim.iut.spaceinvaders.model.Dimension;
import fr.unilim.iut.spaceinvaders.model.Direction;
import fr.unilim.iut.spaceinvaders.model.Position;

public class CollisionTest {
	@Test
	public void test_CollisionBienDetecte() {
		Sprite sprite1 = new Missile(new Dimension(2, 4), new Position(1, 5), 0, Direction.HAUT);
		Sprite sprite2 = new Envahisseur(new Dimension(4, 3), new Position(0, 2), 0);
		
		if(!Collision.detecterCollision(sprite1, sprite2)) {
			fail("Une colision devrait être détectée entre les deux sprite");
		}
	}
}
