package max.bettermagnets.config;

import max.bettermagnets.BetterMagnets;
import max.bettermagnets.proxy.CommonProxy;
import net.minecraftforge.common.config.Configuration;

public class ConfigBetterMagnets {
	
	public static double velocity;
	
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
        velocity = cfg.get("magnet config", "magnet pull velocity", 0.03, "", 0.0, 1.0).getDouble();
        System.out.println("velocit: " + velocity);
	}
}
