package org.github.planetenkiller.phpdocapiannotation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.Document;
import org.netbeans.modules.csl.api.DeclarationFinder;
import org.netbeans.modules.csl.api.DeclarationFinder.DeclarationLocation;
import org.netbeans.modules.csl.core.Language;
import org.netbeans.modules.csl.core.LanguageRegistry;
import org.netbeans.modules.parsing.api.ParserManager;
import org.netbeans.modules.parsing.api.Source;
import org.netbeans.modules.parsing.spi.ParseException;
import org.netbeans.modules.parsing.spi.Parser.Result;
import org.netbeans.modules.parsing.spi.ParserResultTask;
import org.netbeans.modules.parsing.spi.Scheduler;
import org.netbeans.modules.parsing.spi.SchedulerEvent;
import org.netbeans.modules.php.editor.api.elements.TypeElement;
import org.netbeans.modules.php.editor.api.elements.TypeMemberElement;
import org.netbeans.modules.php.editor.parser.PHPParseResult;
import org.netbeans.modules.php.editor.parser.astnodes.MethodInvocation;
import org.netbeans.modules.php.editor.parser.astnodes.visitors.DefaultVisitor;
import org.netbeans.spi.editor.hints.ErrorDescription;
import org.openide.util.NbPreferences;

/**
 */
public class ApiWarningHighlitingTask extends ParserResultTask {

    @Override
    public void run(Result result, SchedulerEvent event) {
        PHPParseResult rs = (PHPParseResult) result;
        Language lang = LanguageRegistry.getInstance().getLanguageByMimeType("text/x-php5");
        DeclarationFinder df = lang.getDeclarationFinder();
        Document doc = rs.getSnapshot().getSource().getDocument(false);
        
        // extract method invocations from ast tree
        MethodInvocationVisitor visitor = new MethodInvocationVisitor();
        rs.getProgram().accept(visitor);
        
        // list with errors for this file
        List<ErrorDescription> l = Collections.synchronizedList(new ArrayList<ErrorDescription>());
        
        for(MethodInvocation mi : visitor.invocations) {
            DeclarationLocation dl = df.findDeclaration(rs, mi.getMethod().getStartOffset()+1);
            
            if(dl != DeclarationLocation.NONE) {
               
                if(dl.getElement() instanceof TypeMemberElement) {
                    TypeElement tele = ((TypeMemberElement)dl.getElement()).getType();
                    String name = tele.getFullyQualifiedName().toString();
                    
                    if(shouldCheckForApiAnnotations(name)) {
                        try {
                            ParserManager.parse(
                                    Collections.singleton(Source.create(dl.getFileObject())),
                                    new MethodInvocationCheckerTask(dl, l, doc, mi));
                        } catch (ParseException ex) {
                            Logger.getLogger(ApiWarningHighlitingTask.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        }
    }

    @Override
    public int getPriority() {
        return 200;
    }

    @Override
    public Class<? extends Scheduler> getSchedulerClass() {
        return Scheduler.EDITOR_SENSITIVE_TASK_SCHEDULER;
    }

    @Override
    public void cancel() {
        Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).severe("ApiWarningHighlitingTask.cancel()");
    }
    
    private static class MethodInvocationVisitor extends DefaultVisitor {
        List<MethodInvocation> invocations = new ArrayList<MethodInvocation>();
        
        @Override
        public void visit(MethodInvocation node) {
            invocations.add(node);
            super.visit(node);
        }
        
    }
    
    private static boolean shouldCheckForApiAnnotations(String className) {
        if(className.charAt(0) != '\\') {
            className = "\\" + className;
        }
        
        String classPrefixes = NbPreferences.forModule(ApiWarningHighlitingTask.class).get("classPrefixes", null);
        
        if(classPrefixes != null) {
            for(String classPrefix : classPrefixes.split(",")) {
                if(!classPrefix.isEmpty() && className.startsWith(classPrefix)) {
                    return true;
                }
            }
        }
        
        return false;
    }
}
