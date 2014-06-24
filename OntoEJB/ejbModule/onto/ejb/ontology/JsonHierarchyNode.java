package onto.ejb.ontology;

import java.util.ArrayList;
import java.util.List;

public class JsonHierarchyNode {
	
	String id;
	String label;
	List<JsonHierarchyNode> children;
	
	public JsonHierarchyNode(String label)
	{
		if(label == null || label.equals(""))
		{
			throw new NullPointerException("The label cannot be null or an empty String");
		}
		this.label = label;
		this.id = label+"_id";
		children = new ArrayList<JsonHierarchyNode>();
	}
	
	public void addChild(JsonHierarchyNode child)
	{
		if(child == null)
		{
			throw new NullPointerException("The child cannot be null");
		}
		children.add(child);
	}
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public List<JsonHierarchyNode> getChildren() {
		return children;
	}
	public void setChildren(List<JsonHierarchyNode> children) {
		this.children = children;
	}

}
