package org.netbeans.modules.php.zikula;

import java.util.Set;
import javax.swing.JComponent;
import javax.swing.event.ChangeListener;
import org.netbeans.modules.php.api.phpmodule.PhpModule;
import org.netbeans.modules.php.spi.phpmodule.PhpModuleExtender;
import org.openide.filesystems.FileObject;
import org.openide.util.HelpCtx;

/**
 * Zikula php module extender.
 */
public class ZikulaPhpModuleExtender extends PhpModuleExtender {

    @Override
    public void addChangeListener(ChangeListener listener) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void removeChangeListener(ChangeListener listener) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public JComponent getComponent() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public HelpCtx getHelp() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isValid() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getErrorMessage() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getWarningMessage() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Set<FileObject> extend(PhpModule phpModule) throws ExtendingException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
