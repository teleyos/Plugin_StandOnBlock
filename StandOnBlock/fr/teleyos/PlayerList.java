package fr.teleyos;

import java.util.List;
import java.util.ArrayList;

public class PlayerList{

	private static List<String> playerList = new ArrayList<String>();

	static public boolean addPlayer(String playerName){
		for (String name : playerList) {
		    if (name == playerName) {
		        return false;
		    }
		}
		playerList.add(playerName);
		return true;
	}

	static public boolean isInPlayerList(String playerName){
		for (String name : playerList) {
		    if (name == playerName) {
		        return true;
		    }
		}
		return false;
	}

	static public void removePlayer(String playerName){
		playerList.remove(playerName);
	}

	static public void removePlayers(){
		playerList = null;
		playerList = new ArrayList<String>();
	}

	static public List<String> getPlayerList(){
		return playerList;
	}

}