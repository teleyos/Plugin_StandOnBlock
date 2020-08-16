package fr.teleyos;

import java.time.*;
import java.util.concurrent.TimeUnit;

public class GameTime{
	
	private static Instant start = null;
	private static Instant stop = null;

	public static void clickChrono(){
		if(start == null){
			start = Instant.now();
		}else{
			stop = Instant.now();
		}
	}

	public static String getTimeElapsed(){
		long ret = Duration.between(start, stop).toMillis();
		stop =null;
		start =null;
		if(ret > 1000){
			long seconds = TimeUnit.SECONDS.convert(ret, TimeUnit.MILLISECONDS);
			if(seconds > 60){
				long minutes =  TimeUnit.MINUTES.convert(seconds, TimeUnit.SECONDS);
				if(minutes > 60){
					long hours = TimeUnit.HOURS.convert(minutes, TimeUnit.MINUTES);
					return String.valueOf(hours)+" hour(s) "+ String.valueOf(minutes-60*hours)+" minute(s) "+String.valueOf(seconds-60*minutes-60*60*hours)+" seconds";
				}
				return String.valueOf(minutes)+" minute(s) "+ String.valueOf(seconds-60*minutes)+" seconds";
			}
			return String.valueOf(seconds)+" seconds";
		}
		return String.valueOf(ret)+" milliseconds";
	}
}
