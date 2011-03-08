package org.netbeans.modules.php.zikula;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.netbeans.modules.php.api.phpmodule.PhpModule;
import org.netbeans.modules.php.api.phpmodule.PhpModuleProperties;
import org.netbeans.modules.php.spi.commands.FrameworkCommandSupport;
import org.netbeans.modules.php.spi.editor.EditorExtender;
import org.netbeans.modules.php.spi.phpmodule.PhpFrameworkProvider;
import org.netbeans.modules.php.spi.phpmodule.PhpModuleActionsExtender;
import org.netbeans.modules.php.spi.phpmodule.PhpModuleExtender;
import org.netbeans.modules.php.spi.phpmodule.PhpModuleIgnoredFilesExtender;
import org.openide.filesystems.FileObject;
import org.openide.util.NbBundle;

/**
 * Zikula Frame work provider.
 */
public class ZikulaPhpFrameworkProvider extends PhpFrameworkProvider {
    private static final ZikulaPhpFrameworkProvider INSTANCE = new ZikulaPhpFrameworkProvider();


    public static final String MODULES_ROOT = "src/modules";
    public static final String INSTALLER = "Installer.php";
    public static final String VERSION = "Version.php";

    public static ZikulaPhpFrameworkProvider getInstance() {
        return INSTANCE;
    }

    private ZikulaPhpFrameworkProvider() {
        super(NbBundle.getMessage(ZikulaPhpFrameworkProvider.class, "LBL_FrameworkName"), 
              NbBundle.getMessage(ZikulaPhpFrameworkProvider.class, "LBL_FrameworkDescription"));
    }

    public static FileObject getModuleRoot(PhpModule pm) {
        FileObject sourceDir = pm.getSourceDirectory();

        FileObject modulesDir = sourceDir.getFileObject(MODULES_ROOT);

        if(modulesDir != null) {
            FileObject[] childs = modulesDir.getChildren();

            if(childs.length == 1) {
                return childs[0];
            }
        }

        return null;
    }

    public static FileObject getLibRoot(PhpModule pm) {
        FileObject moduleDir = getModuleRoot(pm);

        if(moduleDir == null) {
            return null;
        }

        FileObject libRoot = moduleDir.getFileObject("lib");
        if(libRoot != null) {
            libRoot = libRoot.getFileObject(moduleDir.getName());
            if(libRoot != null) {
                return libRoot;
            }
        }

        return null;
    }

    @Override
    public boolean isInPhpModule(PhpModule pm) {
        FileObject moduleDir = getModuleRoot(pm);

        if(moduleDir == null) {
            return false;
        }

        FileObject installer = moduleDir.getFileObject("lib");
        if(installer != null) {
            installer = installer.getFileObject(moduleDir.getName());
            if(installer != null) {
                installer = installer.getFileObject(INSTALLER);
            }
        }

        FileObject version = moduleDir.getFileObject("lib");
        if(version != null) {
            version = version.getFileObject(moduleDir.getName());
            if(version != null) {
                version = version.getFileObject(VERSION);
            }
        }

        return installer != null && version != null;
    }

    @Override
    public File[] getConfigurationFiles(PhpModule pm) {
        return new File[0];
    }

    @Override
    public PhpModuleExtender createPhpModuleExtender(PhpModule pm) {
       //return new ZikulaPhpModuleExtender();
        return null;
    }

    @Override
    public PhpModuleProperties getPhpModuleProperties(PhpModule pm) {
        PhpModuleProperties properties = new PhpModuleProperties();

        return properties;
    }

    @Override
    public PhpModuleActionsExtender getActionsExtender(PhpModule pm) {
        return new ZikulaPhpModuleActionsExtender();
    }

    @Override
    public PhpModuleIgnoredFilesExtender getIgnoredFilesExtender(PhpModule pm) {
        return null;
    }

    @Override
    public FrameworkCommandSupport getFrameworkCommandSupport(PhpModule pm) {
        return null;
    }

    @Override
    public EditorExtender getEditorExtender(PhpModule pm) {
        return null;
    }
}
