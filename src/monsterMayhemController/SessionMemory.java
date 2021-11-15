package monsterMayhemController;

import java.util.ArrayList;

import monsterMayhem.Entity;
import monsterMayhem.Monster;
import monsterMayhem.Player;

public class SessionMemory {

	private static SessionMemory instance;
	private static ArrayList<Player> players;
	private static ArrayList<Monster> monsters;
	private static ArrayList<Entity> turnOrder;
	
	private SessionMemory() {
		players = new ArrayList<Player>();
		monsters = new ArrayList<Monster>();
		turnOrder = new ArrayList<Entity>();
	}
	
	public static synchronized SessionMemory getInstance() {
		if (instance == null) {
			instance = new SessionMemory();
		}
		return instance;
	}
	
	public void addPlayer(Player player) {
		players.add(player);
	}
	
	public void removePlayer(Player player) {
		players.remove(player);
	}
	
	public ArrayList<Player> getPlayers() {
		return SessionMemory.players;
	}
	
	public void addMonster(Monster monster) {
		monsters.add(monster);
	}
	
	public void removeMonster(Monster monster) {
		monsters.remove(monster);
	}
	
	public ArrayList<Monster> getMonsters() {
		return SessionMemory.monsters;
	}
	
	public ArrayList<Entity> getTurns() {
		return SessionMemory.turnOrder;
	}
	
	public void addGeneratedTurns(ArrayList<Entity> turns) {
		for (Entity t : turns) {
			turnOrder.add(t);
		}
	}
	
	public void removeTurn() {
		turnOrder.remove(0);
	}
	
	public void removeEntity(Entity entity) {
		turnOrder.remove(entity);
	}
	
	public void removeSpecificTurn(Entity entity) {
		while (SessionMemory.getInstance().getTurns().contains(entity)) {
			SessionMemory.getInstance().removeEntity(entity);
		}
	}
	
	public Entity getCurrentTurn() {
		return SessionMemory.turnOrder.get(0);
	}
	
	public void resetMemory() {
		SessionMemory.monsters.clear();
		SessionMemory.players.clear();
		SessionMemory.turnOrder.clear();
	}

}
