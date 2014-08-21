package us.meyerzinn.bukkit.Spectators;

import java.util.UUID;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class Listeners implements Listener {

	@EventHandler
	public void onPlayerDropItemEvent(PlayerDropItemEvent e) {
		UUID uuid = e.getPlayer().getUniqueId();
		if (Spectators.isPlayerSpectating(uuid)) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlayerPickupItemEvent(PlayerPickupItemEvent e) {
		UUID uuid = e.getPlayer().getUniqueId();
		if (Spectators.isPlayerSpectating(uuid)) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent e) {
		if (e.getDamager().getType() == EntityType.PLAYER) {
			Player p = (Player) e.getDamager();
			if (Spectators.isPlayerSpectating(p.getUniqueId())) {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onInventoryClickEvent(InventoryClickEvent e) {
		if (Spectators.isPlayerSpectating(e.getWhoClicked().getUniqueId())) {
			e.setCancelled(true);
		}
	}

	@EventHandler(priority= EventPriority.HIGHEST)
	public void onBlockBreakEvent(BlockBreakEvent e) {
		if (Spectators.isPlayerSpectating(e.getPlayer().getUniqueId())) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler(priority= EventPriority.HIGHEST)
	public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent e) {
		if (Spectators.isPlayerSpectating(e.getPlayer().getUniqueId())) {
			e.setCancelled(true);
			e.getPlayer().sendMessage("You cannot chat in spectator mode.");
		}
	}
	
	@EventHandler(priority= EventPriority.HIGHEST)
	public void onPlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent e) {
		if (Spectators.isPlayerSpectating(e.getPlayer().getUniqueId())) {
			if (!e.getMessage().equalsIgnoreCase("spectate")) {
				e.setCancelled(true);
				e.getPlayer().sendMessage("You cannot use commands in spectator mode.");
			}
		}
	}
}
