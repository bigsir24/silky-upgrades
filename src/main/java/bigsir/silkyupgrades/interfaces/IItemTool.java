package bigsir.silkyupgrades.interfaces;

import net.minecraft.core.item.material.ToolMaterial;

public interface IItemTool {
	default ToolMaterial silky_upgrades$getMaterial(){
		return null;
	}
}
