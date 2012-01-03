package me.blaise.ExtendAdminGUI;

import java.util.ArrayList;

import org.getspout.spoutapi.gui.ContainerType;
import org.getspout.spoutapi.gui.GenericContainer;

public class Finder extends GenericContainer implements Navigable, Chooser {
	private ArrayList<String> choices;
	private Chooser parent;
	private SearchBox search;
	private GridContainer grid;
	private int index;
	private int nbrows=5;
	private int nbcols=2;
	private int colwidth=60;
	private int rowheight=20;
	private NavButton next;
	private NavButton prev;
	private GenericContainer nav;
	private ArrayList<String> matches;
	private String var;

	public Finder(Chooser parent, String var, ArrayList<String> choicesList){
		this.parent=parent;
		this.var=var;
		this.choices=new ArrayList<String>(choicesList);
		this.matches=new ArrayList<String>(choices);
		index=0;
		this.draw();
	}
	
	public Finder(Chooser parent, String var){
		this.parent=parent;
		this.var=var;
		this.choices=new ArrayList<String>();
		this.matches=new ArrayList<String>(choices);
		index=0;
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
		this.nav=new GenericContainer();
		this.nav.setLayout(ContainerType.HORIZONTAL);
		this.nav.setFixed(true);
		this.nav.setHeight(rowheight);
		this.nav.setWidth(nbcols*colwidth);
		
		this.search=new SearchBox(this);
		this.search.setHeight(15);
		this.search.setWidth(nbcols*colwidth-25*2);
		this.search.setMargin(0);
		this.search.setFixed(true);
		
		this.next=new NavButton(">>",NavButton.NavButtonType.NEXT,this);
		this.next.setWidth(15);
		this.next.setHeight(15);
		this.next.setFixed(true);
		
		this.prev=new NavButton("<<",NavButton.NavButtonType.PREV,this);
		this.prev.setWidth(15);
		this.prev.setHeight(15);
		this.prev.setFixed(true);
		
		this.nav.addChildren(this.prev, this.search, this.next);
		
		this.grid= new GridContainer(nbrows, nbcols, colwidth, rowheight);
		this.drawButton();
		this.setLayout(ContainerType.VERTICAL);
		//this.setHeight(300).setWidth(120);
		this.addChildren(this.nav, this.grid);
		
	}
	
	private void drawButton(){
		String pattern=this.search.getText().toLowerCase();
		this.matches.clear();
		for(int i=0; i<this.choices.size();i++){
			if(this.choices.get(i).contains(pattern)||pattern.equalsIgnoreCase(""))
				matches.add(this.choices.get(i));
		}
		this.grid.clear();
		for(int i=10*index; (i<matches.size()) && (i<10*(1+index));i++){
			this.grid.put(new ChoiceButton(this.var, matches.get(i), this));
		}
	}

	public void onTextChange(String text) {
		this.index=0;
		this.drawButton();
	}

	@Override
	public void navigateNext() {
		if(index<(matches.size()/10)){
			++index;
			this.drawButton();
		}
	}

	@Override
	public void navigatePrevious() {
		if(index>0){
			--index;
			this.drawButton();
		}
	}

	@Override
	public void search() {
		
	}

}
