package card;

public class Card {
	private int damage = 0;
	private int hp = 0;
	private String name = "";

	public Card(String name, int hp, int damage) {
		this.name = name;
		this.hp = hp;
		this.damage = damage;
	}

	public String getName() {
		return name;
	}

	public int getHp() {
		return hp;
	}

	public int getDamage() {
		return damage;
	}
}
