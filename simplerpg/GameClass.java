package simplerpg;

import java.util.Random;
import java.util.Scanner;

public class GameClass {
    
    public static Random rand = new Random(); // Генератор случайных чисел
    
    private Hero[] heroPattern = new Hero[3];
    private Monster[] monsterPattern = new Monster[3];
    
    private Hero mainHero;
    private Monster currentMonster;
    private int currentRound;
    
    public GameClass()
    {
        initGame();
    }
    
    public void mainGameLoop() // Метод, отвечающий за игровую логику
    {
        // Внимание! В коде данного метода не прописаны проверки на правильность введенных данных
        Scanner sc = new Scanner(System.in); // Создаем объект для считвание ввода с консоли
        int inpInt = 0;
        System.out.println("Игра началась!");        
        System.out.println("Выберите героя:");
        for (int i = 0; i < 3; i++)
            System.out.println((i + 1) + ". " + heroPattern[i].getName());
        inpInt = sc.nextInt(); // Считываем введенное в консоль число
        mainHero = (Hero)heroPattern[inpInt - 1].clone(); // Создаем героя путем копирования из шаблона
        System.out.println("Вы выбрали " + mainHero.getName());   
        currentMonster = (Monster)monsterPattern[0].clone();  // Создаем монстра путем копирования из шаблона                       
        do
        {            
            // Выводим информацию о текущем раунде, состоянии героя и врага
            System.out.println("Текущий раунд: " + currentRound);
            mainHero.ShowInfo();
            currentMonster.ShowInfo();
            
            //// Ход игрока ////
            System.out.println("Ход игрока: 1. Атака 2. Защита 3. Пропустить ход 9. Завершить игру"); // Просто сообщение о возможных действиях
            mainHero.makeNewRound(); // Вызываем метод сброса параметров героя на начало раунда
            inpInt = sc.nextInt(); // Считываем введенное в консоль число
            System.out.print("\n\n"); // Печатаем два символа перевода строки
            if (inpInt == 1) // Герой атакует
            {
                currentMonster.getDamage(mainHero.makeAttack()); // Вызываем метод получения урона монстром
                if (!currentMonster.isAlive()) // Делаем проверку жив ли монстр после удара героя
                {
                    System.out.println(currentMonster.getName() + " погиб"); // Печатаем сообщение о гибели монстра
                    mainHero.expGain(currentMonster.getHpMax() * 2); // Даем герою опыта в размере (Здоровье_монстра * 2)
					mainHero.goldGain(rand.nextInt(currentMonster.getHpMax() / 10)); // Получаем золото
					System.out.println("Получено золота: " + mainHero.gold);
                    currentMonster = (Monster)monsterPattern[rand.nextInt(3)].clone(); // Создаем нового монстра случайного типа, копируя из шаблона
                    System.out.println("На поле боя выходит " + currentMonster.getName()); // Выводим сообщение о выходе нового врага на поле боя
                }
            }
            if (inpInt == 2) // Герой защищается
                mainHero.setBlockStance(); // Вызывем метод включения защитной стойки
            if (inpInt == 9) break; // Выход из игры
            // Если выбран любой другой inpInt - Герой пропустит ход

            //// Ход монстра ////
            currentMonster.makeNewRound();  // Вызываем метод сброса параметров монстра на начало раунда
            mainHero.getDamage(currentMonster.makeAttack()); // По умолчанию, монстр всегда атакует
            if (!mainHero.isAlive()) // Если после удара монстра герой погибает - выходим из основного игрового цикла
                break;
            
            //// Смена раунда ////
            currentRound++;
        }
        while(true);
        // В зависимости от того, кто остался в живых - выводим итоговое сообщение о результате игры
        if(currentMonster.isAlive() && mainHero.isAlive()) System.out.println(mainHero.getName() + " сбежал с поля боя");
        if(!currentMonster.isAlive()) System.out.println("Победил " + mainHero.getName());
        if(!mainHero.isAlive()) System.out.println("Победил " + currentMonster.getName());        
        System.out.println("Игра завершена");
    }
    
    public void initGame() // Инициализируем начальное состояние игры
    {
        // Задаем шаблоны героев и монстров
        heroPattern[0] = new Hero("Knight", "Lancelot", 600, 30, 12);
        heroPattern[1] = new Hero("Barbarian", "Konan", 600, 50, 0);
        heroPattern[2] = new Hero("Dwarf", "Gimli", 600, 20, 25);        
        monsterPattern[0] = new Monster("Humanoid", "Goblin", 120, 30, 2);
        monsterPattern[1] = new Monster("Humanoid", "Orc", 240, 50, 2);
        monsterPattern[2] = new Monster("Humanoid", "Troll", 400, 25, 5);        
        currentRound = 1;
    }
    
}
