package onto.ejb.ontology;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.ejb.Stateless;

import org.jboss.logging.Logger;

import com.google.gson.Gson;

import aminePlatform.kernel.lexicons.Lexicon;
import aminePlatform.kernel.lexicons.ToStringException;
import aminePlatform.kernel.ontology.CS;
import aminePlatform.kernel.ontology.Individual;
import aminePlatform.kernel.ontology.Ontology;
import aminePlatform.kernel.ontology.Situation;
import aminePlatform.kernel.ontology.Type;
import aminePlatform.util.Identifier;
import aminePlatform.util.cg.CG;

@Stateless
public class JsonHierarchyMO {
	
    private static final Logger logger = Logger.getLogger(JsonHierarchyMO.class);	
	
	public String loadHierarchy()
	{
		  Ontology ontology;
		  Lexicon englishLexicon;
		  
		  List<JsonHierarchyNode> jsonHierarchy = new ArrayList<JsonHierarchyNode>();
		  
		  String home = System.getProperty("user.home");

		  Identifier english = new Identifier("English");
		  Identifier rootIdentifier = new Identifier("Universal");
		  String ontologyFilePath = home+"/GerontechnologyOntology/GerontechnologyOntology.xml";
		  try
		  {
		    CG cg = new CG();
		    ontology = Ontology.loadOntologyFromXML(ontologyFilePath, cg);
		    englishLexicon = ontology.getLexicon(english);
		    
		    JsonHierarchyNode node = new JsonHierarchyNode(rootIdentifier.toString());
		    jsonHierarchy.add(node);
		    loadNextJsonHierarchy(englishLexicon, rootIdentifier, node);
	
//		    JsonHierarchyNode node = new JsonHierarchyNode("Root1");
//		    jsonHierarchy.add(node);
//		    JsonHierarchyNode subNode = new JsonHierarchyNode("Child1");
//		    node.addChild(subNode);
//		    subNode = new JsonHierarchyNode("Child2");
//		    node.addChild(subNode);
//		    node = new JsonHierarchyNode("Root2");
//		    jsonHierarchy.add(node);		    
//		    subNode = new JsonHierarchyNode("Child3");
//		    node.addChild(subNode);
		    
		  } catch (Exception e) {
			    e.printStackTrace();
			    System.exit(0);
			  }
			  
//		  System.out.println("Succesfully Completed");
		  
	      final Gson gson = new Gson();

	      String jsonHierarchyString = gson.toJson(jsonHierarchy);

		  return jsonHierarchyString;
	}

    @SuppressWarnings("unchecked")
	private void loadNextJsonHierarchy(Lexicon lexicon,
			Identifier parentIdentifier, JsonHierarchyNode parentNode)
	{
		Enumeration<Identifier> e = lexicon.getDirectSubTypes(parentIdentifier);
	    if (e != null)
	    {
		      while (e.hasMoreElements()) {
			    Identifier id = (Identifier)e.nextElement();
			    JsonHierarchyNode node = new JsonHierarchyNode(id.getName());
			    parentNode.addChild(node);
			    
			    loadNextJsonHierarchy(lexicon, id, node);
		      }
	      }
		
	}
    
    public String retrieveDefinition(String itemName)
    {
		  Ontology ontology;
		  Lexicon englishLexicon;
		  CG cg;
		  String itemGraphStr = null;
		  
		  logger.info("Starting retrieveDefinition - itemName="+itemName);
		  
		  String home = System.getProperty("user.home");

		  Identifier english = new Identifier("English");
		  String ontologyFilePath = home+"/GerontechnologyOntology/GerontechnologyOntology.xml";
		  Identifier itemIdentifier = new Identifier(itemName.trim());
		  
		  try
		  {
			cg = new CG();
		    ontology = Ontology.loadOntologyFromXML(ontologyFilePath, cg);
		    englishLexicon = ontology.getLexicon(english);
	        Type itemType = englishLexicon.getTypeCS(itemIdentifier);
	        if(itemType != null)
	        {
		        cg = (CG) itemType.getDefinition(); // Note the casting to CG
		        if(cg != null)
		        {
		        	itemGraphStr = cg.toString(englishLexicon);
		        }
		        else
		        {
		        	itemGraphStr = "There is no Definition for "+itemName;
		        }
	        }
	        else
	        {
	        	itemGraphStr = "Item "+itemName+" does not exist in the ontology.";
	        }
		  }
		  catch (Exception e)
		  {
			  logger.error(e, e);
		  }
	        
		  return itemGraphStr;
//    	return "[C1]-r->[C2]";
    }
	
	public static void main(String[] args)
	{
		JsonHierarchyMO hierarchy = new JsonHierarchyMO();
		hierarchy.loadHierarchy();
	}

}
