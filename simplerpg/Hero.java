package simplerpg;

public class Hero extends GameCharacter { // Класс "герой" наследуется от класса "игровой персонаж"
    
    private int currentExp;
    private int expToNextLevel;

	private int gold;
    
    public Hero(String _charClass, String _name, int _hp, int _attack, int _defense)
    {        
        super(_charClass, _name, _hp, _attack, _defense);
        currentExp = 0;
        expToNextLevel = 1000;
		gold = 0;
    }
    
    public void expGain(int _exp) // Метод получение опыта
    {
        currentExp += _exp;
        System.out.println(name + " получил " + _exp + " ед. опыта");
        if(currentExp > expToNextLevel) // Если нарали необходимый уровень опыта, повышаем уровень
        {            
            currentExp -= expToNextLevel;
            expToNextLevel *= 2;
            level++;
            attack += 5;
            System.out.println("Атака героя повысилась до " + attack + " ед. урона");
            hpMax += 50;
            System.out.println("Запас здоровья героя повысился до " + hpMax);
            hp = hpMax;
            System.out.println(name + " повысил уровень до " + level);
        }        
    }
    
	public int getGold()
	{
		return gold;
	}

	public void goldGain(int _gold) // Метод получения золота
	{
		gold = getGold() + _gold;
	}
	
	@Override
	public void ShowInfo() // Вывод инфо по персонажу
    {
        System.out.println("Имя: " + name + " Здоровье: " + hp + "/" + hpMax + " Опыт: " + currentExp + " Золото: " + gold);
    }
}
