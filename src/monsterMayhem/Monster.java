package monsterMayhem;

import java.util.ArrayList;
import java.util.Arrays;

public class Monster extends Entity {
	/**
	 * Monster constructor
	 * @param name: monsters's name
	 * @param speed: monster's speed
	 * @param power: monster's power
	 * @param life: monster's life
	 */
	public Monster(String name, int speed, int power, int life, Element element) {
		super(name, speed, power, life, element);
	}

	// Preconstructed Monsters
	static Monster monster_dragon = new Monster("Dragon", 2, 50, 120, Element.getFire());
	static Monster monster_cerberus = new Monster("Cerber-Ash", 5, 35, 100, Element.getFire()); // monster-name pun as requested
	static Monster monster_kraken = new Monster("Kraken", 3, 40, 120, Element.getWater());
	static Monster monster_golem = new Monster("Snow Golem", 2, 40, 60, Element.getIce());
	static Monster monster_kelpie = new Monster("Kelpie", 7, 10, 80, Element.getWater());
	static Monster monster_phoenix = new Monster("Phoenix", 3, 45, 90, Element.getFire());
	static Monster monster_wendigo = new Monster("Wendigo", 4, 30, 110, Element.getIce());
	
	private static ArrayList<Monster> monsters = new ArrayList<Monster>(
			Arrays.asList(monster_dragon, monster_cerberus, monster_kraken, monster_golem,
					monster_kelpie, monster_phoenix, monster_wendigo));
	
	public static ArrayList<Monster> getMonsters() {
		return monsters;
	}
}
