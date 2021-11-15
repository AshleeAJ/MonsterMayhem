package monsterMayhem;

public abstract class Entity {
	private String name;
	private int speed;
	private int power;
	private int life; // total allowed
	private int health; // current
	private Element element; // element applies to both monster type and player weapon attirbute
	
	/**
	 * Abstract Entity constructor,
	 * can be used in construction of player or monster entites
	 * @param name: name of entity
	 * @param speed: speed value
	 * @param power: power value
	 * @param life: life amount; also applied to health.
	 * Note: health refers to 'current' health of entity,
	 * life refers to total amount of health allowed
	 */
	public Entity(String name, int speed, int power, int life, Element element) {
		this.name = name;
		this.speed = speed;
		this.power = power;
		this.life = life;
		this.health = life;
		this.element = element;
	}

	public String getName() {
		return name;
	}

	public int getSpeed() {
		return speed;
	}
	
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getPower() {
		return power;
	}
	
	public void setPower(int power) {
		this.power = power;
	}

	public int getLife() {
		return life;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}
	
	public Element getElement() {
		return element;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
	
	/**
	 * Entity attacks another entity
	 * @param attackee: entity being attacked
	 */
	public void attack(Entity attackee) {
		int attackDamage = this.getPower();
		attackee.setHealth(attackee.getHealth() - attackDamage);
		if (attackee.getHealth() < 0)
			attackee.setHealth(0);
	}
	
}
