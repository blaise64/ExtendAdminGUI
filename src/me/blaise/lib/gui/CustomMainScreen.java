package me.blaise.lib.gui;

import org.getspout.spoutapi.gui.Button;
import org.getspout.spoutapi.gui.GenericPopup;
import org.getspout.spoutapi.gui.TextField;
import org.getspout.spoutapi.gui.Widget;
import org.getspout.spoutapi.player.SpoutPlayer;

public abstract class CustomMainScreen extends GenericPopup {
	private SpoutPlayer player;

	public CustomMainScreen(SpoutPlayer player){
		super();
		this.player = player;
		initScreen();
	}

	/*create in this fonction all the stuff needed
	 * then attach ut to your plugin
	 * 	CustomPlugin plugin = CustomPlugin.getInstance();
	 * 	attachWidget(plugin, widget)
	 * use the refreshView(); function if needed
	 */
	protected abstract void initScreen();


	public void open(){
		player.getMainScreen().attachPopupScreen(this);
		setDirty(true);
		for(Widget widget:getAttachedWidgets()){
			widget.setVisible(true);
			widget.setDirty(true);
		}
		refreshView();
	}

	public void hide(){
		close();
	}

	public abstract void handleTextFieldChange(TextField field, String newValue);

	public abstract void handleClick(Button button);
	
	public abstract void handleSelected(int item, boolean doubleClick);

	public abstract void refreshView();
	
	public void handleClose() {

	}
}
