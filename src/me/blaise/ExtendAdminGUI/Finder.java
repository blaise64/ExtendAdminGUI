package me.blaise.ExtendAdminGUI;

import java.util.ArrayList;

import org.getspout.spoutapi.gui.ContainerType;
import org.getspout.spoutapi.gui.GenericContainer;
import org.getspout.spoutapi.gui.GenericListWidget;
import org.getspout.spoutapi.gui.ListWidgetItem;

public class Finder extends GenericContainer implements Navigable, Chooser {
	private ArrayList<String> choices;
	private Chooser parent;
	private SearchBox search;
	private ArrayList<String> matches;
	private String var;
	GenericListWidget list;

	public Finder(Chooser parent, String var, ArrayList<String> choicesList){
		this.parent=parent;
		this.var=var;
		this.choices=new ArrayList<String>(choicesList);
		this.matches=new ArrayList<String>(choices);
		this.draw();
		this.drawButton();
	}
	
	public Finder(Chooser parent, String var){
		this.parent=parent;
		this.var=var;
		this.choices=new ArrayList<String>();
		this.matches=new ArrayList<String>(choices);
		this.draw();
	}	
	
	public Finder setChoices(ArrayList<String> choicesList){
		this.choices.clear();
		this.choices=new ArrayList<String>(choicesList);
		drawButton();
		return this;
	}
	
	public Finder addChoices(ArrayList<String> choicesList){
		for(String c:choicesList)
			this.choices.add(c);
		drawButton();
		return this;
	}
	
	public Finder addChoice(String choice){
		this.choices.add(choice);
		drawButton();
		return this;
	}

	public void choose(String var, String name){
		this.parent.choose(this.var, name);
	}
	
	private void draw(){
		
		this.search=new SearchBox(this);
		this.search.setMaxHeight(15);
		
		this.list = new ChoosableListWidget(this, this.var);
		this.list.setMargin(0);
		this.list.setMarginTop(5);
		
		this.setLayout(ContainerType.VERTICAL);
		this.setMargin(5);		//this.setHeight(300).setWidth(120);
		this.addChildren(this.search, this.list);
		
	}
	
	private void drawButton(){
		String pattern=this.search.getText().toLowerCase();
		this.matches.clear();
		for(int i=0; i<this.choices.size();i++){
			if(this.choices.get(i).contains(pattern)||pattern.equalsIgnoreCase(""))
				matches.add(this.choices.get(i));
		}
		this.list.clear();
		for(int i=0; i<this.matches.size();i++){
			 this.list.addItem(new ListWidgetItem(this.matches.get(i), this.matches.get(i)));
		}
	}



	@Override
	public void navigateNext() {
	}

	@Override
	public void navigatePrevious() {
	}

	@Override
	public void search() {
		this.drawButton();
	}

}
