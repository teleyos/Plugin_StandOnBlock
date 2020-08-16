package fr.teleyos;

import org.bukkit.Material;
import java.util.Random;

public class ObjectChooser{

	private static Material object = Material.ACACIA_LOG;

	public static Material getObject(){
		return object;
	}

	public static void newObject(){
		do{
			object = Material.values()[new Random().nextInt(Material.values().length)];
		}while(!object.isBlock());
		
	}

	public static String getName(){
		return "§c"+object.toString().toLowerCase().replace("_"," ");
	}

}