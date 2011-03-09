package org.netbeans.modules.php.zikula;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.swing.Action;
import org.netbeans.modules.php.spi.actions.GoToActionAction;
import org.netbeans.modules.php.spi.actions.GoToViewAction;
import org.netbeans.modules.php.spi.phpmodule.PhpModuleActionsExtender;
import org.openide.filesystems.FileObject;

/**
 * Zikula actions.
 */
public class ZikulaPhpModuleActionsExtender extends PhpModuleActionsExtender {

    @Override
    public String getMenuName() {
        return "Zikula";
    }

    @Override
    public List<? extends Action> getActions() {
        return new ArrayList<Action>();
    }

    @Override
    public boolean isActionWithView(FileObject fo) {
        Logger.getLogger(getClass().getName()).severe("isActionWithView:"+ZikulaUtil.isAction(fo));
        return ZikulaUtil.isAction(fo);
    }

    @Override
    public boolean isViewWithAction(FileObject fo) {
        Logger.getLogger(getClass().getName()).severe("isViewWithAction:"+ZikulaUtil.isViewWithAction(fo));
        return ZikulaUtil.isViewWithAction(fo);
    }

    @Override
    public GoToActionAction getGoToActionAction(FileObject viewFo, int offset) {
        return new ZikulaGoToActionAction(viewFo);
    }

    @Override
    public GoToViewAction getGoToViewAction(FileObject actionFo, int offset) {
        return new ZikulaGoToViewAction(actionFo, offset);
    }
}
