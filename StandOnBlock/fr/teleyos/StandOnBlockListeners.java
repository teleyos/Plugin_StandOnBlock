package fr.teleyos;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.block.Block;
import org.bukkit.Location;
import fr.teleyos.customevents.PlayerWinsEvent;
import org.bukkit.Bukkit;
import fr.teleyos.PlayerList;
import java.util.List;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.NamespacedKey;
import fr.teleyos.StandOnBlockGame;
import fr.teleyos.ObjectChooser;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import fr.teleyos.GameTime;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.entity.Item;

public class StandOnBlockListeners implements Listener{

	@EventHandler
	public void onInteract(PlayerInteractEvent event){
		Player player = event.getPlayer();
		Action action = event.getAction();
		ItemStack it = event.getItem();

		if(it == null) return;

		if (it.getItemMeta().getDisplayName().equalsIgnoreCase("§cThe Compass") && event.getMaterial() == Material.COMPASS){

			if (!StandOnBlockGame.isLaunched()) {
				StandOnBlockGame.setLaunched(true);
				GameTime.clickChrono();
				ObjectChooser.newObject();

				Material obj = ObjectChooser.getObject();
				String objN = ObjectChooser.getName();

				Bukkit.getServer().broadcastMessage("The block is : §c"+objN);

				for (String pl : PlayerList.getPlayerList()){
					if (Bukkit.getPlayer(pl) != null) {
						Bukkit.getServer().broadcastMessage(Bukkit.getPlayer(pl).getName()+" participate !");
						Bukkit.getPlayer(pl).getInventory().setHelmet(makeItem(obj,1,"§c"+objN));
					}
				}
			}else if(StandOnBlockGame.isLaunched() && player.isSneaking()){
				StandOnBlockGame.setLaunched(false);
				GameTime.clickChrono();
				Bukkit.getServer().broadcastMessage(event.getPlayer().getName()+" §cstopped §fthe game after §6"+GameTime.getTimeElapsed()+"§r !");
				for (String pl : PlayerList.getPlayerList()){
					if (Bukkit.getPlayer(pl) != null) {
						Bukkit.getPlayer(pl).getInventory().setHelmet(new ItemStack(Material.AIR,1));
					}
				}
				PlayerList.removePlayers();
			}else{
				player.sendMessage("sneak and click to stop the game");
			}
			
		}
	}

	@EventHandler
	public void onMove(PlayerMoveEvent event){
		Player player = event.getPlayer();
		if (PlayerList.isInPlayerList(player.getName()) && StandOnBlockGame.isLaunched()){
			Location to = event.getTo();
			Location under = new Location(player.getWorld(),to.getX(),to.getY()-1,to.getZ());
			Block block = under.getBlock();

			if (player.getInventory().getHelmet() != null ){
				// player.sendMessage("HEAD : "+player.getInventory().getHelmet().getType().toString());
				// player.sendMessage("FOOT : "+block.getType().toString());

				if(player.getInventory().getHelmet().getType() == block.getType()){
					Bukkit.getServer().getPluginManager().callEvent(new PlayerWinsEvent(player));
				}
			}
		}
		
	}

	@EventHandler
	public void onPlayerWin(PlayerWinsEvent event){
		StandOnBlockGame.setLaunched(false);		
		GameTime.clickChrono();
		Bukkit.getServer().broadcastMessage(event.getPlayer().getName()+" §awins §f! It took him §6"+GameTime.getTimeElapsed());
		for (String pl : PlayerList.getPlayerList()){
			if (Bukkit.getPlayer(pl) != null) {
				Bukkit.getPlayer(pl).getInventory().setHelmet(new ItemStack(Material.AIR,1));
			}
		}
		PlayerList.removePlayers();
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event){
		Player player = (Player)event.getWhoClicked();
		ItemStack curr = event.getCurrentItem();

		Material obj = ObjectChooser.getObject();
		String objN = ObjectChooser.getName();

		if (curr == null) return; 
		if (curr.getType() == obj && objN.equalsIgnoreCase(curr.getItemMeta().getDisplayName()) && event.getRawSlot() == 5){
			player.closeInventory();
			player.sendTitle("Don't try cheating !","",10,70,20);
			event.setCancelled(true);
		}

		//to debug
		//player.getInventory().addItem(makeItem(Material.APPLE,1,"§cPomme")); 
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent event){
		Player player = event.getEntity();

		if(PlayerList.isInPlayerList(player.getName()) && StandOnBlockGame.isLaunched()){
			PlayerList.removePlayer(player.getName());
			player.sendMessage("You §6quitted §rthe game by §cdying!");
		}

		for(ItemStack drop : event.getDrops()){
		    if(drop.getItemMeta().getDisplayName().equalsIgnoreCase(ObjectChooser.getName())){
		        event.getDrops().remove(event.getDrops().indexOf(drop));
		    }
		}
	}

	@EventHandler
	public void onDrop(PlayerDropItemEvent event){
		Item it = event.getItemDrop();
		Player player = event.getPlayer();
		Material obj = ObjectChooser.getObject();
		String objN = ObjectChooser.getName();

		if (it.getItemStack().getItemMeta().getDisplayName().equalsIgnoreCase(objN)) {
			player.sendMessage("non");
			it.remove();
		}
	}

	public ItemStack makeItem(Material material, int count, String displayname){
		ItemStack it = new ItemStack(material,count);
		ItemMeta itM = it.getItemMeta();
		itM.setDisplayName(displayname);
		it.setItemMeta(itM);
		return it;
	}

}