package max.bettermagnets.client;

import java.util.Random;

import max.bettermagnets.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ParticleMagnet extends Particle {

	private ResourceLocation texture = new ResourceLocation(Reference.MODID, "textures/entities/magnet_particle.png");
	Random rand = new Random();
	public int life = 8;
	
	public ParticleMagnet(World worldIn, double posXIn, double posYIn, double posZIn) {
		super(worldIn, posXIn, posYIn, posZIn);
		this.particleAge = life;
		
		TextureAtlasSprite sprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(texture.toString());
		this.setParticleTexture(sprite);
	}
	
	@Override
	public int getFXLayer() {
		return 1;
	}
	
	@Override
	public void onUpdate() {
		if (this.particleAge-- <=0) {
			this.setExpired();
		}
	}

}
