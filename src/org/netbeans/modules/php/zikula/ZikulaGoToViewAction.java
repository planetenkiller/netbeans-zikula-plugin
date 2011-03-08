package org.netbeans.modules.php.zikula;

import org.netbeans.modules.csl.api.UiUtils;
import org.netbeans.modules.php.api.editor.EditorSupport;
import org.netbeans.modules.php.api.editor.PhpBaseElement;
import org.netbeans.modules.php.spi.actions.GoToViewAction;
import org.openide.filesystems.FileObject;
import org.openide.util.Lookup;

/**
 * Zikula GoToView Action.
 */
public class ZikulaGoToViewAction extends GoToViewAction {

    private FileObject actionFo;
    private int offset;

    public ZikulaGoToViewAction(FileObject actionFo, int offset) {
        this.actionFo = actionFo;
        this.offset = offset;
    }

    @Override
    public void actionPerformedInternal() {
        EditorSupport editorSupport = Lookup.getDefault().lookup(EditorSupport.class);
        PhpBaseElement phpElement = editorSupport.getElement(actionFo, offset);
        if (phpElement == null) {
            return;
        }

        FileObject view = ZikulaUtil.getView(actionFo, phpElement);
        if (view != null) {
            UiUtils.open(view, DEFAULT_OFFSET);
        }
    }
}
