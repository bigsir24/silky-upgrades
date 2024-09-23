package bigsir.silkyupgrades.models;

import bigsir.silkyupgrades.SilkyUpgrades;
import net.minecraft.client.render.item.model.ItemModelStandard;
import net.minecraft.client.render.stitcher.IconCoordinate;
import net.minecraft.client.render.stitcher.TextureRegistry;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.client.util.debug.Debug;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.tool.ItemToolAxe;
import net.minecraft.core.item.tool.ItemToolHoe;
import net.minecraft.core.item.tool.ItemToolPickaxe;
import net.minecraft.core.item.tool.ItemToolShovel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class ItemModelSilky extends ItemModelStandard {
	public ItemModelSilky(Item item, String namespace) {
		super(item, namespace);
	}

	@Override
	public @NotNull IconCoordinate getIcon(@Nullable Entity entity, ItemStack itemStack) {
		return UpgradeIconRegistry.getIcon(itemStack);
	}

	@Override
	public void renderTexturedQuad(Tessellator tessellator, int x, int y, IconCoordinate icon) {
		super.renderTexturedQuad(tessellator, x, y, icon);
	}
}
