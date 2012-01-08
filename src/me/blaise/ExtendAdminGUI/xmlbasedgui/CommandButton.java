package me.blaise.ExtendAdminGUI.xmlbasedgui;

import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.blaise.ExtendAdminGUI.gui.ExtendAdminGUI;
import me.blaise.lib.gui.GridContainer;
import me.blaise.lib.main.CustomPlugin;

import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.gui.GenericButton;
import org.getspout.spoutapi.player.SpoutPlayer;
import org.w3c.dom.Element;


/**
 * @author blaise
 *
 */
public class CommandButton extends GenericButton
{
	/*
	 * label = text of the button
	 * command = command to execute or text to write
	 * row = row positon in the screen
	 * col = col positon in the screen
	 * stay_open = boolean true to keep the screen after executing the command
	 */
	
	public ExtendAdminGUI app_root;

	public int col;

	public String command;
	public int id;
	public String info;
	public Element node;
	public GridContainer parent;
	public int row;
	public boolean stay_open;
	public int colspan=1;
	public int rowspan=1;
	
	public CommandButton(Element node, int rowheight, int colwidth, GridContainer parent, ExtendAdminGUI app_root)
	{
		/*importing attributes from the xml node
		 * xml=xmlparent.getElementsByTagName("button")[i];
		 * hasAttribute to check if the attribute exist
		 * getAttribute to get the value of the attribute as a string
		 */

		//reference to the parent screen
		this.parent = parent;
		this.app_root=app_root;
		this.node=node;

		if(node.hasAttribute("stay_open"))
		{
			this.stay_open=(new Boolean(node.getAttribute("stay_open"))).booleanValue();
		}
		else
		{
			this.stay_open = false;
		}

		if(node.hasAttribute("label"))
		{
			setText(node.getAttribute("label"));
		}
		else
		{
			setText("NoNameButton");
		}

		if(node.hasAttribute("command"))
		{
			this.command=node.getAttribute("command");
		}
		else
		{
			this.command = "";
		}

		if(node.hasAttribute("row"))
		{
			this.row = (new Integer(node.getAttribute("row"))).intValue();
		}
		else
		{
			this.row = 1;
		}

		if(node.hasAttribute("col"))
		{
			this.col=(new Integer(node.getAttribute("col"))).intValue();
		}
		else
		{
			this.col = 1;
		}
		
		if(node.hasAttribute("rowspan"))
		{
			this.rowspan = (new Integer(node.getAttribute("rowspan"))).intValue();
		}
		
		if(node.hasAttribute("colspan"))
		{
			this.colspan = (new Integer(node.getAttribute("colspan"))).intValue();
		}
		
		if(node.hasAttribute("width"))
		{
			this.setWidth((new Integer(node.getAttribute("width"))).intValue());
		}
		else
		{
			this.setWidth(colwidth*colspan);
		}

		if(node.hasAttribute("height"))
		{ 
			this.setHeight((new Integer(node.getAttribute("height"))).intValue());
		}
		else
		{
			if(rowheight>15)
			{
				this.setHeight(15);
			}
			else
			{
				this.setHeight(rowheight*rowspan);
			}

		}

		if(node.hasAttribute("info"))
		{
			this.info=node.getAttribute("info");
		}
		else
		{
			this.info=this.command;
		}

		
		setColor(ExtendAdminGUI.COMMAND_BUTTON_COLOUR);
		setHoverColor(ExtendAdminGUI.BUTTON_HOVER_COLOUR);
		setDisabledColor(ExtendAdminGUI.DISABLED_BUTTON_COLOUR);
		
		//request to be placed on the container grid
		this.parent.addElement(this, this.row, this.col, this.colspan, this.rowspan);
		//standard skin parameters
		this.setMargin(1,5);
		this.setTooltip(this.info);
		this.setFixed(true);
		
		//TODO: better tooltip coloration
	}
	
	public void onButtonClick(ButtonClickEvent event)
	{
		SpoutPlayer user = event.getPlayer();
		String commands[] = command.split(";");

		//seeking for parameters
		for(int i = 0; i < commands.length; i++)
		{
			String s = commands[i];

			Pattern pattern = Pattern.compile("%(\\w+)%");
			Matcher matcher = pattern.matcher(s);
			matcher.find();
			if(matcher.groupCount()>1)
				s = matcher.replaceAll(this.app_root.getParamValue(matcher.group(1)));

			s = s.trim(); //delete whites spaces in start and end of the string
			s = s.replaceAll("\\s+", " "); //remove multiples spaces
			user.chat(s); //execute command in the chat
			//debug
			//if(this.app_root.plugin.config.getBoolean("debug_mode"))
				CustomPlugin.doLog(""+user.getName()+" executed the command/chat '"+s+"'", Level.FINEST);
			i++;
		}
		//close the GUI
		//TODO: check if there is no better solution
		if(!stay_open)
			user.getMainScreen().closePopup();
	}
}
