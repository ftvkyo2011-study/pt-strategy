package Model.Unit;

import Model.Item.GenericItem;
import Model.Item.ItemProperty.IProperty;
import Model.Player.Player;
import Model.Unit.UnitAction.IAction;

import java.util.ArrayList;
import java.util.HashSet;


/**
 * GenericUnit -- класс для всех Units.
 * Почти как IUnit interface, но с package private и private членами,
 * которые не предполагается использовать вне папки.
 * <p>
 * Конкретно -- методы для корректной реализации паттерна Builder.
 * Это значит, что некоторые поля объекта нельзя будет изменять из мест вне папки.
 */
class GenericUnit implements IUnit {

    /**
     * Игрок, владеющй Unit'ом.
     */
    Player owner;


    /**
     * Максимально возможное значение Health Points Unit'a.
     */
    int healthPointsMax;


    /**
     * Текущее значение Health Points Unit'a.
     */
    private int healthPointsCurrent;


    /**
     * Максимально возможное значение Action Points Unit'a.
     */
    int actionPointsMax;


    /**
     * Текущее значение Action Points Unit'a.
     */
    private int actionPointsCurrent;


    /**
     * Урон, наносимый Unit'ом другим Unit'ам во время атаки.
     */
    int damagePoints;


    /**
     * Возможность Unit'a избежать ответной атаки после атаки самого Unit'a.
     */
    boolean canIgnoreCounterAttack;


    /**
     * Инвентарь (набор Items) Unit'a.
     */
    private final ArrayList<GenericItem> items = new ArrayList<>();


    /**
     * Действия, доступные Unit'у.
     */
    final HashSet<IAction> availableActions = new HashSet<>();


    /**
     * Спрятанный конструктор.
     */
    GenericUnit() {
    }


    public boolean getCanIgnoreCounterAttack() {
        return canIgnoreCounterAttack;
    }


    public void restoreAllPoints() {
        healthPointsCurrent = healthPointsMax;
        actionPointsCurrent = actionPointsMax;
    }


    public void restoreActionPoints() {
        actionPointsCurrent = actionPointsMax;
    }


    public int getHealthPoints() {
        return healthPointsCurrent;
    }


    public int getActionPoints() {
        return actionPointsCurrent;
    }


    public int getDamagePoints() {
        return damagePoints;
    }


    public Player getOwner() { return owner; }


    public void changeActionPoints(int delta) {
        actionPointsCurrent = Math.max(0, Math.min(actionPointsCurrent + delta, actionPointsMax));
    }


    public void changeHealthPoints(int delta) {
        healthPointsCurrent = Math.max(0, Math.min(healthPointsCurrent + delta, healthPointsMax));
    }


    public void zeroActionPoints() {
        actionPointsCurrent = 0;
    }


    public void zeroHealthPoints() {
        actionPointsCurrent = 0;
    }


    public ArrayList<GenericItem> getItems() {
        return new ArrayList<>(items);
    }


    public void addItem(GenericItem item) {
        items.add(item);
    }


    public void removeItem(GenericItem item) {
        items.remove(item);
    }


    public void activateItem(int n, IUnit targetUnit) {
        GenericItem thisItem = items.get(n);
        IProperty property = thisItem.getProperty();
        property.onActivate(thisItem, this, targetUnit);

        items.remove(n);
    }


    //TODO: Добавить проверку по-другому (передавать action и проверять, доступно ли оно)
    public HashSet<IAction> getAvailableActions() {
        return new HashSet<>(availableActions);
    }


    /**
     * Часть паттерна Builder.
     * "Создатель и инициализатор" Units.
     * Должен быть использован в классе UnitBuilder.
     * <p>
     * Методы createUnit() и getUnit() следует вызывать пр создании Unit'a только один раз.
     */
    static abstract class GenericUnitMaker {

        /**
         * Unit.
         */
        GenericUnit unit;


        /**
         * Метод, который нужно вызвать первым в классе UnitBuilder.
         */
        abstract void createUnit();


        /**
         * Сэттер для владельца Unit'a.
         *
         * @param owner Owner of the unit.
         */
        public void setOwner(Player owner) {
            unit.owner = owner;
        }


        /**
         * Сэттер для дефолтного инвентаря для данного типа Unit'a.
         */
        abstract void setInventory();


        /**
         * Сэттер для дефолтных параметров для данного типа Unit'a.
         */
        abstract void setDefaults();


        /**
         * Сэттер для доступных данному типу Unit'a действий.
         */
        abstract void setActions();


        /**
         * Геттер для Unit'a.
         *
         * @return созданный Unit
         */
        public GenericUnit getUnit() {
            return unit;
        }
    }
}
