package simplerpg;

public class GameCharacter implements Cloneable  {

    protected String name;
    public String getName()
    {
        return name;
    }    
    protected String charClass;
    protected int hp;
    protected int hpMax;
    public int getHpMax()
    {
        return hpMax;
    }
    protected int attack;
    protected int defense;
    protected int critChance;
    protected int level;
    protected boolean blockStance;
    protected boolean life;
    public boolean isAlive()
    {
        return life;
    }
    
    public GameCharacter(String _charClass, String _name, int _hp, int _attack, int _defense)
    {
        name = _name;
        charClass = _charClass;
        hpMax = _hp;
        hp = hpMax;
        attack = _attack;
        defense = _defense;
        critChance = 10;
        level = 1;
        life = true;
        blockStance = false;
    }
    
    public void ShowInfo() // Вывод инфо по персонажу
    {
        System.out.println("Имя: " + name + " Здоровье: " + hp + "/" + hpMax);
    }
    
    public void setBlockStance() // Включение защитной стойки
    {
        blockStance = true;
        System.out.println(name + " стал в защитную стойку");
    }
    
    public Object clone() // Копирование объектов 
    {  
        try
        {
            return super.clone();
        } 
        catch (CloneNotSupportedException e)
        {
            System.out.println("Клонирование невозможно");
            return this;
        }                
    } 
    
    public void makeNewRound() // Действия на начало нового раунда
    {
        blockStance = false; // На начало раунда сбрасываем защитную стойку
    }
    
    public int makeAttack() // Метод атаки
    {
        int minAttack = (int)(attack * 0.8f);
        int deltaAttack = (int)(attack * 0.4f);
        int currentAttack = minAttack + GameClass.rand.nextInt(deltaAttack); // Делаем разброс атаки 80-120%
        if(GameClass.rand.nextInt(100) < critChance) // Проверяем условие на срабатывание критического удара
        {            
            currentAttack *= 2; // Если крит сработал, умножаем атаку на 2
            System.out.println(name + " нанес критический урон в размере " + currentAttack + " ед. урона");
        }
        else
            System.out.println(name + " нанес " + currentAttack + " ед. урона");
        return currentAttack; // возвращаем полученное значение атаки
    }
    
    public void getDamage(int _inputDamage) // Метод получения урона
    {        
        _inputDamage -= defense; // из входящего урона вычитается значение защиты
        if (blockStance) // если включена защитная стойка - снижаем входящий урон еще раз
        {
            System.out.println(name + " дополнительно заблокировал " + defense + " ед. урона в защитной стойке");
            _inputDamage -= defense;
        }
        if (_inputDamage < 0) _inputDamage = 0; // делаем прверку на отрицательный урон, для предотвращения эффекта лечения
        System.out.println(name + " получил " + _inputDamage + " ед. урона");
        hp -= _inputDamage; // снижаем уровень здоровья
        if(hp < 1) // если здоровье опускается ниже 0
            life = false; // переключаем life = false
    }
}
