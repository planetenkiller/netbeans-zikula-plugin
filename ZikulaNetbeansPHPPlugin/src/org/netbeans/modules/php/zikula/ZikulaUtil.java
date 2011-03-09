package org.netbeans.modules.php.zikula;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.netbeans.modules.php.api.editor.PhpBaseElement;
import org.netbeans.modules.php.api.editor.PhpClass;
import org.openide.filesystems.FileObject;

/**
 * Zikula Util functions.
 */
public abstract class ZikulaUtil {
    public static boolean isViewWithAction(FileObject fo) {
        return isView(fo) && getAction(fo) != null;
    }

    public static boolean isView(FileObject fo) {
        FileObject parent = fo.getParent();

        while(parent != null && !parent.getName().equals("templates")) {
            FileObject newParent = parent.getParent();
            if(parent.getName().equals("modules") && newParent != null && newParent.getName().equals("src")) {
                break;
            }
            
            parent = newParent;
        }

        return parent != null && parent.getName().equals("templates");
    }

    public static boolean isAction(FileObject fo) {
        FileObject parent = fo.getParent();

        return parent != null && parent.getName().equals("Controller");
    }

    public static FileObject getAction(FileObject fo) {
        FileObject parent = fo.getParent();

        while(parent != null && !parent.getName().equals("templates")) {
            FileObject newParent = parent.getParent();
            if(parent.getName().equals("modules") && newParent != null && newParent.getName().equals("src")) {
                break;
            }

            parent = newParent;
        }

        FileObject moduleDir = parent.getParent();
        FileObject libDir = moduleDir.getFileObject("lib");
        if(libDir != null) {
            libDir = libDir.getFileObject(moduleDir.getName());

            if(libDir != null) {
                FileObject controllerDir = libDir.getFileObject("Controller");

                if(controllerDir != null) {
                    String type = getTypeFromView(fo.getNameExt());
                    type = String.valueOf(type.charAt(0)).toUpperCase() + type.substring(1);

                    return controllerDir.getFileObject(type, "php");
                }
            }
        }

        return null;
    }

    public static String getTypeFromView(String templatePath) {
        Pattern p = Pattern.compile("(.*?)_(.*?)_(.*?).(html|htm|tpl)");

        Matcher m = p.matcher(templatePath);

        if(m.matches()) {
            m.find();
            return m.group(2);
        } else {
            return null;
        }
    }

    public static String getFunctionFromView(String templatePath) {
        Pattern p = Pattern.compile("(.*?)_(.*?)_(.*?).(html|htm|tpl)");

        Matcher m = p.matcher(templatePath);

        if(m.matches()) {
            m.find();
            return m.group(3);
        } else {
            return null;
        }
    }

    public static FileObject getView(FileObject actionFo, PhpBaseElement phpElement) {
        FileObject view = null;

        if (phpElement instanceof PhpClass.Method) {
            String methodName = phpElement.getName();

            view = getView(actionFo, methodName.substring(0, 1).toLowerCase() + methodName.substring(1));
        }

        if (view == null) {
            view = getView(actionFo, "main");
        }
        
        return view;
    }

    private static FileObject getView(FileObject actionFo, String viewName) {
        FileObject moduleDir = actionFo.getParent().getParent().getParent().getParent();

        FileObject templateDir = moduleDir.getFileObject("templates");

        if(templateDir != null) {
            String filename = moduleDir.getName().toLowerCase() + "_"
                              + actionFo.getName().substring(0, 1).toLowerCase()
                              + actionFo.getName().substring(1) + "_" + viewName;

            if(templateDir.getFileObject(filename, "tpl") != null) {
                return templateDir.getFileObject(filename, "tpl");
            } else if(templateDir.getFileObject(filename, "html") != null) {
                return templateDir.getFileObject(filename, "html");
            } else if(templateDir.getFileObject(filename, "htm") != null) {
                return templateDir.getFileObject(filename, "htm");
            }
        }

        return null;
    }
}
