package org.netbeans.modules.php.zikula.ui;

import java.awt.Image;
import org.netbeans.api.project.Project;
import org.netbeans.modules.php.api.phpmodule.PhpModule;
import org.netbeans.spi.project.ui.support.NodeFactory;
import org.netbeans.spi.project.ui.support.NodeFactorySupport;
import org.netbeans.spi.project.ui.support.NodeList;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.nodes.FilterNode;
import org.openide.util.Exceptions;
import org.openide.util.ImageUtilities;
import org.netbeans.modules.php.zikula.ZikulaPhpFrameworkProvider;
import org.openide.util.NbBundle;

/**
 * Controller Node Factory.
 */
public class TemplateNodeFactory implements NodeFactory {

    @Override
    public NodeList<?> createNodes(Project p) {
        FileObject moduleDir = ZikulaPhpFrameworkProvider.getModuleRoot(p.getLookup().lookup(PhpModule.class));

        if(moduleDir != null) {
            FileObject templateDir = moduleDir.getFileObject("templates");

            if (templateDir != null) {
                try {
                    return NodeFactorySupport.fixedNodeList(new TemplateNode(templateDir));
                } catch (DataObjectNotFoundException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
        }

        return NodeFactorySupport.fixedNodeList();
    }

    private static class TemplateNode extends FilterNode {

        private static Image smallImage = ImageUtilities.loadImage("org/netbeans/modules/php/zikula/ui/resources/template.png");

        public TemplateNode(FileObject template) throws DataObjectNotFoundException {
            super(DataObject.find(template).getNodeDelegate());
        }

        @Override
        public String getDisplayName() {
            return NbBundle.getMessage(TemplateNodeFactory.class, "TemplateNode.DisplayName");
        }

        public Image getIcon(int type) {
            return smallImage;
        }

        public Image getOpenedIcon(int type) {
            return smallImage;
        }
    }
}
