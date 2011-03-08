package org.netbeans.modules.php.zikula;

import org.netbeans.modules.csl.api.UiUtils;
import org.netbeans.modules.php.api.editor.EditorSupport;
import org.netbeans.modules.php.api.editor.PhpClass;
import org.netbeans.modules.php.spi.actions.GoToActionAction;
import org.openide.filesystems.FileObject;
import org.openide.util.Lookup;

/**
 * Zikula GoToAction Action.
 */
public class ZikulaGoToActionAction extends GoToActionAction {

    private FileObject viewFo;

    public ZikulaGoToActionAction(FileObject viewFo) {
        this.viewFo = viewFo;
    }

    @Override
    public void actionPerformedInternal() {
        FileObject actionFo = ZikulaUtil.getAction(viewFo);

        if(actionFo != null) {
            UiUtils.open(actionFo, getActionMethodOffset(actionFo));
        }
    }

    private int getActionMethodOffset(FileObject action) {
        String actionMethodName = ZikulaUtil.getFunctionFromView(viewFo.getNameExt());
        EditorSupport editorSupport = Lookup.getDefault().lookup(EditorSupport.class);
        
        for (PhpClass phpClass : editorSupport.getClasses(action)) {
            if (actionMethodName != null) {
                for (PhpClass.Method method : phpClass.getMethods()) {
                    if (actionMethodName.equals(method.getName())) {
                        return method.getOffset();
                    }
                }
            }
            return phpClass.getOffset();
        }
        return DEFAULT_OFFSET;
    }
}
