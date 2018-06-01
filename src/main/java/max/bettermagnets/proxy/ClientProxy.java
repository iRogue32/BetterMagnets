package max.bettermagnets.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleCrit;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {

	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
	}
	
	public void Init(FMLInitializationEvent event) {
		super.Init(event);
	}
	
	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);
	}
	
	@Override
	public void generateMagnetParticles(World world, EntityItem item) {
		//ParticleCrit particle = new ParticleCrit(world, item.posX, item.posY, item.posZ, 0, 0, 0);
		//particle.setRBGColorF(244, 66, 66);
		world.spawnParticle(EnumParticleTypes.CRIT, item.posX, item.posY, item.posZ, 0, 0, 0);
	}
	
}
