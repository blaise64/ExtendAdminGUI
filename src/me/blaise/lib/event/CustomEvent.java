package me.blaise.lib.event;

import org.bukkit.event.Event;

public class CustomEvent extends Event {
	private static final long serialVersionUID = 7024450850892041516L;
	
	protected CustomEvent(/*More argument,*/ String name) {
		super(name);
	}
	
	/* getters */
}
