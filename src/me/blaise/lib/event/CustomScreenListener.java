package me.blaise.lib.event;

import me.blaise.lib.gui.CustomMainScreen;

import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.event.screen.ScreenCloseEvent;
import org.getspout.spoutapi.event.screen.ScreenListener;
import org.getspout.spoutapi.event.screen.TextFieldChangeEvent;
import org.getspout.spoutapi.gui.Screen;



public class CustomScreenListener extends ScreenListener {

	public CustomScreenListener() {
	}

	@Override
	public void onButtonClick(ButtonClickEvent event) {
		if(!event.getButton().isVisible())
			return;
		Screen screen = event.getScreen();
		if(screen instanceof CustomMainScreen){
			CustomMainScreen mainscreen = ((CustomMainScreen)screen);
			mainscreen.handleClick(event.getButton());
		}
	}

	@Override
	public void onTextFieldChange(TextFieldChangeEvent event) {
		Screen screen = event.getScreen();
		if(screen instanceof CustomMainScreen){
			((CustomMainScreen)screen).handleTextFieldChange(event.getTextField(), event.getNewText());
		}
	}

	@Override
	public void onScreenClose(ScreenCloseEvent event) {
		Screen screen = event.getScreen();
		if(screen instanceof CustomMainScreen){
			CustomMainScreen custom = ((CustomMainScreen)screen);
			custom.handleClose();
		}
	}
}
