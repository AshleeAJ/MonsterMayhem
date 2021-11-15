package unitTests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import monsterMayhem.Monster;
import monsterMayhem.Player;
import monsterMayhem.Element;

class TestMonsterActions {

	// construct some objects for testing
	Player player1 = new Player("Knight Eric", 3, 40, 100, Element.getIce());
	Monster monster1 = new Monster("Dragon", 2, 50, 120, Element.getFire());
	
	@Test
	void testAttackPlayer() {
		monster1.attack(player1);
		assertEquals(50, player1.getHealth());
	}
	
	@Test
	void testAttackPlayerLifeBelowZero() {
		player1.setHealth(player1.getHealth() - 60);
		monster1.attack(player1);
		assertEquals(0, player1.getHealth());
	}

}
