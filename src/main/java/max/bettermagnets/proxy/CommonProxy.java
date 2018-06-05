package max.bettermagnets.proxy;

import java.io.File;

import max.bettermagnets.config.ConfigBetterMagnets;
import max.bettermagnets.items.ModItemRegistry;
import max.bettermagnets.network.PacketHandler;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {

	public static Configuration config;
	
	public void preInit(FMLPreInitializationEvent event) {
		File directory = event.getModConfigurationDirectory();
		config = new Configuration(new File(directory.getPath(), "BetterMagnets.cfg"));
        ConfigBetterMagnets.readConfig();
		
		ModItemRegistry.initItems();
		MinecraftForge.EVENT_BUS.register(new ModItemRegistry());
        PacketHandler.init();
	}
	
	public void Init(FMLInitializationEvent event) {
		
	}
	
	public void postInit(FMLPostInitializationEvent event) {
		if (config.hasChanged()) {
            config.save();
        }
	}
	
	public void generateMagnetParticles(World world, EntityItem item) {
		
	}
	
}
