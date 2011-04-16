package org.github.planetenkiller.phpdocapiannotation;

import java.util.List;
import javax.swing.text.Document;
import org.netbeans.modules.csl.api.DeclarationFinder.DeclarationLocation;
import org.netbeans.modules.parsing.api.ResultIterator;
import org.netbeans.modules.parsing.api.UserTask;
import org.netbeans.modules.parsing.spi.Parser;
import org.netbeans.modules.php.editor.parser.PHPParseResult;
import org.netbeans.modules.php.editor.parser.api.Utils;
import org.netbeans.modules.php.editor.parser.astnodes.Comment;
import org.netbeans.modules.php.editor.parser.astnodes.MethodInvocation;
import org.netbeans.modules.php.editor.parser.astnodes.PHPDocBlock;
import org.netbeans.spi.editor.hints.ErrorDescription;
import org.netbeans.spi.editor.hints.ErrorDescriptionFactory;
import org.netbeans.spi.editor.hints.HintsController;
import org.netbeans.spi.editor.hints.Severity;

/**
 * Checks an singel method invocation.
 */
class MethodInvocationCheckerTask extends UserTask {
    private final DeclarationLocation dl;
    private final List<ErrorDescription> l;
    private final Document doc;
    private final MethodInvocation mi;

    public MethodInvocationCheckerTask(DeclarationLocation dl, List<ErrorDescription> l, Document doc, MethodInvocation mi) {
        this.dl = dl;
        this.l = l;
        this.doc = doc;
        this.mi = mi;
    }

    @Override
    public void run(ResultIterator resultIterator) throws Exception {
        Parser.Result decrs = resultIterator.getParserResult(dl.getOffset());
        
        if (decrs != null) {
            PHPParseResult phpdecrs = (PHPParseResult) decrs;
            Comment comment = Utils.getCommentForNode(phpdecrs.getProgram(), Utils.getNodeAtOffset(phpdecrs, dl.getOffset()));
            
            boolean illegalMethodCall = false;
            if (comment != null) {
                if (comment instanceof PHPDocBlock) {
                    PHPDocBlock pHPDocBlock = (PHPDocBlock) comment;
                    illegalMethodCall = !pHPDocBlock.getDescription().contains("@api");
                }
            } else {
                illegalMethodCall = true;
            }
            
            if (illegalMethodCall) {
                l.add(ErrorDescriptionFactory.createErrorDescription(Severity.WARNING, "use of non @api method", doc, doc.createPosition(mi.getMethod().getStartOffset()), doc.createPosition(mi.getMethod().getEndOffset())));
                HintsController.setErrors(doc, "api-annos", l);
            }
        }
    }
    
}
