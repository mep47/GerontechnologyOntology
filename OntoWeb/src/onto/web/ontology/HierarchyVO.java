package onto.web.ontology;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import onto.ejb.ontology.HierarchyMO;

@Named
@ViewScoped
public class HierarchyVO implements Serializable {
	
	static final long serialVersionUID = -1;
	
	@EJB
	HierarchyMO hierarchyMO;
	
	public String loadHierarchy()
	{
		String hierarchy = "<pre>"+hierarchyMO.loadHierarchy()+"</pre>";
		return hierarchy;
	}
	
	public static String escape(String s) {
	    StringBuilder builder = new StringBuilder();
	    boolean previousWasASpace = false;
	    for( char c : s.toCharArray() ) {
	        if( c == ' ' ) {
	            if( previousWasASpace ) {
	                builder.append("&nbsp;");
	                previousWasASpace = false;
	                continue;
	            }
	            previousWasASpace = true;
	        } else {
	            previousWasASpace = false;
	        }
	        switch(c) {
	            case '<': builder.append("&lt;"); break;
	            case '>': builder.append("&gt;"); break;
	            case '&': builder.append("&amp;"); break;
	            case '"': builder.append("&quot;"); break;
	            case '\n': builder.append("<br>"); break;
	            // We need Tab support here, because we print StackTraces as HTML
	            case '\t': builder.append("&nbsp; &nbsp; &nbsp;"); break;  
	            default:
	                if( c < 128 ) {
	                    builder.append(c);
	                } else {
	                    builder.append("&#").append((int)c).append(";");
	                }    
	        }
	    }
	    return builder.toString();
	}

}
