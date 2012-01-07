package me.blaise.ExtendAdminGUI.xmlbasedgui;

import me.blaise.ExtendAdminGUI.gui.ExtendAdminGUI;
import me.blaise.ExtendAdminGUI.gui.PlayerChooserPanel;
import me.blaise.lib.gui.GridContainer;

import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.gui.ContainerType;
import org.getspout.spoutapi.gui.GenericButton;
import org.getspout.spoutapi.gui.GenericContainer;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

//TODO: add a <div nbrow, nbcols, row, col, rowspan, colspan> tag to place sudivided objets -> directly place the objets on the grid on the constructor?
public class ScreenGUI
{
	ExtendAdminGUI app_root;
	int colwidth;
	int nbcols;
	int nbrows;
	Element node;
	int rowheight;
	//public ArrayList<ContainerRow> rows;
	private GridContainer grid;
	private PlayerChooserPanel playerChooserPanel;
	private int playerChooserPanelWidth;
	private int playerChooserPanelHeight;
	public PlayerChooser lastEntryBox;
	private NavButton next;
	private GenericContainer menubar;
	private NavButton prev;
	public static enum NavButtonType {PREV, NEXT, FIND}
	
	public class NavButton extends GenericButton
	{
		public int id;
		ScreenGUI parent;
		public NavButtonType type;

		
		public NavButton( String name, NavButtonType type, ScreenGUI parent)
		{
			this.parent=parent;
			this.type=type;
			setText(name);
			setColor(ExtendAdminGUI.COMMAND_BUTTON_COLOUR);
			setHoverColor(ExtendAdminGUI.BUTTON_HOVER_COLOUR);
			setDisabledColor(ExtendAdminGUI.DISABLED_BUTTON_COLOUR);
			this.setMargin(1,5);
			this.setFixed(true);
		}
		
		public void onButtonClick(ButtonClickEvent event)
		{
			if(this.type==NavButtonType.NEXT)
				this.parent.navigateNext();
			else if(this.type==NavButtonType.PREV)
				this.parent.navigatePrevious();
			else if(this.type==NavButtonType.FIND)
				this.parent.search();
		}
	}

	

	public ScreenGUI(ExtendAdminGUI app_root, Element node)
	{
		this.app_root=app_root;
		this.node=node;
		this.lastEntryBox=null;
		
		this.menubar=new GenericContainer();
		this.menubar.setLayout(ContainerType.HORIZONTAL);
		this.menubar.setHeight(20).setWidth(this.app_root.user.getMainScreen().getWidth());
		this.menubar.setFixed(true);
		
		this.next=new NavButton(">>",NavButtonType.NEXT,this);
		this.next.setWidth(15);
		this.next.setHeight(15);
		this.next.setFixed(true);
		
		this.prev=new NavButton("<<",NavButtonType.PREV,this);
		this.prev.setWidth(15);
		this.prev.setHeight(15);
		this.prev.setFixed(true);
		
		this.menubar.addChildren(this.prev, this.next);
		
		this.nbrows=(new Integer(node.getAttribute("nbrows"))).intValue();
		this.nbcols=(new Integer(node.getAttribute("nbcols"))).intValue();
		
		//parameter
		//TODO: clean this
		this.playerChooserPanelHeight=0;//the height occupied
		this.playerChooserPanelWidth=140;//the witdh occupied
		if(this.node.getElementsByTagName("playerchooser").getLength()==0)
		{
			playerChooserPanelHeight=0;//the height occupied
			playerChooserPanelWidth=0;//the witdh occupied
		}
		
		//calculate the size
		this.rowheight=(this.app_root.user.getMainScreen().getHeight()-playerChooserPanelHeight-20)/nbrows;//margin
    	this.colwidth=(this.app_root.user.getMainScreen().getWidth()-playerChooserPanelWidth)/nbcols;
		
		//TODO: check the margin
	    playerChooserPanel=null;
	    this.grid=new DivContainer(this.node, this.nbrows, this.nbcols, this.colwidth, this.rowheight, this.app_root);
	    
	    NodeList playerchoice=this.node.getElementsByTagName("playerchooser");
		for(int i=0; i<Math.min(playerchoice.getLength(),1); i++)//only for the first element : multiples ignored
        {
            this.playerChooserPanel=new PlayerChooserPanel(playerChooserPanelWidth-20, rowheight*nbrows, app_root);
        }
	   // this.parse();
	}
	
	public void clear()
    {
		if(playerChooserPanel!=null)
				playerChooserPanel.clear();
		grid.clear();
    }
	

	public GenericContainer getMainContainer() {
		if(playerChooserPanel!=null)
		{
			GenericContainer temp= new GenericContainer();
			temp.setWidth(colwidth*nbcols+playerChooserPanelWidth).setHeight(rowheight*nbrows);
			temp.setLayout(ContainerType.HORIZONTAL);
		    temp.addChildren(this.grid,this.playerChooserPanel);
		    
		    GenericContainer temp2= new GenericContainer();
			temp2.setWidth(colwidth*nbcols).setHeight(rowheight*nbrows+20);
			temp2.setLayout(ContainerType.VERTICAL);
		    temp2.setX(0).setY(0);
		    temp2.addChildren(this.menubar, temp);
		    return temp2;
		}
		else
		{
			GenericContainer temp= new GenericContainer();
			temp.setWidth(colwidth*nbcols).setHeight(rowheight*nbrows+20);
			temp.setLayout(ContainerType.VERTICAL);
		    temp.setX(0).setY(0);
		    temp.addChildren(this.menubar, this.grid);
		    return temp;
		}
	}
	
	public void registerLastEntry(PlayerChooser e)
	{
		this.lastEntryBox=e;
	}

	public void fillLastEntry(String name) {
		if(this.lastEntryBox!=null)
			this.lastEntryBox.setText(name);
	}

	public void navigateNext() {
		this.app_root.goNext();
	}

	public void navigatePrevious() {
		this.app_root.goBack();
	}

	public void search() {		
	}
	
}
