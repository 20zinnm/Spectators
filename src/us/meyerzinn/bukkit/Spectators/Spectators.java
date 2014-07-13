package us.meyerzinn.bukkit.Spectators;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;

public class Spectators extends JavaPlugin {

	protected static Map<UUID, PlayerData> spectators = new HashMap<>();

	public static Boolean isPlayerSpectating(UUID uuid) {
		if (spectators.containsKey(uuid)) {
			return true;
		} else {
			return false;
		}
	}

	@SuppressWarnings("deprecation")
	public static void store(Player player) {
		PlayerData data = new PlayerData();

		data.potionEffects = player.getActivePotionEffects();
		data.allowFlight = player.getAllowFlight();
		data.compassTarget = player.getCompassTarget();
		data.displayName = player.getDisplayName();
		data.enderItems = player.getEnderChest().getContents();
		data.exaustion = player.getExhaustion();
		data.exp = player.getExp();
		data.fallDistance = player.getFallDistance();
		data.fireTicks = player.getFireTicks();
		data.flySpeed = player.getFlySpeed();
		data.foodLevel = player.getFoodLevel();
		data.gameMode = player.getGameMode().getValue();
		data.health = player.getHealth();
		data.invItems = player.getInventory().getContents();
		data.invArmour = player.getInventory().getArmorContents();
		data.level = player.getLevel();
		data.location = player.getLocation().clone();
		data.playerListName = player.getPlayerListName();
		data.playerTimeOffset = player.getPlayerTimeOffset();
		data.remainingAir = player.getRemainingAir();
		data.saturation = player.getSaturation();
		data.ticksLived = player.getTicksLived();
		data.velocity = player.getVelocity();
		data.walkSpeed = player.getWalkSpeed();
		data.flying = player.isFlying();
		data.playerTimeRelative = player.isPlayerTimeRelative();
		data.sleepingIgnored = player.isSleepingIgnored();
		data.sneaking = player.isSneaking();
		data.sprinting = player.isSprinting();

		spectators.put(player.getUniqueId(), data);
		player.getInventory().clear();
		player.setFlying(true);
		player.setDisplayName(ChatColor.RED + "[Spectator]" + player.getDisplayName());
	}

	@SuppressWarnings("deprecation")
	public static void restore(Player player) {
		PlayerData data = spectators.get(player.getUniqueId());
		player.teleport(data.location);

		for (PotionEffect effect : player.getActivePotionEffects()) {
			player.addPotionEffect(new PotionEffect(effect.getType(), 0, 0),
					true);
		}

		player.addPotionEffects(data.potionEffects);
		player.setAllowFlight(data.allowFlight);

		player.setCompassTarget(data.compassTarget);
		player.setDisplayName(data.displayName);
		player.getEnderChest().setContents(data.enderItems);
		player.setExhaustion(data.exaustion);
		player.setExp(data.exp);
		player.setFallDistance(data.fallDistance);
		player.setFireTicks(data.fireTicks);
		player.setFlySpeed(data.flySpeed);
		player.setFoodLevel(data.foodLevel);

		GameMode gamemode = GameMode.getByValue(data.gameMode);
		if (player.getGameMode() != gamemode) {
			player.setGameMode(gamemode);
		}

		player.setHealth(data.health);
		player.getInventory().setContents(data.invItems);
		player.getInventory().setArmorContents(data.invArmour);
		player.setLevel(data.level);
		player.setPlayerListName(data.playerListName);

		if (!data.playerTimeRelative)
			player.setPlayerTime(data.playerTimeOffset, true);
		else {
			player.resetPlayerTime();
		}

		player.setRemainingAir(data.remainingAir);
		player.setSaturation(data.saturation);
		player.setTicksLived(data.ticksLived);
		player.setVelocity(data.velocity);
		player.setWalkSpeed(data.walkSpeed);
		player.setFlying(data.flying);
		player.setSleepingIgnored(data.sleepingIgnored);
		player.setSneaking(data.sneaking);
		player.setSprinting(data.sprinting);
		
		spectators.remove(player.getUniqueId());
	}

	private void spectateModeOn(Player p) {
		if (spectators.containsKey(p.getUniqueId())) {
			p.sendMessage("You are already in spectate mode! Do /spectate off to return to normal mode.");
			return;
		}
		store(p);
	}

	private void spectateModeOff(Player p) {
		if (!spectators.containsKey(p.getUniqueId())) {
			p.sendMessage("You are not in spectate mode! Do /spectate on to enter spectate mode.");
		}
		restore(p);
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {

		if (args.length != 1) {
			return false;
		}

		if (!(sender instanceof Player)) {
			sender.sendMessage("Only players can use this command!");
			return true;
		}

		Player p = (Player) sender;

		if (cmd.getName().equalsIgnoreCase("spectate")) {
			if (args[0] == "off") {
				if (!spectators.containsKey(p.getUniqueId())) {
					p.sendMessage("You are not in spectator mode!");
					return true;
				}
				spectateModeOff(p);
				return true;
			}
			if (args[0] == "on") {
				if (spectators.containsKey(p.getUniqueId())) {
					p.sendMessage("You are already in spectator mode!");
				}
				spectateModeOn(p);
			}
			return false;
		}
		return false;
	}

	public void onEnable() {

		getServer().getPluginManager().registerEvents(new Listeners(), this);

	}

	public void onDisable() {

		for (Player p : Bukkit.getOnlinePlayers()) {
			restore(p);
		}
		
	}

}
