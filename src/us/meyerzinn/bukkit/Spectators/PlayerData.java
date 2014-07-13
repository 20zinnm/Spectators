package us.meyerzinn.bukkit.Spectators;

import java.util.Collection;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.Vector;

public class PlayerData {

	public Collection<PotionEffect> potionEffects;
	public boolean allowFlight;
	public Location compassTarget;
	public String displayName;
	public ItemStack[] enderItems;
	public float exaustion;
	public float exp;
	public float fallDistance;
	public int fireTicks;
	public float flySpeed;
	public int foodLevel;
	public int gameMode;
	public double health;
	public ItemStack[] invItems;
	public ItemStack[] invArmour;
	public int level;
	public Location location;
	public long playerTimeOffset;
	public String playerListName;
	public int remainingAir;
	public float saturation;
	public int ticksLived;
	public Vector velocity;
	public float walkSpeed;
	public boolean flying;
	public boolean playerTimeRelative;
	public boolean sleepingIgnored;
	public boolean sneaking;
	public boolean sprinting;

	public PlayerData() {
		
	}
	
}
