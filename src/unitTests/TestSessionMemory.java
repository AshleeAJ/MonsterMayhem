package unitTests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import monsterMayhem.Element;
import monsterMayhem.Entity;
import monsterMayhem.Monster;
import monsterMayhem.Player;
import monsterMayhemController.SessionController;
import monsterMayhemController.SessionMemory;

class TestSessionMemory {
	
	// construct some monsters for testing
		Monster phoenix = SessionController.getInstance().findMonster("Phoenix");
		Monster dragon1 = SessionController.getInstance().findMonster("Dragon");
		Monster dragon2 = SessionController.getInstance().findMonster("Dragon");
		
	// construct some players for testing
		Player player1 = new Player("Knight Eric", 3, 40, 100, Element.getIce());
		Player player2 = new Player("Princess Yasmin", 1, 30, 80, Element.getWater());

	@Test
	void testRemoveAllTurnOccurances() {
		SessionMemory.getInstance().addPlayer(player1);
		SessionMemory.getInstance().addPlayer(player2);
		SessionMemory.getInstance().addMonster(phoenix);
		SessionMemory.getInstance().addMonster(dragon1);
		SessionMemory.getInstance().addMonster(dragon2);
		ArrayList<Entity> originalList = SessionController.getInstance().generateTurns();
		SessionMemory.getInstance().addGeneratedTurns(originalList);
		SessionMemory.getInstance().removeSpecificTurn(phoenix);
		ArrayList<Entity> newList = SessionMemory.getInstance().getTurns();
		assertNotEquals(originalList, newList);
	}
	
	@Test
	void testResetMemory() {
		SessionMemory.getInstance().addPlayer(player1);
		SessionMemory.getInstance().addPlayer(player2);
		SessionMemory.getInstance().addMonster(phoenix);
		SessionMemory.getInstance().addMonster(dragon1);
		SessionMemory.getInstance().addMonster(dragon2);
		SessionMemory.getInstance().addGeneratedTurns(SessionController.getInstance().generateTurns());
		SessionMemory.getInstance().resetMemory();
		assertEquals(SessionMemory.getInstance().getPlayers(), new ArrayList<Player>());
		assertEquals(SessionMemory.getInstance().getMonsters(), new ArrayList<Monster>());
		assertEquals(SessionMemory.getInstance().getTurns(), new ArrayList<Entity>());
	}

}
