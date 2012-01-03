package me.blaise.ExtendAdminGUI;

import java.util.ArrayList;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class XmlUtils {
	
	public static ArrayList<Element> getChildrenByTagName(Element node, String tagname){
		NodeList childrenNode=node.getChildNodes();
		ArrayList<Element> matches=new ArrayList<Element>();
		for(int i=0; i<childrenNode.getLength(); i++)
        {
			if (childrenNode.item(i) instanceof Element){
	            Element child = (Element)childrenNode.item(i);
	            if(child.getTagName().equals(tagname)){
	            	matches.add(child);
	            }
			}
        }
		return matches;
	}
	
}
