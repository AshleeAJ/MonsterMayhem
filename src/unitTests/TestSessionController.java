package unitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import monsterMayhem.Element;
import monsterMayhem.Entity;
import monsterMayhem.Monster;
import monsterMayhem.Player;
import monsterMayhemController.SessionController;
import monsterMayhemController.SessionMemory;

class TestSessionController {
	
	// construct some monsters for testing
	Monster phoenix = SessionController.getInstance().findMonster("Phoenix");
	Monster dragon1 = SessionController.getInstance().findMonster("Dragon");
	Monster dragon2 = SessionController.getInstance().findMonster("Dragon");
	
	// construct some players for testing
	Player player1 = new Player("Knight Eric", 3, 40, 100, Element.getIce());
	Player player2 = new Player("Princess Yasmin", 1, 30, 80, Element.getWater());

	@Test
	void testFindElements() {
		Element testFire = SessionController.getInstance().findElement("Fire");
		assertEquals(testFire, Element.getFire());
		Element testWater = SessionController.getInstance().findElement("Water");
		assertEquals(testWater, Element.getWater());
		Element testIce = SessionController.getInstance().findElement("Ice");
		assertEquals(testIce, Element.getIce());
	}
	
	@Test
	void testInvalidElement() {
		Element testInvalid = SessionController.getInstance().findElement("Earth");
		assertEquals(testInvalid, null);
	}
	
	@Test
	void testFindCreateMonster() {
		assertEquals(phoenix.getClass(), Monster.class);
	}
	
	@Test
	void testFindCreateMultipleMonster() {
		assertNotEquals(dragon1.hashCode(), dragon2.hashCode());
	}
	
	@Test
	void testGenerateTurns() {
		// add players and monsters to session memory
		SessionMemory.getInstance().addPlayer(player1);
		SessionMemory.getInstance().addPlayer(player2);
		SessionMemory.getInstance().addMonster(phoenix);
		SessionMemory.getInstance().addMonster(dragon1);
		SessionMemory.getInstance().addMonster(dragon2);
		// generate turns from session memory
		ArrayList<Entity> generatedTurns = SessionController.getInstance().generateTurns();
		assertNotEquals(generatedTurns, null);
	}
	
	@Test
	void testGeneratedTurnsRandomOrder() {
		SessionMemory.getInstance().addPlayer(player1);
		SessionMemory.getInstance().addPlayer(player2);
		SessionMemory.getInstance().addMonster(phoenix);
		SessionMemory.getInstance().addMonster(dragon1);
		SessionMemory.getInstance().addMonster(dragon2);
		
		ArrayList<Entity> orderedTurns = new ArrayList<Entity>();
		ArrayList<Player> players = SessionMemory.getInstance().getPlayers();
		for (Player p : players) {
			for (int i = 0; i < p.getSpeed(); i++) {
				orderedTurns.add(p);
			}
		}
		ArrayList<Monster> monsters = SessionMemory.getInstance().getMonsters();
		for (Monster m : monsters) {
			for (int i = 0; i < m.getSpeed(); i++) {
				orderedTurns.add(m);
			}
		}
		ArrayList<Entity> generatedTurns = SessionController.getInstance().generateTurns();
		assertNotEquals(generatedTurns, orderedTurns);
	}
	
	@Test
	void testDisplayStats() {
		System.out.println(SessionController.getInstance().displayStats(phoenix));
	}
	
	@Test
	void testFindEntityInMemoryFromString() {
		SessionMemory.getInstance().addPlayer(player1);
		SessionMemory.getInstance().addPlayer(player2);
		SessionMemory.getInstance().addMonster(phoenix);
		SessionMemory.getInstance().addMonster(dragon1);
		SessionMemory.getInstance().addMonster(dragon2);
		
		Entity found = SessionController.getInstance().findEntity("Phoenix");
		assertEquals(found.getName(),"Phoenix");
		assertEquals(found.getClass(), Monster.class);
	}
	
	@Test
	void testMoveLegalityLegalMove() {
		boolean isLegal = SessionController.getInstance().checkActionLegality(player1, "Attack", dragon1);
		assertEquals(isLegal, true);
	}
	
	@Test
	void testMoveLegalityIllegalMove() {
		boolean isLegal = SessionController.getInstance().checkActionLegality(player1, "Attack", player2);
		assertEquals(isLegal, false);
	}
	
	@Test
	void testMoveSuccessFailureProbabilty() {
		// to see distribution of true/false values
		for (int i = 0; i < 10; i++) {
			System.out.println(SessionController.getInstance().getChance(80));
		}
	}


}
