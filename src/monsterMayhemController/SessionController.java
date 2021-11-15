package monsterMayhemController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import monsterMayhem.Element;
import monsterMayhem.Entity;
import monsterMayhem.Monster;
import monsterMayhem.Player;
import monsterMayhemView.SetupScreen;

public class SessionController {
	
	private static SessionController instance;
	
	public static synchronized SessionController getInstance() {
		if (instance == null) {
			instance = new SessionController();
		}
		return instance;
	}
	
	/**
	 * @param element
	 * @return finds element object maching element string
	 */
	public Element findElement(String element) {
		if (element == "Fire")
			return Element.getFire();
		else if (element == "Water")
			return Element.getWater();
		else if (element == "Ice")
			return Element.getIce();
		else
			return null;
	}
	
	/**
	 * finds monster object matching monster string
	 * @param monster
	 * @return creates new monster object; allowing multiple of the 
	 * same monster to be added to a game session
	 */
	public Monster findMonster(String monster) {
		Monster selected = null;
		for (Monster m : Monster.getMonsters()) {
			if (m.getName() == monster) {
				selected = new Monster(m.getName(), m.getSpeed(),
						m.getPower(), m.getLife(), m.getElement());
			}
		}
		return selected;
		
	}
	
	/**
	 * @param name: player's name
	 * @param speed: player's speed
	 * @param power: player's power
	 * @param life: player's life
	 * @param element: player's weapon attribute
	 * @return creates and returns player object
	 */
	public Player createPlayer() {
		Player player = new Player(SetupScreen.getInputName(),
				SetupScreen.getInputSpeed(), SetupScreen.getInputPower(), SetupScreen.getInputLife(),
				findElement(SetupScreen.getInputWeapon()));
		return player;
		
	}
	
	/**
	 * Determines if an entity is a Player or Monster and adds it to
	 * the appropriate list display in the view
	 * @param entity: entity to be added to list display
	 */
	public static void updateListModelAdd(Entity entity) {
		if (entity.getClass() == Player.class) {
			SetupScreen.getPartyMembersModel().addElement((Player) entity);
		}
		else if (entity.getClass() == Monster.class) {
			SetupScreen.getMonsterGroupModel().addElement((Monster) entity);
		}
		else
			return;
	}
	
	/**
	 * Determines if an entity is a Player or Monster and removes it
	 * from the appropriate list display in the view
	 * @param entity: entity to be removed from list display
	 */
	public static void updateListModelRemove(Entity entity) {
		if (entity.getClass() == Player.class) {
			SetupScreen.getPartyMembersModel().removeElement((Player) entity);
		}
		else if (entity.getClass() == Monster.class) {
			SetupScreen.getMonsterGroupModel().removeElement((Monster) entity);
		}
		else
			return;
	}
	
	/**
	 * Generates an arraylist of turns
	 * adds entites to arraylist based on speed values and shuffles order
	 * @return random arraylist of entities in memory
	 */
	public ArrayList<Entity> generateTurns() {
		ArrayList<Entity> turns = new ArrayList<Entity>();
		ArrayList<Player> players = SessionMemory.getInstance().getPlayers();
		for (Player p : players) {
			for (int i = 0; i < p.getSpeed(); i++) {
				turns.add(p);
			}
		}
		ArrayList<Monster> monsters = SessionMemory.getInstance().getMonsters();
		for (Monster m : monsters) {
			for (int i = 0; i < m.getSpeed(); i++) {
				turns.add(m);
			}
		}
		Collections.shuffle(turns);
		return turns;
	}
	
	/**
	 * Formats stats of entity for display on battle screen
	 * @param entity: entity stats to display
	 * @return Formatted string of entity stats
	 */
	public String displayStats(Entity entity) {
		String name = String.format("Name: %s", entity.getName());
		String speed = String.format("Speed: %s", entity.getSpeed());
		String power = String.format("Power: %s", entity.getPower());
		String currentHealth = entity.getHealth() + "/" + entity.getLife();
		String healthDisplay = String.format("Health: %s", currentHealth);
		String element = "";
		if (entity.getClass() == Player.class) {
			element = String.format("Weapon attribute: %s", entity.getElement().getElementName());
		}
		else if (entity.getClass() == Monster.class) {
			element = String.format("Monster type: %s", entity.getElement().getElementName());
		}
		String stats = name + "\n" + speed + "\n" + power + "\n" + healthDisplay + "\n" + element;
		return stats;
	}
	
	/**
	 * @param name: name of entity
	 * @return entity in memory associated with the name
	 */
	public Entity findEntity(String name) {
		Entity entity = null;
		ArrayList<Entity> entities = new ArrayList<Entity>();
		entities.addAll(SessionMemory.getInstance().getMonsters());
		entities.addAll(SessionMemory.getInstance().getPlayers());
		for (Entity e : entities) {
			if (name == e.getName()) {
				entity = e;
			}
		}
		return entity;
	}
	
	/**
	 * Check if action is legal
	 * @param origin: entity performing action
	 * @param action: action to be perfomed 
	 * @param target: target of action
	 * @return boolean value if move is legal
	 */
	public boolean checkActionLegality(Entity origin, String action, Entity target) {
		if (origin.getClass() == Player.class) {
			if (action == "Attack" && target.getClass() == Monster.class) {
				return true;
			}
			else if (action == "Heal" && target.getClass() == Player.class) {
				return true;
			}
			else if (action == "Power-Up" && target.getClass() == Player.class) {
				return true;
			}
			else
				return false;
		}
		else if (origin.getClass() == Monster.class) {
			if (action == "Attack" && target.getClass() == Player.class) {
				return true;
			}
			else
				return false;
		}
		else
			return false;
	}
	
	/**
	 * @param percentChance: percentage chance move has of being successful
	 * @return boolean value if move will be successful
	 */
	public boolean getChance(int percentChance) {
		Random r = new Random();
		int randInt = r.nextInt(100) + 1;
		if (randInt <= percentChance) {
			return true;
		} 
		else
			return false;
	}
	
	/**
	 * Origin attacks target if get chance value is within the percent chance range
	 * @param origin
	 * @param target
	 * @return true if the attack was successful
	 */
	public boolean Attack(Entity origin, Entity target) {
		if (origin.getClass() == Player.class) {
			if (getChance(80)) {
				((Player) origin).attack(target);
				return true;
			}
			else
				return false;
		}
		else if (origin.getClass() == Monster.class) {
			if (getChance(75)) {
				((Monster) origin).attack(target);
				return true;
			}
			else
				return false;
		}
		else
			return false;
	}
	
	/**
	 * Origin heals target if get chance value is within the percent chance range
	 * @param origin
	 * @param target
	 * @return true if heal was successful
	 */
	public boolean Heal(Entity origin, Entity target) {
		if (getChance(50)) {
			((Player) origin).heal((Player) target);
			return true;
		}
		else
			return false;
	}
	
	/**
	 * origin powers up self if get chance value is within the percent chance range
	 * @param origin
	 * @return true if power up was successful
	 */
	public boolean PowerUp(Entity origin) {
		if (getChance(75)) {
			if (((Player) origin).powerUp((Player) origin)) {
				return true;
			}
			else 
				return false;
		}
		else 
			return false;
	}

}
