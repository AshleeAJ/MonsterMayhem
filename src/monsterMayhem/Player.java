package monsterMayhem;

public class Player extends Entity {
	// init speed and power allow for restoring the original
	// speed and power values to the player at the end of combat
	int initSpeed; 
	int initPower;
	/**
	 * Player constructor
	 * @param name: player's name
	 * @param speed: player's speed
	 * @param power: player's power
	 * @param life: player's life
	 * @param element: player's weapon attribute
	 */
	public Player(String name, int speed, int power, int life, Element element) {
		super(name, speed, power, life, element);
		@SuppressWarnings("unused")
		int initSpeed = speed;
		@SuppressWarnings("unused")
		int initPower = power;
	}
	
	@Override
	/**
	 * Attacks do double damage if the player
	 * has a buffed weapon the monster attribute is weak to
	 */
	public void attack(Entity attackee) {
		int attackDamage = this.getPower();
		boolean doubleDamage = Element.evaluateBuff(this.getElement(), attackee.getElement());
		if (doubleDamage)
			attackDamage = attackDamage*2;
		attackee.setHealth(attackee.getHealth() - attackDamage);
		if (attackee.getHealth() < 0)
			attackee.setHealth(0);
	}
	
	/**
	 * Heal another player (including self)
	 * @param player: player to be healed
	 * @return boolean: true if heal was successful
	 * Player health cannot be greater than life
	 */
	public void heal(Player player) {
		player.setHealth(player.getHealth() + this.getPower());
		if (player.getHealth() > player.getLife()) {
			player.setHealth(player.getLife());
		}
		
	}
	
	/**
	 * Power up
	 * @param player: player using power up action
	 * @return boolean: true if power up was successful
	 * Powering up halves speed and doubles power
	 * Only executable if speed is greater than 1
	 */
	public boolean powerUp(Player player) {
		if (player.getSpeed() > 1) {
			player.setSpeed((int)Math.ceil(player.getSpeed()/2.0));
			player.setPower((int)(getPower()*2.0));
			return true;
		} 
		else
			return false;
	}
}
