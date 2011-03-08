package org.netbeans.modules.php.zikula.ui.wizard;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import javax.swing.event.ChangeListener;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.SourceGroup;
import org.netbeans.api.project.Sources;
import org.netbeans.modules.php.api.phpmodule.PhpModule;
import org.netbeans.spi.project.ui.templates.support.Templates;
import org.openide.WizardDescriptor;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataFolder;
import org.openide.loaders.DataObject;
import org.openide.loaders.TemplateWizard;
import org.netbeans.modules.php.zikula.ZikulaPhpFrameworkProvider;

public final class NewZikulaControllerWizardIterator implements WizardDescriptor.InstantiatingIterator {

    private int index;
    private WizardDescriptor wizard;
    private WizardDescriptor.Panel[] panels;

    /**
     * Initialize panels representing individual wizard's steps and sets
     * various properties for them influencing wizard appearance.
     */
    private WizardDescriptor.Panel[] getPanels() {
        Project project = Templates.getProject(wizard);
        Sources sources = (Sources)project.getLookup().lookup(Sources.class);
        SourceGroup[] groups = sources.getSourceGroups(Sources.TYPE_GENERIC);
        WizardDescriptor.Panel packageChooserPanel = Templates.buildSimpleTargetChooser(project, groups).create();
        if (panels == null) {
            panels = new WizardDescriptor.Panel[] {
                packageChooserPanel,
            };
        }

        return panels;
    }

    public Set instantiate() throws IOException {
        String className = Templates.getTargetName(wizard);
        FileObject pkg = Templates.getTargetFolder(wizard);
        DataFolder targetFolder = DataFolder.findFolder(pkg);

        TemplateWizard template = (TemplateWizard) wizard;
        DataObject doTemplate = template.getTemplate();

        Map<String, Object> params = new HashMap<String, Object>();

        FileObject moduleDir = ZikulaPhpFrameworkProvider.getModuleRoot(Templates.getProject(wizard).getLookup().lookup(PhpModule.class));
        if(moduleDir != null) {
            String type = className.substring(0, 1).toUpperCase() + className.substring(1);
            if(type.substring(type.length() - 4).equals(".php")) {
                type = type.substring(0, type.length() - 4);
            }
            String module = moduleDir.getName();
            params.put("type", type);
            params.put("module", module);

            FileObject createdFile = doTemplate.createFromTemplate(targetFolder, className, params).getPrimaryFile();
//            FileObject createdFile = doTemplate.getPrimaryFile();
            return Collections.singleton(createdFile);
        }
        
        return Collections.EMPTY_SET;
    }

    public void initialize(WizardDescriptor wizard) {
        this.wizard = wizard;
    }

    public void uninitialize(WizardDescriptor wizard) {
        panels = null;
    }

    public WizardDescriptor.Panel current() {
        return getPanels()[index];
    }

    public String name() {
        return index + 1 + ". from " + getPanels().length;
    }

    public boolean hasNext() {
        return index < getPanels().length - 1;
    }

    public boolean hasPrevious() {
        return index > 0;
    }

    public void nextPanel() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        index++;
    }

    public void previousPanel() {
        if (!hasPrevious()) {
            throw new NoSuchElementException();
        }
        index--;
    }

    // If nothing unusual changes in the middle of the wizard, simply:
    public void addChangeListener(ChangeListener l) {
    }

    public void removeChangeListener(ChangeListener l) {
    }

    // If something changes dynamically (besides moving between panels), e.g.
    // the number of panels changes in response to user input, then uncomment
    // the following and call when needed: fireChangeEvent();
    /*
    private Set<ChangeListener> listeners = new HashSet<ChangeListener>(1); // or can use ChangeSupport in NB 6.0
    public final void addChangeListener(ChangeListener l) {
    synchronized (listeners) {
    listeners.add(l);
    }
    }
    public final void removeChangeListener(ChangeListener l) {
    synchronized (listeners) {
    listeners.remove(l);
    }
    }
    protected final void fireChangeEvent() {
    Iterator<ChangeListener> it;
    synchronized (listeners) {
    it = new HashSet<ChangeListener>(listeners).iterator();
    }
    ChangeEvent ev = new ChangeEvent(this);
    while (it.hasNext()) {
    it.next().stateChanged(ev);
    }
    }
     */
    // You could safely ignore this method. Is is here to keep steps which were
    // there before this wizard was instantiated. It should be better handled
    // by NetBeans Wizard API itself rather than needed to be implemented by a
    // client code.
    private String[] createSteps() {
        String[] beforeSteps = null;
        Object prop = wizard.getProperty("WizardPanel_contentData");
        if (prop != null && prop instanceof String[]) {
            beforeSteps = (String[]) prop;
        }

        if (beforeSteps == null) {
            beforeSteps = new String[0];
        }

        String[] res = new String[(beforeSteps.length - 1) + panels.length];
        for (int i = 0; i < res.length; i++) {
            if (i < (beforeSteps.length - 1)) {
                res[i] = beforeSteps[i];
            } else {
                res[i] = panels[i - beforeSteps.length + 1].getComponent().getName();
            }
        }
        return res;
    }
}
