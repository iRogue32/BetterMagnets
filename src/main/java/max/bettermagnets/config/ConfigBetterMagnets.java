package max.bettermagnets.config;

import max.bettermagnets.BetterMagnets;
import max.bettermagnets.proxy.CommonProxy;
import net.minecraftforge.common.config.Configuration;

public class ConfigBetterMagnets {
	
	public static int tier1Range;
	public static int tier2Range;
	public static int tier3Range;
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
        tier1Range = cfg.get(GENERAL_CONFIG_CATEGORY, "Tier 1 Magnet Range", 5, "Range of the Tier 1 Magnet [Default: 5]").getInt();
        tier2Range = cfg.get(GENERAL_CONFIG_CATEGORY, "Tier 2 Magnet Range", 10, "Range of the Tier 2 Magnet [Default: 10]").getInt();
        tier3Range = cfg.get(GENERAL_CONFIG_CATEGORY, "Tier 3 Magnet Range", 15, "Range of the Tier 3 Magnet [Default: 15]").getInt();
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
