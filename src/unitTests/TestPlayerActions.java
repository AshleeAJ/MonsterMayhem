package unitTests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import monsterMayhem.Element;
import monsterMayhem.Monster;
import monsterMayhem.Player;

class TestPlayerActions {
	
	// construct some objects for testing
	Player player1 = new Player("Knight Eric", 3, 40, 100, Element.getIce());
	Player player2 = new Player("Princess Yasmin", 1, 30, 80, Element.getWater());
	Monster monster1 = new Monster("Dragon", 2, 50, 120, Element.getFire());

	@Test
	void testAttackMonsterNoBuff() {
		player1.attack(monster1);
		assertEquals(80, monster1.getHealth());
	}
	
	@Test
	void testAttackMonsterElementalBuff() {
		player2.attack(monster1);
		assertEquals(60, monster1.getHealth());
	}
	
	@Test
	void testAttackMonsterLifeBelowZero() {
		monster1.setHealth(monster1.getHealth() - 100);
		player1.attack(monster1);
		assertEquals(0, monster1.getHealth());
	}
	
	@Test
	void testHealPlayer() {
		player1.setHealth(player1.getHealth() - 50);
		player2.heal(player1);
		assertEquals(80, player1.getHealth());
	}
	
	@Test
	void testHealPlayerOverLife() {
		player1.setHealth(player1.getHealth() - 10);
		player2.heal(player1);
		assertEquals(player1.getLife(), player1.getHealth());
	}
	
	@Test
	void testPowerUp() {
		player1.powerUp(player1);
		assertEquals(2, player1.getSpeed());
		assertEquals(80, player1.getPower());
	}
	
	@Test
	void testPowerUpInvalidSpeedAmount() {
		assertFalse(player2.powerUp(player2));
	}

}
