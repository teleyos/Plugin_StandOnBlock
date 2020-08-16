package fr.teleyos;

public class StandOnBlockGame{

	private static boolean launched;

	public static boolean isLaunched(){
		return launched;
	}

	public static void setLaunched(boolean state){
		launched = state;
	}

}