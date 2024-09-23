package bigsir.silkyupgrades.models;

import bigsir.silkyupgrades.SilkyUpgrades;
import bigsir.silkyupgrades.upgrades.ToolUpgrade;
import net.minecraft.client.render.stitcher.IconCoordinate;
import net.minecraft.client.render.stitcher.TextureRegistry;
import net.minecraft.client.util.debug.Debug;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.tool.*;

import java.util.HashMap;
import java.util.Map;

public class UpgradeIconRegistry {
	private static final Map<String, IconCoordinate> keyToIconMap = new HashMap<>();

	public static IconCoordinate getIcon(ItemStack itemStack){
		String upgradeKey = ToolUpgrade.getItemUpgrade(itemStack).getKey();
		return keyToIconMap.get(upgradeKey + (ToolUpgrade.isInGenericMap(itemStack.getItem()) ? "." + itemStack.getItemKey().split("\\.")[2] : ""));
	}



	static{
		keyToIconMap.put(ToolUpgrade.gilded.getKey() + ".shovel", TextureRegistry.getTexture(SilkyUpgrades.MOD_ID + ":item/gilded_shovel"));
		keyToIconMap.put(ToolUpgrade.gilded.getKey() + ".pickaxe", TextureRegistry.getTexture(SilkyUpgrades.MOD_ID + ":item/gilded_pickaxe"));
		keyToIconMap.put(ToolUpgrade.gilded.getKey() + ".axe", TextureRegistry.getTexture(SilkyUpgrades.MOD_ID + ":item/gilded_axe"));
		keyToIconMap.put(ToolUpgrade.gilded.getKey() + ".hoe", TextureRegistry.getTexture(SilkyUpgrades.MOD_ID + ":item/gilded_hoe"));
		keyToIconMap.put(ToolUpgrade.gilded.getKey() + ".sword", TextureRegistry.getTexture(SilkyUpgrades.MOD_ID + ":item/gilded_sword"));
		keyToIconMap.put(ToolUpgrade.gilded.getKey(), TextureRegistry.getTexture(SilkyUpgrades.MOD_ID + ":item/gilded_unassigned"));
	}
}
