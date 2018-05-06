package Model.Unit.UnitAction;

import Model.Item.GenericItem;
import Model.Unit.IUnit;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

public class SimpleAction extends Action {

    private static Action instance = new SimpleAction();

    private SimpleAction() {}

    @Override
    public ActionResult perform(@NotNull IUnit thisUnit, @Nullable IUnit targetUnit, @Nullable GenericItem item) {
        return ActionResult.SUCCESS;
    }

    @NotNull
    public static Action getAction() {
        return instance;
    }
}