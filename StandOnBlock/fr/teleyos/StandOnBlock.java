package fr.teleyos;

import org.bukkit.plugin.java.JavaPlugin;
import fr.teleyos.commands.CommandTest;
import org.bukkit.Bukkit;
import fr.teleyos.StandOnBlockListeners;
import fr.teleyos.PlayerList;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;

public class StandOnBlock extends JavaPlugin{

	@Override
	public void onEnable(){

		System.out.println("[StandOnBLock] : Plugin operationnal");
		getCommand("givecompass").setExecutor(new CommandTest());
		getCommand("joingame").setExecutor(new CommandTest());
		getCommand("guidesob").setExecutor(new CommandTest());
		getServer().getPluginManager().registerEvents(new StandOnBlockListeners(), this);

	}

	@Override
	public void onDisable(){
		for (String pl : PlayerList.getPlayerList()){
			if (Bukkit.getPlayer(pl) != null) {
				Bukkit.getPlayer(pl).getInventory().setHelmet(new ItemStack(Material.AIR,1));
			}
		}
		StandOnBlockGame.setLaunched(false);
		GameTime.clickChrono();
		GameTime.getTimeElapsed();
		System.out.println("[StandOnBLock] : Plugin shut down");
	}
	


}