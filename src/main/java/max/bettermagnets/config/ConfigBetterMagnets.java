package max.bettermagnets.config;

import max.bettermagnets.BetterMagnets;
import max.bettermagnets.proxy.CommonProxy;
import net.minecraftforge.common.config.Configuration;

public class ConfigBetterMagnets {
	
	public static double velocity;
	public static boolean requireEnergy;
	public static int energyCost;
	public static int tier1EnergyStorage;
	public static int tier2EnergyStorage;
	public static int tier3EnergyStorage;
	public static int tier1EnergyTransfer;
	public static int tier2EnergyTransfer;
	public static int tier3EnergyTransfer;
	
	private static final String RF_CONFIG_CATEGORY = "RF Config";
	private static final String GENERAL_CONFIG_CATEGORY = "General Config";
	
	public static void readConfig() {
        Configuration cfg = CommonProxy.config;
        try {
            cfg.load();
            initGeneralConfig(cfg);
        } catch (Exception e1) {
        } finally {
            if (cfg.hasChanged()) {
                cfg.save();
            }
        }
    }
	
	private static void initGeneralConfig(Configuration cfg) {
        velocity = cfg.get(GENERAL_CONFIG_CATEGORY, "Magnet Pull Velocity", 0.03, "Speed at which magnet should pull items toward player [Default: 0.03, Max: 1.00, Min: 0.00] ", 0.0, 1.0).getDouble();
        requireEnergy = cfg.get(RF_CONFIG_CATEGORY, "Magnet Requires RF", false, "Set true if you want magnets to require RF to function [Default: false]").getBoolean();
        energyCost = cfg.get(RF_CONFIG_CATEGORY, "Magnet Energy Cost", 50, "Amount of RF per tick to consume when moving an item towards the player [Default: 50]").getInt();
        tier1EnergyStorage = cfg.get(RF_CONFIG_CATEGORY, "Tier 1 Capacity", 100000, "Amount of RF the Tier 1 magnet can store [Default: 100000]").getInt();
        tier2EnergyStorage = cfg.get(RF_CONFIG_CATEGORY, "Tier 2 Capacity", 1000000, "Amount of RF the Tier 2 magnet can store [Default: 1000000]").getInt();
        tier3EnergyStorage = cfg.get(RF_CONFIG_CATEGORY, "Tier 3 Capacity", 10000000, "Amount of RF the Tier 3 magnet can store [Default: 10000000]").getInt();
        tier1EnergyTransfer = cfg.get(RF_CONFIG_CATEGORY, "Tier 1 Transfer", 100, "Amount of RF the Tier 1 magnet can recieve per tick [Default: 100]").getInt();
        tier2EnergyTransfer = cfg.get(RF_CONFIG_CATEGORY, "Tier 2 Transfer", 1000, "Amount of RF the Tier 2 magnet can recieve per tick [Default: 1000]").getInt();
        tier3EnergyTransfer = cfg.get(RF_CONFIG_CATEGORY, "Tier 3 Transfer", 10000, "Amount of RF the Tier 3 magnet can recieve per tick [Default: 10000]").getInt();
	}
}
