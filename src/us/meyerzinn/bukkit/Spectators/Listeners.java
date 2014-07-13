package us.meyerzinn.bukkit.Spectators;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Listeners implements Listener {

	public void onPlayerPickupItemEvent(PlayerPickupItemEvent e) {
		UUID uuid = e.getPlayer().getUniqueId();
		if (Spectators.isPlayerSpectating(uuid)) {
			e.setCancelled(true);
		}
	}
	
	public void onPlayerQuitEvent(PlayerQuitEvent e) {
		Spectators.restore(e.getPlayer());
	}
	
	public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player) {
			Player p = (Player) e.getDamager();
			if (Spectators.isPlayerSpectating(p.getUniqueId())) {
				e.setCancelled(true);
			}
		}
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if (Spectators.isPlayerSpectating(p.getUniqueId())) {
				e.setCancelled(true);
			}
		}
	}
}
