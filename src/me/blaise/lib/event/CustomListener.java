package me.blaise.lib.event;

import org.bukkit.event.CustomEventListener;
import org.bukkit.event.Event;

public class CustomListener extends CustomEventListener {

	public void onCustomEvent(CustomEvent event) {
		
	}
	
	@Override
	public void onCustomEvent(Event event) {
		if(event instanceof CustomEvent) {
			onCustomEvent((CustomEvent)event);
		}
	}
}
