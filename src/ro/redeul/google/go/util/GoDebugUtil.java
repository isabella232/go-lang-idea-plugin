package ro.redeul.google.go.util;

import com.intellij.openapi.editor.Document;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import org.jetbrains.annotations.NotNull;
import ro.redeul.google.go.lang.psi.typing.GoType;
import ro.redeul.google.go.lang.psi.typing.GoTypePsiBacked;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class GoDebugUtil {
    public static void Println(Object ... objects){
        StringBuilder sb = new StringBuilder();
        Dump(sb,objects);
        System.out.println(sb.toString());
    }

    public static void Dump(StringBuilder sb,Object o){
        if (o==null){
            sb.append("null ");
            return;
        }
        if (o instanceof String){
            sb.append((String)o).append(" ");
            return;
        }
        if (o instanceof PsiElement){
            sb.append(o.getClass().getSimpleName()).append("(")
                    .append(((PsiElement) o).getText()).append(") ");
            return;
        }
        if (o instanceof GoType){
            sb.append(o.getClass().getSimpleName()).append("(");
            if (o instanceof GoTypePsiBacked){
                sb.append(((GoTypePsiBacked) o).getPsiType().getText());
            }
            sb.append(") ");
            return;
        }
        Class c = o.getClass();
        if (c.isArray() && !c.getComponentType().isPrimitive()){
            Object[] objects = (Object[])o;
            sb.append("[");
            for(Object o1:objects){
                Dump(sb,o1);
            }
            sb.append("] ");
            return;
        }
        sb.append(o).append(" ");
        return;
    }

    @NotNull
    public static String Dump(PsiReference[] references){
        if (references==null){
            return "null";
        }
        if (references.length==0){
            return "[]";
        }
        String output = "[";
        for(PsiReference reference:references){
            output+=reference.toString()+", ";
        }
        return output+"]";
    }

    @NotNull
    public static String Dump(GoType[] goTypes){
        if (goTypes==null){
            return "null";
        }
        if (goTypes.length==0){
            return "[]";
        }
        String output = "[";
        for(GoType type:goTypes){
            output+=type.toString()+", ";
        }
        return output+"]";
    }

    public static String PsiGetPosString(PsiElement element){
        if (element==null){
            return "null";
        }
        Document doc = PsiDocumentManager.getInstance(element.getProject()).getDocument(element.getContainingFile());
        if (doc==null){
            return "null(can not get Document)";
        }
        int lineNum = doc.getLineNumber(element.getTextOffset());
        String filePath = element.getContainingFile().getVirtualFile().getPath();
        return filePath+":"+lineNum;
    }
}
