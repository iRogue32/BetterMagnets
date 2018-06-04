package max.bettermagnets.config;

import max.bettermagnets.BetterMagnets;
import max.bettermagnets.proxy.CommonProxy;
import net.minecraftforge.common.config.Configuration;

public class ConfigBetterMagnets {
	
	public static double velocity;
	
	public static boolean requireEnergy;

	public static int energyCost;
	
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
        velocity = cfg.get("Magnet Config", "magnet pull velocity", 0.03, "Speed at which magnet should pull items toward player [Default: 0.03, Max: 1.00, Min: 0.00] ", 0.0, 1.0).getDouble();
        
        requireEnergy = cfg.get("RF Config", "magnet requires RF", false, "Set true if you want magnets to require RF to function [Default: false]").getBoolean();
        
        energyCost = cfg.get("RF Config", "energy cost", 50, "Amount of RF per tick to consume when moving an item towards the player [Default: 50]").getInt();
	}
}
