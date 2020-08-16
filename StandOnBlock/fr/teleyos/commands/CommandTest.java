package fr.teleyos.commands;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.Bukkit;
import fr.teleyos.PlayerList;
import java.util.List;
import fr.teleyos.ObjectChooser;
import fr.teleyos.StandOnBlockGame;
import java.util.List;
import java.util.ArrayList;

public class CommandTest implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg,String[] args){
		if(sender instanceof Player){

			Player player = (Player)sender;

			if(cmd.getLabel().equalsIgnoreCase("guidesob")){
				ItemStack book = new ItemStack(Material.WRITTEN_BOOK,1);
				BookMeta bookM = (BookMeta)book.getItemMeta();
				bookM.setTitle("§cUser Guide");
				bookM.setAuthor("§6Teleyos");
				List<String> pages = new ArrayList<String>();
				pages.add("Hey "+player.getName()+",Thanks for downloading my plugin.\nIt's the first one i made on my own so I hope there isn't big bugs.You'll find the guide of use of the plugin in the next page");
				pages.add("Compass:\n  Left-click to launch the game\n  Sneak+Left-click to end it\nCommands:\n  givecompass to get §cThe Compass §r\n  joingame before a game start to join it (can't do it while a game is running)");
				bookM.setPages(pages);
				book.setItemMeta(bookM);
				player.getInventory().addItem(book);

			}

			if (cmd.getLabel().equalsIgnoreCase("givecompass")) {
				player.sendMessage("§fYou now have the §cpower §fto launch §athe game");
				player.getInventory().addItem(makeItem(Material.COMPASS,1,"§cThe Compass"));
				return true;
			}

			if (cmd.getLabel().equalsIgnoreCase("joingame")) {
				if(!StandOnBlockGame.isLaunched()){
					PlayerList.addPlayer(player.getName());
					player.sendMessage("You joined the §cStanOnBlockGame");
					return true;
				}else{
					player.sendMessage("A §cStanOnBlockGame §fis still §6in progress §fyou can't join.");
				}
				
			}
		}
		return false;
	}

	public ItemStack makeItem(Material material, int count, String displayname){
		ItemStack it = new ItemStack(material,count);
		ItemMeta itM = it.getItemMeta();
		itM.setDisplayName(displayname);
		it.setItemMeta(itM);
		return it;
	}
}