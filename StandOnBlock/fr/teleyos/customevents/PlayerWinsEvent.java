package fr.teleyos.customevents;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.entity.Player;

public class PlayerWinsEvent extends Event{

	private static final HandlerList HANDLERS = new HandlerList();

	private Player winner;


	public PlayerWinsEvent(Player winner){
		this.winner = winner;
	}

	public Player getPlayer(){
		return this.winner;
	}


    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

}