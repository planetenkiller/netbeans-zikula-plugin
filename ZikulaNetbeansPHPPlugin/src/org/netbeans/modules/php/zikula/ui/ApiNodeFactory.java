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
public class ApiNodeFactory implements NodeFactory {

    @Override
    public NodeList<?> createNodes(Project p) {
        FileObject libDir = ZikulaPhpFrameworkProvider.getLibRoot(p.getLookup().lookup(PhpModule.class));

        if(libDir != null) {
            FileObject apiDir = libDir.getFileObject("Api");

            if (apiDir != null) {
                try {
                    return NodeFactorySupport.fixedNodeList(new ApiNode(apiDir));
                } catch (DataObjectNotFoundException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
        }

        return NodeFactorySupport.fixedNodeList();
    }

    private static class ApiNode extends FilterNode {

        private static Image smallImage = ImageUtilities.loadImage("org/netbeans/modules/php/zikula/ui/resources/api.png");

        public ApiNode(FileObject api) throws DataObjectNotFoundException {
            super(DataObject.find(api).getNodeDelegate());
        }

        @Override
        public String getDisplayName() {
            return NbBundle.getMessage(ApiNodeFactory.class, "ApiNode.DisplayName");
        }

        public Image getIcon(int type) {
//            DataFolder root = DataFolder.findFolder(Repository.getDefault().getDefaultFileSystem().getRoot());
//            Image original = root.getNodeDelegate().getIcon(type);
//            return ImageUtilities.mergeImages(original, smallImage, 7, 7);
            return smallImage;
        }

        public Image getOpenedIcon(int type) {
//            DataFolder root = DataFolder.findFolder(Repository.getDefault().getDefaultFileSystem().getRoot());
//            Image original = root.getNodeDelegate().getIcon(type);
//            return ImageUtilities.mergeImages(original, smallImage, 7, 7);
            return smallImage;
        }
    }
}
