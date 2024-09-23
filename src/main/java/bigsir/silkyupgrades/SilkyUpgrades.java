package bigsir.silkyupgrades;

import bigsir.silkyupgrades.commands.UpgradeCommand;
import bigsir.silkyupgrades.interfaces.IUpgradable;
import bigsir.silkyupgrades.recipes.RecipeEntryUpgrade;
import bigsir.silkyupgrades.upgrades.ToolUpgrade;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.stitcher.TextureRegistry;
import net.minecraft.core.block.Block;
import net.minecraft.core.data.registry.recipe.RecipeGroup;
import net.minecraft.core.data.registry.recipe.RecipeNamespace;
import net.minecraft.core.data.registry.recipe.RecipeSymbol;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import bigsir.silkyupgrades.models.ItemModelSilky;
import turniplabs.halplibe.HalpLibe;
import turniplabs.halplibe.helper.CommandHelper;
import turniplabs.halplibe.helper.RecipeBuilder;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.RecipeEntrypoint;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;


public class SilkyUpgrades implements ModInitializer, GameStartEntrypoint, RecipeEntrypoint {
    public static final String MOD_ID = "silkyupgrades";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static ItemModelSilky itemModelSilky;
    @Override
    public void onInitialize() {
        LOGGER.info("Silky Upgrades initialized.");
    }

	@Override
	public void beforeGameStart() {
		CommandHelper.createCommand(new UpgradeCommand());
	}

	@Override
	public void afterGameStart() {
		itemModelSilky = new ItemModelSilky(Item.stick, MOD_ID);

		TextureRegistry.getTexture(MOD_ID + ":item/gilded_shovel");
		TextureRegistry.getTexture(MOD_ID + ":item/gilded_pickaxe");
		TextureRegistry.getTexture(MOD_ID + ":item/gilded_axe");
		TextureRegistry.getTexture(MOD_ID + ":item/gilded_sword");
		TextureRegistry.getTexture(MOD_ID + ":item/gilded_unassigned");

        try {
			TextureRegistry.initializeAllFiles(MOD_ID, TextureRegistry.itemAtlas, true);
		} catch (URISyntaxException | IOException e) {
			throw new RuntimeException("Failed to initialize textures", e);
		}
		Minecraft.getMinecraft(Minecraft.class).renderEngine.refreshTexturesAndDisplayErrors();
	}

	@Override
	public void onRecipesReady() {
		RecipeNamespace namespace = RecipeBuilder.getRecipeNamespace(MOD_ID);
		RecipeEntryUpgrade upgrade = new RecipeEntryUpgrade(ToolUpgrade.gilded);

		RecipeGroup<RecipeEntryUpgrade> group = new RecipeGroup<>(new RecipeSymbol(Block.workbench.getDefaultStack()));
		group.register("ironaxe.upgrade", upgrade);
		namespace.register("test", group);
	}

	@Override
	public void initNamespaces() {
	}

	public static void setItemUpgradeDamage(ItemStack itemStack, int damage){
		itemStack.getData().getCompound("SilkyUpgrades").putInt("UpgradeDamage", damage);
		((IUpgradable)(Object)itemStack).silky_upgrades$setUpgradeDamage(damage);
	}

	public static int getItemUpgradeDamage(ItemStack itemStack){
		return ((IUpgradable)(Object)itemStack).silky_upgrades$getUpgradeDamage();
	}
}
