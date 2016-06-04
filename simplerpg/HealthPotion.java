package simplerpg;

public class HealthPotion extends Item{
	public HealthPotion(){
		name = "Health Potion";
	}
	
	public void itemAction(GameCharacter gc){
		gc.hp += 100;
	}
}